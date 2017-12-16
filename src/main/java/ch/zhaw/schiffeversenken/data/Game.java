package ch.zhaw.schiffeversenken.data;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.schiffeversenken.guicomponents.Display;
import ch.zhaw.schiffeversenken.helpers.Coordinate;

public class Game {
	private PlayField playerField;
	private PlayField computerField;
	private List<Display> displayList;

	public Game(PlayField playerField, PlayField computerField) {
		this.playerField = playerField;
		this.computerField = computerField;
		displayList = new ArrayList<Display>();
	}
	
	public void processShot(PlayField playField, Coordinate shotCoordinate) {
		playField.processShot(shotCoordinate);
		alertDisplays();
	}

	public void registerDisplay(Display display) {
		displayList.add(display);
	}

	private void alertDisplays() {
		for (Display display : displayList) {
			display.update();
		}
	}

	public PlayField getPlayerField() {
		return playerField;
	}

	public PlayField getComputerField() {
		return computerField;
	}

}
