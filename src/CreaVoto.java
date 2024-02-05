import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreaVoto extends JDialog implements ActionListener {

    private JComboBox cmbClasse = null;
    private JComboBox cmbAlunno = null;
    private JComboBox cmbMateria = null;
    private JComboBox cmbVoto = null;
    private JComboBox cmbTipologia = null;
    private JButton btnSalva = null;
    private Docente docente = null;

    public CreaVoto(Docente docente){
        this.docente = docente;
        setSize(300,400);
        setTitle("Crea nuovo voto");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        setLocationRelativeTo(null);
        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel pnlCenter = new JPanel(new GridLayout(5,1));

        JPanel pnlClasse = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnlCognome = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnlMateria = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnlValutazione = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnlTipologia = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel lblClasse = new JLabel("Classe: ");
        ArrayList<String> classi = null;
        try {
            GenericDAO.connect();
            classi = StudenteDAO.readAllClassi();
            GenericDAO.closeConn();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cmbClasse = new JComboBox<>(classi.toArray());
        cmbClasse.addActionListener(this);

        pnlClasse.add(lblClasse);
        pnlClasse.add(cmbClasse);
        pnlCenter.add(pnlClasse);


        JLabel lblCognome = new JLabel("Alunno: ");
        cmbAlunno = new JComboBox<>();

        pnlCognome.add(lblCognome);
        pnlCognome.add(cmbAlunno);
        pnlCenter.add(pnlCognome);


        JLabel lblMateria = new JLabel("Materia: ");
        ArrayList<Materia> materie;
        try {
            materie = DocenteDAO.readMaterieByDocente(this.docente);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ArrayList<String> nomiMaterie = new ArrayList<>();
        for (Materia materia : materie) {
            nomiMaterie.add(materia.getNome());
        }
        cmbMateria = new JComboBox<>(nomiMaterie.toArray());

        pnlMateria.add(lblMateria);
        pnlMateria.add(cmbMateria);
        pnlCenter.add(pnlMateria);


        JLabel lblValutazione = new JLabel("Valutazione: ");
        String[] valutazioni = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        cmbVoto = new JComboBox<>(valutazioni);
        pnlValutazione.add(lblValutazione);
        pnlValutazione.add(cmbVoto);
        pnlCenter.add(pnlValutazione);


        JLabel lblTipologia = new JLabel("Tipologia: ");
        ArrayList<String> tipologie = new ArrayList<>();
        for(Tipologia tipo : Tipologia.values()){
            tipologie.add(tipo.name());
        }
        cmbTipologia = new JComboBox<>(tipologie.toArray());
        pnlTipologia.add(lblTipologia);
        pnlTipologia.add(cmbTipologia);
        pnlCenter.add(pnlTipologia);


        btnSalva = new JButton("Salva");
        btnSalva.addActionListener(this);
        add(btnSalva, BorderLayout.SOUTH);


        add(pnlCenter, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cmbClasse) {
            ArrayList<Studente> studenti = null;
            ArrayList<String> nomiStudenti = new ArrayList<>();
            try {
                studenti = StudenteDAO.readAllByClass(cmbClasse.getSelectedItem().toString());
            } catch (SQLException s) {
                throw new RuntimeException(s);
            }

            for (Studente studente : studenti) {
                nomiStudenti.add(studente.getNome() + " " + studente.getCognome());
            }

            cmbAlunno.removeAllItems();

            for (String s : nomiStudenti) {
                cmbAlunno.addItem(s);

            }
        }

        if(e.getSource() == btnSalva){

            // gestione alunno
            String nome;
            String cognome;

            String alunno = cmbAlunno.getSelectedItem().toString();

            int indexSpazio = alunno.indexOf(" ");

            nome = alunno.substring(0, indexSpazio);
            cognome = alunno.substring(indexSpazio+1);
            System.out.println("NOME: " + nome);
            System.out.println("COGNOME: " + cognome);



            try {
                int idStudente = StudenteDAO.readByNomeCognome(nome,cognome);
                Materia materia = (Materia)MateriaDAO.readIdByNomeMateria(cmbMateria.getSelectedItem().toString());
                ValutazioneDAO.create(
                        new Valutazione(-1,
                                Tipologia.valueOf(cmbTipologia.getSelectedItem().toString()),
                                Integer.parseInt(cmbVoto.getSelectedItem().toString()),
                                idStudente,
                                this.docente.getMatricola(),
                                materia
                        )
                );
                this.dispose();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
