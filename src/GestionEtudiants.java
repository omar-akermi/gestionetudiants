import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComboBox;

public class GestionEtudiants extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField prenomField;
	private JTextField nomField;
	private JTextField cinField;
	private JTextField telField;
	private JTable table;
	JComboBox comboBox;
	Connection cnx = null;
	ResultSet resultat = null;
	PreparedStatement prepared=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GestionEtudiants dialog = new GestionEtudiants();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GestionEtudiants() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				updateTable();

			}
		});
		cnx =ConnexionMysql.ConnecrDb();
		setBounds(100, 100, 912, 361);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Prenom : ");
		lblNewLabel.setBounds(44, 67, 46, 14);
		contentPanel.add(lblNewLabel);
		
		prenomField = new JTextField();
		prenomField.setBounds(113, 64, 168, 20);
		contentPanel.add(prenomField);
		prenomField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Nom :");
		lblNewLabel_1.setBounds(44, 110, 46, 14);
		contentPanel.add(lblNewLabel_1);
		
		nomField = new JTextField();
		nomField.setColumns(10);
		nomField.setBounds(113, 107, 168, 20);
		contentPanel.add(nomField);
		
		JLabel lblNewLabel_2 = new JLabel("CIN :");
		lblNewLabel_2.setBounds(44, 151, 46, 14);
		contentPanel.add(lblNewLabel_2);
		
		cinField = new JTextField();
		cinField.setColumns(10);
		cinField.setBounds(113, 148, 168, 20);
		contentPanel.add(cinField);
		
		JLabel lblNewLabel_3 = new JLabel("Telephone :");
		lblNewLabel_3.setBounds(44, 195, 59, 14);
		contentPanel.add(lblNewLabel_3);
		
		telField = new JTextField();
		telField.setColumns(10);
		telField.setBounds(113, 192, 168, 20);
		contentPanel.add(telField);
		
		JButton btnNewButton = new JButton("Ajouter");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String prenom = prenomField.getText().toString();
				String nom = nomField.getText().toString();
				String cin = cinField.getText().toString();
				String tel = telField.getText().toString();
				
				String sql = "insert into etudiants(prenom,nom,cin,tel,classe) values ( ? , ? , ? , ? ,?) ";
				try {
					if(!prenom.equals("")&&!nom.equals("")&&!cin.equals("")&&!tel.equals("")) {
					prepared=cnx.prepareStatement(sql);
					prepared.setString(1,prenom);
					prepared.setString(2,nom);
					prepared.setString(3,cin);
					prepared.setString(4,tel);
					prepared.setString(5,comboBox.getSelectedItem().toString());

					prepared.execute();
					updateTable();
					JOptionPane.showMessageDialog(null, "etudiant bien ajoutée");

					prenomField.setText("");
					nomField.setText("");
					cinField.setText("");
					telField.setText("");
					}
					else {
						JOptionPane.showMessageDialog(null, "champs vides");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

			}
		});
		btnNewButton.setBounds(44, 277, 89, 23);
		contentPanel.add(btnNewButton);
		
		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ligne = table.getSelectedRow();
				if(ligne==-1) {
					JOptionPane.showMessageDialog(null,"selectionner un etudiant");
				}
				String id = table.getModel().getValueAt(ligne,0).toString();
				String sql = " delete from etudiants where id_etudiants = '"+id+"'";
				try {
					prepared = cnx.prepareStatement(sql);

					prepared.execute();
					updateTable();
					JOptionPane.showMessageDialog(null, "etudiant supprimé");

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSupprimer.setBounds(242, 277, 89, 23);
		contentPanel.add(btnSupprimer);
		
		JLabel lblNewLabel_4 = new JLabel("Gestion Des Etudiants");
		lblNewLabel_4.setFont(new Font("Sitka Subheading", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(100, 21, 153, 40);
		contentPanel.add(lblNewLabel_4);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(406,5, 480, 280);
		contentPanel.add(scrollPane);
		
		table = new JTable();
		table.setColumnSelectionAllowed(true);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int ligne = table.getSelectedRow();
				String id = table.getModel().getValueAt(ligne,0).toString();
				String sql = " select * from etudiants where id_etudiants = '"+id+"'";
				try {
					prepared = cnx.prepareStatement(sql);
					resultat = prepared.executeQuery();
					if(resultat.next()) {
						prenomField.setText(resultat.getString("prenom"));
						nomField.setText(resultat.getString("nom"));
						cinField.setText(resultat.getString("cin"));
						telField.setText(resultat.getString("prenom"));
						comboBox.setSelectedItem(resultat.getString("classe"));
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		table.setBackground(Color.WHITE);
		scrollPane.setViewportView(table);
		
		JButton btnNewButton_1 = new JButton("Actualiser");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTable();
			}
		});
		btnNewButton_1.setBounds(789, 291, 89, 23);
		contentPanel.add(btnNewButton_1);
		
		JButton btnModifier = new JButton("Modifier");
		btnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ligne = table.getSelectedRow();
				if(ligne==-1) {
					JOptionPane.showMessageDialog(null,"selectionner un etudiant");
				}
				else {
					String id = table.getModel().getValueAt(ligne,0).toString();
					String sql = " update etudiants set prenom = ? , nom = ? , cin = ? , tel = ?, classe = ? where id_etudiants = '"+id+"'";
					try {
						prepared = cnx.prepareStatement(sql);
						prepared.setString(1, prenomField.getText().toString());
						prepared.setString(2, nomField.getText().toString());
						prepared.setString(3, cinField.getText().toString());
						prepared.setString(4, telField.getText().toString());
						prepared.setString(5, comboBox.getSelectedItem().toString());
						prepared.execute();
						updateTable();
						JOptionPane.showMessageDialog(null, "etudiant modifié");

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
		btnModifier.setBounds(143, 277, 89, 23);
		contentPanel.add(btnModifier);
		
		JLabel lblNewLabel_3_1 = new JLabel("Classe : ");
		lblNewLabel_3_1.setBounds(44, 233, 59, 14);
		contentPanel.add(lblNewLabel_3_1);
		
		comboBox = new JComboBox();
		comboBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		comboBox.setBounds(113, 229, 168, 22);
		contentPanel.add(comboBox);
		remplirvCombobox();

	}
	public void updateTable() {
		String sql = "select * from etudiants";
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(resultat));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	public void remplirvCombobox() {
		String sql = "select *  from classes";
		
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			while(resultat.next()) {
				String nom= resultat.getString("nom");
				comboBox.addItem(nom);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
