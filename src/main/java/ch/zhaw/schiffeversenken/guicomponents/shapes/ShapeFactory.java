package ch.zhaw.schiffeversenken.guicomponents;

import java.awt.Color;

import ch.zhaw.schiffeversenken.helpers.Coordinate;

/**
 * Convenience class to produce shape objects located at specific coordinates.
 * Eliminates the need to translate from the "coordinate" form on which the game
 * is based into the "number of pixels" form on which the GUI is based.
 *
 */
public class ShapeFactory {
	/**
	 * Creates a grid line for the playing field.
	 * 
	 * @param index
	 *            Index: The position of the line from the top (horizontal
	 *            lines) or from left (vertical lines). Starts at 0.
	 * @param rowCount
	 *            The number of rows that should be represented by lines
	 * @param columnCount
	 *            The number of columns that should be represented by lines
	 * @param width
	 *            in percent of the size of the playing field
	 * @param height
	 *            in percent of the size of the playing field
	 * @return line Shape
	 */
	protected static Shape createGridLine(int index, int rowCount, int columnCount, int width, int height) {
		double xPosition;
		double yPosition;

		if (height == 0) {
			xPosition = 50;
			yPosition = index * 100.0 / rowCount;
		} else {
			xPosition = index * 100.0 / columnCount;
			yPosition = 50;
		}

		Shape line = new Line(xPosition, yPosition, width, height, Color.GRAY);
		return line;
	}
	/**
	 * Creates the symbol for an intact position of a ship.
	 */
	protected static Shape createShipIntact(Coordinate coordinate, int rowCount, int columnCount) {
		int xCoordinate = coordinate.getxPosition();
		int yCoordinate = coordinate.getyPosition();

		Shape intactShip = new ShipIntact(xCoordinate * 100.0 / columnCount + 100.0 / columnCount / 2,
				yCoordinate * 100.0 / rowCount + 100.0 / rowCount / 2, 100.0 / columnCount * 0.8,
				100.0 / columnCount * 0.8, Color.BLACK);
		return intactShip;
	}
	
	/**
	 * Creates the symbol for a hit position of a ship.
	 */
	protected static Shape createShipHit(Coordinate coordinate, int rowCount, int columnCount) {
		int xCoordinate = coordinate.getxPosition();
		int yCoordinate = coordinate.getyPosition();

		Shape shipHit = new ShipHit(xCoordinate * 100.0 / columnCount + 100.0 / columnCount / 2,
				yCoordinate * 100.0 / rowCount + 100.0 / rowCount / 2, 100.0 / columnCount * 0.8,
				100.0 / columnCount * 0.8, Color.BLACK);
		return shipHit;
	}

	/**
	 * Creates the symbol for a hit position of the sea.
	 */
	protected static Shape createSeaHit(Coordinate coordinate, int rowCount, int columnCount) {
		int xCoordinate = coordinate.getxPosition();
		int yCoordinate = coordinate.getyPosition();

		Shape seaHit = new SeaHit(xCoordinate * 100.0 / columnCount + 100.0 / columnCount / 2,
				yCoordinate * 100.0 / rowCount + 100.0 / rowCount / 2, 100.0 / columnCount * 0.2,
				100.0 / columnCount * 0.2, Color.BLACK);
		return seaHit;
	}

	/**
	 * Creates the shape to be drawn on the coordinate over which the human player's mouse hover.
	 */
	protected static Shape createHoverShape(Coordinate coordinate, int rowCount, int columnCount) {
		int xCoordinate = coordinate.getxPosition();
		int yCoordinate = coordinate.getyPosition();

		Shape hoverShape = new HoverShape(xCoordinate * 100.0 / columnCount + 100.0 / columnCount / 2,
				yCoordinate * 100.0 / rowCount + 100.0 / rowCount / 2, 100.0 / columnCount, 100.0 / columnCount,
				Color.LIGHT_GRAY);
		return hoverShape;
	}

	/**
	 * Creates the symbol for a position of a fully destroyed ship.
	 */
	protected static Shape createShipSunk(Coordinate coordinate, int rowCount, int columnCount) {
		int xCoordinate = coordinate.getxPosition();
		int yCoordinate = coordinate.getyPosition();

		Shape shipSunk = new ShipSunk(xCoordinate * 100.0 / columnCount + 100.0 / columnCount / 2,
				yCoordinate * 100.0 / rowCount + 100.0 / rowCount / 2, 100.0 / columnCount * 0.8,
				100.0 / columnCount * 0.8, Color.BLACK);
		return shipSunk;
	}

}
