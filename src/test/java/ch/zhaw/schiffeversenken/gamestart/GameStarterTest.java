package ch.zhaw.schiffeversenken.gamestart;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.schiffeversenken.data.Directions;
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

		List<Coordinate> coordinates1 = new ArrayList<Coordinate>();
		Coordinate coordinate = new Coordinate(0, 0, false, false);
		coordinates1.add(coordinate);
		coordinate = new Coordinate(0, 1, false, false);
		coordinates1.add(coordinate);

		List<Coordinate> coordinates2 = new ArrayList<Coordinate>();
		coordinate = new Coordinate(1, 0, false, false);
		coordinates2.add(coordinate);
		coordinate = new Coordinate(1, 1, false, false);
		coordinates2.add(coordinate);

		List<Coordinate> coordinates3 = new ArrayList<Coordinate>();
		coordinate = new Coordinate(3, 0, false, false);
		coordinates3.add(coordinate);
		coordinate = new Coordinate(3, 1, false, false);
		coordinates3.add(coordinate);

		computerField.addShip(new Ship(coordinates1));
		playerField.addShip(new Ship(coordinates2));
		computerField.addShip(new Ship(coordinates3));
		computerField.addShip(new Ship(columnCountComputer, rowCountComputer, 2));
		
	    Directions dir = Directions.getRandom();
	    System.out.println(dir);

		Game game = new Game(playerField, computerField, computerPlayer);

		Maingui gui = new Maingui(game);
		game.registerDisplay(gui);
		gui.update();

	}
}
