package ch.zhaw.schiffeversenken.gamestart;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ch.zhaw.schiffeversenken.guicomponents.StartScreen01;

public class GameStarterTest {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		// TODO: current design does only work for squares, so we should remove
		// the distinction between number of rows and number of columns
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new StartScreen01();
	}
}
