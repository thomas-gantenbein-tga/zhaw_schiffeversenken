package ch.zhaw.schiffeversenken.gamestart;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.schiffeversenken.data.Game;
import ch.zhaw.schiffeversenken.data.PlayField;
import ch.zhaw.schiffeversenken.data.Ship;
import ch.zhaw.schiffeversenken.guicomponents.Maingui;
import ch.zhaw.schiffeversenken.helpers.Coordinate;

public class GameStarterTest {
	public static void main(String[] args) {
		int columnCount = 20;
		int rowCount = 20;
		PlayField playerField = new PlayField(columnCount, rowCount);
		PlayField computerField = new PlayField(columnCount, rowCount);
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		Coordinate coordinate = new Coordinate(0,0,false);
		coordinates.add(coordinate);
		coordinate = new Coordinate(5,6,false);
		coordinates.add(coordinate);

		computerField.addShip(new Ship(coordinates));
		Game game = new Game(playerField, computerField);
		
		Maingui gui = new Maingui(rowCount, columnCount, game);
		game.registerDisplay(gui);
		
	}
}
