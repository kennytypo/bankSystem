package bankSystem;
import java.sql.*;
public class DataBase {
    private static final String CONNECTION = "jdbc:postgresql://localhost:5432/banksAccounts";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";

    public static Connection connection() throws SQLException {
            return DriverManager.getConnection(CONNECTION,USER,PASSWORD);
    }
}
