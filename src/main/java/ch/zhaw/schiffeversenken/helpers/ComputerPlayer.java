package ch.zhaw.schiffeversenken.helpers;

import java.util.Random;

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
		Coordinate coordinate = new Coordinate(xCoordinate, yCoordinate, null);
		return coordinate;
	}
}
