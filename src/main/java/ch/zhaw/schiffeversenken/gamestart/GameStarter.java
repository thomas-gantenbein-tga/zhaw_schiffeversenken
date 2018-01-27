package ch.zhaw.schiffeversenken.gamestart;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ch.zhaw.schiffeversenken.view.StartScreen01;

/**
 * Starts the battleship game.
 * 
 *
 */
public class GameStarter {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new StartScreen01();
	}

}
