import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DocenteDAO extends GenericDAO{

    public static ArrayList<Materia> readMaterieByDocente(Docente docente) throws SQLException{
        ArrayList<Materia> materie = new ArrayList<>();
        String sql = "SELECT codice, nome, classeConcorso FROM materie, docentiMaterie " +
                "WHERE materie.codice = docentiMaterie.materia " +
                "AND docentiMaterie.docente = "+ docente.getMatricola() + ";";
        GenericDAO.connect();

        Statement stm = GenericDAO.conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);

        while(rs.next()){
            materie.add(new Materia(rs.getInt("codice"), rs.getString("nome"), rs.getString("classeConcorso")));
        }

        GenericDAO.closeConn();

        return materie;
    }

    public static Docente readDocenteByName(String nome, String cognome)throws SQLException{
        GenericDAO.connect();

        String sql = "SELECT matricola, nome, cognome FROM Docenti WHERE nome = '" + nome + "' AND cognome = '" + cognome + "';";
        System.out.println(sql);

        Statement stm = GenericDAO.conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);

        if(!rs.next()){
            return null;
        }

        Docente d = new Docente(rs.getInt("matricola"), rs.getString("nome"), rs.getString("cognome"), null);
        ArrayList<Materia> materie = DocenteDAO.readMaterieByDocente(d);
        d.setMaterie(materie);

        return d;

    }

    public static int create(Object o) throws SQLException{ return -1; }
    public static Object read(int id) throws SQLException {

        GenericDAO.connect();

        String sql = "SELECT * FROM Docenti WHERE matricola = " + id + ";";

        Statement stm = GenericDAO.conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);
        rs.next();

        Docente d = new Docente(rs.getInt("matricola"), rs.getString("nome"), rs.getString("cognome"), null);

        return d;
    }
    public static ArrayList<Object> readAll() throws SQLException{ return null; }
    public static boolean update(Object o) throws SQLException{ return true; }
    public static boolean delete(Object o) throws SQLException{ return true; }


}
