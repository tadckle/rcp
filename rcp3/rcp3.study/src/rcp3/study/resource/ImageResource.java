package rcp3.study.resource;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class ImageResource {
	
	private static final String PLUGIN_ID = "rcp3.study";

	public static final ImageDescriptor LOGIN = createImg("icons/login.png");
	
	public static final ImageDescriptor ADD = createImg("icons/add.png");
	
	public static final ImageDescriptor HEART = createImg("icons/heart.png");
			
	private static ImageDescriptor createImg(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
