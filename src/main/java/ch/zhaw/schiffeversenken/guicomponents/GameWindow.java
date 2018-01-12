package ch.zhaw.schiffeversenken.guicomponents;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GameWindow {
	private JFrame frame;
	
	
	public GameWindow(ActiveGameDisplay activeGameDisplay) {
		frame = new JFrame("Schiffe versenken: Start");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1600, 800);
		JButton startButton = new JButton("Start game");
		frame.getContentPane().add(startButton, BorderLayout.SOUTH);
		
		
		startButton.addActionListener(e -> {
			frame.setContentPane(activeGameDisplay.getContentPane());
			SwingUtilities.updateComponentTreeUI(frame);
		});
		
		frame.setVisible(true);

	}
	
	
	
}
