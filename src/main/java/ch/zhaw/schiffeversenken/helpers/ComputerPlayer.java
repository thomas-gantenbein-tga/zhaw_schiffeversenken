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

	public Coordinate makeLogicShot(PlayField playerField) {
		Coordinate shootPosition = null;
		int indexOfShoot = 0;
		if( playerField.possibleShipPositionsionsAround1stHit() != null) {
			do{
				shootPosition = playerField.possibleShipPositionsionsAround1stHit();
				indexOfShoot = playerField.getFreeSea().indexOf(shootPosition);
				System.out.println(shootPosition);
			}
			while(playerField.getFreeSea().get(indexOfShoot).getIsHit());
		}

		if (shootPosition != null)
			return shootPosition;
		return makeRandomShot();
	}
	
}
