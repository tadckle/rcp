package rcp3.study.viewers;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColorCellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import rcp3.study.viewers.Student.Sex;

/**
 * Directly add CellEditor to TaleViewer.
 * 
 * @author Alex
 */
public class TableViewerUsage2 extends TableViewerUsage1 {

  public static void main(String[] args) {
    new TableViewerUsage2().openShell();
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
      switch (property) {
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
      switch (property) {
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
  protected void addEditCapability(TableViewer tableViewer) {
    Table table = tableViewer.getTable();
    tableViewer.setCellEditors(createEditors(table));

    tableViewer.setColumnProperties(new String[] { "0", "1", "2", "3", "4", "5" });
    tableViewer.setCellModifier(new StudentModifier(tableViewer));
  }

  private CellEditor[] createEditors(Table table) {
    CellEditor[] cellEditors = new CellEditor[6];
    cellEditors[0] = new TextCellEditor(table);

    cellEditors[1] = new ComboBoxCellEditor(table, new String[] { Sex.MALE.getName(), Sex.FEMALE.getName() });

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
    return cellEditors;
  }

}
