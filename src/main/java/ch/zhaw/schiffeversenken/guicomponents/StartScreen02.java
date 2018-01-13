package ch.zhaw.schiffeversenken.guicomponents;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import ch.zhaw.schiffeversenken.data.Game;
import ch.zhaw.schiffeversenken.data.PlayField;
import ch.zhaw.schiffeversenken.helpers.ComputerPlayer;

public class StartScreen02 {
	private JFrame frame;
	private JPanel contentPane;
	private JTextField sizeComputerFieldInput;
	private JTextField sizePlayerFieldInput;
	private int sizeComputerField;
	private int sizePlayerField;

	public StartScreen02(int sizeComputerField, int sizePlayerField, JFrame frame) {
		this.sizeComputerField = sizeComputerField;
		this.sizePlayerField = sizePlayerField;
		this.frame = frame;

		contentPane = new JPanel();
		Insets settingsInset = new Insets(2, 2, 2, 2);

		JLabel welcomeTextLabel = new JLabel("Place your ships here");
		Font settingsFont = new Font("Sans Serif", Font.PLAIN, welcomeTextLabel.getFont().getSize());
		welcomeTextLabel.setFont(settingsFont);

		LayoutManager gridBagLayout = new GridBagLayout();
		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.fill = GridBagConstraints.NONE;
		gbConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		gbConstraints.insets = new Insets(4, 4, 4, 4);
		contentPane.setLayout(gridBagLayout);

		JButton startButton = new JButton("Start game");

		gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		gbConstraints.gridwidth = 2;
		gbConstraints.weightx = 0;
		gbConstraints.weighty = 0;

		contentPane.add(welcomeTextLabel, gbConstraints);

		gbConstraints.gridy = 1;
		contentPane.add(startButton, gbConstraints);

		startButton.addActionListener(new StartButtonListener());
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	private class StartButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			PlayField playerField = new PlayField(sizePlayerField, sizePlayerField);
			PlayField computerField = new PlayField(sizeComputerField, sizeComputerField);

			ComputerPlayer computerPlayer = new ComputerPlayer(sizePlayerField, sizePlayerField);

			computerField.addRandomShip(5);
			computerField.addRandomShip(3);
			computerField.addRandomShip(1);

			playerField.addRandomShip(2);

			Game game = new Game(playerField, computerField, computerPlayer);

			RunningGameDisplay runningGameDisplay = new RunningGameDisplay(game);

			game.registerDisplay(runningGameDisplay);
			runningGameDisplay.update();

			frame.setContentPane(runningGameDisplay.getContentPane());
			SwingUtilities.updateComponentTreeUI(frame);
		}
	}
}
