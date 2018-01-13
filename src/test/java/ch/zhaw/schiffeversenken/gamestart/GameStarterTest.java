package ch.zhaw.schiffeversenken.gamestart;

import java.util.ArrayList;
import java.util.List;
import ch.zhaw.schiffeversenken.data.Game;
import ch.zhaw.schiffeversenken.data.PlayField;
import ch.zhaw.schiffeversenken.data.Ship;
import ch.zhaw.schiffeversenken.guicomponents.RunningGameDisplay;
import ch.zhaw.schiffeversenken.guicomponents.StartScreen01;
import ch.zhaw.schiffeversenken.helpers.ComputerPlayer;
import ch.zhaw.schiffeversenken.helpers.Coordinate;

public class GameStarterTest {
	public static void main(String[] args) {
		// TODO: current design does only work for squares, so we should remove
		// the distinction between number of rows and number of columns
		new StartScreen01();
	}
}
