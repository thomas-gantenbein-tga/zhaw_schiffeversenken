package ch.zhaw.schiffeversenken.data;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.schiffeversenken.Coordinate;

public class PlayField {
	private List<Ship> ships;
	private List<Coordinate> freeSea;
	private int columnCount;
	private int rowCount;

	public PlayField(int columnCount, int rowCount) {
		this.columnCount = columnCount;
		this.rowCount = rowCount;

		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; i < columnCount; j++) {
				Coordinate coordinate = new Coordinate(i, j, false);
				freeSea.add(coordinate);
			}
		}
	}

	public void processShot(Coordinate coordinate) {
		for (Ship ship : ships) {
			if (ship.isHit(coordinate)) {
				int indexOfHit = ship.getShipPositions().indexOf(coordinate);
				ship.getShipPositions().get(indexOfHit).setIsHit(true);
			}
		}
	}

	public List<Coordinate> getShipsCoordinates() {
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		for (Ship ship : ships) {
			coordinates.addAll(ship.getShipPositions());
		}
		return coordinates;
	}

	public List<Coordinate> getFreeSea() {
		return freeSea;
	}
	
	public void addShip(Ship ship) {
		ships.add(ship);
		List<Coordinate> shipCoordinates = ship.getShipPositions();
		
		for (Coordinate coordinate : shipCoordinates) {
			freeSea.remove(coordinate);
		}
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getRowCount() {
		return rowCount;
	}

}
