package ch.zhaw.schiffeversenken.data;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShipTest {
	List<Coordinate> coordinates;
	Coordinate noShipCoordinate;

	@Before
	public void initialize() {
		coordinates = new ArrayList<Coordinate>();
		Coordinate coordinate = new Coordinate(0, 0, null, null);
		coordinates.add(coordinate);
		coordinate = new Coordinate(0, 1, null, null);
		coordinates.add(coordinate);
		coordinate = new Coordinate(0, 2, null, null);
		coordinates.add(coordinate);

		noShipCoordinate = new Coordinate(0, 3, false, false);

	}

	@Test
	public void testInitialization() {
		Ship ship = new Ship(coordinates);
		Assert.assertTrue(ship.getShipPositions().containsAll(coordinates));
		Assert.assertFalse(coordinates.get(0).getIsHit());
		Assert.assertFalse(coordinates.get(0).getIsSunk());
	}

	@Test
	public void testIsHit() {
		Ship ship = new Ship(coordinates);
		Assert.assertFalse(ship.isHit(noShipCoordinate));
		for(Coordinate coordinate : coordinates) {
			Assert.assertTrue(ship.isHit(coordinate));
		}
	}
	
	@Test
	public void testIsSunk() {
		Ship ship = new Ship(coordinates);
		for(Coordinate coordinate : coordinates) {
			coordinate.setIsHit(true);;
		}
		
		Assert.assertTrue(ship.isSunk());
		coordinates.get(0).setIsHit(false);
		Assert.assertFalse(ship.isSunk());

	}

}
