package ch.zhaw.schiffeversenken.data;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ch.zhaw.schiffeversenken.guicomponents.Display;
import ch.zhaw.schiffeversenken.guicomponents.RunningGameDisplay;
import ch.zhaw.schiffeversenken.helpers.ComputerPlayer;
import ch.zhaw.schiffeversenken.helpers.Coordinate;
import ch.zhaw.schiffeversenken.helpers.Directions;

public class GameTest {

	private PlayField playerField;
	private PlayField computerField;
	private ComputerPlayer computerPlayer;
	private List<Display> displayList;
	private Game game;

	@Before
	public void setUp() throws Exception {
		playerField = new PlayField(15, 15);
		computerField = new PlayField(30, 30);
		computerPlayer = new ComputerPlayer(15, 15);
		game = new Game(playerField, computerField, computerPlayer);

		Ship ship = new Ship(3, Directions.EAST, new Coordinate(0, 0, null, null));
		computerField.addShip(ship);

	}

	@Test
	public void testProcessPlayersShot() {
		Coordinate shot = new Coordinate(14, 14, null, null);
		game.processPlayersShot(shot);

		int shotIndex = game.getComputerField().getFreeSea().indexOf(shot);
		Assert.assertTrue("Shot at Coordinate 14, 14. This coordinate should be marked as isHit",
				game.getComputerField().getFreeSea().get(shotIndex).getIsHit());

		int numberOfHits = getNumberOfHits(game.getPlayerField());
		Assert.assertTrue(
				"After player's shot, the computer should shoot, too. Number of hits on player's field should be == 1",
				numberOfHits == 1);

		game.processPlayersShot(shot);
		numberOfHits = getNumberOfHits(game.getPlayerField());
		Assert.assertTrue("Player shot at the same position. Computer should not shoot. "
				+ "Number of hits on player's field should still be == 1", numberOfHits == 1);

		shot = new Coordinate(12, 12, null, null);
		game.processPlayersShot(shot);
		numberOfHits = getNumberOfHits(game.getPlayerField());
		Assert.assertTrue("Made second shot at computer field. Computer should shoot. "
				+ "Number of hits on player's field should be == 2", numberOfHits == 2);

		numberOfHits = getNumberOfHits(game.getComputerField());
		Assert.assertTrue("Made two shots at computer field." + "Number of hits on computers's field should be == 2",
				numberOfHits == 2);

		List<Coordinate> allCoordinates = getAllCoordinatesPlayerField();

		for (Coordinate coordinate : allCoordinates) {
			game.processPlayersShot(coordinate);
		}

		numberOfHits = getNumberOfHits(game.getComputerField());
		Assert.assertTrue("Shot at all positions on computer field. " + "Number of hits on playing field should be 225",
				numberOfHits == 225);
	}

	private List<Coordinate> getAllCoordinatesPlayerField() {
		List<Coordinate> allCoordinates = new ArrayList<Coordinate>();
		for (int i = 0; i <= 14; i++) {
			for (int j = 0; j <= 14; j++) {
				Coordinate coordinate = new Coordinate(i, j, null, null);
				allCoordinates.add(coordinate);
			}
		}
		return allCoordinates;
	}

	private int getNumberOfHits(PlayField playField) {
		int numberOfHits = 0;
		for (Coordinate coordinate : playField.getFreeSea()) {
			if (coordinate.getIsHit()) {
				numberOfHits++;
			}
		}

		for (Coordinate coordinate : playField.getShipsCoordinates()) {
			if (coordinate.getIsHit()) {
				numberOfHits++;
			}
		}
		return numberOfHits;
	}

	@Test
	public void testRegisterDisplay() {
		Ship ship = new Ship(3, Directions.EAST, new Coordinate(0, 0, null, null));
		playerField.addShip(ship);
		RunningGameDisplay gameDisplay = new RunningGameDisplay(game);
		Coordinate shot = new Coordinate(14, 14, null, null);
		int numberOfShapesBeforeShot = gameDisplay.getComputerField().getShapes().size();
		game.processPlayersShot(shot);
		int numberOfShapesAfterShot = gameDisplay.getComputerField().getShapes().size();

		Assert.assertTrue(numberOfShapesBeforeShot == numberOfShapesAfterShot);

		game.registerDisplay(gameDisplay);
		game.processPlayersShot(shot);
		
		numberOfShapesBeforeShot = gameDisplay.getComputerField().getShapes().size();
		shot = new Coordinate(13, 13, null, null);
		game.processPlayersShot(shot);
		numberOfShapesAfterShot = gameDisplay.getComputerField().getShapes().size();
		
		Assert.assertTrue(numberOfShapesBeforeShot + 1 == numberOfShapesAfterShot);

		// gameDisplay.
	}

	@Test
	public void testGetPlayerField() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetComputerField() {
		fail("Not yet implemented");
	}

}
