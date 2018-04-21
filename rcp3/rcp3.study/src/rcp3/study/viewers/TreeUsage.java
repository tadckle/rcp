package rcp3.study.viewers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import rcp3.study.ShellRunner;

/**
 * Illustrate how to use SWT Tree.
 * 
 * @author Alex
 */
public class TreeUsage implements ShellRunner {

  public static void main(String[] args) {
    new TreeUsage().openShell();
  }

  @Override
  public void fillContent(Composite parent) {
    Tree tree = new Tree(parent, SWT.FULL_SELECTION | SWT.MULTI);
    tree.setHeaderVisible(true);
    tree.setLinesVisible(true);

    TreeColumn column1 = new TreeColumn(tree, SWT.NONE);
    column1.setText("Column1");

    TreeColumn column2 = new TreeColumn(tree, SWT.NONE);
    column2.setText("Column2");

    TreeItem item1 = new TreeItem(tree, SWT.NONE);
    item1.setText(new String[] { "a1", "b1" });

    TreeItem item2 = new TreeItem(item1, SWT.NONE);
    item2.setText(new String[] { "a2", "b2" });

    TreeItem item3 = new TreeItem(item1, SWT.NONE);
    item3.setText(new String[] { "a3", "b3" });

    TreeItem item4 = new TreeItem(item3, SWT.NONE);
    item4.setText(new String[] { "a4", "b4" });

    TreeItem item5 = new TreeItem(item3, SWT.NONE);
    item5.setText(new String[] { "a5", "b5" });

    item1.setExpanded(true);
    column1.pack();
    column2.pack();
  }

}
