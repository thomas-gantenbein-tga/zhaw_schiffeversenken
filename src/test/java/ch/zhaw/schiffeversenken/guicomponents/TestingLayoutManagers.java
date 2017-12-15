package ch.zhaw.schiffeversenken.guicomponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestingLayoutManagers {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container mainContainer = frame.getContentPane();
		
		
		PlayingFieldPanel playerField = new PlayingFieldPanel();
		playerField.setBackground(Color.RED);
		
		PlayingFieldPanel computerField = new PlayingFieldPanel();
		computerField.setBackground(Color.BLACK);
		computerField.setMinimumSize(new Dimension(200,200));
		
		LayoutManager gridLayout = new GridBagLayout();
		GridBagConstraints gconstr = new GridBagConstraints();
	
		
		mainContainer.setLayout(gridLayout);
		mainContainer.add(playerField);
		mainContainer.add(computerField);
		frame.setSize(400, 400);
		frame.setVisible(true);
	}
}
