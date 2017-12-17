package ch.zhaw.schiffeversenken.guicomponents;

import java.awt.Color;
import java.awt.Graphics;

public class ShipSunk extends Shape {
	
	public ShipSunk(double centerX, double centerY, double width, double height, Color color) {
		super(centerX, centerY, width, height, color);
	}

	@Override
	public void draw(int playingFieldSize, Graphics g) {

		//draw outer circle of ship
		int xPosition = (int) (playingFieldSize * centerX / 100.0 - this.width / 100.0 * playingFieldSize / 2);
		int yPosition = (int) (playingFieldSize * centerY / 100.0 - this.height / 100.0 * playingFieldSize / 2);
		int width = (int) (playingFieldSize * this.width / 100);
		int height = (int) (playingFieldSize * this.height / 100);
		g.setColor(color);
		g.fillOval(xPosition, yPosition, width, height);
		
		//draw first line of cross for sunk ship
		int x1Position = xPosition;
		int y1Position = yPosition;
		int x2Position = (int) (playingFieldSize * centerX / 100.0 + this.width / 100.0 * playingFieldSize / 2);
		int y2Position = (int) (playingFieldSize * centerY / 100.0 + this.height / 100.0 * playingFieldSize / 2);
		g.drawLine(x1Position, y1Position, x2Position, y2Position);
		
		//draw second line of cross for sunk ship
		x1Position = x2Position;
		x2Position = xPosition;
		g.drawLine(x1Position, y1Position, x2Position, y2Position);
		
		//draw circle for hit ship
		double resizeFactor = 0.4;
		xPosition = (int) (playingFieldSize * centerX / 100.0
				- this.width * resizeFactor / 100.0 * playingFieldSize / 2);
		yPosition = (int) (playingFieldSize * centerY / 100.0
				- this.height * resizeFactor / 100.0 * playingFieldSize / 2);
		g.setColor(Color.RED);
		g.fillOval(xPosition, yPosition, (int) (width * resizeFactor), (int) (height * resizeFactor));

	}
}
