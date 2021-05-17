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

public class GestionClasses extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField telField;
	private JTable table;
	Connection cnx = null;
	ResultSet resultat = null;
	PreparedStatement prepared=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GestionClasses dialog = new GestionClasses();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GestionClasses() {
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
		
		JLabel lblNewLabel_3 = new JLabel("Nom Du Classe : ");
		lblNewLabel_3.setBounds(10, 125, 93, 14);
		contentPanel.add(lblNewLabel_3);
		
		telField = new JTextField();
		telField.setColumns(10);
		telField.setBounds(113, 122, 168, 20);
		contentPanel.add(telField);
		
		JButton btnNewButton = new JButton("Ajouter");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String tel = telField.getText().toString();
				
				String sql = "insert into classes(nom) values (?) ";
				try {
					if(!tel.equals("")) {
					prepared=cnx.prepareStatement(sql);
					prepared.setString(1,tel);
					prepared.execute();
					updateTable();
					JOptionPane.showMessageDialog(null, "classe bien ajoutée");

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
					JOptionPane.showMessageDialog(null,"selectionner une classe");
				}
				String id = table.getModel().getValueAt(ligne,0).toString();
				String sql = " delete from classes where id_classe = '"+id+"'";
				try {
					prepared = cnx.prepareStatement(sql);

					prepared.execute();
					updateTable();
					JOptionPane.showMessageDialog(null, "classe supprimé");

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSupprimer.setBounds(242, 277, 89, 23);
		contentPanel.add(btnSupprimer);
		
		JLabel lblNewLabel_4 = new JLabel("Gestion Des Classes");
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
				String sql = " select * from classes where id_classe = '"+id+"'";
				try {
					prepared = cnx.prepareStatement(sql);
					resultat = prepared.executeQuery();
					if(resultat.next()) {
						telField.setText(resultat.getString("nom"));
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
					JOptionPane.showMessageDialog(null,"selectionner un classe");
				}
				else {
					String id = table.getModel().getValueAt(ligne,0).toString();
					String sql = " update classes set nom = ? where id_classe = '"+id+"'";
					try {
						prepared = cnx.prepareStatement(sql);
						prepared.setString(1, telField.getText().toString());
						prepared.execute();
						updateTable();
						JOptionPane.showMessageDialog(null, "classe modifié");

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
		btnModifier.setBounds(143, 277, 89, 23);
		contentPanel.add(btnModifier);
	}
	public void updateTable() {
		String sql = "select * from classes";
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
