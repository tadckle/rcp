package rcp3.study.viewers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import rcp3.study.ShellRunner;
import rcp3.study.viewers.TableViewerUsage1.StudentLabelProvider;
import rcp3.study.viewers.TableViewerUsage3.StudentColumnEditingSupport;

/**
 * Illustrate how to use TreeViewer.
 * 
 * @author Alex
 */
public class TreeViewerUsage implements ShellRunner {
	
	private Map<RGB, Color> colorMap = new HashMap<>();
	
	public static void main(String[] args) {
		new TreeViewerUsage().openShell();
	}
	
	private List<TreeViewerColumn> viewerColumns = new ArrayList<>();
	
	private static class StudentTreeContentProvider extends ArrayContentProvider implements ITreeContentProvider {
		@Override
		public Object[] getChildren(Object parentElement) {
			return ((Student) parentElement).getStudents().toArray();
		}

		@Override
		public Object getParent(Object element) {
			return ((Student) element).getParent();
		}

		@Override
		public boolean hasChildren(Object element) {
			List<Student> students = ((Student) element).getStudents();
			return students != null && !students.isEmpty();
		}
	}

	@Override
	public void fillContent(Composite parent) {
		TreeViewer treeViewer = new TreeViewer(parent, SWT.FULL_SELECTION);
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
		
		viewerColumns.forEach(viewerColumn -> viewerColumn.setEditingSupport(new StudentColumnEditingSupport(viewerColumn)));
		treeViewer.setInput(StudentFactory.create());
		
		Arrays.stream(tree.getColumns()).forEach(TreeColumn::pack);
	}
	
	private TreeViewerColumn createViewerColumn(TreeViewer treeViewer, String text) {
		TreeViewerColumn viewerColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		TreeColumn column = viewerColumn.getColumn();
		column.setText(text);
		
		return viewerColumn;
	}
	
}
