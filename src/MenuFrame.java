import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class MenuFrame extends JDialog implements ActionListener {

    private Docente docente = null;
    private JComboBox cmbClasse = null;
    private JComboBox cmbMateria = null;
    private JButton btnCreaVoto = null;
    private JButton btnRefresh = null;
    private JTable tblVoti = null;

    public MenuFrame(Docente docente, JFrame frame){
        this.docente = docente;
        setSize(1000,800);
        setLocationRelativeTo(frame);
        setTitle("Menu Registro");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel pnlNorth = new JPanel(new GridLayout(3,1));
        JPanel pnlClasse = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblClasse = new JLabel("Classe: ");
        try {
            cmbClasse = new JComboBox<>(StudenteDAO.readAllClassi().toArray());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        pnlClasse.add(lblClasse);
        pnlClasse.add(cmbClasse);
        pnlNorth.add(pnlClasse);


        JPanel pnlMateria = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblMateria = new JLabel("Materia: ");

        ArrayList<Materia> materie;

        try {
            materie = DocenteDAO.readMaterieByDocente(docente);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ArrayList<String> cmbMaterieArray = new ArrayList<>();
        for(Materia materia : materie)
            cmbMaterieArray.add(materia.getNome());

        cmbMateria = new JComboBox<>(cmbMaterieArray.toArray());
        pnlMateria.add(lblMateria);
        pnlMateria.add(cmbMateria);
        pnlNorth.add(pnlMateria);


        JPanel pnlBtnListaVoti = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRefresh = new JButton("Aggiorna");
        btnRefresh.addActionListener(this);
        pnlBtnListaVoti.add(btnRefresh);
        pnlNorth.add(pnlBtnListaVoti);


        tblVoti = new JTable();
        JScrollPane pnlScroll = new JScrollPane(tblVoti);



        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCreaVoto = new JButton("Aggiungi voto...");
        btnCreaVoto.addActionListener(this);
        pnlSouth.add(btnCreaVoto);


        add(pnlScroll);
        add(pnlNorth, BorderLayout.NORTH);
        add(pnlSouth, BorderLayout.SOUTH);
    }

    public void populate() throws SQLException {
        DefaultTableModel model = new DefaultTableModel();

        String [] cols = {"Nome", "Cognome", "Voto", "Tipologia"};

        for(String col : cols)
            model.addColumn(col);

        ArrayList<Valutazione> valutazioni = ValutazioneDAO.readAllDocenteMateria(this.cmbClasse.getSelectedItem().toString(), this.cmbMateria.getSelectedItem().toString(), this.docente);

        for(Valutazione v : valutazioni)
            try{
                model.addRow(v.toTable());
            }
            catch(SQLException e){
                e.printStackTrace();
            }

        tblVoti.setModel(model);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnRefresh) {
            try {
                populate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getSource() == btnCreaVoto){
            new CreaVoto(docente);
        }
    }
}
