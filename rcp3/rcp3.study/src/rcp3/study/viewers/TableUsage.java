package rcp3.study.viewers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import rcp3.study.ShellRunner;

/**
 * Example of SWT Table.
 * 
 * @author Alex
 */
public class TableUsage implements ShellRunner {

  public static void main(String[] args) {
    new TableUsage().openShell();
  }

  @Override
  public void fillContent(Composite parent) {
    Table table = new Table(parent, SWT.BORDER | SWT.VIRTUAL | SWT.FULL_SELECTION | SWT.CHECK);
    table.setHeaderVisible(true);
    table.setLinesVisible(true);

    createColumn(table, "Column1");
    createColumn(table, "Column2");

    table.setItemCount(10000);

    table.addListener(SWT.SetData, event -> {
      // Get current empty TableItem.
      TableItem item = (TableItem) event.item;
      int itemIndex = table.indexOf(item);
      item.setText(retrieveItemText(itemIndex));
      if (itemIndex % 2 == 0) {
        item.setChecked(true);
      }
    });

    table.setSelection(100);
    // table.setTopIndex(100);
  }

  private String[] retrieveItemText(int itemIndex) {
    // In real world, you may get data from database or other business logic.
    return new String[] { String.format("State %s", itemIndex), String.format("City %s", itemIndex) };
  }

  private TableColumn createColumn(Table table, String text) {
    TableColumn column = new TableColumn(table, SWT.NONE);
    column.setText(text);
    column.setWidth(100);
    return column;
  }

}
