package rcp3.study.viewers;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * The LabelProvider for Student favorite color.
 * 
 * @author Alex
 */
public class ColorLabelProvider extends ColumnLabelProvider {
	
	private Map<RGB, Color> colorMap = new HashMap<>();
	
	@Override
	public String getText(Object element) {
		return "Color";
	}

	@Override
	public Color getForeground(Object element) {
		Student student = (Student) element;
		RGB rgb = student.getFavoriteColor();
		
		if (!colorMap.containsKey(rgb)) {
			colorMap.put(rgb, new Color(Display.getDefault(), rgb));
		}
		return colorMap.get(rgb);
	}
}