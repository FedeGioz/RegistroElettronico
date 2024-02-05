import java.util.ArrayList;

public class Docente {
    private int matricola = -1;
    private String nome = null;
    private String cognome = null;
    private ArrayList<Materia> materie = null;

    public Docente(int matricola, String nome, String cognome, ArrayList<Materia> materie) {
        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
        this.materie = materie;
    }

    public int getMatricola() {
        return matricola;
    }

    public void setMatricola(int matricola) {
        this.matricola = matricola;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public ArrayList<Materia> getMaterie() {
        return materie;
    }

    public void setMaterie(ArrayList<Materia> materie) {
        this.materie = materie;
    }
}
