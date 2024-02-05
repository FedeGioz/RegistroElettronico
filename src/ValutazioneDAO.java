import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ValutazioneDAO extends GenericDAO{
    public static int create(Object o) throws SQLException {
        Valutazione v = (Valutazione) o;
        System.out.println(v.getMateria());
        String sql = "INSERT INTO Valutazioni VALUES(" +
                "NULL, "
                + v.getVoto() +
                ", '" +
                v.getTipologia().toString() +
                "', " + v.getIdStudente() + ", " +
                v.getIdProfessore() +
                ", " +
                v.getMateria().getCodice() +
                ");";

        GenericDAO.connect();

        Statement s = GenericDAO.conn.createStatement();
        s.executeUpdate(sql);

        ResultSet rs = s.executeQuery("SELECT last_insert_rowid() AS id FROM valutazioni;");
        rs.next();
        int lastId = rs.getInt("id");

        v.setId(lastId);

        GenericDAO.closeConn();

        return lastId;

    }

    public static ArrayList<Valutazione> readAllDocenteMateria(String classe, String materia, Docente docente) throws SQLException{

        Materia mat = (Materia)MateriaDAO.readIdByNomeMateria(materia);
        String sql =
                "SELECT Valutazioni.* " +
                "FROM Studenti, Valutazioni " +
                "WHERE Studenti.matricola = Valutazioni.studente " +
                "AND Valutazioni.docente = " + docente.getMatricola() + " " +
                "AND Valutazioni.materia = " + mat.getCodice() + " " +
                "AND Studenti.classe = '" + classe +
                "';";

        GenericDAO.connect();
        Statement s = GenericDAO.conn.createStatement();
        ResultSet rs = s.executeQuery(sql);

        ArrayList<Valutazione> valutazioni = new ArrayList<>();

        while(rs.next()){
            valutazioni.add(
                    new Valutazione(
                        rs.getInt("id"),
                        Tipologia.valueOf(rs.getString("tipologia")),
                        rs.getInt("voto"),
                        rs.getInt("studente"),
                        rs.getInt("docente"),
                        (Materia)MateriaDAO.read(rs.getInt("materia"))
                    )
            );
        }

        return valutazioni;
    }

    public static void main(String[] args) {
        try {
            ArrayList<Valutazione> valutazioni = readAllDocenteMateria("4AINF", "Informatica", (Docente)DocenteDAO.read(1));
            valutazioni.forEach(System.out::println);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
