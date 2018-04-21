package rcp3.study.nebula;

import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.nebula.widgets.grid.GridColumnGroup;
import org.eclipse.nebula.widgets.grid.GridEditor;
import org.eclipse.nebula.widgets.grid.GridItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import rcp3.study.ShellRunner;

/**
 * Illustrate how to use nebula Grid.
 * 
 * @author Alex
 */
public class GridUsage implements ShellRunner {

  public static void main(String[] args) {
    new GridUsage().openShell();
  }

  @Override
  public void fillContent(Composite parent) {
    Grid grid = new Grid(parent, SWT.V_SCROLL | SWT.H_SCROLL);
    grid.setAutoHeight(true);
    grid.setAutoWidth(true);

    grid.setHeaderVisible(true);

    String[] columnHeadTxt = { "Column 1", "Column 2", "Column 3", "Summary" };

    String LONG_TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
        + "Vestibulum lectus augue, pulvinar quis cursus nec, imperdiet nec ante. "
        + "Cras sit amet arcu et enim adipiscing pellentesque. Suspendisse mi "
        + "felis, dictum a lobortis nec, placerat in diam. Proin lobortis tortor "
        + "at nunc facilisis aliquet. Praesent eget dignissim orci. Ut iaculis bibendum.";

    String[] row1Txt = { "Root Item", "A", "1", "Z" };
    String[] row2Txt = { "Second Item", "B", "2", "H" };
    String[] row3Txt = { "Third Item", "C", LONG_TEXT, "K" };

    GridColumn column1 = new GridColumn(grid, SWT.CHECK);
    column1.setText(columnHeadTxt[0]);
    column1.setWidth(150);
    column1.setTree(true);
    column1.setHeaderControl(createColumn1HeaderControl(grid));

    GridColumnGroup columnGroup = new GridColumnGroup(grid, SWT.TOGGLE);
    columnGroup.setText("Column Group");

    GridColumn column2 = createColumn(columnGroup, columnHeadTxt[1]);
    // column2.setSummary(false);
    // column2.setDetail(true);

    GridColumn column3 = createColumn(columnGroup, columnHeadTxt[2]);
    column3.setWordWrap(true);
    column3.setSummary(false);
    column3.setDetail(true);

    GridColumn summary = createColumn(columnGroup, columnHeadTxt[3]);
    summary.setSummary(true);
    summary.setDetail(false);

    GridItem item1 = new GridItem(grid, SWT.NONE);
    fillGridItemTxt(item1, row1Txt);
    item1.setColumnSpan(0, 1);
    item1.setChecked(0, true);
    item1.setExpanded(true);

    addEditor(grid, item1, 2);

    GridItem item2 = new GridItem(item1, SWT.NONE);
    fillGridItemTxt(item2, row2Txt);

    GridItem item3 = new GridItem(item2, SWT.NONE);
    fillGridItemTxt(item3, row3Txt);
  }

  private void addEditor(Grid grid, GridItem gridItem, int columnIndex) {
    CCombo combo = new CCombo(grid, SWT.NONE);
    combo.setText("CCombo Widget");
    combo.add("item 1");
    combo.add("item 2");
    combo.add("item 3");

    GridEditor editor = new GridEditor(grid);
    editor.minimumWidth = 50;
    editor.grabHorizontal = true;
    editor.setEditor(combo, gridItem, columnIndex);
  }

  private Composite createColumn1HeaderControl(Grid grid) {
    Composite comp = new Composite(grid, SWT.NONE);
    comp.setLayout(new RowLayout(SWT.HORIZONTAL));

    Button selectAll = new Button(comp, SWT.FLAT);
    selectAll.setText("Select All");

    Button clearSelection = new Button(comp, SWT.FLAT);
    clearSelection.setText("Clear");
    return comp;
  }

  private void fillGridItemTxt(GridItem item, String[] texts) {
    for (int i = 0; i < texts.length; ++i) {
      item.setText(i, texts[i]);
    }
  }

  private GridColumn createColumn(GridColumnGroup columnGroup, String text) {
    GridColumn column = new GridColumn(columnGroup, SWT.NONE);
    column.setText(text);
    column.setWidth(100);
    return column;
  }

}
