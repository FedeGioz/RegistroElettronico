public class Studente {
    private int matricola = -1;
    private String nome = null;
    private String cognome = null;
    private String classe = null;

    public Studente(int idStudente, String nome, String cognome, String classe) {
        this.matricola = idStudente;
        this.nome = nome;
        this.cognome = cognome;
        this.classe = classe;
    }

    public int getIdStudente() {
        return matricola;
    }

    public void setIdStudente(int matricola) {
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

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }
}
