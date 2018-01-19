package ch.zhaw.schiffeversenken.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.zhaw.schiffeversenken.helpers.Coordinate;
import ch.zhaw.schiffeversenken.helpers.Directions;

/**
 * Contains the data / references to data with the main information of a
 * player's field: ships, size of the field and locations with free sea.
 * <p>
 * The location of the ships is stored in the Ship objects themselves.
 * <p>
 * Coordinates of free sea are computed when the constructor is called. Whenever
 * a ship is added to the field, the now occupied coordinates are removed from
 * the list of free sea coordinates.
 *
 */
public class PlayField {
	private List<Ship> ships;
	private List<Coordinate> freeSea;
	private int columnCount;
	private int rowCount;

	/**
	 * Initializes the PlayField with a given size (width and height can
	 * differ). At initialization, all coordinates of the field are part of the
	 * freeSea list of this object.
	 * 
	 * @param columnCount
	 *            vertical size of the PlayField
	 * @param rowCount
	 *            horizontal size of the PlayField
	 */
	public PlayField(int columnCount, int rowCount) {
		this.columnCount = columnCount;
		this.rowCount = rowCount;
		freeSea = new ArrayList<Coordinate>();
		ships = new ArrayList<Ship>();

		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				Coordinate coordinate = new Coordinate(i, j, false, false);
				freeSea.add(coordinate);
			}
		}
	}

	/**
	 * Method called by the Game object. Handles the "shot" by manipulating the
	 * data of this object, e.g. setting the "isHit" field of a coordinate of a
	 * ship to "true".
	 * <p>
	 * Returns false if this field has already been hit. If false is returned,
	 * the Game object knows how to deal with this event.
	 * 
	 * @param coordinate
	 * @return false if the shot did not change anything in the state of the
	 *         PlayField, e.g. because a particular coordinate had already been
	 *         hit.
	 */

	public boolean processShot(Coordinate coordinate) {
		// checks for a shot in the sea first
		// Coordinate are considered equal if their x- and y-coordinates are
		// equal.
		if (freeSea.contains(coordinate)) {
			int indexOfHit = freeSea.indexOf(coordinate);

			if (freeSea.get(indexOfHit).getIsHit()) {
				return false;
			} else {
				freeSea.get(indexOfHit).setIsHit(true);
				return true;
			}

			// if no free sea was hit, checks whether a ship was hit.
		} else {
			for (Ship ship : ships) {
				if (ship.isHit(coordinate)) {
					int indexOfHit = ship.getShipPositions().indexOf(coordinate);
					if (ship.getShipPositions().get(indexOfHit).getIsHit()) {
						return false;
					} else {
						// if ship was hit and was not already hit some time
						// before,
						// the "isHit" field of the coordinate is set to true
						// and the function returns true.
						ship.getShipPositions().get(indexOfHit).setIsHit(true);
						// checks whether this hit was the last one for this
						// ship
						if (ship.isSunk()) {
							for (Coordinate sunkCoordinate : ship.getShipPositions()) {
								sunkCoordinate.setIsSunk(true);
							}
						}
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Adds a ship to the PlayField. Removes the ship's coordinates from the
	 * list of coordinates of free sea.
	 * 
	 * @param ship
	 *            the ship object to be placed on this PlayField
	 */
	public void addShip(Ship ship) {
		ships.add(ship);
		List<Coordinate> shipCoordinates = ship.getShipPositions();

		for (Coordinate coordinate : shipCoordinates) {
			freeSea.remove(coordinate);
		}
	}

	/**
	 * Adds a ship to the PlayField at a random position. Removes the ship's
	 * coordinates from the list of coordinates of free sea.
	 * 
	 * @param shipSize
	 *            the size of the Ship to be created
	 * @throws IllegalStateException
	 *             if the ship cannot be placed at a valid position after 100
	 *             tries (e.g. because the PlayField is already too full to
	 *             accomodate another Ship object).
	 */
	public void addRandomShip(int shipSize) throws IllegalStateException {
		addShip(new Ship(columnCount, rowCount, shipSize));
		int tries = 1;
		while (deleteLastAddedShipIfUnviable()) {
			addShip(new Ship(columnCount, rowCount, shipSize));
			tries++;
			if (tries > 100) {
				throw new IllegalStateException();
			}
		}
	}

	/**
	 * Gets the list of coordinates of all ships, regardless of their status
	 * (hit, sunk, intact). Useful for objects implementing the Display
	 * interface, since they only have to "know" how to handle Coordinate
	 * objects, not also Ship objects.
	 * 
	 * @return The list of coordinates where ships are placed in this field.
	 */
	public List<Coordinate> getShipsCoordinates() {
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		for (Ship ship : ships) {
			coordinates.addAll(ship.getShipPositions());
		}
		return coordinates;
	}

	/**
	 * Gets the list of all coordinates that are not occupied by ships.
	 * 
	 * @return coordinates with no ships on them
	 */
	public List<Coordinate> getFreeSea() {
		return freeSea;
	}

	/**
	 * Gets the width of the PlayField as the number of columns.
	 * 
	 * @return the number of columns of this PlayField object
	 */
	public int getColumnCount() {
		return columnCount;
	}

	/**
	 * Gets the height of the PlayField as the number of rows.
	 * 
	 * @return the number of rows of this PlayField object
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * Gets the list of ships on this PlayField. Used by the GUI to count the
	 * number of not destroyed ships. Other objects only rely on Coordinate
	 * instances and do know nothing of Ship objects.
	 * 
	 * @return the number of columns of this PlayField object
	 */
	public List<Ship> getShips() {
		return ships;
	}

	/**
	 * Gets a specific ship (index) on this PlayField.
	 * 
	 * @return the requested ship in the List<Ship>
	 * @author uelik
	 */
	public Ship getShip(int shipIndex) {
		return ships.get(shipIndex);
	}

	/**
	 * Gets the last ship on this PlayField. Used for different checks of the
	 * lastly added ship.
	 * 
	 * @return the last ship in the List<Ship>
	 * @author uelik
	 */
	public Ship getLastShip() {
		return ships.get(ships.size() - 1);
	}

	/**
	 * Delete a ship on this PlayField and add the free coordinates to freeSea
	 * (if they are within the playing field).
	 * 
	 * @author uelik
	 * 
	 */
	public void deleteShip(Ship ship) {
		List<Coordinate> coordinateList = ship.shipPositions;
		ships.remove(ship);

		List<Coordinate> validCoordinateList = new ArrayList<Coordinate>();

		for (Coordinate coordinate : coordinateList) {
			if (coordinate.getxPosition() < rowCount && coordinate.getyPosition() < columnCount
					&& !getShipsCoordinates().contains(coordinate)) {
				validCoordinateList.add(coordinate);
			}
		}

		freeSea.addAll(validCoordinateList);
	}

	/**
	 * Deletes all ships on this playing field and adds the ship coordinates to
	 * the list of free sea coordinates
	 */

	public void deleteAllShips() {
		// copy of ship list to avoid concurrent modification
		List<Ship> copyOfShipList = new ArrayList<Ship>(ships);
		for (Ship ship : copyOfShipList) {
			deleteShip(ship);
		}
	}

	/**
	 * Plausibility tests of a newly added ship. If a ship fails it will be
	 * deleted.
	 * 
	 * @return true if the last added ship is in part placed outside of the
	 *         playField or if it collides with another ship
	 * 
	 * @author uelik
	 * 
	 */
	public boolean deleteLastAddedShipIfUnviable() {
		// Is the ship in the PlayField?
		if (!getLastShip().isInPlayfield(columnCount, rowCount)) {
			deleteShip(ships.get(ships.size() - 1));
			return true;
		}

		// Does the ship not cross an existing one?
		for (int i = 0; i < getShips().size() - 1; i++) {
			if (getLastShip().isInCollision(getShip(i).getShipPositions())) {
				deleteShip(ships.get(ships.size() - 1));
				return true;
			}
		}
		return false;
	}

	/**
	 * Find the next ship position after the first hit. Only positions within
	 * the playField will be returned.
	 * 
	 * @param coordinate1stHit
	 *            Coordinate of the first hit
	 * 
	 * @return The next possible ship position around the first hit. Return null
	 *         if there is no ship with only one wound position
	 * 
	 * @author uelik
	 * 
	 */
	public Coordinate getPossibleShipPositionsionsAround1stHit(Coordinate coordinate1stHit) {
		Coordinate coordinateShootPosition = null;
		// generate coordinates around first hit until it is within the
		// playField
		do {
			coordinateShootPosition = getRandomCoordinateAround4Directions(coordinate1stHit);
		} while (!coordinateShootPosition.isCoordinateInPlayField(this));
		return coordinateShootPosition;
	}

	/**
	 * Get a random Position around a given position according to the four
	 * Directions NORTH,SOUTH,EAST,WEST
	 * 
	 * @param firstHitPosition
	 *            Coordinate of the first hit
	 * 
	 * @return Returns a Coordinate with the random position around the given
	 *         one
	 * @author uelik
	 */
	public Coordinate getRandomCoordinateAround4Directions(Coordinate firstHitPosition) {
		Coordinate randomPositionAround1stHit = null;
		switch (Directions.getRandom()) {
		case NORTH:
			randomPositionAround1stHit = new Coordinate(firstHitPosition.getxPosition(),
					firstHitPosition.getyPosition() + 1, false, false);
			break;
		case SOUTH:
			randomPositionAround1stHit = new Coordinate(firstHitPosition.getxPosition(),
					firstHitPosition.getyPosition() - 1, false, false);
			break;
		case EAST:
			randomPositionAround1stHit = new Coordinate(firstHitPosition.getxPosition() + 1,
					firstHitPosition.getyPosition(), false, false);
			break;
		case WEST:
			randomPositionAround1stHit = new Coordinate(firstHitPosition.getxPosition() - 1,
					firstHitPosition.getyPosition(), false, false);
			break;
		default:
			System.out.println("Richtung nicht programmiert!");
		}
		return randomPositionAround1stHit;
	}

	/**
	 * Get a random Position around a given position in a given direction
	 * Directions NORTH,WEST
	 * 
	 * @param hitPosition
	 *            Coordinate of a hit position directionShip the direction of
	 *            the ship (NORTH for vertical, WEST for horizontal)
	 * 
	 * @return Returns a Coordinate with the random position in a given
	 *         direction
	 * 
	 * @author uelik
	 */
	public Coordinate getRandomCoordinateAround2Directions(Coordinate hitPosition, Directions directionShip) {
		Coordinate randomPositionAroundHit = null;
		Random randomGenerator = new Random();
		boolean randomBool = randomGenerator.nextBoolean();

		switch (directionShip) {
		case NORTH:
			if (randomBool)
				randomPositionAroundHit = new Coordinate(hitPosition.getxPosition(), hitPosition.getyPosition() + 1,
						false, false);
			randomPositionAroundHit = new Coordinate(hitPosition.getxPosition(), hitPosition.getyPosition() - 1, false,
					false);
			break;
		case WEST:
			if (randomBool)
				randomPositionAroundHit = new Coordinate(hitPosition.getxPosition() + 1, hitPosition.getyPosition(),
						false, false);
			randomPositionAroundHit = new Coordinate(hitPosition.getxPosition() - 1, hitPosition.getyPosition(), false,
					false);
			break;
		default:
			System.out.println("Richtung nicht programmiert!");
		}
		return randomPositionAroundHit;
	}

	/**
	 * Get opposite position of a given position in a given direction Directions
	 * NORTH,WEST
	 * 
	 * @param hitPosition
	 *            Coordinate of a hit position
	 * @param firstShot
	 *            Coordinate of the first but unsuccessful shot in the given
	 *            direction (NORTH for vertical, WEST for horizontal)
	 * @param directionShip
	 *            the direction of the ship (NORTH for vertical, WEST for
	 *            horizontal)
	 * 
	 * @return Returns a Coordinate with the random position in a given
	 *         direction
	 * 
	 * @author uelik
	 */
	public Coordinate getOppositCoordinateAround2Directions(Coordinate hitPosition, Coordinate firstShot,
			Directions directionShip) {
		Coordinate shootPosition = null;

		switch (directionShip) {
		case NORTH:
			if (hitPosition.getyPosition() < firstShot.getyPosition())
				shootPosition = new Coordinate(hitPosition.getxPosition(), hitPosition.getyPosition() - 1, false,
						false);
			else
				shootPosition = new Coordinate(hitPosition.getxPosition(), hitPosition.getyPosition() + 1, false,
						false);
			break;
		case WEST:
			if (hitPosition.getxPosition() < firstShot.getxPosition())
				shootPosition = new Coordinate(hitPosition.getxPosition() - 1, hitPosition.getyPosition(), false,
						false);
			else
				shootPosition = new Coordinate(hitPosition.getxPosition() + 1, hitPosition.getyPosition(), false,
						false);
		default:
			System.out.println("Richtung nicht programmiert!");
		}
		return shootPosition;
	}

	/**
	 * Find the next ship position after the second or more hit. Only positions
	 * within the playField will be returned.
	 *
	 * @param hitPosition
	 *            Coordinate of a hit position woundShip the wounded ship
	 * 
	 * @return The next ship position in the direction of the hits. If one
	 *         direction is hit return the other side. Return a hit field
	 *         outside of the Playfield if the calculated Coordinate is not
	 *         within the PlayField
	 * 
	 * @author uelik
	 * 
	 */
	public Coordinate getShipPositionsionsFurtherHits(Coordinate hitPosition, Ship woundShip) {
		Coordinate shootPosition = null;
		Directions directionShip = null;
		directionShip = woundShip.getDirectionsOfHits();
		shootPosition = getRandomCoordinateAround2Directions(hitPosition, directionShip);
		if (!isShipOrFreeSeaCoordinateHit(shootPosition)) {
			if (shootPosition.isCoordinateInPlayField(this))
				return shootPosition;
		} else {
			shootPosition = getOppositCoordinateAround2Directions(hitPosition, shootPosition, directionShip);
			if (shootPosition.isCoordinateInPlayField(this))
				return shootPosition;
		}
		return new Coordinate(-1, -1, true, false);
	}

	/**
	 * Find a wound ship which is still swimming
	 * 
	 * @return the wound ship if there is any; null if there is no wound ship
	 * 
	 * @author uelik
	 * 
	 */
	public Ship getWoundButSwimmingShip() {
		for (Ship ship : ships) {
			if (ship.getWoundPositions().size() < ship.getShipPositions().size())
				if (ship.getWoundPositions().size() > 0)
					return ship;
		}
		return null;
	}

	/**
	 * Checks if a Coordinate is in freeSea
	 * 
	 * @param coordinateFreeSea
	 *            Coordinate of free sea
	 * 
	 * @return true when the Coordinate is in freeSea, otherwise false
	 * 
	 * @author uelik
	 * 
	 */
	public boolean isCoordinateInFreeSea(Coordinate coordinateFreeSea) {
		int indexOfShoot = getFreeSea().indexOf(coordinateFreeSea);
		if (indexOfShoot == -1)
			return false;
		return true;
	}

	/**
	 * Checks if a Coordinate of Ships or freeSea is hit
	 * 
	 * @param shootPosition
	 *            Coordinate of the shoot position
	 * 
	 * @return Return true when the Coordinate of Ships or freeSea is hit,
	 *         otherwise return false
	 * 
	 * @author uelik
	 * 
	 */
	public boolean isShipOrFreeSeaCoordinateHit(Coordinate shootPosition) {
		int indexOfShootFreaSea = 0;
		int indexOfShootShip = 0;
		if (isCoordinateInFreeSea(shootPosition)) {
			indexOfShootFreaSea = getFreeSea().indexOf(shootPosition);
			return getFreeSea().get(indexOfShootFreaSea).getIsHit();
		} else if (getShipsCoordinates().contains(shootPosition)) {
			indexOfShootShip = getShipsCoordinates().indexOf(shootPosition);
			return getShipsCoordinates().get(indexOfShootShip).getIsHit();
		}
		return false;
	}

}
