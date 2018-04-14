package rcp3.study.resource;

import java.net.MalformedURLException;
import java.nio.file.Paths;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

/**
 * Contains the images. Image is created by stand alone java code.
 * 
 * @author Alex
 */
public class SWTImgResource {
	
	/**
	 * Heart image.
	 */
	public static final Image HEART = createImg("heart.png");
	
	/**
	 * Login image.
	 */
	public static final Image LOGIN = createImg("login.png");
	
	/**
	 * Add image.
	 */
	public static final Image ADD = createImg("add.png");
	
	// name is the name of image in icons folder.
	private static Image createImg(String name) {
		try {
			return ImageDescriptor.createFromURL(
					Paths.get(System.getProperty("user.dir"), "icons", name).toUri().toURL())
					.createImage();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
