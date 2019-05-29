package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Class that represents request context.
 * 
 * @author David
 *
 */
public class RequestContext {

	/**
	 * Stream to which the data is written.
	 */
	private OutputStream outputStream;

	/**
	 * Charset.
	 */
	@SuppressWarnings("unused")
	private Charset charset;

	/**
	 * Encoding. Default encoding is UTF-8.
	 */
	private String encoding = "UTF-8";

	/**
	 * Status code. Default status code is 200.
	 */
	private int statusCode = 200;

	/**
	 * Text status. Default text status is 'OK'.
	 */
	private String statusText = "OK";

	/**
	 * Mime type. Default mime type is 'text/html'.
	 */
	private String mimeType = "text/html";

	/**
	 * Content length. By default, this value is null.
	 */
	private Long contentLength = null;

	/**
	 * Map to store parameters.
	 */
	private Map<String, String> parameters;

	/**
	 * Map to store temporary parameters.
	 */
	private Map<String, String> temporaryParameters;

	/**
	 * Map to store persistent parameters.
	 */
	private Map<String, String> persistentParameters;

	/**
	 * Cookies.
	 */
	private List<RCCookie> outputCookies;

	/**
	 * Flag to indicate that header is generated.
	 */
	private boolean headerGenerated = false;

	/**
	 * Read-only IDispatcher property.
	 */
	private IDispatcher dispatcher;

