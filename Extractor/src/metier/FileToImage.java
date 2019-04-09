package metier;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

public class FileToImage implements Callable<Image> {

	private File file;
	
	public FileToImage(File file) {
		this.file = file;
	}
	@Override
	public Image call() throws Exception {
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
		int i = 0;
		for (byte b : fileData) {
			binaire += Integer.toBinaryString((b & 0xFF) + 0x100).substring(1);
			i++;
			System.out.println(i + "/" + fileData.length);
		}
		return new Image(file.getName().substring(0, file.getName().length() - 4), binaire);
	}

}
