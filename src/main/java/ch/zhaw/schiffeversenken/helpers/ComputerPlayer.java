package ch.zhaw.schiffeversenken.helpers;

import java.util.Random;

import ch.zhaw.schiffeversenken.data.PlayField;
import ch.zhaw.schiffeversenken.data.Ship;

/**
 * A very, very stupid computer player. Is even allowed to shoot several times at the same field.
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

	public Coordinate makeRandomShot() {
		int xCoordinate = randomGenerator.nextInt(rowCount);
		int yCoordinate = randomGenerator.nextInt(columnCount);
		Coordinate coordinate = new Coordinate(xCoordinate, yCoordinate, null, null);
		return coordinate;
	}
	/**
	 * 
	 * 
	 */
	public Coordinate makeLogicShot(PlayField playerField) {
		Coordinate shootPosition = null;
		Ship woundShip = null;
		int indexWoundShipPosition = 0;
		
		woundShip = playerField.getWoundButSwimmingShip();
		// check if a ship is wound
		//shootPosition = playerField.possibleShipPositionsionsAround1stHit();
		if (woundShip != null) {
			//check it the wound ship is hit only once
			if( woundShip.getWoundPositions().size()<=1) {
				//search around 1st hit position and check if the position was hit before
				do {
					//Generate new shootPosition if the first trial was a hitPosition
					shootPosition = playerField.getpossibleShipPositionsionsAround1stHit(woundShip.getWoundPositions().get(1));
				}
				while (playerField.isShipOrFreeSeaCoordinateHit(shootPosition));
				return shootPosition;
			}
			// check if a ship is hit more than once but not sunk
			else {
				do {
					indexWoundShipPosition++;
					shootPosition = playerField.getShipPositionsionsFurtherHits(woundShip.getWoundPositions().get(indexWoundShipPosition), woundShip);
				}
				while (playerField.isShipOrFreeSeaCoordinateHit(shootPosition));
				return shootPosition;
			}
		}
		// Generate new random shootPosition with a check if it was hit before
		do {
			shootPosition = makeRandomShot();
			if (!playerField.isShipOrFreeSeaCoordinateHit(shootPosition))
				return shootPosition;
		}
		while (playerField.isShipOrFreeSeaCoordinateHit(shootPosition));
		return shootPosition;
	}
	
}
