package ch.zhaw.schiffeversenken.data;

import java.util.List;

import ch.zhaw.schiffeversenken.Coordinate;

public class Ship {
	List<Coordinate> shipPositions;
	
	public Ship(List<Coordinate> coordinates) {
		shipPositions = coordinates;
	}
	
	protected boolean isHit(Coordinate coordinate) {
		if (shipPositions.contains(coordinate)) {
			return true;
		}
		return false;
	}

	public List<Coordinate> getShipPositions() {
		return shipPositions;
	}

	public void setShipPositions(List<Coordinate> shipPositions) {
		this.shipPositions = shipPositions;
	}
}
