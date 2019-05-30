package hr.fer.zemris.java.webserver;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;
import hr.fer.zemris.java.webserver.workers.CircleWorker;
import hr.fer.zemris.java.webserver.workers.EchoParams;
import hr.fer.zemris.java.webserver.workers.HelloWorker;

/**
 * Program that runs simple multithreading HTTP server. It reads server
 * configuration from file in config directory. In that file is specified
 * address this server listens, domainName, port, numberOfThread this server
 * runs on, session timeout and path to mimeType config file.
 * 
 * @author David
 *
 */
public class SmartHttpServer {
	/**
	 * Address this server listen.
	 */
	private String address;

	/**
	 * Domain name.
	 */
	private String domainName;

	/**
	 * Port this server listen.
	 */
	private int port;

	/**
	 * Number of thread this server runs on.
	 */
	private int workerThreads;

	/**
	 * Session timeout.
	 */
	private int sessionTimeout;

	/**
	 * Map that associates file extensions with mime types.
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();

	/**
	 * Thread that runs server.
	 */
	private ServerThread serverThread;

	/**
	 * Executor service. This service creates newFixedThreadPool.
	 */
	private ExecutorService threadPool;

	/**
	 * Document root.
	 */
	private Path documentRoot;

	/**
	 * Path to mime.properties file.
	 */
	private String mimeConfig;

	/**
	 * Path to workers.properties file.
	 */
	private String workersConfig;

	/**
	 * Stores informations about the workers.
	 */
	private Map<String, IWebWorker> workersMap;

	/**
	 * Constructor which expects only one argument. Path to server.properties file.
	 * Then it reads all necessary information about the server, including the path
	 * to mime.properties and workers.properties file.
	 * 
	 * @param configFileName Argument explained above.
	 */
	public SmartHttpServer(String configFileName) {
		readServerProperties(configFileName);
		readMimeTypes(mimeConfig);
		readWorkersProperties(workersConfig);
	}

	/**
	 * Method that reads all informations from workers.properties file.
	 * 
	 * @param path Path to workers.properties file.
	 */
	private void readWorkersProperties(String pathToFile) {
		Path file = Paths.get(pathToFile);

		List<String> lines = null;
		try {
			lines = Files.readAllLines(file);
		} catch (IOException e) {
			System.err.println("Cannot read properties file");
			System.exit(1);
		}

		workersMap = new HashMap<>();
		for (String s : lines) {
			insertInMap(s);
		}
	}

	@SuppressWarnings("deprecation")
	private void insertInMap(String s) {
		int index = s.indexOf('=');

		if (index < 0 || index >= s.length()) {
			System.err.println("Error");
			System.exit(1);
		}

		String path = s.substring(0, index).trim();
		String fqcn = s.substring(index + 1).trim();

		if (workersMap.containsKey(path)) {
			throw new IllegalStateException();
		}

		Class<?> referenceToClass = null;
		Object newObject = null;

		try {
			referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
			newObject = referenceToClass.newInstance();
		} catch (Exception e) {
			System.err.println("Error");
			System.exit(1);
		}

		IWebWorker iww = (IWebWorker) newObject;
		workersMap.put(path, iww);
	}

	/**
	 * Method that reads all informations from server.properties file.
	 * 
	 * @param path Path to server.properties file.
	 */
	private void readServerProperties(String path) {
		Path file = null;
		try {
			file = Paths.get(path);
		} catch (InvalidPathException e) {
			System.err.println("Invalid path.");
			System.exit(1);
		}

		List<String> lines = null;
		try {
			lines = Files.readAllLines(file);
		} catch (IOException e) {
			System.err.println("Cannot read properties file");
			System.exit(1);
		}

		for (String line : lines) {
			if (line.startsWith("#")) {
				continue;
			}

			int index = line.indexOf('=');
			String key = line.substring(0, index).trim();
			String value = line.substring(index + 1).trim();

			initializeVariables(key, value);
		}
	}

	/**
	 * Initializes variables.
	 * 
	 * @param key   Key in properties file.
	 * @param value Value in properties file.
	 */
	private void initializeVariables(String key, String value) {
		if (key.endsWith("address")) {
			address = value;
		} else if (key.endsWith("domainName")) {
			domainName = value;
		} else if (key.endsWith("port")) {
			port = Integer.parseInt(value);
		} else if (key.endsWith("workerThreads")) {
			workerThreads = Integer.parseInt(value);
		} else if (key.endsWith("documentRoot")) {
			documentRoot = Paths.get(value);
		} else if (key.endsWith("mimeConfig")) {
			mimeConfig = value;
		} else if (key.endsWith("timeout")) {
			sessionTimeout = Integer.parseInt(value);
		} else if (key.endsWith("workers")) {
			workersConfig = value;
		}
	}

