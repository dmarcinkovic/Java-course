package hr.fer.zemris.java.p12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener used to listen when the server is started. When server is started,
 * in session map with key equal to 'time' will be remembered current time. This
 * information is then used to display the time this server is running.
 * 
 * @author David
 *
 */
@WebListener
public class WebApplicationListener implements ServletContextListener {
	private Map<String, String> map;
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("App info hw14 started");
		
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		String fileName = sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties");
		Path path = Paths.get(fileName);

		if (!Files.exists(path)) {
			throw new IllegalStateException("Missing dbsettings.properties file");
		}

		List<String> lines = null;
		try {
			lines = Files.readAllLines(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (lines.size() != 5) {
			throw new IllegalStateException("Missing properties in dbsettings.properties file");
		}

		initialize(lines);

		String connectionURL = "jdbc:derby://" + map.get("host") + ":" + map.get("port") + "/" + map.get("name");

		java.util.Properties dbProperties = new java.util.Properties();
		dbProperties.setProperty("user", map.get("user"));
		dbProperties.setProperty("password", map.get("password"));
		java.sql.Connection dbConnection = null;
		try {
			dbConnection = DriverManager.getConnection(connectionURL, dbProperties);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		createPollTable(dbConnection);
		createPollOptionsTable(dbConnection);
		
		addToPollIfEmpty(dbConnection);
		addToPollOptionsIfEmpty(dbConnection);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
	
	private void addToPollOptionsIfEmpty(java.sql.Connection dbConnection) {
		PreparedStatement pst = null;
		try {
			pst = dbConnection.prepareStatement("select * from polloptions");
			ResultSet rset = pst.executeQuery();
			
			if (!rset.next()) {
				addRowsToPollOptions(dbConnection);
			}
		} catch (SQLException e) {
		}
		
	}

	private void addToPollIfEmpty(java.sql.Connection dbConnection) {
		PreparedStatement pst = null;
		ResultSet rset = null;
		try {
			pst = dbConnection.prepareStatement("select * from polls");
			rset = pst.executeQuery();
			
			if (!rset.next()) {
				addRowsToPolls(dbConnection);
			}
		} catch (SQLException e) {
		}finally {
			try {
				rset.close();
			} catch (SQLException e) {
			}
		}
	}

	private void createPollOptionsTable(java.sql.Connection c) {
		try {
			PreparedStatement pst = c.prepareStatement(
					"CREATE TABLE PollOptions\r\n" + " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n"
							+ " optionTitle VARCHAR(100) NOT NULL,\r\n" + " optionLink VARCHAR(150) NOT NULL,\r\n"
							+ " pollID BIGINT,\r\n" + " votesCount BIGINT,\r\n"
							+ " FOREIGN KEY (pollID) REFERENCES Polls(id)\r\n" + ")");
			pst.executeUpdate();
			
			addRowsToPollOptions(c);
		} catch (SQLException e) {
		}

	}

	private void createPollTable(java.sql.Connection c) {
		try {
			PreparedStatement pst = c.prepareStatement(
					"CREATE TABLE Polls\r\n" + " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n"
							+ " title VARCHAR(150) NOT NULL,\r\n" + " message CLOB(2048) NOT NULL\r\n" + ")");
			pst.executeUpdate();

			addRowsToPolls(c);
		} catch (SQLException e) {
		}

	}
	
	private void addRowsToPolls(java.sql.Connection c) {
		PreparedStatement pst = null;
		
		try {
			pst = c.prepareStatement("INSERT INTO Polls (title, message) values(?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			pst.setString(1, "Glasanje za omiljeni bend:");
			pst.setString(2, "Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!");
			pst.executeUpdate();

			pst.setString(1, "Glasanje za omiljeni operacijski sustav:");
			pst.setString(2, "Od sljedećih operacijskih sustava, koji Vam je najdraži? Kliknite na link kako biste glasali!");
			pst.executeUpdate();
		} catch (SQLException e) {
		}
	}
	
	private void addRowsToPollOptions(java.sql.Connection c) { // TODO change this
		PreparedStatement pst = null;
		ResultSet res = null;
		try {
			pst = c.prepareStatement("select id from polls");
			res = pst.executeQuery();
			
			res.next();
			long id1 = res.getLong("id");
			res.next();
			long id2 = res.getLong("id");
			
			pst = c.prepareStatement("insert into polloptions (optionTitle, optionLink, pollID, votesCount) values (?, ?, ?, ?)");
			
			insertToPollOptions(pst, "The Beatles", "https://www.youtube.com/watch?v=z9ypq6_5bsg", id1, 0);
			insertToPollOptions(pst, "The Platters", "https://www.youtube.com/watch?v=H2di83WAOhU", id1, 0);
			insertToPollOptions(pst, "The Beach Boys", "https://www.youtube.com/watch?v=2s4slliAtQU", id1, 0);
			insertToPollOptions(pst, "The Four Seasons", "https://www.youtube.com/watch?v=y8yvnqHmFds", id1, 0);
			insertToPollOptions(pst, "The Marcels", "https://www.youtube.com/watch?v=qoi3TH59ZEs" , id1, 0);
			insertToPollOptions(pst, "The Everly Brothers", "https://www.youtube.com/watch?v=tbU3zdAgiX8", id1, 0);
			insertToPollOptions(pst, "The Mamas And The Papas", "https://www.youtube.com/watch?v=N-aK6JnyFmk", id1, 0);
			
			insertToPollOptions(pst, "Windows", "https://www.microsoft.com/en-in/windows", id2, 0);
			insertToPollOptions(pst, "Linux", "https://www.linux.org", id2, 0);
			insertToPollOptions(pst, "Mac", "https://support.apple.com/macos", id2, 0);
		} catch (SQLException e) {
		}finally {
			try {
				res.close();
			} catch (SQLException e) {
			}
		}
	}
	
	private void insertToPollOptions(PreparedStatement pst, String optionTitle, String optionLink, Long pollID, int votesCount) {
		try {
			pst.setString(1, optionTitle);
			pst.setString(2, optionLink);
			pst.setLong(3, pollID);
			pst.setLong(4, 0);
			pst.executeUpdate();
		} catch (SQLException e) {
		}
		
	}
	
	private void initialize(List<String> lines) {
		map = new HashMap<>();

		for (String s : lines) {
			int index = s.indexOf('=');
			if (index == -1 || index == s.length() - 1) {
				throw new IllegalStateException("Wrong properties file configuration");
			}

			String key = s.substring(0, index).trim();
			String value = s.substring(index + 1).trim();

			map.put(key, value);
		}

		if (!map.containsKey("host") || !map.containsKey("port") || !map.containsKey("name") || !map.containsKey("user")
				|| !map.containsKey("password")) {
			throw new IllegalStateException("Missing properties in properties file.");
		}
	}
}
