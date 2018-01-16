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

	public StartScreen01() {
		frame = new JFrame("Schiffe versenken: Start");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1600, 800);
		Container contentPane = frame.getContentPane();
		Insets settingsInset = new Insets(2, 2, 2, 2);

		JLabel welcomeTextLabel = new JLabel(
				"Enter your preferred size of your playing field and the one of your computer enemy.");
		Font settingsFont = new Font("Sans Serif", Font.PLAIN, welcomeTextLabel.getFont().getSize());
		welcomeTextLabel.setFont(settingsFont);

		LayoutManager gridBagLayout = new GridBagLayout();
		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.fill = GridBagConstraints.NONE;
		gbConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		gbConstraints.insets = new Insets(4, 4, 4, 4);
		contentPane.setLayout(gridBagLayout);

		sizeComputerFieldInput = new JTextField(4);
		sizeComputerFieldInput.setMargin(settingsInset);
		JLabel sizeComputerFieldLabel = new JLabel("Size computer field");
		sizeComputerFieldLabel.setFont(settingsFont);

		sizePlayerFieldInput = new JTextField(4);
		sizePlayerFieldInput.setMargin(settingsInset);

		JLabel sizePlayerFieldLabel = new JLabel("Size player field");
		sizePlayerFieldLabel.setFont(settingsFont);

		JButton nextButton = new JButton("Go place your ships");

		gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		gbConstraints.gridwidth = 2;
		gbConstraints.weightx = 0;
		gbConstraints.weighty = 0;
		contentPane.add(welcomeTextLabel, gbConstraints);

		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		gbConstraints.gridwidth = 1;
		gbConstraints.anchor = GridBagConstraints.WEST;
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
		contentPane.add(nextButton, gbConstraints);

		nextButton.addActionListener(new NextButtonListener());

		frame.setVisible(true);
	}

	private class NextButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (isInputValid()) {
				StartScreen02 nextScreen = new StartScreen02(sizeComputerField, sizePlayerField, frame);

				frame.setContentPane(nextScreen.getContentPane());
				frame.validate();
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

			boolean computerFieldOkay = sizeComputerField >= 6 && sizeComputerField <= 50;
			boolean playerFieldOkay = sizePlayerField >= 6 && sizePlayerField <= 50;

			if (!playerFieldOkay || !computerFieldOkay) {
				JOptionPane.showMessageDialog(frame,
						"Computer and player fields should be between 6x6 and 50x50 squares big.");
				return false;
			}
			return true;

		}

	}

}
