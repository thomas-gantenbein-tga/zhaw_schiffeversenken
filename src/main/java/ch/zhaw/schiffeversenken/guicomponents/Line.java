package ch.zhaw.schiffeversenken.guicomponents;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape {

	public Line(double centerX, double centerY, int width, int height, Color color) {
		super(centerX, centerY, width, height, color);
	}
	
	@Override
	protected void draw(int playingFieldSize, Graphics g) {
		
		int x1Position = (int) (playingFieldSize*(centerX/100.0) - (width/100.0)/2 * playingFieldSize);
		int y1Position = (int) (playingFieldSize*(centerY/100.0) - (height/100.0)/2 * playingFieldSize);
		int x2Position = (int) (x1Position + (width/100.0 * playingFieldSize));
		int y2Position = (int) (y1Position + (height/100.0 * playingFieldSize));
		g.setColor(color);
		g.drawLine(x1Position, y1Position, x2Position, y2Position);
	}
 
}
