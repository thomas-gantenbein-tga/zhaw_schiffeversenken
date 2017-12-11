package ch.zhaw.schiffeversenken.guicomponents;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Shape {

	protected int centerX;
	protected int centerY;
	protected int width;
	protected int height;
	protected Color color;
	
	
	
	public Shape(int centerX, int centerY, int width, int height, Color color) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.width = width;
		this.height = height;
	}
	
	
	public int getCenterX() {
		return centerX;
	}
	public int getCenterY() {
		return centerY;
	}


	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}


	public Color getColor() {
		return color;
	}

	public void draw(int playingFieldSize, Graphics g){
		
	}
	
}
