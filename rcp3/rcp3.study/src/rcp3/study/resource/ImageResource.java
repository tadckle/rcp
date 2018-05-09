package rcp3.study.resource;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.slf4j.LoggerFactory;

/**
 * Contains the images. Image is created by stand alone java code.
 *
 * @author Alex
 */
public class ImageResource {

  /**
   * Arrow left image.
   */
  public static final Image ARROW_LEFT = createImg("arrow-left.png");

  /**
   * Arrow right image.
   */
  public static final Image ARROW_RIGHT = createImg("arrow-right.png");

  /**
   * Arrow up image.
   */
  public static final Image ARROW_UP = createImg("arrow-up.png");

  /**
   * Arrow down image.
   */
  public static final Image ARROW_DOWN = createImg("arrow-down.png");

  /**
   * Pointer image.
   */
  public static final Image POINTER = createImg("pointer.png");

  /**
   * Zoom in image.
   */
  public static final Image ZOOMIN = createImg("zoomin.png");

  /**
   * Zoom out image.
   */
  public static final Image ZOOMOUT = createImg("zoomout.png");

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
      if (path.toFile().exists()) {
        // Construct URL when start as java application.
        url = path.toUri().toURL();
      } else {
        // Construct URL when start as Plug-in/RCP application.
        url = new URL(String.format("platform:/plugin/%s/icons/%s", "rcp3.study", name));
      }
    } catch (MalformedURLException e) {
      LoggerFactory.getLogger(ImageResource.class).error(e.getMessage(), e);
    }

    return url;
  }

  private ImageResource() {
    // Hide constructor.
  }

}
