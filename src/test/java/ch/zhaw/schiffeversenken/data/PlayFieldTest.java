package ch.zhaw.schiffeversenken.data;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ch.zhaw.schiffeversenken.helpers.Coordinate;

public class PlayFieldTest {
	PlayField playField;
	List<Coordinate> coordinates;
	Coordinate noShipCoordinate;
	Ship ship;

	/**
	 * Set up a PlayField and a list of coordinates
	 */
	@Before
	public void initialize() {
		playField = new PlayField(25, 25);
		coordinates = new ArrayList<Coordinate>();
		Coordinate coordinate = new Coordinate(0, 0, false, false);
		coordinates.add(coordinate);
		coordinate = new Coordinate(0, 1, false, false);
		coordinates.add(coordinate);
		coordinate = new Coordinate(0, 2, false, false);
		coordinates.add(coordinate);
		ship = new Ship(coordinates);

		noShipCoordinate = new Coordinate(0, 3, false, false);
	}

	@Test
	public void testInitialization() {
		Assert.assertTrue(playField.getColumnCount() == 25);
		Assert.assertTrue(playField.getRowCount() == 25);
		Assert.assertTrue(playField.getFreeSea().size() == 625);
		Assert.assertTrue(playField.getShips().size() == 0);
	}

	@Test
	public void testAddShip() {
		Ship ship = new Ship(coordinates);

		playField.addShip(ship);
		Assert.assertTrue(playField.getShips().size() == 1);
		Assert.assertTrue(playField.getFreeSea().size() == 622);

		for (Coordinate coordinate : coordinates) {
			Assert.assertTrue(!playField.getFreeSea().contains(coordinate));
		}

		Assert.assertTrue(playField.getShipsCoordinates().containsAll(coordinates));
		Assert.assertTrue(playField.getFreeSea().contains(noShipCoordinate));
	}

	@Test
	public void testProcessShot() {
		playField.addShip(ship);

		Assert.assertTrue("first shot at the coordinate should return true", playField.processShot(noShipCoordinate));
		for (Coordinate coordinate : coordinates) {
			Assert.assertFalse(coordinate.getIsHit());
		}

		Assert.assertFalse("second shot at a coordinate should return true", playField.processShot(noShipCoordinate));

		Assert.assertTrue("first shot at the coordinate should return true", playField.processShot(coordinates.get(0)));
		Assert.assertTrue(coordinates.get(0).getIsHit());
		Assert.assertFalse(coordinates.get(1).getIsHit());
		Assert.assertFalse(coordinates.get(2).getIsHit());
		Assert.assertFalse(playField.getShips().get(0).isSunk());

		Assert.assertTrue("first shot at the coordinate should return true", playField.processShot(coordinates.get(1)));
		Assert.assertTrue("first shot at the coordinate should return true", playField.processShot(coordinates.get(2)));

		Assert.assertTrue("when all positions have been hit, getIsSunk() should be true",
				playField.getShips().get(0).isSunk());

	}
}
