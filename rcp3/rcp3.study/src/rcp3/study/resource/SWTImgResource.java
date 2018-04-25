package rcp3.study.resource;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
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
   * Refresh image.
   */
  public static final Image REFRESH = createImg("refresh-24.png");

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
    return ImageDescriptor.createFromURL(getImgURL(name)).createImage();
  }

  private static URL getImgURL(String name) {
    URL url = null;

    try {
      Path path = Paths.get(System.getProperty("user.dir"), "icons", name);
      if (Files.exists(path)) {
        // Construct URL when start as java application.
        url = path.toUri().toURL();
      } else {
        // Construct URL when start as Plug-in/RCP application.
        url = new URL(String.format("platform:/plugin/%s/icons/%s", "rcp3.study", name));
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    return url;
  }

}
