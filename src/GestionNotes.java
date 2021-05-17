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

public class GestionNotes extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField prenomField;
	private JTable table;
	Connection cnx = null;
	ResultSet resultat = null;
	PreparedStatement prepared=null;
	private JTextField matiereField;
	private JTextField nomField;
	private JTextField noteField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GestionNotes dialog = new GestionNotes();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GestionNotes() {
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
		
		JLabel lblNewLabel_3 = new JLabel("Prenom Etudiant");
		lblNewLabel_3.setBounds(10, 125, 93, 14);
		contentPanel.add(lblNewLabel_3);
		
		prenomField = new JTextField();
		prenomField.setColumns(10);
		prenomField.setBounds(113, 122, 168, 20);
		contentPanel.add(prenomField);
		
		JButton btnNewButton = new JButton("Ajouter");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String tel = prenomField.getText().toString();
				
				String sql = "Insert into evaluation(id_etudiant,id_matiere,note) VALUES((select id_etudiants from etudiants where nom = ? and prenom = ? limit 1),(select id_matiere from matieres where libelle = ? limit 1),?) ";
				try {
					if(!tel.equals("")) {
					prepared=cnx.prepareStatement(sql);
					prepared.setString(1,nomField.getText().toString());
					prepared.setString(2,prenomField.getText().toString());
					prepared.setString(3,matiereField.getText().toString());
					prepared.setString(4,noteField.getText().toString());

					prepared.execute();
					updateTable();
					JOptionPane.showMessageDialog(null, "etudiant bien ajoutée");

					prenomField.setText("");
					nomField.setText("");
					matiereField.setText("");
					noteField.setText("");

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
		btnNewButton.setBounds(76, 277, 89, 23);
		contentPanel.add(btnNewButton);
		
		JLabel lblNewLabel_4 = new JLabel("Gestion Des Notes");
		lblNewLabel_4.setFont(new Font("Sitka Subheading", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(128, 11, 153, 40);
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
				String sql = " select et.nom,et.prenom,m.libelle,ev.note from evaluation ev,matieres m,etudiants et where ev.id_etudiant=et.id_etudiants and ev.id_matiere=m.id_matiere and et.id_etudiants = '"+id+"'";
				try {
					prepared = cnx.prepareStatement(sql);
					resultat = prepared.executeQuery();
					if(resultat.next()) {
						prenomField.setText(resultat.getString("et.prenom"));
						nomField.setText(resultat.getString("et.nom"));
						matiereField.setText(resultat.getString("m.libelle"));
						noteField.setText(resultat.getString("ev.note"));
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
					JOptionPane.showMessageDialog(null,"selectionner un matiere");
				}
				else {
					String id = table.getModel().getValueAt(ligne,0).toString();
					String sql = " update evaluation ev,matieres m,etudiants et set et.nom = ? , et.prenom = ? , m.libelle = ? , ev.note = ? where ev.id_etudiant=et.id_etudiants and ev.id_matiere=m.id_matiere and et.id_etudiants ='"+id+"'";
					try {
						prepared = cnx.prepareStatement(sql);
						prepared.setString(1, nomField.getText().toString());
						prepared.setString(2, prenomField.getText().toString());
						prepared.setString(3, matiereField.getText().toString());
						prepared.setString(4, noteField.getText().toString());
						prepared.execute();
						updateTable();
						JOptionPane.showMessageDialog(null, "matiere modifié");

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
		btnModifier.setBounds(215, 277, 89, 23);
		contentPanel.add(btnModifier);
		
		JLabel lblNewLabel_3_1 = new JLabel("Matiere");
		lblNewLabel_3_1.setBounds(10, 153, 93, 14);
		contentPanel.add(lblNewLabel_3_1);
		
		matiereField = new JTextField();
		matiereField.setColumns(10);
		matiereField.setBounds(113, 150, 168, 20);
		contentPanel.add(matiereField);
		
		JLabel lblNewLabel_3_2 = new JLabel("Nom Etudiant");
		lblNewLabel_3_2.setBounds(10, 97, 93, 14);
		contentPanel.add(lblNewLabel_3_2);
		
		nomField = new JTextField();
		nomField.setColumns(10);
		nomField.setBounds(113, 94, 168, 20);
		contentPanel.add(nomField);
		
		JLabel lblNewLabel_3_3 = new JLabel("Note");
		lblNewLabel_3_3.setBounds(10, 181, 93, 14);
		contentPanel.add(lblNewLabel_3_3);
		
		noteField = new JTextField();
		noteField.setColumns(10);
		noteField.setBounds(113, 178, 168, 20);
		contentPanel.add(noteField);
	}
	public void updateTable() {
		String sql = "select et.id_etudiants,et.cin,et.nom,et.prenom,m.libelle,ev.note from evaluation ev,etudiants et,matieres m where ev.id_etudiant=et.id_etudiants and ev.id_matiere=m.id_matiere ";
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(resultat));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
}
