package inura.aiya;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection1 {

    private static final String JDBC_URL = "jdbc:derby:DB;user=ASD;password=asd123asd123";
    Connection conn;

    public static Connection ConnectDB() {
        try {
            Connection conn = DriverManager.getConnection(JDBC_URL);
            return conn;
        } catch (SQLException e) {
            Logger.getLogger(DBConnection1.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

}
