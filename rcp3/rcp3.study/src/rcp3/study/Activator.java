package rcp3.study;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import rcp3.service.api.Calculate;
import rcp3.service.api.impl.CalculateUser;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

  // The plug-in ID
  public static final String PLUGIN_ID = "rcp3.study"; //$NON-NLS-1$

  // The shared instance
  private static Activator plugin;

  /**
   * The constructor
   */
  public Activator() {
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
   * BundleContext)
   */
  @Override
  public void start(BundleContext context) throws Exception {
    super.start(context);
    plugin = this;

    ServiceReference<?> calcaulteServiceRef = context.getServiceReference(Calculate.class.getName());
    if (calcaulteServiceRef != null) {
      Calculate calculate = (Calculate) context.getService(calcaulteServiceRef);
      System.out.println("found calculate");
    }

    ServiceReference<?> calcaulteUserServiceRef = context.getServiceReference(CalculateUser.class.getName());
    if (calcaulteUserServiceRef != null) {
      CalculateUser calculateUser = (CalculateUser) context.getService(calcaulteUserServiceRef);
      System.out.println("found calculate user");
    }

    ServiceReference<?> viewInputServiceRef = context.getServiceReference(ViewInput.class.getName());
    if (viewInputServiceRef != null) {
      ViewInput viewInput = (ViewInput) context.getService(viewInputServiceRef);
      System.out.println("found view put");
    }

  }

  /*
   * (non-Javadoc)
   *
   * @see
   * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    plugin = null;
    super.stop(context);
  }

  /**
   * Returns the shared instance
   *
   * @return the shared instance
   */
  public static Activator getDefault() {
    return plugin;
  }

  /**
   * Returns an image descriptor for the image file at the given plug-in relative
   * path
   *
   * @param path
   *          the path
   * @return the image descriptor
   */
  public static ImageDescriptor getImageDescriptor(String path) {
    return imageDescriptorFromPlugin(PLUGIN_ID, path);
  }
}
