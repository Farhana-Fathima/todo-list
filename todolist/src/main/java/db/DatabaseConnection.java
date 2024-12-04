package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());
    private static Connection connection = null;

    public static Connection getConnection() throws DatabaseConnectionException, SQLException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        }
        
        Properties props = new Properties();
        InputStream input = null;

        try {
            // Try multiple ways to load the configuration
            
            // 1. Try loading from classpath
            input = DatabaseConnection.class.getClassLoader().getResourceAsStream(".env");
            
            // 2. If not found in classpath, try loading from WEB-INF directory
            if (input == null) {
                String contextPath = System.getProperty("catalina.base");
                if (contextPath != null) {
                    File envFile = new File(contextPath + "/todolist/src/main/webapp/WEB-INF/.env");
                    if (envFile.exists()) {
                        input = new FileInputStream(envFile);
                    }
                }
            }
            
            // 3. If still not found, try system property
            if (input == null) {
                String envPath = System.getProperty("app.env.path");
                if (envPath != null) {
                    File envFile = new File(envPath);
                    if (envFile.exists()) {
                        input = new FileInputStream(envFile);
                    }
                }
            }
            
            // 4. Final fallback - hardcoded credentials (NOT recommended for production)
            if (input == null) {
                LOGGER.warning("No .env file found. Using hardcoded fallback credentials.");
                props.setProperty("DB_URL", "jdbc:mysql://localhost:3306/todolistapp");
                props.setProperty("DB_USER", "root");
                props.setProperty("DB_PASSWORD","root");
            } else {
                // Load properties from input stream
                props.load(input);
            }

            // Validate required properties
            String jdbcUrl = props.getProperty("DB_URL");
            String username = props.getProperty("DB_USER");
            String password = props.getProperty("DB_PASSWORD");

            if (jdbcUrl == null || username == null || password == null) {
                throw new DatabaseConnectionException("Incomplete database configuration");
            }

          
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            
            LOGGER.info("Database connection established successfully");
            return connection;

        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "JDBC Driver not found", e);
            throw new DatabaseConnectionException("JDBC Driver not found", e);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Connection Error", e);
            throw new DatabaseConnectionException("Unable to connect to database", e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading configuration file", e);
            throw new DatabaseConnectionException("Configuration file read error", e);
        } finally {
            // Close the input stream if it was opened
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    LOGGER.log(Level.WARNING, "Error closing input stream", e);
                }
            }
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                LOGGER.info("Database connection closed");
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing database connection", e);
            } finally {
                connection = null;
            }
        }
    }

   
    public static class DatabaseConnectionException extends Exception {
        public DatabaseConnectionException(String message) {
            super(message);
        }

        public DatabaseConnectionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}