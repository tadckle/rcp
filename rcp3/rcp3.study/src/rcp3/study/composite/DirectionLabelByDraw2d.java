package rcp3.study.composite;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.TextUtilities;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;

/**
 * This label can be directly put in any SWT control.
 * The second parameter specifies text direction. Available directions are:
 * DirectionLabel.LEFT_TO_RIGHT , DirectionLabel.TOP_TO_BOTTOW and DirectionLabel.TOP_TO_BOTTOW.
 * 
 * @author Alex
 */
public class DirectionLabelByDraw2d extends Composite {
	/*
	 * Text rotation.
	 */
	public enum Rotation {
		ANGLE_0, ANGLE_90, ANGLE_180, ANGLE_270;
	}
	
	private String text = "";
	
	private Rotation rotation = Rotation.ANGLE_0;
	
	private FigureCanvas canvas;

	private Figure figure;
	
	/**
	 * Construct an DirectionLabel.
	 * 
	 * @param parent the parent composite.
	 * @param atext the label text.
	 * @param style the appearance style.
	 */
	public DirectionLabelByDraw2d(Composite parent, String atext, int style) {
		super(parent, style);
		this.setText(atext);
		this.setLayout(new FillLayout());
		
		canvas = new FigureCanvas(this);
		figure = new Figure() {
			@Override
			public void paint(Graphics graphics) {
				super.paint(graphics);
				if (text == null || text.isEmpty()) {
					return;
				}
				
				Dimension textExtents = TextUtilities.INSTANCE.getTextExtents(text, canvas.getFont());
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
		};
		
		canvas.setContents(figure);
	}
	
	@Override
	public Point computeSize (int wHint, int hHint, boolean changed) {
		Dimension textExtents = TextUtilities.INSTANCE.getTextExtents(text, canvas.getFont());
		if (Rotation.ANGLE_90.equals(rotation) || Rotation.ANGLE_270.equals(rotation)) {
			return super.computeSize(textExtents.height, textExtents.width, false);
		} else {
			return super.computeSize(textExtents.width, textExtents.height, true);
		}
	}
	
	/**
	 * Set text rotation.
	 * 
	 * @param rotation the rotation to set.
	 */
	public void setRotation(Rotation rotation) {
		this.rotation = rotation;
	}

	@Override
	public void setBackground(Color color) {
		canvas.setBackground(color);
	}

	@Override
	public void setFont(Font font) {
		canvas.setFont(font);
	}

	@Override
	public void setForeground(Color color) {
		canvas.setForeground(color);
	}

	/**
	 * Set label text.
	 * 
	 * @param text the text to set.
	 */
	public void setText(String text) {
		if (text != null) {
			this.text = text;
			if (figure != null) {
				figure.repaint();
			}
		}
	}
	
	@Override
	public void addListener(int eventType, Listener listener) {
		if (canvas != null) {
			canvas.addListener(eventType, listener);
		}
	}
	
}
