package metier;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

class ExtractorTest {

	/**
	 * Besoins : un fichier .doc pour tester voire plusieurs, un objet substrat correspondant a ce qu'il doit être extrait, 
	 * 
	 */
	
	String location = "C:\\Users\\valen\\Desktop\\Stage\\doc\\test\\";
	
	Substrat test = new Substrat("201808-561-CVB-111R1", "Boues stations", 4.0, 3.43, 85.78, "1,50	g" , "0,28	g", "3,26	gN", "-1", 135.62, 4.65, "76,01	");
	
	@Test
	void testInspectDoc() {
		Substrat inspectDoc = Extractor.inspectDoc(location + "201808-561-CVB-111R1.doc");
		assertEquals(inspectDoc.toString(), test.toString());
	}

	@Test
	void testExtractImage() {
		File f = new File(location + "courbes\\201808-561-CVB-111R1.emf");
		File f2 = new File(location + "photos\\201808-561-CVB-111R1.jpg");
		assertTrue(f.isFile());
		assertTrue(f2.isFile());
	}
	
	@Test
	void testConvertImage() {
		Extractor.convertImages(location);
		File f = new File(location + "courbes\\201808-561-CVB-111R1.jpg");
		assertTrue(f.isFile());
	}

}
