package ch.zhaw.schiffeversenken.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ch.zhaw.schiffeversenken.data.Coordinate;
import ch.zhaw.schiffeversenken.data.PlayField;
import ch.zhaw.schiffeversenken.view.Display;

/**
 * Handles the logic of the game. Also acts as the main interface with the GUI
 * (package guicomponents), and holds references to the data describing the
 * current status of the game (i.e. number/location of ships, hits, sea etc).
 * <p>
 * A Game object can register and alert observers. Observers have to implement
 * the Display interface.
 */
public class Game implements Serializable{
	private PlayField playerField;
	private PlayField computerField;
	private ComputerPlayer computerPlayer;
	private transient List<Display> displayList;

	/**
	 * Constructor expects the main components making up a game: two PlayField
	 * (can differ in their size) and a ComputerPlayer object.
	 * 
	 * @param playerField
	 *            the PlayField of the human player
	 * @param computerField
	 *            the PlayField of the computer player
	 * @param computerPlayer
	 *            an instance of ComputerPlayer
	 */
	public Game(PlayField playerField, PlayField computerField, ComputerPlayer computerPlayer) {
		this.playerField = playerField;
		this.computerField = computerField;
		this.computerPlayer = computerPlayer;
		displayList = new ArrayList<Display>();
	}

	/**
	 * Accepts and handles a "shot", made by the player, i.e. a coordinate
	 * with a given target (parameter playField).
	 * <p>
	 * Delegates the treatment of the shot to the PlayField object. If the
	 * shot was valid (i.e. the player did not already shoot at the same
	 * coordinate), the computer player is asked to make its turn. Finally, the
	 * registered observers are alerted.
	 * <p>
	 * If the human player just won, the computer is not allowed to shoot
	 * anymore.
	 * <p>
	 * The method could be extended with some "extra" behaviour, such as
	 * randomly giving the player a second shot before the computer.
	 * 
	 * @param playField
	 *            The PlayField at which the shot is aimed.
	 * @param shotCoordinate
	 *            The coordinates of the shot. "isHit" and "isSunk" fields of
	 *            coordinate object can be null.
	 */
	public void processPlayersShot(Coordinate shotCoordinate) {
		if (computerField.processShot(shotCoordinate)) {
			if (getSwimmingShips(computerField) != 0) {
				playerField.processShot(computerPlayer.makeLogicShot(playerField));
			}
			alertDisplays();
		}
	}

	/**
	 * Registers any display that should be alerted after a shot has been
	 * processed.
	 * 
	 * @param display
	 *            Any object implementing the Display interface.
	 */
	public void registerDisplay(Display display) {
		// needed in case the game object has been deserialized before
		if (displayList == null) {
			displayList = new ArrayList<Display>();
		}
			displayList.add(display);
	}

	private void alertDisplays() {
		for (Display display : displayList) {
			display.update();
		}
	}

	/**
	 * Returns the human's PlayField in this game (including all ships, hits and
	 * misses).
	 * 
	 * @return the PlayField of the human player.
	 */
	public PlayField getPlayerField() {
		return playerField;
	}

	/**
	 * Returns the computer's PlayField in this game (including all ships, hits
	 * and misses).
	 * 
	 * @return the PlayField of the computer player.
	 */
	public PlayField getComputerField() {
		return computerField;
	}

	private int getSwimmingShips(PlayField playField) {
		return playField.getSwimmingShips();
	}

}
