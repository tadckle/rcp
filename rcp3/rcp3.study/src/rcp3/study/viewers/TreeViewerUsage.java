package rcp3.study.viewers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import rcp3.study.ShellRunner;

/**
 * Illustrate how to use TreeViewer.
 *
 * @author Alex
 */
public class TreeViewerUsage implements ShellRunner {

  public static void main(String[] args) {
    new TreeViewerUsage().openShell();
  }

  private List<TreeViewerColumn> viewerColumns = new ArrayList<>();

  @Override
  public void fillContent(Composite parent) {
    TreeViewer treeViewer = ViewerFactory.instance()
        .enableSort(new SimpleDateFormat("dd-MM-yyyy"))
        .createTreeViewer(parent, SWT.FULL_SELECTION);
    treeViewer.setContentProvider(new StudentTreeContentProvider());
    treeViewer.setAutoExpandLevel(2);

    Tree tree = treeViewer.getTree();
    tree.setHeaderVisible(true);
    tree.setLinesVisible(true);

    viewerColumns.add(createViewerColumn(treeViewer, "Name"));
    viewerColumns.add(createViewerColumn(treeViewer, "Sex"));
    viewerColumns.add(createViewerColumn(treeViewer, "Country"));
    viewerColumns.add(createViewerColumn(treeViewer, "Height"));
    viewerColumns.add(createViewerColumn(treeViewer, "Married"));
    TreeViewerColumn colorViewerColumn = createViewerColumn(treeViewer, "Color");
    viewerColumns.add(colorViewerColumn);

    treeViewer.setLabelProvider(new StudentLabelProvider());
    colorViewerColumn.setLabelProvider(new ColorLabelProvider());

    viewerColumns
        .forEach(viewerColumn -> viewerColumn.setEditingSupport(new StudentColumnEditingSupport(viewerColumn)));
    treeViewer.setInput(StudentFactory.treeInput());

    Arrays.stream(tree.getColumns()).forEach(TreeColumn::pack);
  }

  private TreeViewerColumn createViewerColumn(TreeViewer treeViewer, String text) {
    TreeViewerColumn viewerColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
    TreeColumn column = viewerColumn.getColumn();
    column.setText(text);

    return viewerColumn;
  }

}
