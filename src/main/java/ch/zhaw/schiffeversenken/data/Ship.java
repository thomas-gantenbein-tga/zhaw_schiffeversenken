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
	 * Creates a ship with random coordinates and random directions with a given size. isHit and isSunk fields of the
	 * Coordinate objects are set to false by default. The list of coordinates
	 * and the fields of the Coordinate objects define the ship's status
	 * (intact, hit, sunk).
	 * 
	 * @param colCount, rowCount
	 *            The size of the PlayField
	 * @author uelik
	 */
	public Ship (int colCount, int rowCount ,int shipSize) {
		shipPositions = new ArrayList<Coordinate>();
		Coordinate[] coordinates = new Coordinate[shipSize];
		//generate starting point of ship with random coordinates within the given playfield
		coordinates[0] = new Coordinate((int)(Math.random()*rowCount), (int)(Math.random()*colCount), false, false);
		System.out.println(coordinates[0].getxPosition() + " , "+ coordinates[0].getyPosition());

		Directions direction = Directions.getRandom();
		
		for(int i = 1; i < shipSize; i++) {
			//orient the ship randomly within the directions in Enum Directions
			switch (direction) {
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
				coordinates[i] = new Coordinate(coordinates[i-1].getxPosition() - 1, coordinates[i-1].getyPosition(), false, false);
				break;
			default:
				System.out.println("Richtung nicht programmiert!");
				break;
			}
			System.out.println(coordinates[i].getxPosition() + " , "+ coordinates[i].getyPosition());
		}
		for (Coordinate coordinate :coordinates)
			shipPositions.add(coordinate);

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

	/** checks if the ship in the given PlayField
	 * 	 @param colCount, rowCount => the size of the PlayField
	 * 
	 * @return Returns true if the ship fits in the given PlayField. If it doesn't it returns false
	 * @author uelik
	 */
	public boolean isInPlayfield(int columnCount, int rowCount) {
		for (Coordinate shipPosition :shipPositions) {
			if (shipPosition.getxPosition() >= columnCount)
				return false;
			else if (shipPosition.getxPosition() <= 0)
				return false;
			else if (shipPosition.getyPosition() >= rowCount)
				return false;
			else if (shipPosition.getyPosition() <= 0)
				return false;
		}
		return true;
	}
	
	/** checks if the ship does not cross the one in the parameter
	 * @param shipPositionsCollision, the ship to test the collision with 
	 * 
	 * @return Returns true if the two ships use same field(s), false if there is no collision
	 * @author uelik
	 */
	public boolean isInCollision(List<Coordinate> shipPostionsCollision) {
		for (Coordinate shipPosition : shipPositions)
			for (Coordinate shipPositionCollision : shipPostionsCollision)
				if(shipPosition.equals(shipPositionCollision))
					return true;
		return false;
	}
	/**
	 * Get wound positions
	 * 
	 * @return The hit positions of the ship, as coordinates. No Coordinate if the ship is not hit
	 * @author uelik
	 */
	public List<Coordinate> getWoundPositions() {
		ArrayList<Coordinate> shipWoundPositions = new ArrayList<Coordinate>();
		for (Coordinate shipPosition :shipPositions) {
			if (shipPosition.getIsHit())
				shipWoundPositions.add(shipPosition);
		}
		return shipWoundPositions;
	}
	
	/**
	 * Get the position of the first hit only
	 * 
	 * @return The first hit position of the ship, as coordinates if a ship is wounded only once. If it is hit more than once or fully intact return null 
	 * @author uelik
	 */
	public Coordinate getOnlyOneWoundPosition() {
		int hitCounter = 0;
		Coordinate coordinate = null;
		for (Coordinate shipPosition :shipPositions) {
			if (shipPosition.getIsHit()) {
				hitCounter++;
				coordinate = shipPosition;
			}
		}
		if (hitCounter == 1)
			return coordinate;
		return null;
	}
	
	/**
	 * Get a random Position around a given position according to the four Directions NORTH,SOUTH,EAST,WEST
	 * 
	 * @return Retruns a Coordinate with the random position around the given one
	 * @author uelik
	 */	
	public Coordinate getRandomCoordinateAround4Directions(Coordinate firstHitPosition) {
		Coordinate randomPositionAround1stHit= null;
		switch (Directions.getRandom()) {
		case NORTH:
			randomPositionAround1stHit = new Coordinate(firstHitPosition.getxPosition(), firstHitPosition.getyPosition() + 1, false, false);
			break;
		case SOUTH:
			randomPositionAround1stHit = new Coordinate(firstHitPosition.getxPosition(), firstHitPosition.getyPosition() - 1, false, false);
			break;
		case EAST:
			randomPositionAround1stHit = new Coordinate(firstHitPosition.getxPosition() + 1, firstHitPosition.getyPosition(), false, false);
			break;
		case WEST:
			randomPositionAround1stHit = new Coordinate(firstHitPosition.getxPosition() - 1, firstHitPosition.getyPosition(), false, false);
			break;
		default:
			System.out.println("Richtung nicht programmiert!");
		}
		return randomPositionAround1stHit;
	}
}