	/**
	 * Method that reads all informations from server.properties file.
	 * 
	 * @param path Path to mime.properties file.
	 */
	private void readMimeTypes(String path) {
		Path file = Paths.get(path);

		List<String> lines = null;
		try {
			lines = Files.readAllLines(file);
		} catch (IOException e) {
			System.err.println("Cannot read properties file");
			System.exit(1);
		}

		mimeTypes = new HashMap<>();
		for (String line : lines) {
			int index = line.indexOf('=');
			String key = line.substring(0, index).trim();
			String value = line.substring(index + 1).trim();

			mimeTypes.put(key, value);
		}
	}

	/**
	 * Method that starts serverThread if this thread is not already started. Also,
	 * it initializes thread pool with 'workerThreads' threads.
	 */
	protected synchronized void start() {
		if (serverThread == null) {
			serverThread = new ServerThread();

			serverThread.start();
		}

		threadPool = Executors.newFixedThreadPool(workerThreads);
	}

	/**
	 * Method used to stop the serverThread and to shutdown threadPool.
	 */
	protected synchronized void stop() {
		serverThread.interrupt();
		threadPool.shutdown();
	}

	/**
	 * 
	 * Implementation of Thread class. This is type of Thread that runs on server.
	 * It runs in one while loop infinitely.
	 * 
	 * @author David
	 *
	 */
	protected class ServerThread extends Thread {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			try (ServerSocket serverSocket = new ServerSocket(port)) {
				while (true) {
					Socket client = null;
					try {
						client = serverSocket.accept();
					} catch (IOException e) {
					}

					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * Thread that represents client.
	 * 
	 * @author David
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {

		/**
		 * Socket.
		 */
		private Socket csocket;

		/**
		 * InputStream from which this worker retrieves data.
		 */
		private PushbackInputStream istream;

		/**
		 * OutputStream.
		 */
		private OutputStream ostream;

		/**
		 * Version of the HTTP. It can be 1.0 or 1.1
		 */
		private String version;

		/**
		 * Method.
		 */
		private String method;

		/**
		 * Host address.
		 */
		private String host;

		/**
		 * Parameters.
		 */
		private Map<String, String> params = new HashMap<String, String>();

		/**
		 * Temporary parameters.
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();

		/**
		 * Parameters.
		 */
		private Map<String, String> permPrams = new HashMap<String, String>();

		/**
		 * Cookies.
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();

		/**
		 * SID.
		 */
		private String SID;

		private RequestContext context = null;

		/**
		 * Constructor to initialize socket.
		 * 
		 * @param csocket Socket.
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = new BufferedOutputStream(csocket.getOutputStream());
				byte[] requestBytes = readRequest(istream);

				if (requestBytes == null) {
					sendError(ostream, 400, "Bad request");
					return;
				}

				String requestStr = new String(requestBytes, StandardCharsets.US_ASCII);

				List<String> request = extractHeaders(requestStr);

				String[] firstLine = request.isEmpty() ? null : request.get(0).split(" ");

				if (firstLine == null || firstLine.length != 3) {
					sendError(ostream, 400, "Bad request");
					return;
				}

				method = firstLine[0].toUpperCase();
				if (!method.equals("GET")) {
					sendError(ostream, 405, "Method Not Allowed");
					return;
				}

				String requestedPath = firstLine[1];

				version = firstLine[2].toUpperCase();

				if (!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
					sendError(ostream, 505, "HTTP Version Not Supported");
					return;
				}

				for (String header : request) {
					if (header.matches("Host: (.*)")) {
						host = header.substring(6).trim();

						if (host.matches("(.*):\\d+")) {
							host = host.substring(0, host.indexOf(':')).trim();
						}
					}
				}

				if (host == null) {
					host = domainName;
				}

				String path = getPath(requestedPath);
				String paramString = getParamString(requestedPath);

				parseParameters(paramString);

				Path reqPath = documentRoot.resolve(path.substring(1)).toAbsolutePath();

				if (!reqPath.startsWith(documentRoot.toAbsolutePath().toString())) {
					sendError(ostream, 403, "Forbidden");
					return;
				}

				internalDispatchRequest(reqPath.toString(), true);

				ostream.flush();

				ostream.close();
			} catch (Exception e) {
			}
		}

		private String getPath(String requestedPath) {
			int indexOfQuestion = requestedPath.lastIndexOf('?');

			if (indexOfQuestion == -1) {
				indexOfQuestion = requestedPath.length();
			}

			String path = requestedPath.substring(requestedPath.indexOf('/'), indexOfQuestion);

			return path;
		}

		private String getParamString(String requestedPath) {
			int indexOfQuestion = requestedPath.lastIndexOf('?');

			if (indexOfQuestion == -1) {
				indexOfQuestion = requestedPath.length();
			}

			String paramString = null;
			if (indexOfQuestion == requestedPath.length()) {
				paramString = "";
			} else {
				paramString = requestedPath.substring(indexOfQuestion + 1).trim();
			}

			return paramString;
		}

		private void startEngine(String file) throws IOException {
			String documentBody = readFromDisk(file);

			List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();

			if (context == null) {
				context = new RequestContext(ostream, params, permPrams, cookies, new HashMap<>(), this);
			}

			context.setStatusCode(200);

			new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), context).execute();

			ostream.flush();
			ostream.close();
		}

		/**
		 * Method used to read content of file and convert it to one String.
		 * 
		 * @param path Path of file.
		 * @return String that is obtained by concatenating all lines of input file.
		 */
		private String readFromDisk(String path) {
			Path file = Paths.get(path);

			List<String> lines = null;
			try {
				lines = Files.readAllLines(file);
			} catch (IOException e) {
				System.err.println("Cannot read from file");
				System.exit(1);
			}

			StringBuilder sb = new StringBuilder();

			for (String s : lines) {
				sb.append(s).append("\n");
			}

			return sb.toString();
		}

		private void parseParameters(String paramString) {
			if (paramString.equals("")) {
				return;
			}
			String[] groups = paramString.split("&");

			for (String s : groups) {
				int index = s.indexOf('=');
				String key = s.substring(0, index);
				String value = s.substring(index + 1);
				params.put(key, value);
			}
		}

		/**
		 * Method used to extract headers. It takes one string as an argument. This
		 * string represents request header. This method splits this string into List of
		 * Strings and returns that as a result.
		 * 
		 * @param requestHeader Program argument. Explained above.
		 * @return List of strings.
		 */
		private List<String> extractHeaders(String requestHeader) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for (String s : requestHeader.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}

			return headers;
		}

		/**
		 * Method used to read request from inputStream.
		 * 
		 * @param is Given inputStream.
		 * @return Byte array representing read request.
		 * @throws IOException If any error while working with Input Stream occurs.
		 */
		private byte[] readRequest(InputStream is) throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = is.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			return bos.toByteArray();
		}

