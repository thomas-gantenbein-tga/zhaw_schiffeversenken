package ch.zhaw.schiffeversenken.guicomponents;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class StartScreen01 {
	private JFrame frame;
	private JTextField sizeComputerFieldInput;
	private JTextField sizePlayerFieldInput;
	private int sizeComputerField;
	private int sizePlayerField;
	private final int MAX_FIELD_SIZE = 30;
	private final int MIN_FIELD_SIZE = 6;

	public StartScreen01() {
		frame = new JFrame("Battleships: Create your playing fields");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = frame.getContentPane();
		Insets settingsInset = new Insets(2, 2, 2, 2);

		JLabel welcomeTextLabel = new JLabel(
				"How big would you like your playing field? And how big should the field of the computer enemy be?");
		Font settingsFont = new Font("Sans Serif", Font.PLAIN, welcomeTextLabel.getFont().getSize());
		welcomeTextLabel.setFont(settingsFont);

		LayoutManager gridBagLayout = new GridBagLayout();
		contentPane.setLayout(gridBagLayout);

		sizeComputerFieldInput = new JTextField(4);
		sizeComputerFieldInput.setMargin(settingsInset);
		JLabel sizeComputerFieldLabel = new JLabel("Size of enemy field");
		sizeComputerFieldLabel.setFont(settingsFont);

		sizePlayerFieldInput = new JTextField(4);
		sizePlayerFieldInput.setMargin(settingsInset);

		JLabel sizePlayerFieldLabel = new JLabel("Size of your field");
		sizePlayerFieldLabel.setFont(settingsFont);

		JButton nextButton = new JButton("Next: Create and place your ships");

		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		gbConstraints.gridwidth = 2;
		gbConstraints.weightx = 0;
		gbConstraints.weighty = 0;
		gbConstraints.insets = new Insets(15, 15, 5, 15);
		gbConstraints.fill = GridBagConstraints.NONE;
		gbConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		contentPane.add(welcomeTextLabel, gbConstraints);

		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		gbConstraints.gridwidth = 1;
		gbConstraints.anchor = GridBagConstraints.WEST;
		gbConstraints.insets = new Insets(5, 15, 5, 15);

		contentPane.add(sizeComputerFieldLabel, gbConstraints);
		gbConstraints.gridx = 1;
		contentPane.add(sizeComputerFieldInput, gbConstraints);

		gbConstraints.gridx = 0;
		gbConstraints.gridy = 2;
		contentPane.add(sizePlayerFieldLabel, gbConstraints);
		gbConstraints.gridx = 1;
		contentPane.add(sizePlayerFieldInput, gbConstraints);

		gbConstraints.gridx = 0;
		gbConstraints.gridy = 3;
		gbConstraints.insets = new Insets(5, 15, 15, 15);
		contentPane.add(nextButton, gbConstraints);

		nextButton.addActionListener(new NextButtonListener());

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}

	private class NextButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (isInputValid()) {
				StartScreen02 nextScreen = new StartScreen02(sizeComputerField, sizePlayerField, frame);

				frame.setContentPane(nextScreen.getContentPane());
				frame.validate();
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setTitle("Battleships: Ship creation and placement");
			}
		}

		private boolean isInputValid() {

			try {
				sizeComputerField = Integer.parseInt(sizeComputerFieldInput.getText());
				sizePlayerField = Integer.parseInt(sizePlayerFieldInput.getText());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame, "Only numbers (0-9) are allowed in the text fields.");
				return false;
			}

			boolean computerFieldOkay = sizeComputerField >= MIN_FIELD_SIZE && sizeComputerField <= MAX_FIELD_SIZE;
			boolean playerFieldOkay = sizePlayerField >= MIN_FIELD_SIZE && sizePlayerField <= MAX_FIELD_SIZE;

			if (!playerFieldOkay || !computerFieldOkay) {
				JOptionPane.showMessageDialog(frame,
						"Computer and player fields should be between " + MIN_FIELD_SIZE + "x" + MIN_FIELD_SIZE + " and " + MAX_FIELD_SIZE + "x" + MAX_FIELD_SIZE + " squares big.");
				return false;
			}
			return true;

		}

	}

}
