


import java.sql.*;

public class DBConnection {
    private static final String DB_URL = "jdbc:ucanaccess://VUE_Exhibition.accdb";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}
