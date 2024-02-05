import java.sql.SQLException;
import java.util.ArrayList;

public class Valutazione{
    private int id = -1;
    private Tipologia tipologia = Tipologia.UNICO;
    private int voto = -1;
    private int idStudente = -1;
    private int idProfessore = -1;
    private Materia materia = null;

    public Valutazione(int id, Tipologia tipologia, int voto, int idStudente, int idProfessore, Materia materia) {
        this.id = id;
        this.tipologia = tipologia;
        this.voto = voto;
        this.idStudente = idStudente;
        this.idProfessore = idProfessore;
        this.materia = materia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tipologia getTipologia() {
        return tipologia;
    }

    public void setTipologia(Tipologia tipologia) {
        this.tipologia = tipologia;
    }

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public int getIdStudente() {
        return idStudente;
    }

    public void setIdStudente(int idStudente) {
        this.idStudente = idStudente;
    }

    public int getIdProfessore() {
        return idProfessore;
    }

    public void setIdProfessore(int idProfessore) {
        this.idProfessore = idProfessore;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public String[] toTable() throws SQLException{

        String[] table = new String[6];

        Studente s = (Studente) StudenteDAO.read(this.idStudente);

        table[0] = s.getNome();
        table[1] = s.getCognome();
        table[2] = this.voto+"";
        table[3] = this.tipologia.toString();

        return table;
    }

    public static void main(String[] args) {
        Docente d = new Docente(1, "Juan", "Tusel", null);
        Valutazione v = new Valutazione(1, Tipologia.SCRITTO, 8, 1, 1, new Materia(1, "Informatica", "AZ01"));
        try {
            ValutazioneDAO.create(v);
            ArrayList<Materia> ret = DocenteDAO.readMaterieByDocente(d);
            for(Materia s : ret){
                System.out.println(s);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}