		/**
		 * Method used to send error to user. It writes error message to the OuputStream
		 * that is given as a program argument. Also, it gets information about the
		 * status code and text status. For example, most commonly used status code if
		 * 404.
		 * 
		 * @param cos        OutputStream.
		 * @param statusCode status code.
		 * @param statusText text status-
		 * @throws IOException If cannot write to given output stream.
		 */
		private void sendError(OutputStream cos, int statusCode, String statusText) throws IOException {
			cos.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			cos.flush();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		private IWebWorker loadClass(String className) {
			switch (className) {
			case "EchoParams":
				return new EchoParams();
			case "HelloWorker":
				return new HelloWorker();
			case "CircleWorker":
				return new CircleWorker();
			}
			return null;
		}

		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			String extension = null;

			if (context == null) {
				context = new RequestContext(ostream, params, permPrams, outputCookies);
			}

			int index = urlPath.lastIndexOf('.');
			if (index == -1) {
				extension = "";
			} else {
				extension = urlPath.substring(index + 1);
			}

			int startIndex = documentRoot.toAbsolutePath().toString().length() + 1;
			String path = "/" + urlPath.substring(startIndex);

			if (path.startsWith("/ext")) {
				if (path.length() < 6) {
					sendError(ostream, 404, "Cannot open file");
					return;
				}
				String className = path.substring(5);

				IWebWorker worker = loadClass(className);

				if (worker == null) {
					sendError(ostream, 404, "Cannot open file");
					return;
				}

				worker.processRequest(context);
				return;
			}

			if (workersMap.containsKey(path)) {
				IWebWorker worker = workersMap.get(path);
				worker.processRequest(context);
				return;
			}

			if (extension.equals("smscr")) {
				startEngine(urlPath);
				return;
			}

			if (!Files.exists(Paths.get(urlPath)) || !Files.isReadable(Paths.get(urlPath))) {
				sendError(ostream, 404, "Cannot open file");
				return;
			}

			String mimeType = mimeTypes.get(extension);
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			context.setMimeType(mimeType);
			context.setStatusCode(200);

			byte[] data = Files.readAllBytes(Paths.get(urlPath));
			context.write(data);
		}
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("I expected exactly one argument: path to server.properties file.");
			return;
		}

		SmartHttpServer server = new SmartHttpServer(args[0]);

		server.start();
	}
}
