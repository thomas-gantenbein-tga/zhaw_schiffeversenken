package ch.zhaw.schiffeversenken.data;

import static org.junit.Assert.assertTrue;

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
	@Test
	public void	testGetPossibleShipPositionsionsAround1stHit() {
		//testing the possible position ship position after the first hit. The second shot after a corner shot will be tested
		Coordinate coordinate0_0 = new Coordinate(0, 0, false, false);
		Coordinate coordinate0_0East = new Coordinate(1, 0, false, false);
		Coordinate coordinate0_0South = new Coordinate(0, 1, false, false);
		Coordinate possibleShipPosition0_0 = playField.getPossibleShipPositionsionsAround1stHit(coordinate0_0);
		assertTrue("Second shot position after hitting the upper left corner not correct", possibleShipPosition0_0.equals(coordinate0_0South) || possibleShipPosition0_0.equals(coordinate0_0East));
		Coordinate coordinateMax_0 = new Coordinate(playField.getColumnCount() - 1, 0, false, false);
		Coordinate coordinateMax_0West = new Coordinate(playField.getColumnCount() - 2, 0, false, false);
		Coordinate coordinateMax_0South = new Coordinate(playField.getColumnCount() - 1, 1, false, false);
		Coordinate possibleShipPositionMax_0 = playField.getPossibleShipPositionsionsAround1stHit(coordinateMax_0);
		assertTrue("Second shot position after hitting the upper right corner not correct", possibleShipPositionMax_0.equals(coordinateMax_0West) || possibleShipPositionMax_0.equals(coordinateMax_0South));
		Coordinate coordinate0_Max = new Coordinate(0, playField.getRowCount() - 1, false, false);
		Coordinate coordinate0_MaxEast = new Coordinate(1, playField.getRowCount() - 1 , false, false);
		Coordinate coordinate0_MaxNorth = new Coordinate(0,playField.getRowCount() - 2, false, false);
		Coordinate possibleShipPosition0_Max = playField.getPossibleShipPositionsionsAround1stHit(coordinate0_Max);
		assertTrue("Second shot position after hitting the lower left corner not correct", possibleShipPosition0_Max.equals(coordinate0_MaxEast) || possibleShipPosition0_Max.equals(coordinate0_MaxNorth));		
		Coordinate coordinateMax_Max = new Coordinate(playField.getColumnCount() - 1, playField.getRowCount() - 1, false, false);
		Coordinate coordinateMax_MaxWest = new Coordinate(playField.getColumnCount() - 2, playField.getRowCount() - 1, false, false);
		Coordinate coordinateMax_MaxNorth = new Coordinate(playField.getColumnCount() - 1, playField.getRowCount() - 2, false, false);
		Coordinate possibleShipPositionMax_Max = playField.getPossibleShipPositionsionsAround1stHit(coordinateMax_Max);
		assertTrue("Second shot position after hitting the upper right corner not correct", possibleShipPositionMax_Max.equals(coordinateMax_MaxWest) || possibleShipPositionMax_Max.equals(coordinateMax_MaxNorth));
		
		//testing the possible position ship position after the first hit. The second shot after a side shot will be tested
		Coordinate coordinate10_0 = new Coordinate(10, 0, false, false);
		Coordinate coordinate10_0West = new Coordinate(9, 0, false, false);
		Coordinate coordinate10_0East = new Coordinate(11, 0, false, false);
		Coordinate coordinate10_0South = new Coordinate(10, 1, false, false);
		Coordinate possibleShipPosition10_0 = playField.getPossibleShipPositionsionsAround1stHit(coordinate10_0);
		assertTrue("Second shot position after hitting the upper right corner not correct", possibleShipPosition10_0.equals(coordinate10_0West) || possibleShipPosition10_0.equals(coordinate10_0East) || possibleShipPosition10_0.equals(coordinate10_0South));
		Coordinate coordinate0_10 = new Coordinate(0, 10, false, false);
		Coordinate coordinate0_10North = new Coordinate(0, 9, false, false);
		Coordinate coordinate0_10South = new Coordinate(0, 11, false, false);
		Coordinate coordinate0_10East = new Coordinate(1, 10, false, false);
		Coordinate possibleShipPosition0_10 = playField.getPossibleShipPositionsionsAround1stHit(coordinate0_10);
		assertTrue("Second shot position after hitting the upper left corner not correct", possibleShipPosition0_10.equals(coordinate0_10North) || possibleShipPosition0_10.equals(coordinate0_10South) || possibleShipPosition0_10.equals(coordinate0_10East));
		Coordinate coordinate10_Max = new Coordinate(10, playField.getRowCount() - 1, false, false);
		Coordinate coordinate10_MaxWest = new Coordinate(9, playField.getRowCount() - 1, false, false);
		Coordinate coordinate10_MaxEast = new Coordinate(11, playField.getRowCount() - 1, false, false);
		Coordinate coordinate10_MaxNorth = new Coordinate(10,playField.getRowCount() - 2, false, false);
		Coordinate possibleShipPosition10_Max = playField.getPossibleShipPositionsionsAround1stHit(coordinate10_Max);
		assertTrue("Second shot position after hitting the lower left corner not correct", possibleShipPosition10_Max.equals(coordinate10_MaxWest) || possibleShipPosition10_Max.equals(coordinate10_MaxEast) || possibleShipPosition10_Max.equals(coordinate10_MaxNorth));		
		Coordinate coordinateMax_10 = new Coordinate(playField.getColumnCount() - 1, 10, false, false);
		Coordinate coordinateMax_10North = new Coordinate(playField.getColumnCount() - 1, 9, false, false);
		Coordinate coordinateMax_10South = new Coordinate(playField.getColumnCount() - 1, 11, false, false);
		Coordinate coordinateMax_10West = new Coordinate(playField.getColumnCount() - 2, 10, false, false);
		Coordinate possibleShipPositionMax_10 = playField.getPossibleShipPositionsionsAround1stHit(coordinateMax_10);
		assertTrue("Second shot position after hitting the upper right corner not correct", possibleShipPositionMax_10.equals(coordinateMax_10North) || possibleShipPositionMax_10.equals(coordinateMax_10South) || possibleShipPositionMax_10.equals(coordinateMax_10West));
		
		//testing the possible position ship position after the first hit. The second shot after a center shot will be tested
		Coordinate coordinate10_10 = new Coordinate(10, 0, false, false);
		Coordinate coordinate10_10West = new Coordinate(9, 0, false, false);
		Coordinate coordinate10_10East = new Coordinate(11, 0, false, false);
		Coordinate coordinate10_10North = new Coordinate(10, 1, false, false);
		Coordinate coordinate10_10South = new Coordinate(10, 1, false, false);
		Coordinate possibleShipPosition10_10 = playField.getPossibleShipPositionsionsAround1stHit(coordinate10_10);
		assertTrue("Second shot position after hitting a center field is not correct", possibleShipPosition10_10.equals(coordinate10_10West) || possibleShipPosition10_10.equals(coordinate10_10East) || possibleShipPosition10_10.equals(coordinate10_10North) || possibleShipPosition10_10.equals(coordinate10_10South));
	}
	
	@Test
	public void testgetShipPositionsionsFurtherHits() {
		playField.addShip(ship);
		playField.processShot(coordinates.get(0));
		playField.processShot(coordinates.get(1));
		Coordinate coordinate0_2 = new Coordinate(0, 2, false, false);		
		assertTrue("Third shot on a corner ship not correct", playField.getShipPositionsionsFurtherHits(ship.getWoundPositions().get(1), ship).equals(coordinate0_2));
	}
}
