package ch.zhaw.schiffeversenken.guicomponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ch.zhaw.schiffeversenken.data.Game;
import ch.zhaw.schiffeversenken.data.PlayField;
import ch.zhaw.schiffeversenken.data.Ship;
import ch.zhaw.schiffeversenken.guicomponents.shapes.Shape;
import ch.zhaw.schiffeversenken.guicomponents.shapes.ShapeFactory;
import ch.zhaw.schiffeversenken.helpers.ComputerPlayer;
import ch.zhaw.schiffeversenken.helpers.Coordinate;
import ch.zhaw.schiffeversenken.helpers.Directions;

/**
 * Second screen shown to users. Let's them position their ships.
 *
 */
public class StartScreen02 implements Display {
	private JFrame frame;
	private JPanel contentPane;
	private RunningGameDisplay runningGameDisplay;
	private int sizeComputerField;
	private int sizePlayerField;
	private JTextField shipTailPositionInput;

	// preview of how the player field will look
	private PlayingFieldPanel playerPreview;
	private Game game;
	private Coordinate tailPositionNewShip;
	private JComboBox<String> shipOrientationDropDown;
	private JTextField shipSizeInput;
	// marks the location where the user wants to place the "tail" of the ship
	private Shape tailMarking;

	public StartScreen02(int sizeComputerField, int sizePlayerField, JFrame frame) {
		this.sizeComputerField = sizeComputerField;
		this.sizePlayerField = sizePlayerField;
		this.frame = frame;

		runningGameDisplay = initializeGame();

		// styling components
		JLabel welcomeTextLabel = new JLabel(
				"Add your ships here. The same number and size of ships will be added to the computer field.");
		Font settingsFontBold = new Font("Sans Serif", Font.BOLD, welcomeTextLabel.getFont().getSize());
		Font settingsFontRegular = new Font("Sans Serif", Font.PLAIN, welcomeTextLabel.getFont().getSize());

		welcomeTextLabel.setFont(settingsFontBold);

		JLabel shipSizeLabel = new JLabel("Size of ship");
		shipSizeLabel.setFont(settingsFontRegular);
		shipSizeInput = new JTextField(2);

		JLabel shipTailPositionLabel = new JLabel("Click on the field below to pinpoint the tail of your ship.");
		shipTailPositionLabel.setFont(settingsFontRegular);
		shipTailPositionInput = new JTextField(20);
		shipTailPositionInput.setEnabled(false);

		JLabel shipOrientationLabel = new JLabel("In which direction is your ship heading?");
		shipOrientationLabel.setFont(settingsFontRegular);
		shipOrientationDropDown = new JComboBox<String>(new String[] { "North", "East", "South", "West" });

		playerPreview = new PlayingFieldPanel();
		int preferredSize = getPreferredSize(); 
		playerPreview.setPreferredSize(new Dimension(preferredSize, preferredSize));
		playerPreview.setBackground(Color.WHITE);
		paintPlayerFieldPreviewGrid();

		// adding components to layout
		LayoutManager gridBagLayout = new GridBagLayout();
		contentPane = new JPanel();
		contentPane.setLayout(gridBagLayout);

		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.fill = GridBagConstraints.NONE;
		gbConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		gbConstraints.insets = new Insets(15, 15, 5, 15);
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		gbConstraints.gridwidth = 2;
		gbConstraints.weightx = 0;
		gbConstraints.weighty = 0;
		contentPane.add(welcomeTextLabel, gbConstraints);

		gbConstraints.gridwidth = 1;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		gbConstraints.insets = new Insets(5, 15, 5, 15);
		contentPane.add(shipSizeLabel, gbConstraints);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 1;
		contentPane.add(shipSizeInput, gbConstraints);

		gbConstraints.gridx = 0;
		gbConstraints.gridy = 2;
		contentPane.add(shipTailPositionLabel, gbConstraints);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 2;
		contentPane.add(shipTailPositionInput, gbConstraints);

		gbConstraints.gridx = 0;
		gbConstraints.gridy = 3;
		contentPane.add(shipOrientationLabel, gbConstraints);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 3;
		contentPane.add(shipOrientationDropDown, gbConstraints);

		JButton addShipButton = new JButton("Add ship");
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 4;
		contentPane.add(addShipButton, gbConstraints);

		JButton startButton = new JButton("Start game");
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 5;
		gbConstraints.insets = new Insets(5, 15, 15, 15);
		contentPane.add(startButton, gbConstraints);

		gbConstraints.gridx = 0;
		gbConstraints.gridwidth = 2;
		gbConstraints.gridy = 6;
		contentPane.add(playerPreview, gbConstraints);

		HoverListener hoverListener = new HoverListener(playerPreview, sizePlayerField, sizePlayerField);
		playerPreview.addMouseMotionListener(hoverListener);
		playerPreview.addMouseListener(hoverListener);
		playerPreview.addMouseListener(new ShipPositioningListener());
		addShipButton.addActionListener(new AddShipButtonListener());
		startButton.addActionListener(new StartButtonListener());
	}

	private int getPreferredSize() {
		if(sizePlayerField < 15) {
			return 300;
		} else {
			return sizePlayerField * 20;
		}
	}

	/**
	 * Gets this screen's content pane. Used by the previous screen to switch
	 * content pane of the frame.
	 * 
	 * @return
	 */
	public JPanel getContentPane() {
		return contentPane;
	}

