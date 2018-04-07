package rcp3.study.viewers;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * The label provider for Student columns.
 * 
 * @author Alex
 */
public class StudentLabelProvider extends ColumnLabelProvider implements ITableLabelProvider {
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		Student student = (Student) element;
		switch(columnIndex) {
			case 0:
				return student.getName();
			case 1:
				return student.getSex().getName();
			case 2:
				return student.getCountry();
			case 3:
				return String.valueOf(student.getHeight());
			case 4:
				return student.isMarried() ? "YES" : "NO";
			case 5:
				return "";
			default:
			return null;
		}
	}
}