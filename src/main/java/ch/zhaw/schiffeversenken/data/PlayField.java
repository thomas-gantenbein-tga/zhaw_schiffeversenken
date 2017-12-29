package ch.zhaw.schiffeversenken.data;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.schiffeversenken.helpers.Coordinate;

/**
 * Contains the data / references to data with the main information of a
 * player's field: ships and size.
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
	 * @param rowCount
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

	// TODO: maybe completely move this method to game level
	/**
	 * Returns false if this field has already been hit.
	 * If false is returned, the Game object knows how to deal with this event.
	 * 
	 * @param coordinate
	 * @return
	 */

	public boolean processShot(Coordinate coordinate) {
		//checks for a shot in the sea first
		if (freeSea.contains(coordinate)) {
			int indexOfHit = freeSea.indexOf(coordinate);

			if (freeSea.get(indexOfHit).getIsHit()) {
				return false;
			} else {
				freeSea.get(indexOfHit).setIsHit(true);
				return true;
			}
		
		//if no free sea was hit, checks whether a ship was hit.
		} else {
			for (Ship ship : ships) {
				if (ship.isHit(coordinate)) {
					int indexOfHit = ship.getShipPositions().indexOf(coordinate);
					if (ship.getShipPositions().get(indexOfHit).getIsHit()) {
						return false;
					} else {
						//if ship was hit and was not already hit some time before, 
						//the "isHit" field of the coordinate is set to true and the function returns true.
						ship.getShipPositions().get(indexOfHit).setIsHit(true);
						//checks whether this hit was the last one for this ship
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
	 * Gets the list of coordinates of all ships, regardless of their status (hit, sunk, intact).
	 * 
	 * @return	The list of coordinates where ships are placed in this field.
	 */
	public List<Coordinate> getShipsCoordinates() {
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		for (Ship ship : ships) {
			coordinates.addAll(ship.getShipPositions());
		}
		return coordinates;
	}

	/**
	 * Gets the list of all coordinates that are no occupied by ships.
	 * @return	coordinates with no ships on them
	 */
	public List<Coordinate> getFreeSea() {
		return freeSea;
	}

	/**
	 * Adds a ship to the PlayField. Removes the ship's coordinates from the list of
	 * coordinates of free sea. 
	 * 
	 * @param ship the ship object to be placed on this PlayField
	 */
	public void addShip(Ship ship) {
		ships.add(ship);
		List<Coordinate> shipCoordinates = ship.getShipPositions();

		for (Coordinate coordinate : shipCoordinates) {
			freeSea.remove(coordinate);
		}
	}

	/**
	 * Gets the width of the PlayField as the number of columns.
	 * 
	 * @return	the number of columns of this PlayField object
	 */
	public int getColumnCount() {
		return columnCount;
	}

	/**
	 * Gets the height of the PlayField as the number of rows.
	 * 
	 * @return	the number of rows of this PlayField object
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * Gets the list of ships on this PlayField. Used by the GUI to 
	 * count the number of not destroyed ships. Other objects only rely
	 * on Coordinate instances and do know nothing of Ship objects.
	 * 
	 * @return	the number of columns of this PlayField object
	 */
	public List<Ship> getShips() {
		return ships;
	}
	
	/**
	 * Gets a specific ship (index) on this PlayField. 
	 * 
	 * @return	the requested ship in the List<Ship>
	 * @author uelik
	 */
	public Ship getShip(int shipIndex) {
		return ships.get(shipIndex);
	}
	
	/**
	 * Gets the last ship on this PlayField. Used for different checks of the lastly added ship
	 * 
	 * @return	the last ship in the List<Ship>
	 * @author uelik
	 */
	public Ship getLastShip() {
		return ships.get(ships.size()-1);
	}
	
	/**
	 * Delete the last ship on this PlayField.
	 * @author uelik
	 * 
	 */
	public void deleteLastShip() {
		ships.remove(ships.size()-1);
	}
	
	/**Plausibility test of a new ship. If a ship fails it will be deleted
	 * 
	 * @author uelik
	 * 
	 */
	public void shipPlausibilityTest(PlayField playfield, int columnCount, int rowCount) {
		if(!playfield.getLastShip().isInPlayfield(columnCount, rowCount)) {
			playfield.deleteLastShip();
			System.out.println("Schiff geloescht, nicht im Spielfeld");
		}
		else {
			
			for(int i=0; i < playfield.getShips().size() - 1; i++) {
				if (playfield.getLastShip().isInCollision(playfield.getShip(i).getShipPositions())) {
					playfield.deleteLastShip();
					System.out.println("Schiff geloescht, Feld(er) schon besetzt");
				}

			}
		}
	}
}
