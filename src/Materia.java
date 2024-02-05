public class Materia {
    private int codice = -1;
    private String nome = null;
    private String classeConcorso = null;

    public Materia(int codice, String nome, String classeConcorso) {
        this.codice = codice;
        this.nome = nome;
        this.classeConcorso = classeConcorso;
    }

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getClasseConcorso() {
        return classeConcorso;
    }

    public void setClasseConcorso(String classeConcorso) {
        this.classeConcorso = classeConcorso;
    }
}
