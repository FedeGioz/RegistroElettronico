import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MateriaDAO extends GenericDAO{
    public static Object readIdByNomeMateria(String materia) throws SQLException {
        GenericDAO.connect();

        String sql = "SELECT codice, nome, classeConcorso FROM Materie WHERE nome = '" + materia + "';";

        Statement stm = GenericDAO.conn.createStatement();

        ResultSet rs = stm.executeQuery(sql);

        rs.next();

        Materia mat = new Materia(rs.getInt("codice"), rs.getString("nome"), rs.getString("classeConcorso"));

        GenericDAO.closeConn();

        return mat;
    }

    public static Object read(int id) throws SQLException{
        GenericDAO.connect();

        String sql = "SELECT * FROM Materie WHERE codice = " + id + ";";

        Statement stm = GenericDAO.conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);

        rs.next();

        Materia mat = new Materia(rs.getInt("codice"), rs.getString("nome"), rs.getString("classeConcorso"));

        return mat;
    }
}
