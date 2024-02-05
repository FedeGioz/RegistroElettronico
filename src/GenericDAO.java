import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class GenericDAO {
    private final static String DB_URL = "jdbc:sqlite:/home/federico/scuola.db";
    protected static Connection conn = null;
    public static void connect(){
        try {
            if(GenericDAO.conn == null) GenericDAO.conn = DriverManager.getConnection(GenericDAO.DB_URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConn(){
        try {
            if(GenericDAO.conn != null) GenericDAO.conn.close();
            GenericDAO.conn = null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int create(Object o) throws SQLException{ return -1; }
    public static Object read(int id) throws SQLException { return null; }
    public static ArrayList<Object> readAll() throws SQLException{ return null; }
    public static boolean update(Object o) throws SQLException{ return true; }
    public static boolean delete(Object o) throws SQLException{ return true; }
}
