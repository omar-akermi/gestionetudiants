import javax.swing.*;
import java.sql.*;
public class ConnexionMysql {
	Connection conn = null;
	public static Connection ConnecrDb() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/gestionetud","root","");
			return conn;
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}
