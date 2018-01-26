package ch.zhaw.schiffeversenken.view.shapes;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * The shape to be drawn when the player selects a field where he/she would like
 * to place a ship.
 *
 */
public class TailPreviewShape extends Shape {

	protected TailPreviewShape(double centerX, double centerY, double width, double height, Color color) {
		super(centerX, centerY, width, height, color);
	}

	@Override
	public void draw(int playingFieldSize, Graphics2D g) {
		int xPositionMin = (int) (playingFieldSize * centerX / 100.0 - this.width / 100.0 * playingFieldSize / 2);
		int xPositionMax = (int) (playingFieldSize * centerX / 100.0 + this.width / 100.0 * playingFieldSize / 2);
		int numberOfDots = 10;
		double xIntervalBetweenDots = (xPositionMax - xPositionMin) / (double) numberOfDots;

		int yPositionMin = (int) (playingFieldSize * centerY / 100.0 - this.height / 100.0 * playingFieldSize / 2);
		int yPositionMax = (int) (playingFieldSize * centerY / 100.0 + this.height / 100.0 * playingFieldSize / 2);
		double yIntervalBetweenDots = (yPositionMax - yPositionMin) / (double) numberOfDots;

		int width = (int) (playingFieldSize * this.width / 100 / numberOfDots);
		int height = (int) (playingFieldSize * this.height / 100 / numberOfDots);

		g.setColor(color);
		for (int i = 0; i < numberOfDots; i = i + 2) {
			int xPosition = (int) (xPositionMin + i * xIntervalBetweenDots);
			for (int j = 0; j < numberOfDots; j = j + 2) {
				int yPosition = (int) (yPositionMin + j * yIntervalBetweenDots);
				g.fillRect(xPosition, yPosition, width, height);
			}
		}
	}
}
