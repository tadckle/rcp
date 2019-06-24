package rcp3.study.viewers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import rcp3.study.ShellRunner;

/**
 * A sample that only show data in TableViewer.
 *
 * @author Alex
 */
public class TableViewerUsage1 implements ShellRunner {

  protected List<TableViewerColumn> viewerColumns = new ArrayList<>();

  private final FormToolkit toolkit = new FormToolkit(Display.getDefault());
  private final ViewerFilterOnCellText viewerFilter = new ViewerFilterOnCellText();
  private TableViewer tableViewer;

  public static void main(String[] args) {
    new TableViewerUsage1().openShell();
  }

  @Override
  public void fillContent(Composite parent) {
    GridLayoutFactory.swtDefaults().numColumns(2).applyTo(parent);
    toolkit.adapt(parent);

    Label searchLbl = toolkit.createLabel(parent, "Search", SWT.NONE);
    GridDataFactory.swtDefaults().applyTo(searchLbl);

    Text searchText = toolkit.createText(parent, "", SWT.BORDER);
    GridDataFactory.swtDefaults().hint(300, SWT.DEFAULT).applyTo(searchText);
    searchText.addModifyListener(event -> Display.getDefault().asyncExec(() -> {
        if (tableViewer != null) {
          viewerFilter.setSearchText(searchText.getText());
          tableViewer.refresh();
        }
      }));

    tableViewer = ViewerFactory.instance()
        .enableSort(new SimpleDateFormat("dd-MM-yyyy"))
        .createTableViewer(parent, SWT.CHECK | SWT.FULL_SELECTION);
    tableViewer.addFilter(viewerFilter);
    tableViewer.setContentProvider(new StudentTableContentProvider());

    tableViewer.refresh();

    Table table = tableViewer.getTable();
    GridDataFactory.fillDefaults().grab(true, true).span(2, 1).applyTo(table);
    table.setHeaderVisible(true);
    table.setLinesVisible(true);

    viewerColumns.add(createViewerColumn(tableViewer, "Name"));
    viewerColumns.add(createViewerColumn(tableViewer, "Sex"));
    viewerColumns.add(createViewerColumn(tableViewer, "Country"));
    viewerColumns.add(createViewerColumn(tableViewer, "Height"));
    viewerColumns.add(createViewerColumn(tableViewer, "Married"));
    viewerColumns.add(createViewerColumn(tableViewer, "Date"));
    TableViewerColumn colorViewerColumn = createViewerColumn(tableViewer, "Color");
    viewerColumns.add(colorViewerColumn);

    tableViewer.setLabelProvider(new StudentLabelProvider());
    colorViewerColumn.setLabelProvider(new ColorLabelProvider());

    addEditCapability(tableViewer);
    tableViewer.setInput(StudentFactory.tableInput());
    Arrays.stream(table.getColumns()).forEach(TableColumn::pack);
  }

  private TableViewerColumn createViewerColumn(TableViewer tableViewer, String text) {
    TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
    TableColumn column = viewerColumn.getColumn();
    column.setText(text);

    return viewerColumn;
  }

  protected void addEditCapability(TableViewer tableViewer) {
    // Do nothing.
  }

}
