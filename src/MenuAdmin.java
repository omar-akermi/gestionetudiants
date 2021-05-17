import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class MenuAdmin extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MenuAdmin dialog = new MenuAdmin();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MenuAdmin() {
		setBounds(100, 100, 1077, 563);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton btnNewButton = new JButton("");
			btnNewButton.setIcon(new ImageIcon("C:\\logos\\gradd.png"));
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new GestionEtudiants().show();
					
				}
			});
			btnNewButton.setFont(new Font("Monospaced", Font.PLAIN, 15));
			btnNewButton.setBounds(90, 115, 152, 241);
			contentPanel.add(btnNewButton);
		}
		{
			JButton btnNewButton_1 = new JButton("");
			btnNewButton_1.setIcon(new ImageIcon("C:\\logos\\test.png"));
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new GestionNotes().show();
				}
			});
			btnNewButton_1.setFont(new Font("Monospaced", Font.PLAIN, 15));
			btnNewButton_1.setBounds(790, 115, 161, 241);
			contentPanel.add(btnNewButton_1);
		}
		{
			JLabel lblNewLabel = new JLabel("Gestion des etudiants");
			lblNewLabel.setBounds(112, 382, 130, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblGestionDesNotes = new JLabel("Gestion des notes");
			lblGestionDesNotes.setBounds(825, 382, 103, 14);
			contentPanel.add(lblGestionDesNotes);
		}
		{
			JButton btnNewButton_2 = new JButton("");
			btnNewButton_2.setIcon(new ImageIcon("C:\\logos\\open-book.png"));
			btnNewButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new GestionMatieres().show();
				}
			});
			btnNewButton_2.setFont(new Font("Monospaced", Font.PLAIN, 15));
			btnNewButton_2.setBounds(332, 115, 152, 241);
			contentPanel.add(btnNewButton_2);
		}
		{
			JLabel lblGestionDesMatieres = new JLabel("Gestion des matieres");
			lblGestionDesMatieres.setBounds(360, 382, 124, 14);
			contentPanel.add(lblGestionDesMatieres);
		}
		{
			JButton btnNewButton_2 = new JButton("");
			btnNewButton_2.setIcon(new ImageIcon("C:\\logos\\classroom.png"));
			btnNewButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new GestionClasses().show();
				}
			});
			btnNewButton_2.setFont(new Font("Monospaced", Font.PLAIN, 15));
			btnNewButton_2.setBounds(560, 115, 152, 241);
			contentPanel.add(btnNewButton_2);
		}
		{
			JLabel lblGestionDesClasses = new JLabel("Gestion des classes");
			lblGestionDesClasses.setBounds(594, 382, 118, 14);
			contentPanel.add(lblGestionDesClasses);
		}
	}

}
