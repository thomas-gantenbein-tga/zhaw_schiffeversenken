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
		int columnCount = 10;
		int rowCount = 10;
		
		PlayField playerField = new PlayField(columnCount, rowCount);
		PlayField computerField = new PlayField(columnCount, rowCount);
		
		ComputerPlayer computerPlayer = new ComputerPlayer(columnCount, rowCount);
		
		List<Coordinate> coordinates1 = new ArrayList<Coordinate>();
		Coordinate coordinate = new Coordinate(0,0,false);
		coordinates1.add(coordinate);
		coordinate = new Coordinate(0,1,false);
		coordinates1.add(coordinate);
		
		List<Coordinate> coordinates2 = new ArrayList<Coordinate>();
		coordinate = new Coordinate(1,0,false);
		coordinates2.add(coordinate);
		coordinate = new Coordinate(1,1,false);
		coordinates2.add(coordinate);
		
		
		computerField.addShip(new Ship(coordinates1));
		playerField.addShip(new Ship(coordinates2));
		
		Game game = new Game(playerField, computerField, computerPlayer);
		
		Maingui gui = new Maingui(game);
		game.registerDisplay(gui);
		gui.update();
		
	}
}
