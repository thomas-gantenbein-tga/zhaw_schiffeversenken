package ch.zhaw.schiffeversenken.guicomponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ch.zhaw.schiffeversenken.data.Game;
import ch.zhaw.schiffeversenken.data.PlayField;
import ch.zhaw.schiffeversenken.data.Ship;
import ch.zhaw.schiffeversenken.guicomponents.shapes.Line;
import ch.zhaw.schiffeversenken.guicomponents.shapes.Shape;
import ch.zhaw.schiffeversenken.guicomponents.shapes.ShapeFactory;
import ch.zhaw.schiffeversenken.helpers.Coordinate;

/**
 * GUI to interact with the Game object. Active when the game is set up and
 * running. Should be registered as an observer of the Game object.
 *
 */
public class RunningGameDisplay implements Display {
	private PlayingFieldPanel playerField;
	private PlayingFieldPanel computerField;
	private JPanel contentPane;
	private Game game;
	private int rowCountComputer;
	private int columnCountComputer;
	private int rowCountPlayer;
	private int columnCountPlayer;
	private JLabel labelShipsPlayer;
	private JLabel labelShipsComputer;
	private MouseListener mouseListener;

	/**
	 * Expects a Game object, since this is the key component of the GUI.
	 * 
	 * @param game
	 *            The game that should be displayed by the GUI.
	 */
	public RunningGameDisplay(Game game) {
		this.game = game;
		initializeMainFields();

		// set up player's field
		playerField.setBackground(Color.WHITE);
		int preferredSize;
		if (rowCountPlayer > rowCountComputer) {
			preferredSize = getPreferredSize(rowCountPlayer);
		} else {
			preferredSize = getPreferredSize(rowCountComputer);
		}
		playerField.setPreferredSize(new Dimension(preferredSize, preferredSize));
		playerField.setMinimumSize(new Dimension(300, 300));

		// set up computer's field
		computerField.setBackground(Color.WHITE);
		computerField.setPreferredSize(new Dimension(preferredSize, preferredSize));
		computerField.setMinimumSize(new Dimension(300, 300));

		// adding components to the content pane
		LayoutManager gridBagLayout = new GridBagLayout();
		contentPane.setLayout(gridBagLayout);

		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.fill = GridBagConstraints.NONE;
		gbConstraints.gridx = 0;
		gbConstraints.weightx = 1;
		gbConstraints.weighty = 0;
		gbConstraints.anchor = GridBagConstraints.FIRST_LINE_START;

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem fileMenuOpen = new JMenuItem("Open");
		fileMenu.add(fileMenuOpen);
		menuBar.add(fileMenu);
		contentPane.add(menuBar, gbConstraints);

		JLabel labelPlayer = new JLabel("Player");
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		gbConstraints.insets = new Insets(10, 10, 0, 10);
		contentPane.add(labelPlayer, gbConstraints);

		JLabel labelComputer = new JLabel("Computer");
		gbConstraints.gridx = 1;
		contentPane.add(labelComputer, gbConstraints);

		gbConstraints.gridx = 0;
		gbConstraints.gridy = 2;
		gbConstraints.weighty = 1;
		gbConstraints.fill = GridBagConstraints.BOTH;
		contentPane.add(playerField, gbConstraints);

		gbConstraints.gridx = 1;
		contentPane.add(computerField, gbConstraints);

		gbConstraints.fill = GridBagConstraints.NONE;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 3;
		contentPane.add(labelShipsPlayer, gbConstraints);

		gbConstraints.gridx = 1;
		contentPane.add(labelShipsComputer, gbConstraints);

		drawPlayingFieldsGrid();

		computerField.addMouseListener(mouseListener);
		HoverListener hoverListener = new HoverListener(computerField, columnCountComputer, rowCountComputer);
		computerField.addMouseMotionListener(hoverListener);
		computerField.addMouseListener(hoverListener);
	}

	private void initializeMainFields() {
		rowCountComputer = game.getComputerField().getRowCount();
		columnCountComputer = game.getComputerField().getColumnCount();
		rowCountPlayer = game.getPlayerField().getRowCount();
		columnCountPlayer = game.getPlayerField().getColumnCount();

		contentPane = new JPanel();

		labelShipsPlayer = new JLabel();
		labelShipsComputer = new JLabel();

		playerField = new PlayingFieldPanel();
		computerField = new PlayingFieldPanel();
		mouseListener = new ShootListener();

	}
	
	private int getPreferredSize(int size) {
		if(size < 15) {
			return 300;
		} else {
			return size * 20;
		}
	}

	private void drawPlayingFieldsGrid() {
		// draw lines for player field
		// first loop: horizontal lines
		for (int i = 0; i <= rowCountPlayer; i++) {
			Shape line = ShapeFactory.createGridLine(i, rowCountPlayer, columnCountPlayer, 100, 0);
			playerField.addShape(line);
		}

		// second loop: vertical lines
		for (int i = 0; i <= columnCountPlayer; i++) {
			Shape line = ShapeFactory.createGridLine(i, rowCountPlayer, columnCountPlayer, 0, 100);
			playerField.addShape(line);
		}

		// draw lines for computer field
		// first loop: horizontal lines
		for (int i = 0; i <= rowCountComputer; i++) {
			Shape line = ShapeFactory.createGridLine(i, rowCountComputer, columnCountComputer, 100, 0);
			computerField.addShape(line);
		}

		// second loop: vertical lines
		for (int i = 0; i <= columnCountComputer; i++) {
			Shape line = ShapeFactory.createGridLine(i, rowCountComputer, columnCountComputer, 0, 100);
			computerField.addShape(line);
		}
	}

