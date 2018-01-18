package ch.zhaw.schiffeversenken.data;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.schiffeversenken.helpers.Coordinate;
import ch.zhaw.schiffeversenken.helpers.Directions;

/**
 * Simple Ship representation. Has only a position (list of coordinates). The
 * other properties isHit and isSunk are based on these coordinates and thus
 * evaluated by methods, not stored in fields.
 */
public class Ship {
	List<Coordinate> shipPositions;
	int shipSize;
	Directions direction;

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
		for (Coordinate coordinate : coordinates) {
			coordinate.setIsHit(false);
			coordinate.setIsSunk(false);
		}
		shipPositions = coordinates;
	}

	/**
	 * Creates a ship with random coordinates and random directions with a given
	 * size. isHit and isSunk fields of the Coordinate objects are set to false
	 * by default. The list of coordinates and the fields of the Coordinate
	 * objects define the ship's status (intact, hit, sunk).
	 * 
	 * @param colCount	Width of the PlayField
	 * @param rowCount	Height of the PlayField
	 * @param shipSize	Size (= length) of the ship
	 * 
	 * @author uelik
	 */
	public Ship(int colCount, int rowCount, int shipSize) {
		shipPositions = new ArrayList<Coordinate>();
		this.shipSize = shipSize;

		//sets the starting point of the ship
		shipPositions.add(new Coordinate((int) (Math.random() * rowCount), (int) (Math.random() * colCount), false,	false));
		direction = Directions.getRandom();
		
		//constructs the rest of the ship
		setShipPositionsGivenDirectionStartingPoint();
	}

	/**
	 * Creates a ship with a given starting point (the 'head' of the ship) and a
	 * given direction. isHit and isSunk fields of the Coordinate objects are
	 * set to false by default. The list of coordinates and the fields of the
	 * Coordinate objects define the ship's status (intact, hit, sunk).
	 * 
	 * @param shipSize	size (= length) of the ship
	 * @param direction	the direction where the ship is heading to
	 * @param headPosition the position of the head of the ship
	 */
	public Ship(int shipSize, Directions direction, Coordinate headPosition) {
		shipPositions = new ArrayList<Coordinate>();
		headPosition.setIsHit(false);
		headPosition.setIsSunk(false);
		
		this.shipSize = shipSize;
		this.direction = direction;
	
		//sets the starting point of the ship
		shipPositions.add(headPosition);
		
		//constructs the rest of the ship
		setShipPositionsGivenDirectionStartingPoint();

	}
	
	/**
	 * Creates the rest of the ship from a given starting point with a given direction and a given size.
	 * isHit and isSunk fields of the Coordinate objects are set to false by default.
	 * 
	 * @author uelik
	 */	
	private void setShipPositionsGivenDirectionStartingPoint (){

		for (int i = 0; i < shipSize - 1; i++) {
			// orient the ship in the given direction
			switch (direction) {
			case NORTH:
				shipPositions.add(new Coordinate(shipPositions.get(i).getxPosition(),shipPositions.get(i).getyPosition() - 1, false, false));
				break;
			case SOUTH:
				shipPositions.add(new Coordinate(shipPositions.get(i).getxPosition(),shipPositions.get(i).getyPosition() + 1, false, false));
				break;
			case EAST:
				shipPositions.add(new Coordinate(shipPositions.get(i).getxPosition() + 1,shipPositions.get(i).getyPosition(), false, false));
				break;
			case WEST:
				shipPositions.add(new Coordinate(shipPositions.get(i).getxPosition() -1 ,shipPositions.get(i).getyPosition(), false, false));
				break;
			default:
				System.out.println("Richtung nicht programmiert!");
				break;
			}
		}
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
	public List<Coordinate> getShipPositions() {
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

	/**
	 * Checks if the ship in the given PlayField
	 * 
	 * @param colCount,
	 *            rowCount => the size of the PlayField
	 * 
	 * @return true if the ship fits in the given PlayField. If it
	 *         doesn't, it returns false
	 * @author uelik
	 */
	public boolean isInPlayfield(int columnCount, int rowCount) {
		for (Coordinate shipPosition : shipPositions) {
			if (shipPosition.getxPosition() >= columnCount)
				return false;
			else if (shipPosition.getxPosition() < 0)
				return false;
			else if (shipPosition.getyPosition() >= rowCount)
				return false;
			else if (shipPosition.getyPosition() < 0)
				return false;
		}
		return true;
	}

	/**
	 * Checks if the ship does not cross the one in the parameter
	 * 
	 * @param shipPositionsCollision,
	 *            the ship to test the collision with
	 * 
	 * @return Returns true if the two ships use same field(s), false if there
	 *         is no collision
	 *         
	 * @author uelik
	 */
	public boolean isInCollision(List<Coordinate> shipPostionsCollision) {
		for (Coordinate shipPosition : shipPositions)
			for (Coordinate shipPositionCollision : shipPostionsCollision)
				if (shipPosition.equals(shipPositionCollision))
					return true;
		return false;
	}

	/**
	 * Get wound positions
	 * 
	 * @return the hit positions of the ship, as a list of coordinates. Returns an empty list if
	 *         the ship is not hit
	 * @author uelik
	 */
	public List<Coordinate> getWoundPositions() {
		ArrayList<Coordinate> shipWoundPositions = new ArrayList<Coordinate>();
		for (Coordinate shipPosition : shipPositions) {
			if (shipPosition.getIsHit())
				shipWoundPositions.add(shipPosition);
		}
		return shipWoundPositions;
	}

	/**
	 * Get direction of the hits in vertical (NORTH) or horizontal (WEST) direction
	 * 
	 * @return the direction of the hit points
	 * @author uelik
	 */
	public Directions getDirectionsOfHits() {
		List<Coordinate> woundPositions = getWoundPositions();
		if (woundPositions.get(0).getxPosition() == woundPositions.get(1).getxPosition())
			return Directions.NORTH;
		else
			return Directions.WEST;
	}
}
