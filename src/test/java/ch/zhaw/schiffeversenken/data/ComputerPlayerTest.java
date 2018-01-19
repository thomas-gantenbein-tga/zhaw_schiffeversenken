package ch.zhaw.schiffeversenken.data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.zhaw.schiffeversenken.helpers.ComputerPlayer;
import ch.zhaw.schiffeversenken.helpers.Coordinate;

public class ComputerPlayerTest {
	private ComputerPlayer computerPlayer;
	private PlayField playerField;
	
	@Before
	public void setUp() throws Exception {
		playerField = new PlayField(15, 15);
		computerPlayer = new ComputerPlayer(15, 15);
	}


	@Test
	public void testmakeRandomShot() {
		Coordinate testCoordinate = computerPlayer.makeRandomShot();
		assertTrue("Random Coordiante is not within the Playfield, Coordinate is too big", testCoordinate.getxPosition() < playerField.getColumnCount() && testCoordinate.getyPosition() < playerField.getRowCount());
		assertTrue("Random Coordiante is not within the Playfield, Coordinate is too small", testCoordinate.getxPosition() > 0 && testCoordinate.getyPosition() > 0);
	}

}
