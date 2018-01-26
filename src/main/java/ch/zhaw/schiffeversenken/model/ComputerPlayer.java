package ch.zhaw.schiffeversenken.model;

import java.util.Random;

import ch.zhaw.schiffeversenken.data.Coordinate;
import ch.zhaw.schiffeversenken.data.PlayField;
import ch.zhaw.schiffeversenken.data.Ship;

/**
 * Computer player who makes random shots at the human player's field and acts
 * like a (smart enough) human player would when a ship is hit.
 *
 */
public class ComputerPlayer {
	private Random randomGenerator = new Random();
	private int rowCount;
	private int columnCount;

	public ComputerPlayer(int rowCount, int columnCount) {
		this.columnCount = columnCount;
		this.rowCount = rowCount;
	}
	
	/**
	 * A random Coordinate within the player's field will be generated and returned
	 * 
	 * @return a random Coordiante within the player's field
	 */	

	public Coordinate makeRandomShot() {
		int xCoordinate = randomGenerator.nextInt(rowCount);
		int yCoordinate = randomGenerator.nextInt(columnCount);
		Coordinate coordinate = new Coordinate(xCoordinate, yCoordinate, null, null);
		return coordinate;
	}

	/**
	 * Calculates the next possible shoot Coordinate on the player's field with a human like logic
	 * 1st it looks if a ship is hit only once and does random shoths around it
	 * 2nd it will check the direction of the hits and will shoot on a random end of the ship.
	 * If the random end is hit it will shoot at the other end.
	 * When there is no wound ship it will make a random shot at the player's field
	 * 
	 *  @param playerField
	 *  		the field to shoot at
	 *  
	 *  @author uelik
	 */
	public Coordinate makeLogicShot(PlayField playerField) {
		Coordinate shootPosition = null;
		Ship woundShip = null;
		int indexWoundShipPosition = 0;

		woundShip = playerField.getWoundButSwimmingShip();
		// check if a ship is wound
		if (woundShip != null) {
			// check it the wound ship is hit only once
			if (woundShip.getWoundPositions().size() <= 1) {
				// search around 1st hit position and check if the position was
				// hit before
				do {
					// Generate new shootPosition if the first trial was a
					// hitPosition
					shootPosition = playerField
							.getPossibleShipPositionsionsAround1stHit(woundShip.getWoundPositions().get(0));
				} while (playerField.isShipOrFreeSeaCoordinateHit(shootPosition));
				return shootPosition;
			}
			// check if a ship is hit more than once but not sunk
			else {
				do {
					shootPosition = playerField.getShipPositionsionsFurtherHits(
							woundShip.getWoundPositions().get(indexWoundShipPosition), woundShip);
					indexWoundShipPosition++;
				} while (playerField.isShipOrFreeSeaCoordinateHit(shootPosition));
				// when the proposed shoot position is not within the PlayField
				// shoot on the other end of the ship
				if (shootPosition.isCoordinateInPlayField(playerField))
					return shootPosition;
				else {
					indexWoundShipPosition = 1; // Don't begin at the first
												// wound position because the
												// ship can start at the borders
												// of the playfield
					do {
						shootPosition = playerField.getShipPositionsionsFurtherHits(
								woundShip.getWoundPositions().get(indexWoundShipPosition), woundShip);
						indexWoundShipPosition++;
					} while (playerField.isShipOrFreeSeaCoordinateHit(shootPosition));
					return shootPosition;
				}
			}
		}
		// Generate new random shootPosition with a check if it was hit before
		do {
			shootPosition = makeRandomShot();
			if (!playerField.isShipOrFreeSeaCoordinateHit(shootPosition))
				return shootPosition;
		} while (playerField.isShipOrFreeSeaCoordinateHit(shootPosition));
		return shootPosition;
	}

}