	private RunningGameDisplay initializeGame() {
		PlayField playerField = new PlayField(sizePlayerField, sizePlayerField);
		PlayField computerField = new PlayField(sizeComputerField, sizeComputerField);

		ComputerPlayer computerPlayer = new ComputerPlayer(sizePlayerField, sizePlayerField);

		// the Game object holds the data
		game = new Game(playerField, computerField, computerPlayer);

		// the display displays the data of the game and is registered as an
		// observer of the game object
		RunningGameDisplay runningGameDisplay = new RunningGameDisplay(game);
		game.registerDisplay(runningGameDisplay);

		return runningGameDisplay;
	}

	private void paintPlayerFieldPreviewGrid() {
		for (int i = 0; i <= sizePlayerField; i++) {
			Shape line = ShapeFactory.createGridLine(i, sizePlayerField, sizePlayerField, 100, 0);
			playerPreview.addShape(line);
		}

		// second loop: vertical lines
		for (int i = 0; i <= sizePlayerField; i++) {
			Shape line = ShapeFactory.createGridLine(i, sizePlayerField, sizePlayerField, 0, 100);
			playerPreview.addShape(line);
		}
	}

	/*
	 * Checks if everything is ready to start the game. If so, it starts the
	 * game by switching the content pane of this frame with the content pane of
	 * the RunningGameDisplay which should be registered now as an observer of a
	 * completely set up Game object.
	 */
	private class StartButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			if (game.getPlayerField().getShips().size() > 0) {
				for (Ship ship : game.getPlayerField().getShips()) {
					// addRandomShip explicitly throws an exception if the ship
					// cannot be place on the computer field, e.g. because it is
					// already full.
					try {
						game.getComputerField().addRandomShip(ship.getShipPositions().size());
					} catch (IllegalStateException ex) {
						game.getComputerField().deleteAllShips();
						game.getPlayerField().deleteAllShips();
						JOptionPane.showMessageDialog(frame,
								"Error: Computer ships could not be placed. You placed too many or too big ships on your playing field."
										+ " Your ships have been deleted. Try again and place fewer or smaller ships.");

						update();
						return;
					}
				}
				runningGameDisplay.update();
				frame.repaint();
				frame.setContentPane(runningGameDisplay.getContentPane());
				frame.pack();
				//if null is given, frame is centered on the screen
				frame.setLocationRelativeTo(null);
				frame.setTitle("Battleships");
				frame.validate();
			} else {
				JOptionPane.showMessageDialog(frame,
						"Funny. How about adding some ships so your enemy has something to shoot at?");
			}
		}
	}

	/*
	 * Adds a shape to the spot where the user seems to want place his/her new ship
	 */
	private class ShipPositioningListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			int size = playerPreview.getSquareSize();
			int posX = (int) ((double) e.getX() / size * sizePlayerField);
			int posY = (int) ((double) e.getY() / size * sizePlayerField);
			if (posX <= sizePlayerField - 1 && posY <= sizePlayerField - 1) {
				shipTailPositionInput.setText("x-Position: " + (posX + 1) + " | y-Position: " + (posY + 1));
				tailPositionNewShip = new Coordinate(posX, posY, false, false);
				if (tailMarking != null) {
					playerPreview.removeShape(tailMarking);
				}
				tailMarking = ShapeFactory.createTailPreviewShape(tailPositionNewShip, sizePlayerField,
						sizePlayerField);
				playerPreview.addShape(tailMarking);
				frame.repaint();
			}
		}
	}

	private class AddShipButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (isInputValid()) {
				update();
				playerPreview.removeShape(tailMarking);
				frame.repaint();
			}
		}

		private boolean isInputValid() {
			int shipSize;
			try {
				shipSize = Integer.parseInt(shipSizeInput.getText());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame,
						"Please enter a number (0-9) in the text field for the ship size.");
				return false;
			}

			if (shipSize > sizePlayerField) {
				JOptionPane.showMessageDialog(frame, "Your ship cannot be bigger than your playing field.");
				return false;
			}

			if (tailPositionNewShip == null) {
				JOptionPane.showMessageDialog(frame,
						"Please click on the field below to pinpoint the tail of your ship.");
				return false;
			}

			Directions direction = getDirectionFromDropdown();
			Ship ship = new Ship(shipSize, direction, tailPositionNewShip);
			game.getPlayerField().addShip(ship);
			if (game.getPlayerField().deleteLastAddedShipIfUnviable()) {
				JOptionPane.showMessageDialog(frame,
						"Sorry, your ship does not fit in the playing field or is colliding with another ship. Try again.");
				return false;
			}
			return true;
		}
	}
	@Override
	public void update() {
		// copy of list to avoid concurrent modification
		playerPreview.removeAllShapesButLines();

		for (Coordinate coordinate : game.getPlayerField().getShipsCoordinates()) {
			Shape intactShip = ShapeFactory.createShipIntact(coordinate, sizePlayerField, sizePlayerField);
			playerPreview.addShape(intactShip);
		}
	}

	private Directions getDirectionFromDropdown() {
		switch (shipOrientationDropDown.getSelectedIndex()) {
		case 0:
			return Directions.NORTH;
		case 1:
			return Directions.EAST;
		case 2:
			return Directions.SOUTH;
		case 3:
			return Directions.WEST;
		}
		return null;
	}

}
