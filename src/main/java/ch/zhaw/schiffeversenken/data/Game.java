package ch.zhaw.schiffeversenken.data;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.schiffeversenken.guicomponents.Display;
import ch.zhaw.schiffeversenken.helpers.ComputerPlayer;
import ch.zhaw.schiffeversenken.helpers.Coordinate;

/**
 * Handles the logic of the game. Also acts as the main interface with the GUI, 
 * and holds references to the data describing the current status of the game
 * (i.e. number/location of ships, hits, sea etc).
 * <p>
 * A Game object can register and alert observers. Observers have to implement the Display interface.
 */
public class Game {
	private PlayField playerField;
	private PlayField computerField;
	private ComputerPlayer computerPlayer;
	private List<Display> displayList;

	/**
	 * Expects the main components making up a game: two PlayField (can differ in their size)
	 * and a ComputerPlayer object. 
	 * 
	 * @param playerField		the PlayField of the human player
	 * @param computerField		the PlayField of the computer player
	 * @param computerPlayer	an instance of ComputerPlayer 
	 */
	public Game(PlayField playerField, PlayField computerField, ComputerPlayer computerPlayer) {
		this.playerField = playerField;
		this.computerField = computerField;
		this.computerPlayer = computerPlayer;
		displayList = new ArrayList<Display>();
	}

	/**
	 * Accepts and handles a "shot" (parameter coordinate), i.e. a coordinate with a given target (parameter playField).
	 * <p>
	 * Delegates the treatment of the shot to the PlayField concerned. If the shot was valid (i.e.
	 * the player did not already shoot at the same coordinate), the computer player
	 * is asked to make its turn.  Finally, the registered observers are alerted.
	 * <p>
	 * If the human player just won, the computer is not allowed to shoot anymore.
	 * <p>
	 * The method could be extended with some "extra" behaviour, such as randomly giving the player a second
	 * shot before the computer.
	 * 
	 * @param playField			The PlayField at which the shot is aimed.
	 * @param shotCoordinate	The coordinates of the shot. "isHit" and "isSunk" fields of coordinate object can be null.
	 */
	public void processShot(PlayField playField, Coordinate shotCoordinate) {
		if (playField.processShot(shotCoordinate)) {
			if(getSwimmingShips(computerField) != 0) {
				playerField.processShot(computerPlayer.makeRandomShot());
			}
			alertDisplays();
		}
	}
	
	/**
	 * Registers any display that should be alerted after a shot has been processed.
	 * @param display	Any object implementing the Display interface.
	 */
	public void registerDisplay(Display display) {
		displayList.add(display);
	}

	private void alertDisplays() {
		for (Display display : displayList) {
			display.update();
		}
	}

	/**
	 * Returns the human's PlayField in this game (including all ships, hits and misses).
	 * @return	the PlayField of the human player.
	 */
	public PlayField getPlayerField() {
		return playerField;
	}

	/**
	 * Returns the computer's PlayField in this game (including all ships, hits and misses).
	 * @return	the PlayField of the computer player.
	 */
	public PlayField getComputerField() {
		return computerField;
	}
	
	private int getSwimmingShips(PlayField playField) {
		int remainingShips = 0;
		List<Ship> ships = playField.getShips();
		for (Ship ship : ships) {
			if (!ship.isSunk()) {
				remainingShips++;
			}
		}
		return remainingShips;
	}

}
