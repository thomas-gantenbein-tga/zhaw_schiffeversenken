package ch.zhaw.schiffeversenken.guicomponents;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class GameWindow {
	private JFrame frame;
	
	
	public GameWindow(ActiveGameDisplay activeGameDisplay) {
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
		
		JTextField sizeComputerField = new JTextField();
		JLabel sizeComputerFieldLabel = new JLabel("Size computer field");
		
		JTextField sizePlayerField = new JTextField();
		JLabel sizePlayerFieldLabel = new JLabel("Size player field");
		
		JButton startButton = new JButton("Start game");
		
		settingsPanel.add(sizeComputerFieldLabel);
		settingsPanel.add(sizeComputerField);
		settingsPanel.add(sizePlayerFieldLabel);
		settingsPanel.add(sizePlayerField);
		settingsPanel.add(startButton);
		
		contentPane.add(settingsPanel);
		
		startButton.addActionListener(e -> {
			frame.setContentPane(activeGameDisplay.getContentPane());
			SwingUtilities.updateComponentTreeUI(frame);
		});
		
		frame.setVisible(true);

	}

}
