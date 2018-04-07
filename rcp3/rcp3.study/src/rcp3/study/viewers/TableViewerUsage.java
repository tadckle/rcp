package rcp3.study.viewers;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColorCellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import rcp3.study.ShellRunner;
import rcp3.study.viewers.Student.Sex;

/**
 * Show how to use TableViewer.
 * 
 * @author Alex
 */
public class TableViewerUsage implements ShellRunner {
	
	private TableViewer tableViewer;
	
	private Map<RGB, Color> colorMap = new HashMap<>();
	
	public static void main(String[] args) {
		new TableViewerUsage().openShell();
	}
	
	/**
	 * Construct table row element from input object.
	 * The implementation is exactly same as ArraContentProvider.
	 * 
	 * @author Alex
	 */
	private static class StudentContentProvider implements IStructuredContentProvider {
		@Override
		public void dispose() {
			// Dispose some resource.
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// Do something when input is changed.
		}

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof Object[]) {
				return (Object[]) inputElement;
			}
	        if (inputElement instanceof Collection) {
				return ((Collection) inputElement).toArray();
			}
	        return new Object[0];
		}
	}
	
	private static class StudentLabelProvider extends ColumnLabelProvider implements ITableLabelProvider {
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
	
	private static class StudentModifier implements ICellModifier {
		private TableViewer tableViewer;
		
		private StudentModifier(TableViewer tableViewer) {
			this.tableViewer = tableViewer;
		}
		
		@Override
		public boolean canModify(Object element, String property) {
			return true;
		}

		@Override
		public Object getValue(Object element, String property) {
			Student student = (Student) element;
			switch(property) {
				case "0":
					return student.getName();
				case "1":
					return student.getSex().ordinal();
				case "2":
					return student.getCountry();
				case "3":
					return String.valueOf(student.getHeight());
				case "4":
					return student.isMarried();
				case "5":
					return student.getFavoriteColor();
				default: 
					return null;
			}
		}

		@Override
		public void modify(Object element, String property, Object value) {
			TableItem item = (TableItem) element;
			Student student = (Student) item.getData();
			switch(property) {
				case "0":
					student.setName((String) value);
					break;
				case "1":
					student.setSex(Sex.fromOrdinal((Integer) value));
					break;
				case "2":
					student.setCountry(String.valueOf(value));
					break;
				case "3":
					student.setHeight(Double.valueOf((String) value));
					break;
				case "4":
					student.setMarried((boolean) value);
					break;
				case "5":
					student.setFavoriteColor((RGB) value);
					break;
				default: 
					break;
					// Do nothing.
			}			
			
			tableViewer.refresh(student);
		}
	}
	

	@Override
	public void fillContent(Composite parent) {
		tableViewer = new TableViewer(parent, SWT.CHECK | SWT.FULL_SELECTION);
		tableViewer.setContentProvider(new StudentContentProvider());
		
		Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableViewerColumn nameViewerColumn = createViewerColumn("Name");
		TableViewerColumn sexViewerColumn = createViewerColumn("Sex");
		TableViewerColumn countryViewerColumn = createViewerColumn("Country");
		TableViewerColumn heightViewerColumn = createViewerColumn("Height");
		TableViewerColumn marriedViewerColumn = createViewerColumn("Married");
		TableViewerColumn colorViewerColumn = createViewerColumn("Color");
		
		// Table's label provider and column itself's label provider can override each other.
		// The last set will take effect.
		tableViewer.setLabelProvider(new StudentLabelProvider());
		
		colorViewerColumn.setLabelProvider(new ColumnLabelProvider() {
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
		});
		
		CellEditor[] cellEditors = new CellEditor[6];
		cellEditors[0] = new TextCellEditor(table);
		
		cellEditors[1] = new ComboBoxCellEditor(table, 
				new String[]{Sex.MALE.getName(), Sex.FEMALE.getName()});
		
		cellEditors[2] = new TextCellEditor(table);
		
		cellEditors[3] = new TextCellEditor(table);
		cellEditors[3].setValidator(new ICellEditorValidator() {
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
		
		cellEditors[4] = new CheckboxCellEditor(table);
		
		cellEditors[5] = new ColorCellEditor(table);
		
		tableViewer.setCellEditors(cellEditors);
		
		tableViewer.setColumnProperties(new String[] {"0", "1", "2", "3", "4", "5"});
		
		tableViewer.setCellModifier(new StudentModifier(tableViewer));
		
		tableViewer.setInput(StudentFactory.create());
		Arrays.stream(table.getColumns()).forEach(TableColumn::pack);
	}
	
	private TableViewerColumn createViewerColumn(String text) {
		TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn column = viewerColumn.getColumn();
		column.setText(text);
		
		return viewerColumn;
	}

}
