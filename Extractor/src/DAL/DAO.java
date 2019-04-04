package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import metier.Courbe;
import metier.Substrat;

public class DAO {
	private Connection cn;
	private PreparedStatement ps;
	private ResultSet rs;

	public DAO() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Stage?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver introuvable");
			e.printStackTrace();
		} catch (SQLException e) {
			this.deconnexion();
			e.printStackTrace();
		}
	}

	private void deconnexion() {
		try {
			if(cn != null) this.cn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean createSubstrats(Substrat s) {
		try {
			ps = cn.prepareStatement("insert into Substrats(reference, Nom, MS, MV, ratioMSMV, Phosphore, Potassium, AzoteK,  AzoteA, bpmMV,  bmpMF, pourcentageMethane,  detail, famille) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, 'ND' );");
			ps.setString(1, s.getReference());
			ps.setString(2, s.getNom());
			ps.setDouble(3, s.getMs());
			ps.setDouble(4, s.getMv());
			ps.setDouble(5, s.getRatioMsMv());
			ps.setDouble(6, s.getPhosophore());
			ps.setDouble(7, s.getPotassium());
			ps.setDouble(8, s.getAzoteK());
			ps.setDouble(9, s.getAzoteA());
			ps.setDouble(10, s.getBmpMV());
			ps.setDouble(11, s.getBmpMF());
			ps.setDouble(12, s.getPourcentageMethane());
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}
	
	public void createAllSubstrats(List<Substrat> liste) {
		for (Substrat substrat : liste) {
			createSubstrats(substrat);
		}
	}
	
	public void createAllCourbes(List<Courbe> liste) {
		for (Courbe courbe : liste) {
			createCourbe(courbe);
		}
	}

	public void createCourbe(Courbe courbe) {
		try {
			ps = cn.prepareStatement("Insert into Courbes(img_reference, img_type, img_blob) values(?, 'jpg', ?)");
			ps.setString(1, courbe.getReference());
			ps.setString(2, courbe.getBlob());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
