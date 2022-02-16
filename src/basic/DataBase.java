package basic;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBase {
    private static final String Connection  = "jdbc:mysql://localhost:3306/bank_demo";
    private static final String user = "root";
    private static final String password = "root";

    public static java.sql.Connection connection() throws Exception{
        return DriverManager.getConnection(Connection, user, password);
    }

}