	/**
	 * Constructor to initialize private fields.
	 * 
	 * @param outputStream         Stream to which the data is written. must not be
	 *                             null.
	 * @param parameters           Map to store parameters. If null, will be treated
	 *                             as empty.
	 * @param persistentParameters Map to store persistent parameters. If null, will
	 *                             be treated as empty.
	 * @param outputCookies        Cookies. If null, will be treated as empty.
	 * @param temporaryParameters  Temporary parameters map.
	 * @param dispatcher           Reference to IDispatcher.
	 * @throws NullPointerException if outputStream is null.
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher) {
		this(outputStream, parameters, persistentParameters, outputCookies);
		this.dispatcher = dispatcher;
		this.temporaryParameters = temporaryParameters;
	}

	/**
	 * Constructor to initialize private fields.
	 * 
	 * @param outputStream         Stream to which the data is written. Must not be
	 *                             null.
	 * @param parameters           Map to store parameters. If null, will be treated
	 *                             as empty.
	 * @param persistentParameters Map to store persistent parameters. If null, will
	 *                             be treated as empty.
	 * @param outputCookies        Cookies. If null, will be treated as empty.
	 * @throws NullPointerException if outputStream is null.
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		Objects.requireNonNull(outputStream);

		if (parameters == null) {
			parameters = new HashMap<>();
		}

		if (persistentParameters == null) {
			persistentParameters = new HashMap<>();
		}

		if (outputCookies == null) {
			outputCookies = new ArrayList<>();
		}

		this.outputStream = outputStream;
		this.parameters = parameters;
		this.persistentParameters = persistentParameters;
		this.outputCookies = outputCookies;
		temporaryParameters = new HashMap<>();
		parameters = new HashMap<>();
	}

	/**
	 * Sets encoding.
	 * 
	 * @param encoding Encoding.
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * Sets output cookies.
	 * 
	 * @param cookies Cookies.
	 * @throws RuntimeException if this method is called after the header is
	 *                          generated.
	 */
	public void outputCookies(List<RCCookie> cookies) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		this.outputCookies = cookies;
	}

	/**
	 * Sets status code.
	 * 
	 * @param statusCode Status code.
	 * @throws RuntimeException if this method is called after the header is
	 *                          generated.
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		this.statusCode = statusCode;
	}

	/**
	 * Sets text status.
	 * 
	 * @param statusText Text status.
	 * @throws RuntimeException if this method is called after the header is
	 *                          generated.
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		this.statusText = statusText;
	}

	/**
	 * Sets mime type.
	 * 
	 * @param mimeType Mime type.
	 * @throws RuntimeException if this method is called after the header is
	 *                          generated.
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		this.mimeType = mimeType;
	}

	/**
	 * Sets content length.
	 * 
	 * @param contentLength Content length.
	 * @throws RuntimeException if this method is called after the header is
	 *                          generated.
	 */
	public void setContentLength(Long contentLength) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		this.contentLength = contentLength;
	}

	/**
	 * Adds new cookie.
	 * 
	 * @param cookie Cookies to be added.
	 * @throws NullPointerException if cookie is null.
	 */
	public void addRCCookie(RCCookie cookie) {
		outputCookies.add(Objects.requireNonNull(cookie));
	}

	/**
	 * Method that retrieves value from parameters map(or null if no association
	 * exists).
	 * 
	 * @param name Key that is associated with value that will be returned.
	 * @return Returns value from parameters map.
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in parameters map
	 * 
	 * @return Unique names of all parameters in parameters map.
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Method that retrieves value from persistentParameters map (or null if no
	 * association exists):
	 * 
	 * @param name Key that is associated with value that will be returned.
	 * @return Value from persistentParameters.
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in persistent parameters map
	 * 
	 * @return Unique names of all parameters in persistentParameters map.
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * Method that stores a value to persistentParameters map.
	 * 
	 * @param name  Key that is associated with value that will be returned.
	 * @param value Value to be associated with given name.
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * Method that removes a value from persistentParameters map.
	 * 
	 * @param name Key that is associated with value that will be removed.
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Method that retrieves value from temporaryParameters map.
	 * 
	 * @param name Key that is associated with value that will be returned.
	 * @return Value from temporaryParameters map.
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in temporary parameters map.
	 * 
	 * @return Unique names of all parameters in temporaryParameters map.
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * Method that that retrieves an identifier which is unique for current user
	 * session .
	 * 
	 * @return Session ID.
	 */
	public String getSessionID() {
		return "";
	}

	/**
	 * Method that stores a value to temporaryParameters map.
	 * 
	 * @param name  Key that will be associated with value.
	 * @param value Value that will be associated with given name.
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * Method that removes a value from temporaryParameters map.
	 * 
	 * @param name Name that is associated with the value which will be removed.
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Method to generate header. First line start with the version of HTTP and
	 * status code and text status. After that comes mime type. After mime type
	 * comes content length. If content length is null, then mime type, as well as
	 * content length will not be presented in header. After that line follows zero
	 * or more lines in which cookies are specified. Each line is separated with
	 * \r\n sequence.
	 * 
	 * @throws IOException If error will writing to outputStream occurs.
	 */
	private void generateHeader() throws IOException {
		headerGenerated = true;
		charset = Charset.forName(encoding);

		outputStream.write(
				new String("HTTP/1.1 " + statusCode + " " + statusText + "\r\n").getBytes(StandardCharsets.ISO_8859_1));

		if (mimeType.startsWith("text/")) {
			mimeType += "; charset=" + encoding;
		}
		
		outputStream.write(new String("Content-Type: " + mimeType + "\r\n").getBytes(StandardCharsets.ISO_8859_1));

		if (contentLength != null) {
			outputStream.write(new String("Content-Length: " + contentLength.toString() + "\r\n")
					.getBytes(StandardCharsets.ISO_8859_1));
		}
		 
		if (!outputCookies.isEmpty()) {
			outputCookies.forEach(t -> {
				try {
					outputStream.write(
							new String("Set-Cookie: " + t.toString() + "\r\n").getBytes(StandardCharsets.ISO_8859_1));
				} catch (IOException e) {
				}

			});
		}

		outputStream.write(new String("\r\n").getBytes(StandardCharsets.ISO_8859_1));
	}

	/**
	 * Writes given bytes to output stream, and construct header if not already.
	 * 
	 * @param data Data to be written to outputStream.
	 * @return RequestContext.
	 * @throws IOException If writing to outputStream occurs.
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}

		outputStream.write(data);
		return this;
	}

	/**
	 * Writes given bytes to output stream with given offset and length, and
	 * constructs header if not already.
	 * 
	 * @param data   Given byes to be written to output stream.
	 * @param offset Starting position in which bytes are written.
	 * @param len    Length to be written.
	 * @return RequestContext.
	 * @throws IOException If writing to outputStream occurs.
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}
		outputStream.write(data, offset, len);
		return this;
	}

	/**
	 * Writes given String to output stream.
	 * 
	 * @param text String to be written to output stream.
	 * @return RequestContext.
	 * @throws IOException          If writing to output stream occurs.
	 * @throws NullPointerException If text is null.
	 */
	public RequestContext write(String text) throws IOException {
		Objects.requireNonNull(text);

		return write(text.getBytes());
	}

	/**
	 * Class that represents cookies.
	 * 
	 * @author David
	 *
	 */
	public static class RCCookie {
		/**
		 * Name.
		 */
		private String name;

		/**
		 * Value that is associated with the name.
		 */
		private String value;

		/**
		 * Ip address.
		 */
		private String domain;

		/**
		 * Path.
		 */
		private String path;

		/**
		 * Max age.
		 */
		private Integer maxAge;

		/**
		 * Constructor to initialize name, value, maxAge, domain and paath.
		 * 
		 * @param name   Name.
		 * @param value  Value that is associated with the name.
		 * @param maxAge Max Age.
		 * @param domain Ip address.
		 * @param path   Path.
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * Returns name.
		 * 
		 * @return Name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Returns value that is associated with the name.
		 * 
		 * @return Value.
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Returns domain.
		 * 
		 * @return Domain.
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Returns path.
		 * 
		 * @return Path.
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Returns max age.
		 * 
		 * @return max age.
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

		/**
		 * Convert all private fields to String.
		 */
		public String toString() {
			StringBuilder sb = new StringBuilder();

			sb.append(name).append("=").append("\"").append(value).append("\"");

			if (domain != null) {
				sb.append("; Domain=").append(domain);
			}

			if (path != null) {
				sb.append("; ").append("Path=").append(path);
			}

			if (maxAge != null) {
				sb.append("; ").append("Max-Age=").append(maxAge);
			}

			return sb.toString();
		}

	}
}
