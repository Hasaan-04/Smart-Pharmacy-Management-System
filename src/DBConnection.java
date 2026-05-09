import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    // Method to establish a connection to the database using user-provided credentials from configuration.properties
    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();

        try {
            // Load the properties file containing the database connection details
            FileInputStream inputStream = new FileInputStream("configuration.properties");  // Make sure it's named configuration.properties
            properties.load(inputStream);

            // Retrieve database connection details from the properties file
            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            // Establish and return the database connection
            return DriverManager.getConnection(url, username, password);

        } catch (IOException e) {
            System.out.println("Error loading the configuration file: " + e.getMessage());
            throw new SQLException("Unable to load database configuration");
        }
    }
}
