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
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Table;

import rcp3.study.viewers.Student.Sex;

/**
 * Add EditingSupport for each TableViewerColumn.
 * 
 * @author Alex
 */
public class TableViewerUsage3 extends TableViewerUsage1 {
	
	private static class StudentColumnEditingSupport extends EditingSupport {
		private final int index;
		private final Table table;
		
		public StudentColumnEditingSupport(TableViewerColumn viewerColumn) {
			super(viewerColumn.getViewer());
			
			table = ((TableViewer) viewerColumn.getViewer()).getTable();
			index = table.indexOf(viewerColumn.getColumn());
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			switch(index) {
			case 0:
				return new TextCellEditor(table);
			case 1:
				return new ComboBoxCellEditor(table, 
						new String[]{Sex.MALE.getName(), Sex.FEMALE.getName()});
			case 2:
				return new TextCellEditor(table);
			case 3:
				TextCellEditor heightEditor = new TextCellEditor(table);
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
				return new CheckboxCellEditor(table);
			case 5:
				return new ColorCellEditor(table);
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

			((TableViewer) getViewer()).refresh(student);
		}
		
	}
	
	public static void main(String[] args) {
		new TableViewerUsage3().openShell();
	}

	@Override
	protected void addEditCapability(TableViewer tableViewer) {
		viewerColumns.forEach(viewerColumn -> viewerColumn.setEditingSupport(new StudentColumnEditingSupport(viewerColumn)));
	}

}
