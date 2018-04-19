package rcp3.study.composite;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import rcp3.study.composite.DirectionLabel.Rotation;


/**
 * This composite has a sash bar which can hide or show a "hide composite".
 * 
 * Usage example:
 * <code>
 * // SWT.LEFT means left hide.
 * // SWT.RIGHT means right hide.
 * // SWT.TOP means top hide.
 * // SWT.BOTTOM means bottom hide.
 * SashComposite sashComp = new SashComposite(shell, SWT.BORDER, new HideStyle(SWT.LEFT, 300, true));
 * Composite hideComp = sashComp.getHideComp();
 * Composite mainComp = sashComp.getMainComp();
 * </code>
 * @author alzhang
 */
public class SashComposite extends Composite {
	
	/**
	 * Contains parameters that define the behavior of "hide composite".
	 * 
	 * @author alzhang
	 */
	public static class HideStyle {
		private int direction = SWT.LEFT;
		private int width = 300;
		private boolean showByDefault = true;
		
		/**
		 * Create a HideStyle of SashComposite.
		 * 
		 * @param direction indicate the direction of hide composite.
		 * @param width indicate the width of hide composite.
		 * @param showByDefault true means show hide composite at first open.
		 */
		public HideStyle(int hideDirection, int hideWidth, boolean showByDefault) {
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
	
	private static final Image ARROW_LEFT_IMG = new Image(Display.getDefault(),  
			SashComposite.class.getResource("arrow-left.png").getPath());
	
	private static final Image ARROW_RIGHT_IMG = new Image(Display.getDefault(),  
			SashComposite.class.getResource("arrow-right.png").getPath());
	
	private static final Image ARROW_UP_IMG = new Image(Display.getDefault(),  
			SashComposite.class.getResource("arrow-up.png").getPath());
	
	private static final Image ARROW_DOWN_IMG = new Image(Display.getDefault(),  
			SashComposite.class.getResource("arrow-down.png").getPath());
	
	private final Composite hideComp;
	private final Composite sashComp;
	private final Composite mainComp;
	
	private final HideStyle hideStyle;

	private Label sashImgLbl;
	private DirectionLabel sashTxtLbl;

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
		if (hideStyle.isHorizontal()) {
			GridLayoutFactory.swtDefaults().margins(0, 5).spacing(0, 0).applyTo(middleComp);
		} else {
			GridLayoutFactory.swtDefaults().margins(5, 0).spacing(0, 0).numColumns(2).applyTo(middleComp);
		}
		
		middleComp.setBackground(COLOR_LIGHT_BLUE);
		middleComp.setBackgroundMode(SWT.INHERIT_DEFAULT);
		
		sashTxtLbl = new DirectionLabel(middleComp, "", SWT.NONE);
		sashTxtLbl.setxMargin(0);
		if (hideStyle.isHorizontal()) {
			sashTxtLbl.setRotation(Rotation.ANGLE_90);
		}
		sashTxtLbl.setBackground(COLOR_LIGHT_BLUE);
		GridDataFactory.swtDefaults().applyTo(sashTxtLbl);
		
		sashImgLbl = new Label(middleComp, SWT.CENTER);
		if (hideStyle.isHorizontal()) {
			GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.FILL).grab(false, true).applyTo(sashImgLbl);
		} else {
			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(sashImgLbl);
		}
		
		Listener sashListener = e -> {
			updateSashImage(!hideComp.isVisible());
			setControlVisible(hideComp, !hideComp.isVisible());
			SashComposite.this.layout(true);
		};
		sashTxtLbl.addListener(SWT.MouseUp, sashListener);
		sashImgLbl.addListener(SWT.MouseUp,sashListener);
		
		updateSashImage(hideStyle.showByDefault);
	}
	
	private void updateSashImage(boolean isVisible) {
		switch (hideStyle.direction) {
			case SWT.LEFT:
				sashImgLbl.setImage(isVisible ? ARROW_LEFT_IMG : ARROW_RIGHT_IMG);
				break;
			case SWT.RIGHT:
				sashImgLbl.setImage(isVisible ? ARROW_RIGHT_IMG : ARROW_LEFT_IMG);
				break;
			case SWT.UP:
				sashImgLbl.setImage(isVisible ? ARROW_UP_IMG : ARROW_DOWN_IMG);
				break;
			case SWT.DOWN:
				sashImgLbl.setImage(isVisible ? ARROW_DOWN_IMG : ARROW_UP_IMG);
				break;
			default:
				break;
		}
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
	
	/**
	 * Set sash text.
	 * 
	 * @param text the sash text to set.
	 */
	public void setSashText(String text) {
		if (sashTxtLbl != null && text != null) {
			sashTxtLbl.setText(text.toUpperCase());
		}
	}
	
}
