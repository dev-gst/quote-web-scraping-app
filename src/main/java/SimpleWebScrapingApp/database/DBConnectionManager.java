package SimpleWebScrapingApp.database;

import SimpleWebScrapingApp.config.Env;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {

    private static final DBConnectionManager instance = new DBConnectionManager();

    private Connection connection;

    private DBConnectionManager() {
        connect();
    }

    public static DBConnectionManager getInstance() {
        return instance;
    }

    public Connection getConnection() {
        connect();
        return this.connection;
    }

    private void connect() {
        try {
            Class.forName("org.postgresql.Driver");

            if (connection != null && !connection.isClosed()) {
                return;
            }

            this.connection = DriverManager.getConnection(
                    Env.getDbUrl(),
                    Env.getDbUser(),
                    Env.getDbPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC driver not found", e);
        }
    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
           throw new RuntimeException("Failed to close the database connection", e);
        }
    }
}
