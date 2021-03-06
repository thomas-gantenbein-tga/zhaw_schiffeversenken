package ch.zhaw.schiffeversenken.view.shapes;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Represents a hit position of a ship.
 *
 */
public class ShipHit extends Shape {

	protected ShipHit(double centerX, double centerY, double width, double height, Color color) {
		super(centerX, centerY, width, height, color);
	}
	
	@Override
	public void draw(int playingFieldSize, Graphics2D g) {
		
		int xPosition = (int) (playingFieldSize*centerX/100.0 - this.width/100.0*playingFieldSize/2);
		int yPosition = (int) (playingFieldSize*centerY/100.0 - this.height/100.0*playingFieldSize/2);
		int width = (int) (playingFieldSize * this.width/100);
		int height = (int) (playingFieldSize * this.height/100);
		g.setColor(color);
		g.fillOval(xPosition, yPosition, width, height);
		
		double resizeFactor = 0.4;
		xPosition = (int) (playingFieldSize*centerX/100.0 - this.width*resizeFactor/100.0*playingFieldSize/2);
		yPosition = (int) (playingFieldSize*centerY/100.0 - this.height*resizeFactor/100.0*playingFieldSize/2);
		g.setColor(Color.RED);
		g.fillOval(xPosition, yPosition, (int)(width*resizeFactor), (int)(height*resizeFactor));
		
	}

}
