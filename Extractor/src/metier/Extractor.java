package metier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.extractor.POITextExtractor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.ooxml.extractor.ExtractorFactory;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.xmlbeans.XmlException;

import com.profesorfalken.jpowershell.PowerShell;

import DAL.DAO;

/**
 *  <b>Extractor est la classe qui permet d'extraire et d'enregistrer en base les données relatives aux substrats </b>
 *  <p>
 *  Cette classe permet d'extraire les informations suivantes :
 *  <ul>
 *  <li>Le nom</li>
 *  <li>La référence</li>
 *  <li>Le pourcentage de matière sèche</li>
 *  <li>Le pourcentage de matière volatile</li>
 *  <li>Le ratio matière sèche / matière volatile</li>
 *  <li>La contenance de potassium</li>
 *  <li>La contenance de phosphore</li>
 *  <li>La contenance d'azote ammoniacal</li>
 *  <li>La contenance d'azote kjeldahl</li>
 *  <li>Le bmp de la matière volatile</li>
 *  <li>Le bmp de la matière fraîche</li>
 *  <li>Le pourcentage de méthane parmis les gaz émis</li>
 *  </ul>
 *  </p>
 *  
 *  @author COLAS Valentin
 *  @version 1.0
 */

public class Extractor {

	
	
	/**
	 * L'adresse de destination des images et photos extraites
	 */
	
	
	/**
	 * 
	 * @param location
	 * 		L'adresse à laquelle ce trouve le fichier a inspecter
	 * @return Un objet Substrat contenant les informations extraites à partir de l'adresse du fichier passé en paramètre
	 * @see Substrat
	 */
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

		Substrat substrat = getInfosFromText(text);
		extractImage(location, substrat.getReference());

		return substrat;
	}
	
	
	/**
	 * 
	 * @param text
	 * 		Le texte contenu dans un fichier Substrat
	 * @return Un objet Substrat contenant les informations extraites à partir du texte issu d'un fichier Substrat
	 * @see Substrat
	 */
	private static Substrat getInfosFromText(String text) {
		Substrat substrat = new Substrat();
		
		String nom = extractRegex(text, "Préparation :	([0-9]+-[0-9]+(-[0-9]+)?)?(.+?)-(-)?[0-9]+\\/[0-9]+\\/[0-9]+", 3);
		if (nom != null) {
			substrat.setNom(nom.trim());
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

		
		String phosphore = extractRegex(text, "Phosphore total \\(P2O5\\)(.+)?\\.kg-1", 1);
		if (phosphore != null) {
			substrat.setPhosphore(phosphore.trim());
		} else {
			substrat.setPhosphore("-1");
		}
		

		
		String potassium = extractRegex(text, "Potassium total \\(K2O\\)(.+)?\\.kg-1", 1);
		if (phosphore != null) {
			substrat.setPotassium(potassium.trim());
		} else {
			substrat.setPotassium("-1");
		}


		String azoteK = extractRegex(text, "Azote total Kjeldahl \\(NTK\\)(.+)?\\.kg-1", 1);
		if (azoteK != null) {
			substrat.setAzoteK(azoteK.trim());
		} else {
			substrat.setAzoteK("-1");
		}


		String azoteA = extractRegex(text, "Azote ammoniacal \\(NH4\\+\\)(.+)?\\.kg-1", 1);
		if (azoteA != null) {
			substrat.setAzoteA(azoteA.trim());
		} else {
			substrat.setAzoteA("-1");
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

		String pctage = extractRegex(text, "CH4 \\(% vol\\.\\)	(.+%?)(.+%?)	(.+%?)", 3);
		if (pctage != null) {
			substrat.setPourcentageMethane(pctage.replaceAll("%", ""));
		} else {
			substrat.setPourcentageMethane("-1");
		}
		
		

		return substrat;
	}

	/**
	 * 
	 * @param text
	 * 		Le texte contenant l'information devant être extraite
	 * @param regex
	 * 		L'expression régulière premetant de retrouver l'information que l'on souhaite extraire
	 * @param i
	 * 		L'index du groupe de regex à extraire
	 * @return La chaîne de caractères qui correspond au groupe d'indice i de l'expression régulière trouvée dans le texte
	 */
	private static String extractRegex(String text, String regex, int i) {
		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
        	return matcher.group(i);
        }else {
        	return null;
        }
	}

	/**
	 * 
	 * @param location
	 * 		L'adresse du dossier que l'on souhaite inspecter
	 * @return Une liste contenant des objets de type Substrat contenant toutes les informations extraites à partir du dossier renseigné dans location
	 */
	public static List<Substrat> allDocInspector(String location) {
		
		List<Substrat> liste = new ArrayList<Substrat>();
		File repertoire = new File(location);
		File[] files = repertoire.listFiles();
		for (File file : files) {
			if (!file.isDirectory()) {
				liste.add(inspectDoc(file.getAbsolutePath()));
			}
		}
		return liste;
	}

	/**
	 * 
	 * @param location
	 * 		L'adresse du fichier substrat contenant les images à extraire
	 * @param ref
	 * 		La référence du substrat 
	 * 
	 */
	public static void extractImage(String location, String ref) {

		try {
			String destination = location.substring(0, location.lastIndexOf("\\"));
			String imagePath;
			HWPFDocument doc = new HWPFDocument(new FileInputStream(location));
			List<Picture> pics = doc.getPicturesTable().getAllPictures();
			Picture pic1 = (Picture) pics.get(0);
			FileOutputStream outputStream = null;
			
			if(pic1.suggestFileExtension().equals("emf")) {
				imagePath = destination + "\\courbes\\" + ref;
			} else {
				imagePath = destination + "\\photos\\" + ref;
			}
			
			outputStream = new FileOutputStream(imagePath + "." + pic1.suggestFileExtension());
			outputStream.write(pic1.getContent());
			outputStream.close();
			
			if (pics.size() > 5) {
				imagePath = destination + "\\photos\\" + ref;
				Picture pic2 = (Picture) pics.get(2);
				outputStream = new FileOutputStream(imagePath + "." + pic2.suggestFileExtension());
				outputStream.write(pic2.getContent());
				outputStream.close();
			}
			doc.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	private static void compressImages(String arg) {
		ImageCompressor.compressAll(arg + "\\courbes");
		ImageCompressor.compressAll(arg + "\\photos");
	}


	public static void convertImages(String arg) {
		PowerShell powerShell = PowerShell.openSession();
		System.out.println(powerShell.executeScript("scripts/convertImages.ps1", arg ).getCommandOutput());
		powerShell.close();
	}

	/**
	 * 
	 * @param args
	 * 		Contient l'adresse du dossier contenant les documents à inspecter
	 */
	public static void main(String[] args) {

//		DAO dao = new DAO();

		List<Substrat> liste = allDocInspector(args[0]);
		
		for (Substrat substrat : liste) {
			System.out.println(substrat);
		}
		
//		dao.createAllSubstrats(liste);
//		
		convertImages(args[0]);
		
		compressImages(args[0]);

	}


	

}
