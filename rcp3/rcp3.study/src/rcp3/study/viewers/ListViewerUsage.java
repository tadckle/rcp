package rcp3.study.viewers;

import java.util.Objects;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IElementComparer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.google.common.collect.Lists;

import rcp3.study.ShellRunner;

/**
 * Illustrate how to use ListViewer.
 *
 * @author alzhang
 */
public class ListViewerUsage implements ShellRunner {

  static class Person {
    private String name = "";
    private String gender = "";

    public Person(String name, String gender) {
      this.name = name;
      this.gender = gender;
    }

    @Override
    public int hashCode() {
      return Objects.hash(name, gender);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null || obj.getClass() != this.getClass()) {
        return false;
      }
      return Objects.equals(name, ((Person) obj).name) && Objects.equals(gender, ((Person) obj).gender);
    }
  }

  private final ViewerFilterOnCellText viewerFilter = new ViewerFilterOnCellText();

  public static void main(String[] args) {
    new ListViewerUsage().openShell();
  }

  @Override
  public void fillContent(Composite parent) {
    parent.setLayout(new GridLayout(1, false));

    Text searchTxt = new Text(parent, SWT.BORDER);
    GridDataFactory.swtDefaults().applyTo(searchTxt);


    createTableViewer(parent);
    createListViewer(parent);
    createComboViewer(parent);
  }

  private void createTableViewer(Composite parent) {
    TableViewer tableViewer = new TableViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
    tableViewer.setFilters(viewerFilter);
    tableViewer.setContentProvider(ArrayContentProvider.getInstance());
    Table table = tableViewer.getTable();
    GridDataFactory.swtDefaults().applyTo(table);
    table.setHeaderVisible(true);

    tableViewer.setComparer(new IElementComparer() {
      @Override
      public boolean equals(Object elementA, Object elementB) {
        return elementA == null || elementB == null ? false : elementA == elementB;
      }

      @Override
      public int hashCode(Object element) {
        return element != null ? element.hashCode() : 0;
      }
    });

    TableViewerColumn nameViewerCln = new TableViewerColumn(tableViewer, SWT.NONE);
    TableColumn column = nameViewerCln.getColumn();
    column.setText("Name");
    column.setWidth(100);
    nameViewerCln.setLabelProvider(new ColumnLabelProvider() {
      @Override
      public String getText(Object element) {
        return element instanceof Person ? ((Person) element).name : "";
      }
    });

    nameViewerCln.setEditingSupport(new EditingSupport(tableViewer) {
      @Override
      protected CellEditor getCellEditor(Object element) {
        return new TextCellEditor(table);
      }

      @Override
      protected boolean canEdit(Object element) {
        return true;
      }

      @Override
      protected Object getValue(Object element) {
        return ((Person) element).name;
      }

      @Override
      protected void setValue(Object element, Object value) {
        ((Person) element).name = value.toString();
        tableViewer.refresh(element);
      }
    });

    TableViewerColumn genderViewerCln = new TableViewerColumn(tableViewer, SWT.NONE);
    TableColumn column2 = genderViewerCln.getColumn();
    column2.setText("Gender");
    column2.setWidth(100);
    genderViewerCln.setLabelProvider(new ColumnLabelProvider() {
      @Override
      public String getText(Object element) {
        return element instanceof Person ? ((Person) element).gender : "";
      }
    });

    Person p1 = new Person("Tom", "male");
    Person p2 = new Person("Jerry", "male");
    tableViewer.setInput(Lists.newArrayList(p1, p2));
  }

  private void createComboViewer(Composite parent) {
    ComboViewer viewer = new ComboViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL);
    viewer.setFilters(viewerFilter);
    GridDataFactory.swtDefaults().applyTo(viewer.getCombo());
    viewer.setContentProvider(ArrayContentProvider.getInstance());

    viewer.setLabelProvider(new LabelProvider() {
      @Override
      public String getText(Object element) {
        return element instanceof Person ? ((Person) element).name : "";
      }
    });

    String donald = "Donald";
    Person p1 = new Person(donald, "male");
    Person p2 = new Person("Trump", "male");
    viewer.setInput(Lists.newArrayList(p1, p2));
  }

  private void createListViewer(Composite parent) {
    ListViewer viewer = new ListViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL);
    GridDataFactory.swtDefaults().applyTo(viewer.getControl());
    viewer.setContentProvider(ArrayContentProvider.getInstance());

    viewer.setLabelProvider(new LabelProvider() {
      @Override
      public String getText(Object element) {
        return element instanceof Person ? ((Person) element).name : "";
      }
    });

    Person p1 = new Person("Donald", "male");
    Person p2 = new Person("XXXXXX", "female");
    viewer.setInput(Lists.newArrayList(p1, p2));
  }

}
