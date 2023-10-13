package Classes;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private String jdbcUrl;
    private String username;
    private String password;

    public Database() {
        loadCredentials();
    }

    private void loadCredentials() {
        Properties properties = new Properties();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // TO DO exception handling
        }
        try {
            // Loading application.properties file and storing url,username and password in variables for conntion
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
            properties.load(inputStream);

            jdbcUrl = properties.getProperty("jdbc.url");
            username = properties.getProperty("db.username");
            password = properties.getProperty("db.password");
        } catch (IOException e) {
            e.printStackTrace();
            // TO DO exception handling
        }
    }

    public Connection getConnection() throws SQLException {
        // Gets Database connection
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    public void closeConnection(Connection connection) {
        // Closes Database connection
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeResultSet(ResultSet resultSet) {
        // Closes result set
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closePreparedStatement(PreparedStatement preparedStatement) {
        // closes prepared statement 
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    // QUERY FUNCTIONS

    // Queries databsae to check if username exists
    public boolean doesUsernameExist(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = getConnection();
            String query = "SELECT UserID FROM UserDB WHERE Username = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // If there is a result, the username exists
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an error.
            return false; // Consider it as non-existing on error
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement); // Fixed variable name
            closeConnection(connection);
        }
    }

    // Queries database to check if email exists
    public boolean doesEmailExist(String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String query = "SELECT UserID FROM UserDB WHERE Email = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // If there is a result, the email exists
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an error.
            return false; // Consider it as non-existing on error
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement); // Fixed variable name
            closeConnection(connection);
        }
    }
    
    // Inserts new user into database
    public void insertUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            String query = "INSERT INTO UserDB (Username, FirstName, LastName, Email, Password) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an error.
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    // Function to check if user exists in databse, and if exists, check if password matches, if password or user does not match, return false
    public boolean authenticate(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String query = "SELECT Password FROM UserDB WHERE Username = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("Password");
                return PasswordValidations.verifyPassword(password, hashedPassword);
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an error.
            return false; // Consider it as non-existing on error
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement); // Fixed variable name
            closeConnection(connection);
        }
    }


    // TEST WITH HELLOSERVLET TO DUMP ALL USERS TO COMMAND LINE
    public void getAllUsers() {
        // initialise variables 
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // get connection
            connection = getConnection();

            // create query string 
            String query = "SELECT * FROM UserDB";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("UserID");
                String username = resultSet.getString("Username");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String email = resultSet.getString("Email");
                String password = resultSet.getString("Password");
                
                // print all data
                System.out.println("User ID: " + userId + ", Username: " + username + ", First Name: " + firstName + ", Last Name: " + lastName + ", Email: " + email + ", Password: " + password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            // error.
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
    }
}