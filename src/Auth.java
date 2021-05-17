import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Auth extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField usernameField;
	private JFrame frame;
	private JPasswordField passwordField;
	Connection cnx = null;
	ResultSet resultat = null;
	PreparedStatement prepared=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Auth dialog = new Auth();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	void fermer() {
		System.exit(0);
	}

	/**
	 * Create the dialog.
	 */
	public Auth() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
	
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		usernameField = new JTextField();
		usernameField.setColumns(10);
		usernameField.setBounds(139, 160, 173, 20);
		contentPanel.add(usernameField);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(54, 163, 59, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setBounds(54, 163, 59, 14);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password");
		lblNewLabel_1_1.setBounds(54, 194, 59, 14);
		contentPanel.add(lblNewLabel_1_1);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					String username = usernameField.getText().toString();
					String password = passwordField.getText().toString();
					String sql="select username , password from users ";
					try {
						prepared = cnx.prepareStatement(sql);
						resultat = prepared.executeQuery();
						int i=0;
						while(resultat.next()) {
							String user=resultat.getString("username");
							String pass=resultat.getString("password");
							if(user.equals(username) && pass.equals(password)) {
								JOptionPane.showMessageDialog(null, "connexion reussi");
								i=1;	
								new MenuAdmin().show();
								fermer();
							}

							
						}
							if(i==0){
								JOptionPane.showMessageDialog(null, "connexion echouée");

							}
						
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
			}
		});
		btnNewButton.setBounds(181, 222, 89, 23);
		contentPanel.add(btnNewButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(139, 191, 173, 20);
		contentPanel.add(passwordField);
		
		JLabel lblNewLabel_2 = new JLabel("Gestion des Etudiants");
		lblNewLabel_2.setFont(new Font("Cascadia Code", Font.PLAIN, 22));
		lblNewLabel_2.setBackground(Color.WHITE);
		lblNewLabel_2.setBounds(89, 0, 273, 69);
		contentPanel.add(lblNewLabel_2);
	}

}
