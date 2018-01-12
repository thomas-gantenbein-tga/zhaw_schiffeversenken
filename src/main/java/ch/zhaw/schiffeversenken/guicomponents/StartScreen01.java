package ch.zhaw.schiffeversenken.guicomponents;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import ch.zhaw.schiffeversenken.data.Game;
import ch.zhaw.schiffeversenken.data.PlayField;
import ch.zhaw.schiffeversenken.helpers.ComputerPlayer;

public class StartScreen01 {
	private JFrame frame;
	private ActiveGameDisplay activeGameDisplay;
	private JTextField sizeComputerFieldInput;
	private JTextField sizePlayerFieldInput;
	private int sizeComputerField;
	private int sizePlayerField;

	public StartScreen01() {
		frame = new JFrame("Schiffe versenken: Start");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1600, 800);
		Container contentPane = frame.getContentPane();
		JPanel settingsPanel = new JPanel();

		GridLayout gridLayout = new GridLayout(3, 2);
		gridLayout.setVgap(5);
		gridLayout.setHgap(5);
		settingsPanel.setLayout(gridLayout);
		contentPane.setLayout(new FlowLayout());

		sizeComputerFieldInput = new JTextField();
		JLabel sizeComputerFieldLabel = new JLabel("Size computer field");

		sizePlayerFieldInput = new JTextField();
		JLabel sizePlayerFieldLabel = new JLabel("Size player field");

		JButton startButton = new JButton("Start game");

		settingsPanel.add(sizeComputerFieldLabel);
		settingsPanel.add(sizeComputerFieldInput);
		settingsPanel.add(sizePlayerFieldLabel);
		settingsPanel.add(sizePlayerFieldInput);
		settingsPanel.add(startButton);

		contentPane.add(settingsPanel);

		startButton.addActionListener(new StartButtonListener());
		
		frame.setVisible(true);
	}
	

	private class StartButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (isInputValid()) {
				PlayField playerField = new PlayField(sizePlayerField, sizePlayerField);
				PlayField computerField = new PlayField(sizeComputerField, sizeComputerField);

				ComputerPlayer computerPlayer = new ComputerPlayer(sizePlayerField, sizePlayerField);
				
				computerField.addRandomShip(5);
				computerField.addRandomShip(3);
				computerField.addRandomShip(1);
				
				playerField.addRandomShip(2);
				
				Game game = new Game(playerField, computerField, computerPlayer);

				ActiveGameDisplay activeGameDisplay = new ActiveGameDisplay(game);
				
				game.registerDisplay(activeGameDisplay);
				activeGameDisplay.update();
				
				frame.setContentPane(activeGameDisplay.getContentPane());
				SwingUtilities.updateComponentTreeUI(frame);
			}
		}

		private boolean isInputValid() {
			
			try {
				sizeComputerField = Integer.parseInt(sizeComputerFieldInput.getText());
				sizePlayerField = Integer.parseInt(sizePlayerFieldInput.getText());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame,
						"Only numbers (0-9) are allowed in the text fields.");
				return false;
			}
			
			boolean computerFieldOkay = sizeComputerField >= 6 && sizeComputerField <= 50;
			boolean playerFieldOkay = sizePlayerField >= 6 && sizePlayerField <= 50;
			
			if(!playerFieldOkay || !computerFieldOkay) {
				JOptionPane.showMessageDialog(frame,
					"Computer and player fields should be between 6x6 and 50x50 squares big.");
				return false;
			}
			return true;

		}

	}

}
