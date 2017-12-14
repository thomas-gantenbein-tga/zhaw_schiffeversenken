package ch.zhaw.schiffeversenken.guicomponents;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Shape {

	protected int centerX;
	protected int centerY;
	protected double width;
	protected double height;
	protected Color color;
	
	
	/**
	 * All parameteres (except color) are given in percent of the the total width of the playing field.
	 * <p>
	 * What these percentages mean in absolute terms (pixels) is computed in the "draw" method of each subclass.
	 * The draw method receives the absolute width/height of the panel on which the playing field is displayed.
	 * @param centerX
	 * @param centerY
	 * @param width
	 * @param height
	 * @param color
	 */
	public Shape(int centerX, int centerY, double width, double height, Color color) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	
	public int getCenterX() {
		return centerX;
	}
	public int getCenterY() {
		return centerY;
	}


	public double getWidth() {
		return width;
	}


	public double getHeight() {
		return height;
	}


	public Color getColor() {
		return color;
	}

	public abstract void draw(int playingFieldSize, Graphics g);
	
}
