package metier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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
//			System.out.println(text);
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
		String nom3 = StringUtils.substringBetween(text, "Prétraitement / Préparation :" + nom1 + "-" + nom2 + "-",
				"--");
		if (nom3 != null) {
			nom3 = nom3.substring(4);
			substrat.setNom(nom3);
		} else {
			substrat.setNom("erreur");
		}

		substrat.setReference(StringUtils.substringBetween(text, "Référence :", "\r\n").trim());

		String ms = StringUtils.substringBetween(text, "Matière Sèche", "%");
		if (ms != null) {
			substrat.setMs(Double.parseDouble(ms.replace(',', '.')));
		} else {
			substrat.setMs(-1);
		}

		String mv = StringUtils.substringBetween(text, "Matière Volatile", "%");
		if (mv != null) {
			substrat.setMv(Double.parseDouble(mv.replace(',', '.')));
		} else {
			substrat.setMv(-1);
		}

		String ratio = StringUtils.substringBetween(text, "Ratio MV/MS", "%");
		
		if(ratio != null) {
			substrat.setRatioMsMv(
					Double.parseDouble(ratio.replace(',', '.')));
		}else {
			substrat.setRatioMsMv(-1);
		}

		String phosphore = StringUtils.substringBetween(text, "Phosphore total (P2O5)", "g.kg-1");
		if (phosphore != null && !phosphore.trim().isEmpty()) {
			substrat.setPhosophore(Double.parseDouble(phosphore.replace(',', '.').replaceAll("<", "")));
		} else {
			substrat.setPhosophore(-1);
		}

		String potassium = StringUtils.substringBetween(text, "Potassium total (K2O)", "g.kg-1");
		if (potassium != null  && !potassium.trim().isEmpty()) {
			substrat.setPotassium(Double.parseDouble(potassium.replace(',', '.').replaceAll("<", "")));
		} else {
			substrat.setPotassium(-1);
		}

		String azoteK = StringUtils.substringBetween(text, "Azote total Kjeldahl (NTK)", "gN.kg-1");
		if (azoteK != null  && !azoteK.trim().isEmpty()) {
			substrat.setAzoteK(Double.parseDouble(azoteK.replace(',', '.').replaceAll("<", "")));
		} else {
			substrat.setAzoteK(-1);
		}

		String azoteA = StringUtils.substringBetween(text, "Azote ammoniacal (NH4+)", "gN.kg-1");
		if (azoteA != null  && !azoteA.trim().isEmpty()) {
			substrat.setAzoteA(Double.parseDouble(azoteA.replace(',', '.').replaceAll("<", "")));
		} else {
			substrat.setAzoteA(-1);
		}

		String bmpMV = StringUtils.substringBetween(text, "Potentiel Méthanogène(2)", "NLCH4.kgMV-1");
		if (bmpMV != null) {
			substrat.setBmpMV(Double.parseDouble(bmpMV.replace(',', '.')));
		} else {
			substrat.setBmpMV(-1);
		}

		String bmpMF = StringUtils.substringBetween(text, "NLCH4.kgMS-1	\r\n" + "	", "	NLCH4.kgMF-1");
		if (bmpMF != null) {
			substrat.setBmpMF(Double.parseDouble(bmpMF.replace(',', '.')));
		} else {
			substrat.setBmpMF(-1);
		}

		String pctage = StringUtils.substringBetween(text, "CH4 (% vol.)", "%	\r\n" + "	H2S (% vol.)	");
		if (pctage != null) {
			String[] pctageArray = pctage.split("	");
			substrat.setPourcentageMethane(Double.parseDouble(pctageArray[3].replace(',', '.')));
		} else {
			substrat.setPourcentageMethane(-1);
		}

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
			Picture pic1 = (Picture) pics.get(0);
			FileOutputStream outputStream = null;
			String imagePath = destination + "\\courbes\\" + ref;
			outputStream = new FileOutputStream(imagePath + "." + pic1.suggestFileExtension());
			outputStream.write(pic1.getContent());
			outputStream.close();
			
			if (pics.size() >= 5) {
				imagePath = destination + "\\photos\\" + ref;
				Picture pic2 = (Picture) pics.get(2);
				outputStream = new FileOutputStream(imagePath + "." + pic2.suggestFileExtension());
				outputStream.write(pic2.getContent());
				outputStream.close();
				ImageCompressor.compress(imagePath + "." + pic2.suggestFileExtension(), 0.15);
			}
			doc.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private static List<Image> getImages(String location) {
		ExecutorService es = Executors.newFixedThreadPool(8);
		List<Image> liste = new ArrayList<Image>();
		List<FileToImage> callables = new ArrayList<FileToImage>();
		File repertoire = new File(location);
		File[] files = repertoire.listFiles();
		for (File file : files) {
			callables.add(new FileToImage(file));
		}
		try {
			List<Future<Image>> futures = es.invokeAll(callables);
			es.shutdown();
			es.awaitTermination(1, TimeUnit.HOURS);
			
			for (Future<Image> future : futures) {
				liste.add(future.get());
			}
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
	}

//	private static Image fileToImage(File file) {
//		byte[] fileData = new byte[(int) file.length()];
//		FileInputStream in;
//		try {
//			in = new FileInputStream(file);
//			in.read(fileData);
//			in.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		String binaire = "";
//		for (byte b : fileData) {
//			binaire += Integer.toBinaryString((b & 0xFF) + 0x100).substring(1);
//		}
//		return new Image(file.getName().substring(0, file.getName().length() - 4), binaire);
//	}

	public static void main(String[] args) {
		DAO dao = new DAO();
////		if (args.length <= 1) {
//		List<Substrat> liste = AllDocInspector("C:\\Users\\valen\\Desktop\\test\\doc");
//		for (Substrat substrat : liste) {
//			System.out.println(substrat);
//		}
//		dao.createAllSubstrats(liste);
//		} else {
			ImageCompressor.compressAll("C:\\Users\\valen\\Desktop\\test\\doc" + "\\courbes", 0.65);
			List<Image> courbes = getImages("C:\\Users\\valen\\Desktop\\test\\doc" + "\\courbes");
			dao.createAllCourbes(courbes);
			List<Image> photos = getImages("C:\\Users\\valen\\Desktop\\test\\doc" + "\\photos");
			dao.createAllPhotos(photos);
//		}
//		Image c = fileToImage(new File("C:\\Users\\valen\\Desktop\\test\\doc\\images\\201809-561-CVB-121-1R.jpg"));
////		Courbe c = new Courbe("201809-561-CVB-121-1R", "101010101010");
//		System.out.println(c.getBlob().length());
//		dao.createCourbe(c);
	}

}
