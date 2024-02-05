import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StudenteDAO extends GenericDAO{
    public static int create(Object o) throws SQLException {
        Studente st = (Studente) o;
        String sql = "INSERT INTO Studenti VALUES(NULL, '" + st.getNome() + "', '" + st.getCognome() + "', '" + st.getClasse() + "'";
        Statement s = GenericDAO.conn.createStatement();
        s.executeQuery(sql);

        ResultSet rs = s.executeQuery("select last_insert_rowid() as id from studenti;");
        rs.next();
        int lastId = rs.getInt("id");

        return lastId;
    }
    public static ArrayList<Studente> readAllByClass(String classe) throws SQLException {
        GenericDAO.connect();
        String sql = "SELECT * FROM Studenti WHERE Studenti.classe = '" + classe + "';";
        Statement s = GenericDAO.conn.createStatement();
        ResultSet rs = s.executeQuery(sql);

        ArrayList<Studente> ret = new ArrayList<Studente>();
        while(rs.next())
            ret.add(new Studente(
                    rs.getInt("matricola"),
                    rs.getString("nome"),
                    rs.getString("cognome"),
                    rs.getString("classe")
            ));

        return ret;
    }

    public static int readByNomeCognome(String nome, String cognome) throws SQLException{
        GenericDAO.connect();
        String sql = "SELECT DISTINCT matricola FROM Studenti WHERE Studenti.nome = '" + nome + "' AND Studenti.cognome = '" + cognome + "';";
        Statement stm = GenericDAO.conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        int id = rs.getInt("matricola");
        GenericDAO.closeConn();
        return id;
    }

    public static ArrayList<String> readAllClassi() throws SQLException {
        GenericDAO.connect();
        String sql = "SELECT DISTINCT classe FROM Studenti ORDER BY classe;"; // rimuove duplicati
        Statement s = GenericDAO.conn.createStatement();
        ResultSet rs = s.executeQuery(sql);

        ArrayList<String> ret = new ArrayList<String>();
        while(rs.next())
            ret.add(rs.getString("classe"));

        return ret;
    }

    public static Object read(int id) throws SQLException{
        GenericDAO.connect();
        String sql = "SELECT DISTINCT matricola, nome, cognome, classe FROM Studenti WHERE matricola = " + id + ";";
        Statement s = GenericDAO.conn.createStatement();
        ResultSet rs = s.executeQuery(sql);

        rs.next();

        Studente ret = new Studente(rs.getInt("matricola"), rs.getString("nome"), rs.getString("cognome"), rs.getString("classe"));

        GenericDAO.closeConn();

        return ret;
    }
}
