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

import ch.zhaw.schiffeversenken.data.Directions;
import ch.zhaw.schiffeversenken.data.Game;
import ch.zhaw.schiffeversenken.data.PlayField;
import ch.zhaw.schiffeversenken.data.Ship;
import ch.zhaw.schiffeversenken.guicomponents.shapes.Shape;
import ch.zhaw.schiffeversenken.guicomponents.shapes.ShapeFactory;
import ch.zhaw.schiffeversenken.helpers.ComputerPlayer;
import ch.zhaw.schiffeversenken.helpers.Coordinate;

public class StartScreen02 implements Display {
	private JFrame frame;
	private JPanel contentPane;
	private RunningGameDisplay runningGameDisplay;
	private int sizeComputerField;
	private int sizePlayerField;
	private JTextField shipTailPositionInput;
	private PlayingFieldPanel playerPreview;
	private Game game;
	private Coordinate tailPositionNewShip;
	private JComboBox shipOrientationDropDown;
	private JTextField shipSizeInput;

	public StartScreen02(int sizeComputerField, int sizePlayerField, JFrame frame) {
		this.sizeComputerField = sizeComputerField;
		this.sizePlayerField = sizePlayerField;
		this.frame = frame;

		runningGameDisplay = initializeGame();

		contentPane = new JPanel();
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
		shipOrientationDropDown = new JComboBox(new String[] { "North", "East", "South", "West" });

		JButton addShipButton = new JButton("Add ship");
		JButton startButton = new JButton("Start game");

		playerPreview = new PlayingFieldPanel();
		playerPreview.setPreferredSize(new Dimension(300, 300));
		playerPreview.setBackground(Color.WHITE);
		paintPlayerFieldPreview();
		this.update();

		LayoutManager gridBagLayout = new GridBagLayout();
		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.fill = GridBagConstraints.NONE;
		gbConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		gbConstraints.insets = new Insets(4, 4, 4, 4);
		contentPane.setLayout(gridBagLayout);

		gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		gbConstraints.gridwidth = 2;
		gbConstraints.weightx = 0;
		gbConstraints.weighty = 0;
		contentPane.add(welcomeTextLabel, gbConstraints);

		gbConstraints.gridwidth = 1;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
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

		gbConstraints.gridx = 0;
		gbConstraints.gridy = 4;
		contentPane.add(addShipButton, gbConstraints);

		gbConstraints.gridx = 0;
		gbConstraints.gridy = 5;
		contentPane.add(startButton, gbConstraints);

		gbConstraints.gridx = 0;
		gbConstraints.gridwidth = 2;
		gbConstraints.gridy = 6;
		contentPane.add(playerPreview, gbConstraints);
		playerPreview.addMouseMotionListener(new HoverListener(playerPreview, sizePlayerField, sizePlayerField));
		playerPreview.addMouseListener(new ShipPositioningListener());
		addShipButton.addActionListener(new AddShipButtonListener());

		startButton.addActionListener(new StartButtonListener());
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	private RunningGameDisplay initializeGame() {
		PlayField playerField = new PlayField(sizePlayerField, sizePlayerField);
		PlayField computerField = new PlayField(sizeComputerField, sizeComputerField);

		ComputerPlayer computerPlayer = new ComputerPlayer(sizePlayerField, sizePlayerField);

		game = new Game(playerField, computerField, computerPlayer);

		RunningGameDisplay runningGameDisplay = new RunningGameDisplay(game);

		game.registerDisplay(runningGameDisplay);

		return runningGameDisplay;
	}

	private void paintPlayerFieldPreview() {
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

	private class StartButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (game.getPlayerField().getShips().size() > 0) {
				game.getComputerField().addRandomShip(5);
				game.getComputerField().addRandomShip(3);
				game.getComputerField().addRandomShip(1);
				runningGameDisplay.update();
				frame.repaint();
				frame.setContentPane(runningGameDisplay.getContentPane());
				frame.validate();
			} else {
				JOptionPane.showMessageDialog(frame,
						"Funny. How about adding some ships so your enemy has something to shoot at?");
			}
		}
	}

	private class ShipPositioningListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			int size = playerPreview.getSquareSize();
			int posX = (int) ((double) e.getX() / size * sizePlayerField);
			int posY = (int) ((double) e.getY() / size * sizePlayerField);
			if (posX <= sizePlayerField - 1 && posY <= sizePlayerField - 1) {
				shipTailPositionInput.setText("x-Position: " + (posX + 1) + " | y-Position: " + (posY + 1));
				tailPositionNewShip = new Coordinate(posX, posY, false, false);
			}
		}
	}

	private class AddShipButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (isInputValid()) {
				update();
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
			}

			Directions direction = getDirectionFromDropdown();
			Ship ship = new Ship(sizePlayerField, sizePlayerField, shipSize, direction, tailPositionNewShip);
			game.getPlayerField().addShip(ship);
			if (game.getPlayerField().deleteLastAddedShipIfUnviable()) {
				JOptionPane.showMessageDialog(frame,
						"Sorry, your ship does not fit in the playing field or is intersecting with another ship. Try again.");
				return false;
			}
			return true;
		}
	}

	@Override
	public void update() {
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
