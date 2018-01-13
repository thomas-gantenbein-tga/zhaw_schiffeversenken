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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ch.zhaw.schiffeversenken.data.Game;
import ch.zhaw.schiffeversenken.data.PlayField;
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
	private PlayingFieldPanel playerPreview;
	private Game game;

	public StartScreen02(int sizeComputerField, int sizePlayerField, JFrame frame) {
		this.sizeComputerField = sizeComputerField;
		this.sizePlayerField = sizePlayerField;
		this.frame = frame;

		runningGameDisplay = initializeGame();

		contentPane = new JPanel();
		JLabel welcomeTextLabel = new JLabel(
				"Add your ships here. The same number and size of ships will be added to the computer field.");
		
		JLabel shipSizeLabel = new JLabel("Size of ship");
		JTextField shipSizeInput = new JTextField(2);
		
		JLabel shipHeadPositionLabel = new JLabel("Where will the head of you ship be placed?");
		JTextField shipHeadPositionInput = new JTextField(2);

		JLabel shipOrientationLabel = new JLabel("In which direction is your ship heading?");
		JComboBox shipOrientationDropDown = new JComboBox(new String[] {"North", "East", "South", "West"});

		JButton addShipButton = new JButton("Add ship");
		JButton startButton = new JButton("Start game");

		Font settingsFont = new Font("Sans Serif", Font.PLAIN, welcomeTextLabel.getFont().getSize());
		welcomeTextLabel.setFont(settingsFont);

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
		contentPane.add(shipHeadPositionLabel, gbConstraints);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 2;
		contentPane.add(shipHeadPositionInput, gbConstraints);
		
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

		startButton.addActionListener(new StartButtonListener());
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	private RunningGameDisplay initializeGame() {
		PlayField playerField = new PlayField(sizePlayerField, sizePlayerField);
		PlayField computerField = new PlayField(sizeComputerField, sizeComputerField);

		ComputerPlayer computerPlayer = new ComputerPlayer(sizePlayerField, sizePlayerField);

		computerField.addRandomShip(5);
		computerField.addRandomShip(3);
		computerField.addRandomShip(1);

		playerField.addRandomShip(2);

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
			runningGameDisplay.update();
			frame.repaint();
			frame.setContentPane(runningGameDisplay.getContentPane());
			frame.validate();
		}
	}

	@Override
	public void update() {
		for (Coordinate coordinate : game.getPlayerField().getShipsCoordinates()) {
			Shape intactShip = ShapeFactory.createShipIntact(coordinate, sizePlayerField, sizePlayerField);
			playerPreview.addShape(intactShip);
		}
	}

}
