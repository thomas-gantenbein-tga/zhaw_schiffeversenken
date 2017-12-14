package ch.zhaw.schiffeversenken.guicomponents;

import java.awt.Color;

import ch.zhaw.schiffeversenken.Coordinate;

public class ShapeFactory {
	/**
	 * Creates a grid line for the playing field.
	 * Index: The position of the line from the top (horizontal lines) or from left (vertical lines).
	 * Width and height: in percent of the size of the playing field.
	 * rowCount and columnCount: Number of rows/columns in the playing field to be drawn
	 * 
	 * @param index
	 * @param rowCount
	 * @param columnCount
	 * @param width
	 * @param height
	 * @return
	 */
	public static Shape createGridLine(int index, int rowCount, int columnCount, int width, int height) {
		int xPosition;
		int yPosition;
		
		if(height == 0) {
			xPosition = 50;
			yPosition = index*100/rowCount;
		} else {
			xPosition = index*100/columnCount;
			yPosition = 50;
		}
		
		Shape line = new Line(xPosition, yPosition, width, height, Color.GRAY);
		return line;
	}
	
	public static Shape createShipIntact(Coordinate coordinate, int rowCount, int columnCount) {
		int xCoordinate = coordinate.getxPosition();
		int yCoordinate = coordinate.getyPosition();
		
		Shape intactShip = new ShipIntact(xCoordinate*100/columnCount+100/columnCount/2, yCoordinate*100/rowCount+100/rowCount/2, 100/columnCount*0.8, 100/columnCount*0.8, Color.BLACK);
		return intactShip;
	}
	
	public static Shape createShipHit(Coordinate coordinate, int rowCount, int columnCount) {
		int xCoordinate = coordinate.getxPosition();
		int yCoordinate = coordinate.getyPosition();
		
		Shape shipHit= new ShipHit(xCoordinate*100/columnCount+100/columnCount/2, yCoordinate*100/rowCount+100/rowCount/2, 100/columnCount*0.8, 100/columnCount*0.8, Color.BLACK);
		return shipHit;
	}

}
