package rcp3.study.composite;

import org.eclipse.draw2d.TextUtilities;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * This label can be directly put in a SWT composite.
 * Field rotation specifies text direction. Available directions are:
 * Rotation.ANGLE_0, Rotation.ANGLE_90, Rotation.ANGLE_180, Rotation.ANGLE_270.
 * 
 * @author Alex
 */
public class DirectionLabel extends Canvas {
	
	/*
	 * Text rotation.
	 */
	public enum Rotation {
		ANGLE_0(0f), ANGLE_90(90f), ANGLE_180(180f), ANGLE_270(270f);
		
		private float angle;
		private Rotation(float theAngle) {
			this.angle = theAngle;
		}
	}
	
	private String text = "";
	
	private Rotation rotation = Rotation.ANGLE_0;
	
	private int xMargin = 2;

	private PaintListener paintListener = new PaintListener(){
		@Override
		public void paintControl(PaintEvent evt) {
			if (text == null || text.isEmpty()) {
				return;
			}
			
			Transform transform = new Transform(Display.getDefault());
			transform.rotate(rotation.angle);
			evt.gc.setTransform(transform);
			
			Dimension textExtents = TextUtilities.INSTANCE.getTextExtents(text, evt.gc.getFont());
			switch (rotation) {
				case ANGLE_90:
					evt.gc.drawText(text, xMargin, -textExtents.height);
					break;
				case ANGLE_180:
					evt.gc.drawText(text, -textExtents.width - xMargin, -textExtents.height);
					break;
				case ANGLE_270:
					evt.gc.drawText(text, -textExtents.width - xMargin, 0);
					break;
				default:
					evt.gc.drawText(text, xMargin, 0);
					break;
			}
			
			transform.dispose();
			DirectionLabel.this.removePaintListener(this);
		}
	};
	
	/**
	 * Construct an label instance.
	 * 
	 * @param parent the parent composite.
	 * @param style the appearance style.
	 */
	public DirectionLabel(Composite parent, String text, int style) {
		super(parent, style);
		updateText(text);
	}
	
	private void updateText(String aText) {
		if (aText != null) {
			this.text = aText;
			this.addPaintListener(paintListener);
		}
	}
	
	/**
	 * Set the text.
	 *
	 * @param aText the text to set
	 */
	public void setText(String aText) {
		updateText(aText);
	}

	/**
	 * Set the rotation.
	 *
	 * @param rotation the rotation to set
	 */
	public void setRotation(Rotation rotation) {
		this.rotation = rotation;
	}

	@Override
	public Point computeSize (int wHint, int hHint, boolean changed) {
		Dimension textExtents = TextUtilities.INSTANCE.getTextExtents(text, this.getFont());

		Point size;
		if (Rotation.ANGLE_90.equals(rotation) || Rotation.ANGLE_270.equals(rotation)) {
			size = super.computeSize(textExtents.height, textExtents.width, false);
			size.y += xMargin * 2;
		} else {
			size = super.computeSize(textExtents.width, textExtents.height, true);
			size.x += xMargin * 2;
		}
		
		return size;
	}

	/**
	 * Set the xMargin.
	 *
	 * @param xMargin the xMargin to set
	 */
	public void setxMargin(int xMargin) {
		this.xMargin = xMargin;
	}
	
}
