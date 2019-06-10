package hr.fer.zemris.java.p12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.DatabaseMetaData;
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

		DatabaseMetaData dbmd = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		try {
			dbmd = dbConnection.getMetaData();

			rs1 = dbmd.getTables(null, null, "Polls", null);
			rs2 = dbmd.getTables(null, null, "PollOptions", null);

			if (!rs1.next()) {
				createPollTable(dbConnection);
				System.out.println("Created tables");
			}

			if (!rs2.next()) {
				createPollOptionsTable(dbConnection);
				System.out.println("Created poll options table");
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
			System.out.println("executed query poll options");
			pst = c.prepareStatement(
					"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) values(?, ?, ? ,?)");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void createPollTable(java.sql.Connection c) {
		try {
			PreparedStatement pst = c.prepareStatement(
					"CREATE TABLE Polls\r\n" + " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n"
							+ " title VARCHAR(150) NOT NULL,\r\n" + " message CLOB(2048) NOT NULL\r\n" + ")");
			pst.executeUpdate();
			System.out.println("executes query poll");

			pst = c.prepareStatement("INSERT INTO Polls (title, message) values(?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, "Glasanje za omiljeni bend:");
			pst.setString(2, "Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!");
			pst.executeUpdate();

			pst.setString(1, "Glasanje za omiljeni bend:");
			pst.setString(2, "Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
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
