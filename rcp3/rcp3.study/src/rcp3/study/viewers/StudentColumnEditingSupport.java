package rcp3.study.viewers;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColorCellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;

import rcp3.study.viewers.Student.Sex;

/**
 * The EditingSupport for student column.
 * 
 * @author Alex
 */
public class StudentColumnEditingSupport extends EditingSupport {
	private final int index;
	private final Composite comp;
	
	public StudentColumnEditingSupport(ViewerColumn viewerColumn) {
		super(viewerColumn.getViewer());
		
		if (viewerColumn instanceof TableViewerColumn) {
			TableViewer tableViewer = (TableViewer) viewerColumn.getViewer();
			comp = tableViewer.getTable();
			index = tableViewer.getTable().indexOf(((TableViewerColumn) viewerColumn).getColumn());
		} else if (viewerColumn instanceof TreeViewerColumn) {
			TreeViewer treeViewer = (TreeViewer) viewerColumn.getViewer();
			comp = treeViewer.getTree();
			index = treeViewer.getTree().indexOf(TreeViewerColumn.class.cast(viewerColumn).getColumn());
		} else {
			index = 0;
			comp = (Composite) viewerColumn.getViewer().getControl();
		}
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		switch(index) {
		case 0:
			return new TextCellEditor(comp);
		case 1:
			return new ComboBoxCellEditor(comp, 
					new String[]{Sex.MALE.getName(), Sex.FEMALE.getName()});
		case 2:
			return new TextCellEditor(comp);
		case 3:
			TextCellEditor heightEditor = new TextCellEditor(comp);
			heightEditor.setValidator(new ICellEditorValidator() {
				@Override
				public String isValid(Object value) {
					try {
						Double.parseDouble((String) value); 
						return null;
					} catch (Exception e) {
						return value.toString();
					}
				}
			});
			return heightEditor;
		case 4:
			return new CheckboxCellEditor(comp);
		case 5:
			return new ColorCellEditor(comp);
		default: 
			return null;
		}
	}

	@Override
	protected boolean canEdit(Object element) {
		return element != null;
	}

	@Override
	protected Object getValue(Object element) {
		Student student = (Student) element;
		switch(index) {
		case 0:
			return student.getName();
		case 1:
			return student.getSex().ordinal();
		case 2:
			return student.getCountry();
		case 3:
			return String.valueOf(student.getHeight());
		case 4:
			return student.isMarried();
		case 5:
			return student.getFavoriteColor();
		default: 
			return null;
		}
	}

	@Override
	protected void setValue(Object element, Object value) {
		Student student = (Student) element;
		
		switch(index) {
		case 0:
			student.setName((String) value);
			break;
		case 1:
			student.setSex(Sex.fromOrdinal((Integer) value));
			break;
		case 2:
			student.setCountry(String.valueOf(value));
			break;
		case 3:
			student.setHeight(Double.valueOf((String) value));
			break;
		case 4:
			student.setMarried((boolean) value);
			break;
		case 5:
			student.setFavoriteColor((RGB) value);
			break;
		default: 
			break;
			// Do nothing.
		}			

		(getViewer()).refresh(student);
	}
	
}