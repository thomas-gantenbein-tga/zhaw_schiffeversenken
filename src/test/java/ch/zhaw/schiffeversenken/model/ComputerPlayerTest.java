package ch.zhaw.schiffeversenken.model;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ch.zhaw.schiffeversenken.data.Coordinate;
import ch.zhaw.schiffeversenken.data.PlayField;
import ch.zhaw.schiffeversenken.model.ComputerPlayer;

public class ComputerPlayerTest {
	private ComputerPlayer computerPlayer;
	private PlayField playerField;

	@Before
	public void setUp() throws Exception {
		playerField = new PlayField(15, 15);
		computerPlayer = new ComputerPlayer(15, 15);
	}

	@Test
	public void testMakeRandomShot() {
		Set<Coordinate> shotSet = new HashSet<Coordinate>();
		for (int i = 0; i < 100000; i++) {
			Coordinate testCoordinate = computerPlayer.makeRandomShot();
			shotSet.add(testCoordinate);
			assertTrue("Coordiante of random shot is not within the Playfield, Coordinate is too big",
					testCoordinate.getxPosition() < playerField.getColumnCount()
							&& testCoordinate.getyPosition() < playerField.getRowCount());
			assertTrue("Coordiante of random shot is not within the Playfield, Coordinate is too small",
					testCoordinate.getxPosition() >= 0 && testCoordinate.getyPosition() >= 0);
		}
		Set<Coordinate> allPossibleShots = computeAllPossibleShots();
		assertTrue("Even after 100000 shots, not all fields of the 15x15 playfield were covered.",
				shotSet.containsAll(allPossibleShots));
	}

	private Set<Coordinate> computeAllPossibleShots() {
		Set<Coordinate> allPossibleShots = new HashSet<Coordinate>();
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				Coordinate coordinate = new Coordinate(i, j, null, null);
				allPossibleShots.add(coordinate);
			}
		}
		return allPossibleShots;
	}

}