	/**
	 * Makes a "shot" at teh computer field when the mouse button is pressed.
	 * Checks whether the press happens in the playing field. Does accept shots
	 * at coordinates that have been shot at before. The Game object is
	 * responsible for dealing with such a double shot.
	 *
	 */
	private class ShootListener extends MouseAdapter {

		public void mousePressed(MouseEvent e) {
			int size = playerField.getSquareSize();
			int posX = (int) ((double) e.getX() / size * columnCountComputer);
			int posY = (int) ((double) e.getY() / size * rowCountComputer);
			if (posX <= columnCountComputer - 1 && posY <= rowCountComputer - 1) {
				Coordinate coordinate = new Coordinate(posX, posY, null, null);
				game.processPlayersShot(coordinate);
			}
		}
	}

	/**
	 * Is called by the Game object after a shot was made and processed by the
	 * Game object. Gets the status of the PlayField of both computer and human
	 * player, re-computes and redraws their graphical representation and any
	 * other objects on the GUI related to their status.
	 */
	public void update() {
		computerField.removeAllShapesButLines();
		playerField.removeAllShapesButLines();
		
		// update computer field
		for (Coordinate coordinate : game.getComputerField().getShipsCoordinates()) {
			if (coordinate.getIsSunk()) {
				Shape sunkShip = ShapeFactory.createShipSunk(coordinate, rowCountComputer, columnCountComputer);
				computerField.addShape(sunkShip);
			} else if (coordinate.getIsHit()) {
				Shape hitShip = ShapeFactory.createShipHit(coordinate, rowCountComputer, columnCountComputer);
				computerField.addShape(hitShip);
			}
			// TODO: just for testing; remove this else-Block to hide computer
			// ships again
			else {
				Shape intactShip = ShapeFactory.createShipIntact(coordinate, rowCountComputer, columnCountComputer);
				computerField.addShape(intactShip);
			}
		}

		for (Coordinate coordinate : game.getComputerField().getFreeSea()) {
			if (coordinate.getIsHit()) {
				Shape hitSea = ShapeFactory.createSeaHit(coordinate, rowCountComputer, columnCountComputer);
				computerField.addShape(hitSea);
			}
		}
		computerField.repaint();

		// update player field
		for (Coordinate coordinate : game.getPlayerField().getShipsCoordinates()) {
			if (coordinate.getIsSunk()) {
				Shape sunkShip = ShapeFactory.createShipSunk(coordinate, rowCountPlayer, columnCountPlayer);
				playerField.addShape(sunkShip);
			} else if (coordinate.getIsHit()) {
				Shape hitShip = ShapeFactory.createShipHit(coordinate, rowCountPlayer, columnCountPlayer);
				playerField.addShape(hitShip);
			} else {
				Shape intactShip = ShapeFactory.createShipIntact(coordinate, rowCountPlayer, columnCountPlayer);
				playerField.addShape(intactShip);
			}
		}

		for (Coordinate coordinate : game.getPlayerField().getFreeSea()) {
			if (coordinate.getIsHit()) {
				Shape hitSea = ShapeFactory.createSeaHit(coordinate, rowCountPlayer, columnCountPlayer);
				playerField.addShape(hitSea);
			}
		}
		playerField.repaint();

		// update labels and quit game if either player or computer have no more
		// ships
		int remainingShips = getSwimmingShips(game.getComputerField());
		if (remainingShips == 0) {
			showVictory();
			computerField.removeMouseListener(mouseListener);
		}
		labelShipsComputer.setText("Remaining ships: " + remainingShips);

		remainingShips = getSwimmingShips(game.getPlayerField());
		if (remainingShips == 0) {
			showDefeat();
			computerField.removeMouseListener(mouseListener);
		}
		labelShipsPlayer.setText("Remaining ships: " + remainingShips);
	}

	private int getSwimmingShips(PlayField playField) {
		int remainingShips = 0;
		List<Ship> ships = playField.getShips();
		for (Ship ship : ships) {
			if (!ship.isSunk()) {
				remainingShips++;
			}
		}
		return remainingShips;
	}

	private void showVictory() {
		JOptionPane.showMessageDialog(this.contentPane, "You won!", "Victory", JOptionPane.INFORMATION_MESSAGE);
	}

	private void showDefeat() {
		JOptionPane.showMessageDialog(this.contentPane, "You lost!", "Defeat", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Gets the game object of this object. Used by one of the StartingScreens
	 * to switch the contentPane of the frame.
	 * 
	 * @return the Game object of this RunningGameDisplay
	 */
	public JPanel getContentPane() {
		return contentPane;
	}

	/**
	 * Used for JUnit test, to test the "update" method.
	 * @return
	 */
	public PlayingFieldPanel getPlayerField() {
		return playerField;
	}
	
	/**
	 * Used for JUnit test, to test the "update" method.
	 * @return
	 */
	public PlayingFieldPanel getComputerField() {
		return computerField;
	}

}
