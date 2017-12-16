package ch.zhaw.schiffeversenken.gamestart;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.schiffeversenken.Coordinate;
import ch.zhaw.schiffeversenken.data.Game;
import ch.zhaw.schiffeversenken.data.PlayField;
import ch.zhaw.schiffeversenken.data.Ship;
import ch.zhaw.schiffeversenken.guicomponents.Maingui;

public class GameStarter {
	public static void main(String[] args) {
		int columnCount = 10;
		int rowCount = 10;
		PlayField playerField = new PlayField(columnCount, rowCount);
		PlayField computerField = new PlayField(columnCount, rowCount);
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		Coordinate coordinate = new Coordinate(0,0,false);
		coordinates.add(coordinate);
		computerField.addShip(new Ship(coordinates));
		Game game = new Game(playerField, computerField);
		
		Maingui gui = new Maingui(rowCount, columnCount, game);
		game.registerDisplay(gui);
		
	}
}
