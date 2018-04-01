package rcp3.study.composite;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.TextUtilities;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.widgets.Composite;

/**
 * This label can be directly put in any SWT control.
 * The second parameter specifies text direction. Available directions are:
 * DirectionLabel.LEFT_TO_RIGHT , DirectionLabel.TOP_TO_BOTTOW and DirectionLabel.TOP_TO_BOTTOW.
 * 
 * @author Alex
 */
public class DirectionLabel extends FigureCanvas {
	/*
	 * Text rotation.
	 */
	public enum Rotation {
		ANGLE_0, ANGLE_90, ANGLE_180, ANGLE_270;
	}
	
	private String text;
	
	private Rotation rotation = Rotation.ANGLE_0;
	
	/**
	 * Construct an DirectionLabel.
	 * 
	 * @param parent the parent composite.
	 */
	public DirectionLabel(Composite parent) {
		super(parent);
		this.setBackground(ColorConstants.white);
		
		this.setContents(new Figure() {
			@Override
			public void paint(Graphics graphics) {
				super.paint(graphics);
				if (text == null || text.isEmpty()) {
					return;
				}
				Dimension textExtents = TextUtilities.INSTANCE.getTextExtents(text, graphics.getFont());
				
				switch (rotation) {
					case ANGLE_90:
						graphics.rotate(90);
						graphics.drawText(text, 0, -textExtents.height);
						break;
					case ANGLE_180:
						graphics.rotate(180);
						graphics.drawText(text, -textExtents.width, -textExtents.height);
						break;
					case ANGLE_270:
						graphics.rotate(270);
						graphics.drawText(text, -textExtents.width, 0);
						break;
					default:
						graphics.drawText(text, 0, 0);
						break;
						
				}
				graphics.rotate(0);
			}
		});
	}
	
	/**
	 * Set label text.
	 * 
	 * @param text the text to set.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Set text rotation.
	 * 
	 * @param rotation the rotation to set.
	 */
	public void setRotation(Rotation rotation) {
		this.rotation = rotation;
	}

}
