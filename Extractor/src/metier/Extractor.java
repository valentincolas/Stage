package metier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.extractor.POITextExtractor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.ooxml.extractor.ExtractorFactory;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.xmlbeans.XmlException;

import DAL.DAO;

public class Extractor {

	/*
	 * Données : chemin d'accès vers un fichier .doc Résultat : Retourne un objet
	 * Substrat contenant les informations utiles pour la BDD
	 */

	private static String destination;

	public static Substrat inspectDoc(String location) {
		String text = "";
		File f = new File(location);
		try {
			POITextExtractor textExtractor = ExtractorFactory.createExtractor(f);
			WordExtractor wordExtractor = (WordExtractor) textExtractor;
			text = wordExtractor.getText();
		} catch (IOException | OpenXML4JException | XmlException e) {
			e.printStackTrace();
		}

		Substrat substrat = getTextualInfos(text);
		extractImage(location, substrat.getReference());

		return substrat;
	}

	private static Substrat getTextualInfos(String text) {

		Substrat substrat = new Substrat();
		String nom1 = StringUtils.substringBetween(text, "Prétraitement / Préparation :", "-");
		String nom2 = StringUtils.substringBetween(text, "Prétraitement / Préparation :" + nom1 + "-", "-");
		substrat.setNom(StringUtils
				.substringBetween(text, "Prétraitement / Préparation :" + nom1 + "-" + nom2 + "-", "--").substring(4));
		substrat.setReference(StringUtils.substringBetween(text, "Référence :", "\r\n").trim());
		substrat.setMs(Double.parseDouble(StringUtils.substringBetween(text, "Matière Sèche", "%").replace(',', '.')));
		substrat.setMv(
				Double.parseDouble(StringUtils.substringBetween(text, "Matière Volatile", "%").replace(',', '.')));
		substrat.setRatioMsMv(
				Double.parseDouble(StringUtils.substringBetween(text, "Ratio MV/MS", "%").replace(',', '.')));

		Double phosphore = Double
				.parseDouble(StringUtils.substringBetween(text, "Phosphore total (P2O5)", "g.kg-1").replace(',', '.'));
		if (!phosphore.equals(null)) {
			substrat.setPhosophore(phosphore);
		} else {
			substrat.setPhosophore(-1);
		}

		Double potassium = Double
				.parseDouble(StringUtils.substringBetween(text, "Potassium total (K2O)", "g.kg-1").replace(',', '.'));
		if (!potassium.equals(null)) {
			substrat.setPotassium(potassium);
		} else {
			substrat.setPotassium(-1);
		}

		Double azoteK = Double.parseDouble(
				StringUtils.substringBetween(text, "Azote total Kjeldahl (NTK)", "gN.kg-1").replace(',', '.'));
		if (!azoteK.equals(null)) {
			substrat.setAzoteK(azoteK);
		} else {
			substrat.setAzoteK(-1);
		}

		Double azoteA = Double.parseDouble(
				StringUtils.substringBetween(text, "Azote ammoniacal (NH4+)", "gN.kg-1").replace(',', '.'));
		if (!azoteA.equals(null)) {
			substrat.setAzoteA(azoteA);
		} else {
			substrat.setAzoteA(-1);
		}

		substrat.setBmpMV(Double.parseDouble(
				StringUtils.substringBetween(text, "Potentiel Méthanogène(2)", "NLCH4.kgMV-1").replace(',', '.')));
		substrat.setBmpMF(Double.parseDouble(StringUtils
				.substringBetween(text, "NLCH4.kgMS-1	\r\n" + "	", "	NLCH4.kgMF-1").replace(',', '.')));
		String[] pctage = StringUtils.substringBetween(text, "CH4 (% vol.)	", "%	\r\n" + "	H2S (% vol.)	")
				.split("	");
		substrat.setPourcentageMethane(Double.parseDouble(pctage[2].replace(',', '.')));

		return substrat;
	}

	public static List<Substrat> AllDocInspector(String location) {
		destination = location;
		List<Substrat> liste = new ArrayList<Substrat>();
		File repertoire = new File(destination);
		File[] files = repertoire.listFiles();
		for (File file : files) {
			if (!file.isDirectory()) {
				liste.add(inspectDoc(file.getAbsolutePath()));
			}
		}
		return liste;
	}

	public static void extractImage(String location, String ref) {

		try {
			HWPFDocument doc = new HWPFDocument(new FileInputStream(location));

			List<Picture> pics = doc.getPicturesTable().getAllPictures();
			Picture pic = (Picture) pics.get(0);
			FileOutputStream outputStream = null;
			String imagePath = destination + "\\images\\" + ref;
			outputStream = new FileOutputStream(imagePath + "." + pic.suggestFileExtension());
			outputStream.write(pic.getContent());
			outputStream.close();
			doc.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private static List<Courbe> getCourbes(String location) {
		List<Courbe> liste = new ArrayList<Courbe>();
		File repertoire = new File(location);
		File[] files = repertoire.listFiles();
		for (File file : files) {
			liste.add(fileToCourbe(file));
		}
		return liste;
	}

	private static Courbe fileToCourbe(File file) {
		byte[] fileData = new byte[(int) file.length()];
		FileInputStream in;
		try {
			in = new FileInputStream(file);
			in.read(fileData);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String binaire = "";
		for (byte b : fileData) {
			binaire += Integer.toBinaryString((b & 0xFF) + 0x100).substring(1);
		}
		return new Courbe(file.getName().substring(0, file.getName().length() - 4), binaire);
	}

	public static void main(String[] args) {
		DAO dao = new DAO();
		if (args.length <= 1) {
		List<Substrat> liste = AllDocInspector("C:\\Users\\valen\\Desktop\\test\\doc\\");
		for (Substrat substrat : liste) {
			System.out.println(substrat);
		}
			dao.createAllSubstrats(liste);
		} else {
			List<Courbe> liste = getCourbes(args[0]);
			dao.createAllCourbes(liste);
		}
		Courbe c = fileToCourbe(new File("C:\\Users\\valen\\Desktop\\test\\doc\\images\\201809-561-CVB-121-1R.jpg"));
//		Courbe c = new Courbe("201809-561-CVB-121-1R", "101010101010");
		System.out.println(c.getBlob().length());
		dao.createCourbe(c);
	}

}
