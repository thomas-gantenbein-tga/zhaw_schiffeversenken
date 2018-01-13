package ch.zhaw.schiffeversenken.guicomponents.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class Shape implements Comparable<Shape> {

	protected double centerX;
	protected double centerY;
	protected double width;
	protected double height;
	protected Color color;

	/**
	 * All parameters (except color) are given in percent of the the total
	 * width of the playing field.
	 * <p>
	 * What these percentages mean in absolute terms (pixels) is computed in the
	 * "draw" method of each subclass. The draw method receives the absolute
	 * width/height (whatever is smaller) of the panel on which the playing
	 * field is displayed.
	 * 
	 * @param centerX
	 *            Horizontal center of the shape, given in percent of the field
	 *            size.
	 * @param centerY
	 *            Vertical center of the shape, given in percent of the field
	 *            size.
	 * @param width
	 *            Width of the shape, given in percent of the field size.
	 * @param height
	 *            Height of the shape, given in percent of the field size.
	 * @param color
	 *            Color of the shape. The draw method of the shape may make the
	 *            shape multi-colored. In this case, this parameter defines the
	 *            "main" color of the shape.
	 */
	protected Shape(double centerX, double centerY, double width, double height, Color color) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.width = width;
		this.height = height;
		this.color = color;
	}



	public abstract void draw(int playingFieldSize, Graphics2D g);

	public int compareTo(Shape otherShape) {
		return (int) (otherShape.width - this.width);
	}
	
}
