package ch.zhaw.schiffeversenken.helpers;

import java.util.Random;

import ch.zhaw.schiffeversenken.data.PlayField;

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
		int indexOfShootFreaSea = 0;
		int indexOfShootShip = 0;
		boolean shipOrFreeSeaUnhit = false;
		// check if a ship is only hit once
		shootPosition = playerField.possibleShipPositionsionsAround1stHit();
		if (shootPosition != null) {
			//search around 1st hit position and check if the position was hit before
			do {
				if (playerField.isCoordinateInFreeSea(shootPosition)) {
					indexOfShootFreaSea = playerField.getFreeSea().indexOf(shootPosition);
					shipOrFreeSeaUnhit = !playerField.getFreeSea().get(indexOfShootFreaSea).getIsHit();
				}
				else if (playerField.getShipsCoordinates().contains(shootPosition)) {
					indexOfShootShip = playerField.getShipsCoordinates().indexOf(shootPosition);
					shipOrFreeSeaUnhit = !playerField.getShipsCoordinates().get(indexOfShootShip).getIsHit();
				}
				//Generate new shootPosition if the first trial was a hitPosition
				if (!shipOrFreeSeaUnhit)
					shootPosition = playerField.possibleShipPositionsionsAround1stHit();
			}
			while (!shipOrFreeSeaUnhit);
			return shootPosition;
		}
		// Generate new random shootPosition with a check if it was hit before
		shipOrFreeSeaUnhit = false;
		do {
			shootPosition = makeRandomShot();
			if (playerField.isCoordinateInFreeSea(shootPosition)) {
				indexOfShootFreaSea = playerField.getFreeSea().indexOf(shootPosition);
				shipOrFreeSeaUnhit = !playerField.getFreeSea().get(indexOfShootFreaSea).getIsHit();
			}
			else if (playerField.getShipsCoordinates().contains(shootPosition)) {
				indexOfShootShip = playerField.getShipsCoordinates().indexOf(shootPosition);
				shipOrFreeSeaUnhit = !playerField.getShipsCoordinates().get(indexOfShootShip).getIsHit();
			}
		}
		while (!shipOrFreeSeaUnhit);
		return shootPosition;
	}
	
}
