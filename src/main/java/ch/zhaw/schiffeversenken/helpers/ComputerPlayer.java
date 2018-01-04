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
	 * @param display	Any object implementing the Display interface.
	 */
	public Coordinate makeLogicShot(PlayField playerField) {
		Coordinate shootPosition = null;
		int indexOfShoot = 0;
		// check if a ship is only hit once
		if( playerField.possibleShipPositionsionsAround1stHit() != null) {
			//search around 1st hit position and check if the position was hit before
			do{
				shootPosition = playerField.possibleShipPositionsionsAround1stHit();
				indexOfShoot = playerField.getFreeSea().indexOf(shootPosition);
			}
			while(playerField.getFreeSea().get(indexOfShoot).getIsHit() || indexOfShoot != -1 );
		}

		if (shootPosition != null)
			return shootPosition;
		return makeRandomShot();
	}
	
}
