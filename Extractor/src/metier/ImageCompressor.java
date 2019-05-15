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
	 * @param ratio
	 * Le niveau de compression souhaité 
	 * @throws IOException
	 */
	public static void compress(String location, double ratio) throws IOException {
        File infile = new File(location);
        File outfile = new File(location);

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
	
	/**
	 * 
	 * @param location
	 * L'adresse du dossier contenant les images à compresser
	 * @param ratio
	 * Le niveau de compression souhaité 
	 */
	public static void compressAll(String location, double ratio) {
		File repertoire = new File(location);
		File[] files = repertoire.listFiles();
		for (File file : files) {
			try {
				compress(file.getAbsolutePath(), ratio);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

  
}