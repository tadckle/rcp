package rcp3.study.viewers;

import org.eclipse.jface.viewers.TableViewer;

/**
 * Add EditingSupport for each TableViewerColumn.
 * 
 * @author Alex
 */
public class TableViewerUsage3 extends TableViewerUsage1 {
	
	public static void main(String[] args) {
		new TableViewerUsage3().openShell();
	}

	@Override
	protected void addEditCapability(TableViewer tableViewer) {
		viewerColumns.forEach(viewerColumn -> viewerColumn.setEditingSupport(new StudentColumnEditingSupport(viewerColumn)));
	}

}
