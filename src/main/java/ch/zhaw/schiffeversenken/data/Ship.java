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
	
	/**
	 * Creates a ship with random coordinates. isHit and isSunk fields of the
	 * Coordinate objects are set to false by default. The list of coordinates
	 * and the fields of the Coordinate objects define the ship's status
	 * (intact, hit, sunk).
	 * 
	 * @param colCount, rowCount
	 *            The size of the PlayField
	 */
	public Ship (int colCount, int rowCount) {
		shipPositions = new ArrayList<Coordinate>();
		Coordinate coordinate = new Coordinate((int)(Math.random()*colCount), (int)(Math.random()*rowCount), false, false);
		shipPositions.add(coordinate);
		System.out.println(coordinate.getxPosition() + " , "+ coordinate.getyPosition());
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
