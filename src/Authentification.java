import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;

public class Authentification extends JFrame{

	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField passwordField;
	Connection cnx = null;
	ResultSet resultat = null;
	PreparedStatement prepared=null;
	/**
	 * Launch the application.
	 */
	void fermer() {
		frame.dispose();
		
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Authentification window = new Authentification();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Authentification() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1077, 563);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		cnx =ConnexionMysql.ConnecrDb();
		
		usernameField = new JTextField();
		usernameField.setBounds(310, 171, 173, 20);
		frame.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(225, 174, 59, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setBounds(225, 174, 59, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password");
		lblNewLabel_1_1.setBounds(225, 205, 59, 14);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText().toString();
				String password = passwordField.getText().toString();
				String sql="select username , password from enseignant ";
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
		btnNewButton.setBounds(352, 233, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(310, 202, 173, 20);
		frame.getContentPane().add(passwordField);
		
		JLabel lblNewLabel_2 = new JLabel("Gestion des Etudiants");
		lblNewLabel_2.setBackground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Cascadia Code", Font.PLAIN, 22));
		lblNewLabel_2.setBounds(260, 11, 273, 69);
		frame.getContentPane().add(lblNewLabel_2);
	}
}
