package metier;

import java.awt.RenderingHints;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.media.jai.JAI;
import javax.media.jai.OpImage;
import javax.media.jai.RenderedOp;

import com.sun.media.jai.codec.SeekableStream;
/**
 * <b> Cette classe permet de compresser des images jpg </b>
 * @author COLAS Valentin
 * @version 1.0
 */
public class ImageCompressor {
    
	/**
	 * 
	 * @param location
	 * L'adresse de l'image à compresser
	 * @throws IOException
	 */
	public static void compress(String location) throws IOException {
        File infile = new File(location);
        File outfile = new File(location);
        
        double startSize = infile.length()/1024;
        System.out.println(startSize);
        double finalSize = 100;
        
        double ratio = computeRatio(startSize, finalSize);
        
        System.out.println(ratio);
        
        if(startSize >=  2 * finalSize) {
        	BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
                    infile));
           

            SeekableStream s = SeekableStream.wrapInputStream(bis, true);

            RenderedOp image = JAI.create("stream", s);
            ((OpImage) image.getRendering()).setTileCache(null);

            RenderingHints qualityHints = new RenderingHints(
                    RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);

            RenderedOp resizedImage = JAI.create("SubsampleAverage", image, ratio,
            		ratio, qualityHints);

            bis.close();
            
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(outfile));
            
            JAI.create("encode", resizedImage, bos, "JPEG", null);
        }  

    }
	
	/**
	 * 
	 * @param startSize
	 * 			La taille du fichier original
	 * @param finalSize
	 * 			La taille du fichier final
	 * @return le ratio à utiliser afin que le fichier une fois compressé soit de taille environ égale à finalSize
	 */
	private static double computeRatio(double startSize, double finalSize) {
		return (-finalSize * Math.log10(startSize))/(-startSize - finalSize * Math.log10(startSize));
	}

	/**
	 * Comperesse toutes les images se situant dans le dossier se trouvant à l'adresse en paramètre
	 * @param location
	 * L'adresse du dossier contenant les images à compresser
	 */
	public static void compressAll(String location) {
		File repertoire = new File(location);
		File[] files = repertoire.listFiles();
		for (File file : files) {
			try {
				compress(file.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

  
}