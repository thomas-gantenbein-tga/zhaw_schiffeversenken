package ch.zhaw.schiffeversenken.guicomponents;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ch.zhaw.schiffeversenken.data.Game;
import ch.zhaw.schiffeversenken.data.PlayField;
import ch.zhaw.schiffeversenken.data.Ship;
import ch.zhaw.schiffeversenken.helpers.Coordinate;

/**
 * GUI to interact with the Game object. Should be registered as an observer of
 * the Game object.
 *
 */
public class ActiveGameDisplay implements Display {
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
	public ActiveGameDisplay(Game game) {
		// TODO: comments for this class and breaking apart a little
		this.game = game;
		rowCountComputer = game.getComputerField().getRowCount();
		columnCountComputer = game.getComputerField().getColumnCount();
		rowCountPlayer = game.getPlayerField().getRowCount();
		columnCountPlayer = game.getPlayerField().getColumnCount();

		contentPane = new JPanel();

		JLabel labelPlayer = new JLabel("Player");
		JLabel labelComputer = new JLabel("Computer");
		labelShipsPlayer = new JLabel("lkj");
		labelShipsComputer = new JLabel("lkjsss");

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem fileMenuOpen = new JMenuItem("Open");
		fileMenu.add(fileMenuOpen);
		menuBar.add(fileMenu);

		playerField = new PlayingFieldPanel();
		playerField.setBackground(Color.WHITE);
		playerField.setPreferredSize(new Dimension(300, 300));

		computerField = new PlayingFieldPanel();
		computerField.setBackground(Color.WHITE);
		computerField.setPreferredSize(new Dimension(300, 300));

		// set up layout for computer and player field
		LayoutManager gridBagLayout = new GridBagLayout();
		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.fill = GridBagConstraints.NONE;
		gbConstraints.gridx = 0;
		gbConstraints.weightx = 1;
		gbConstraints.weighty = 0;
		gbConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		contentPane.setLayout(gridBagLayout);

		contentPane.add(menuBar, gbConstraints);
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		gbConstraints.insets = new Insets(10, 10, 0, 10);
		contentPane.add(labelPlayer, gbConstraints);
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

		mouseListener = new ShootListener();
		computerField.addMouseListener(mouseListener);
		computerField.addMouseMotionListener(new HoverListener(computerField));
	}

	public PlayingFieldPanel getPlayerField() {
		return playerField;
	}

	public PlayingFieldPanel getComputerField() {
		return computerField;
	}

	/**
	 * Does accept shots at coordinates that have been shot at before. The Game
	 * object is responsible for dealing with such a double shot.
	 *
	 */
	private class ShootListener extends MouseAdapter {

		public void mousePressed(MouseEvent e) {
			int size = playerField.getSquareSize();
			int posX = (int) ((double) e.getX() / size * columnCountComputer);
			int posY = (int) ((double) e.getY() / size * rowCountComputer);
			if (posX <= columnCountComputer - 1 && posY <= rowCountComputer - 1) {
				Coordinate coordinate = new Coordinate(posX, posY, null, null);
				game.processShot(game.getComputerField(), coordinate);
			}
		}
	}

	/**
	 * Draws a rectangular at the current position of the mouse over the
	 * computer field.
	 *
	 */
	private class HoverListener extends MouseAdapter {
		// TODO: comments for this inner class
		int posX = -1;
		int posY = -1;
		Shape previousHoverShape = null;
		PlayingFieldPanel panel;

		public HoverListener(PlayingFieldPanel panel) {
			this.panel = panel;
		}

		public void mouseMoved(MouseEvent e) {
			int size = panel.getSquareSize();
			int posX = (int) ((double) e.getX() / size * columnCountComputer);
			int posY = (int) ((double) e.getY() / size * rowCountComputer);

			if (posX < columnCountComputer && posY < rowCountComputer && (posX != this.posX || posY != this.posY)) {
				this.posX = posX;
				this.posY = posY;
				panel.getShapes().remove(previousHoverShape);
				Shape hoverShape = ShapeFactory.createHoverShape(new Coordinate(posX, posY, null, null), rowCountComputer,
						columnCountComputer);
				panel.addShape(hoverShape);
				//Shapes are sorted z-a by their width; makes the hover shape appear behind other shapes
				Collections.sort(panel.getShapes());
				previousHoverShape = hoverShape;
				panel.repaint();
			}
		}

	}

	/**
	 * Is called after a shot was made and processed by the Game object. Gets
	 * the status of the PlayField of both computer and human player,
	 * re-computes and redraws their graphical representation and any other
	 * objects on the GUI related to their status.
	 */
	public void update() {
		// update computer field
		for (Coordinate coordinate : game.getComputerField().getShipsCoordinates()) {
			if (coordinate.getIsSunk()) {
				Shape sunkShip = ShapeFactory.createShipSunk(coordinate, rowCountComputer, columnCountComputer);
				computerField.addShape(sunkShip);
			} else if (coordinate.getIsHit()) {
				Shape hitShip = ShapeFactory.createShipHit(coordinate, rowCountComputer, columnCountComputer);
				computerField.addShape(hitShip);
			} 
			//TODO: just for testing; remove this else-Block to hide computer ships again
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

		// update labels
		int remainingShips = getSwimmingShips(game.getComputerField());
		if(remainingShips == 0) {
			showVictory();
			computerField.removeMouseListener(mouseListener);
		}
		labelShipsComputer.setText("Remaining ships: " + remainingShips);

		remainingShips = getSwimmingShips(game.getPlayerField());
		if(remainingShips == 0) {
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

	public JPanel getContentPane() {
		return contentPane;
	}
}
