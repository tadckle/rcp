package rcp3.study.composite;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

/**
 * This composite has a sash bar which can hide or show a "hide composite".
 * 
 * @author alzhang
 */
public class SashComposite extends Composite {
	
	private static class HideStyle {
		private int direction = SWT.LEFT;
		private int width = 300;
		private boolean showByDefault = true;
		
		private HideStyle(int hideDirection, int hideWidth, boolean showByDefault) {
			super();
			this.direction = hideDirection;
			this.width = hideWidth;
			this.showByDefault = showByDefault;
		}
		
		/**
		 * Check whether it is horizontal layout.
		 * 
		 * @return true if direction is SWT.LEFT or SWT.RIGHT.
		 */
		public boolean isHorizontal() {
			return SWT.LEFT == direction || SWT.RIGHT == direction;
		}
	}
	
	private static final Color COLOR_LIGHT_BLUE = new Color(Display.getDefault(), 225, 230, 246);
	
	private final Composite hideComp;
	private Composite sashComp;
	private final Composite mainComp;
	
	private final HideStyle hideStyle;

	/**
	 * Create a HideStyle of SashComposite.
	 * 
	 * @param direction indicate the direction to hide composite.
	 * @param width indicate the width of hide composite.
	 * @param showByDefault true means show hide composite at first open.
	 * @return a HideStyle.
	 */
	public static HideStyle style(int direction, int width, boolean showByDefault) {
		return new HideStyle(direction, width, showByDefault);
	}

	/**
	 * Construct an instance.
	 * 
	 * @param parent the parent composite.
	 * @param style the appearance style.
	 * @param hideDirection where the hide part stays. Candidate value are SWT.LEFT, SWT.RIGHT, SWT.TOP and SWT.BOTTOM.
	 * @param hideWidth the width of hide composite.
	 */
	public SashComposite(Composite parent, int style, HideStyle hideStyle) {
		super(parent, style);
		this.hideStyle = hideStyle;
		
		Composite composite1 = new Composite(this, SWT.NONE);
		sashComp = new Composite(this, SWT.NONE);
		Composite composite2 = new Composite(this, SWT.NONE);
		
		initSash(sashComp);
		
		if (SWT.LEFT == hideStyle.direction || SWT.TOP == hideStyle.direction) {
			hideComp = composite1;
			mainComp = composite2;
		} else {
			hideComp = composite2;
			mainComp = composite1;
		}
		
		initLayout();
	}
	
	private void initLayout() {
		GridLayoutFactory.fillDefaults().spacing(0, 0).numColumns(hideStyle.isHorizontal() ? 3 : 1).applyTo(this);
		hideComp.setLayoutData(createHideCompGd());
		GridDataFactory.fillDefaults().applyTo(sashComp);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(mainComp);
		
		if (!hideStyle.showByDefault) {
			setControlVisible(hideComp, false);
		}
	}
	
	private GridData createHideCompGd() {
		Point hint = hideStyle.isHorizontal() 
				? new Point(hideStyle.width, SWT.DEFAULT) : new Point(SWT.DEFAULT, hideStyle.width);
		boolean xGrab = true;
		boolean yGrab = true;
		if (hideStyle.isHorizontal()) {
			xGrab = false;
		} else {
			yGrab = false;
		}
		return GridDataFactory.fillDefaults().hint(hint).grab(xGrab, yGrab).create();
	}

	private void initSash(Composite middleComp) {
		GridLayoutFactory.fillDefaults().applyTo(middleComp);
		
		CLabel sashLbl = new CLabel(middleComp, SWT.CENTER);
		sashLbl.setText(hideStyle.showByDefault ? ">>" : "<<");
		sashLbl.setBackground(COLOR_LIGHT_BLUE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(sashLbl);
		
		sashLbl.addListener(SWT.MouseUp, e -> {
			sashLbl.setText(hideComp.isVisible() ? "<<" : ">>");
			setControlVisible(hideComp, !hideComp.isVisible());
			SashComposite.this.layout(true);
		});
	}
	
	private void setControlVisible(Control control, boolean visible) {
		GridData gridData = (GridData) control.getLayoutData();
		control.setVisible(visible);
		gridData.exclude = !visible;
	}
	
	/**
	 * Get the hide composite.
	 * 
	 * @return the composite that will be hidden if sash bar is clicked.
	 */
	public Composite getHideComp() {
		return hideComp;
	}

	/**
	 * Get the main composite.
	 * 
	 * @return the composite that will always be shown.
	 */
	public Composite getMainComp() {
		return mainComp;
	}
	
}
