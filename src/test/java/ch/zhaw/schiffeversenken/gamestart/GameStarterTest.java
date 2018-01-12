package ch.zhaw.schiffeversenken.gamestart;

import java.util.ArrayList;
import java.util.List;
import ch.zhaw.schiffeversenken.data.Game;
import ch.zhaw.schiffeversenken.data.PlayField;
import ch.zhaw.schiffeversenken.data.Ship;
import ch.zhaw.schiffeversenken.guicomponents.Maingui;
import ch.zhaw.schiffeversenken.helpers.ComputerPlayer;
import ch.zhaw.schiffeversenken.helpers.Coordinate;

public class GameStarterTest {
	public static void main(String[] args) {
		// TODO: current design does only work for squares, so we should remove
		// the distinction between number of rows and number of columns
		int columnCountComputer = 10;
		int rowCountComputer = 10;
		int columnCountPlayer = 3;
		int rowCountPlayer = 3;

		PlayField playerField = new PlayField(columnCountPlayer, rowCountPlayer);
		PlayField computerField = new PlayField(columnCountComputer, rowCountComputer);

		ComputerPlayer computerPlayer = new ComputerPlayer(rowCountPlayer, columnCountPlayer);
		
		computerField.addRandomShip(5);
		computerField.addRandomShip(3);
		computerField.addRandomShip(1);
		
		playerField.addRandomShip(2);
		
		Game game = new Game(playerField, computerField, computerPlayer);

		Maingui gui = new Maingui(game);
		game.registerDisplay(gui);
		gui.update();

	}
}
