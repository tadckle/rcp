package rcp3.study.chartdirector;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import ChartDirector.ChartViewer;

/**
 * Base composite for showing a ChartViewer of ChartDirector.
 * 
 * @author Alex
 */
public abstract class ChartComposite extends Composite {

	/**
	 * Construct a ChartComposite.
	 * 
	 * @param parent the parent composite.
	 * @param style the appearance style.
	 */
	public ChartComposite(Composite parent, int style) {
		super(parent, style);
		this.setLayout(new FillLayout());
		this.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));
		
		Composite composite = new Composite(this, SWT.EMBEDDED | SWT.NO_BACKGROUND);
		Frame frame = SWT_AWT.new_Frame(composite);
		frame.setBackground(Color.white);
		frame.setLayout(new BorderLayout());
		
		composite.addDisposeListener(event -> {
			// Manually terminate current running Java Virtual Machine.
			// Otherwise program won't stop.
			System.exit(0);
		});

		ChartViewer viewer = new ChartViewer();
		frame.add(viewer, BorderLayout.CENTER);
		
		createChart(viewer);
	}
	
	// Create chart in ChartViewer.
	protected abstract void createChart(ChartViewer viewer);
	
}
