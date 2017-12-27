package ch.zhaw.schiffeversenken.data;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.schiffeversenken.helpers.Coordinate;

/**
 * Simple Ship representation. Has only a position (list of coordinates). The
 * other properties isHit and isSunk are based on these coordinates and thus
 * evaluated by methods, not stored in fields.
 */
public class Ship {
	List<Coordinate> shipPositions;
	private Directions dir;
	private int rowProposition1stPosition, colProposition1stPosition;
	/**
	 * Creates a ship with random coordinates and random directions with a given size. isHit and isSunk fields of the
	 * Coordinate objects are set to false by default. The list of coordinates
	 * and the fields of the Coordinate objects define the ship's status
	 * (intact, hit, sunk).
	 * 
	 * @param colCount, rowCount
	 *            The size of the PlayField
	 */
	public Ship (int colCount, int rowCount ,int shipSize) {
		shipPositions = new ArrayList<Coordinate>();
		Coordinate[] coordinates = new Coordinate[shipSize];
		
		do{
			rowProposition1stPosition = (int)(Math.random()*rowCount);
			colProposition1stPosition = (int)(Math.random()*colCount);
			dir = Directions.getRandom();	
		}
		while(isShipInPlayfield( colCount, rowCount, shipSize, dir));

		coordinates[0] = new Coordinate(rowProposition1stPosition, colProposition1stPosition, false, false);
		//shipPositions.add(coordinates[0]);
		System.out.println(coordinates[0].getxPosition() + " , "+ coordinates[0].getyPosition());

		for(int i = 1; i < shipSize; i++) {
			switch (dir) {
			case NORTH:
				coordinates[i] = new Coordinate(coordinates[i-1].getxPosition(), coordinates[i-1].getyPosition() + 1, false, false);
				break;
			case SOUTH:
				coordinates[i] = new Coordinate(coordinates[i-1].getxPosition(), coordinates[i-1].getyPosition() - 1, false, false);
				break;
			case EAST:
				coordinates[i] = new Coordinate(coordinates[i-1].getxPosition() + 1, coordinates[i-1].getyPosition(), false, false);
				break;
			case WEST:
				coordinates[i] = new Coordinate(coordinates[i-1].getxPosition() - 1, coordinates[i-1].getyPosition() - 1, false, false);
				break;
			default:
				System.out.println("Richtung nicht programmiert!");
				break;
			}
			coordinates[i] = new Coordinate(coordinates[i-1].getxPosition() + 1, coordinates[i-1].getyPosition(), false, false);
			System.out.println(coordinates[i].getxPosition() + " , "+ coordinates[i].getyPosition());
		}
		for (Coordinate coordinate :coordinates)
			shipPositions.add(coordinate);

	}
	
	/* checks if a proposed ship is in the given playfield
	 * 	 @param colCount, rowCount => the size of the playfield
	 * 			colPosition, rowPosition => the proposed first position of the ship
	 * 			shipSize => the length of the ship
	 * 			dir = the direction of the Ship
	 * 
	 * @return Returns true if the proposed ship fits in the given playfield. If it doesn't it returns false
	 */
	public boolean isShipInPlayfield(int colCount, int rowCount , int shipSize, Directions dir) {
		switch (dir) {
		case NORTH:
			if (rowProposition1stPosition + shipSize <= colCount)
			return true;
		case SOUTH:
			if(rowProposition1stPosition - shipSize >= 1)
			return true;
		case EAST:
			if(colProposition1stPosition + shipSize <= rowCount)
			return true;
		case WEST:
			if(colProposition1stPosition - shipSize >= 1)
			return true;
		}
		return false;
	}

	/**
	 * Creates a ship with the given coordinates. isHit and isSunk fields of the
	 * Coordinate objects are set to false by default. The list of coordinates
	 * and the fields of the Coordinate objects define the ship's status
	 * (intact, hit, sunk).
	 * 
	 * @param coordinates
	 *            The coordinates of the ship
	 */
	public Ship(List<Coordinate> coordinates) {
		for(Coordinate coordinate : coordinates) {
			coordinate.setIsHit(false);
			coordinate.setIsSunk(false);
		}
		shipPositions = coordinates;
	}

	/**
	 * Checks whether a given coordinate corresponds to one of the ship's
	 * location coordinates.
	 * 
	 * @param coordinate
	 *            The coordinate of the shot aimed at this ship
	 * @return Returns true if the shot corresponds to one of the ship
	 *         positions.
	 */
	protected boolean isHit(Coordinate coordinate) {
		// Due to override of "equals" method, "contains" only checks whether x-
		// and y-coordinate are equal.
		// Other properties of the coordinate object are ignored.
		if (shipPositions.contains(coordinate)) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the list of coordinates of the ship.
	 * 
	 * @return The positions of the ship, as coordinates.
	 */
	protected List<Coordinate> getShipPositions() {
		return shipPositions;
	}

	/**
	 * Checks whether all positions of this ship have already been hit.
	 * 
	 * @return true if all positions of this ship have been shot at
	 */
	public boolean isSunk() {
		for (Coordinate coordinate : shipPositions) {
			if (!coordinate.getIsHit()) {
				return false;
			}
		}
		return true;
	}
}
