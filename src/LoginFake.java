import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginFake extends JFrame implements ActionListener {

    private JTextField txtNome = null;
    private JTextField txtCognome = null;
    private JButton btnLogin = null;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LafManager.install(new DarculaTheme());
                LafManager.install();
                new LoginFake();
            }
        });
    }

    public LoginFake(){
        setSize(300,400);
        setTitle("Fake Login");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel pnlCenter = new JPanel(new GridLayout(2,1));
        JPanel pnlNome = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel pnlCognome = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlNome.add(new JLabel("Nome: "));
        txtNome = new JTextField(20);
        pnlNome.add(txtNome);

        pnlCognome.add(new JLabel("Cognome: "));
        txtCognome = new JTextField(20);
        pnlCognome.add(txtCognome);

        pnlCenter.add(pnlNome);
        pnlCenter.add(pnlCognome);

        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLogin = new JButton("Login...");
        btnLogin.addActionListener(this);

        pnlSouth.add(btnLogin);

        add(pnlCenter, BorderLayout.CENTER);
        add(pnlSouth, BorderLayout.SOUTH);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnLogin){
            checkLogin();
        }
    }

    private void checkLogin() {
        Docente d;
        try {
            d = DocenteDAO.readDocenteByName(txtNome.getText(), txtCognome.getText());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(d != null){
            /*
            txtNome.setBackground(Color.WHITE);
            txtCognome.setBackground(Color.WHITE);
            txtNome.setForeground(Color.RED);
            txtCognome.setForeground(Color.RED);

            */
            
            new MenuFrame(d, this);
        }
        else{
            txtNome.setBackground(Color.RED);
            txtCognome.setBackground(Color.RED);
            txtNome.setForeground(Color.WHITE);
            txtCognome.setForeground(Color.WHITE);
        }
    }

}
