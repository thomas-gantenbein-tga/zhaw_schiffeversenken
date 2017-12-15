package ch.zhaw.schiffeversenken.guicomponents;
//jhkjhkjjjgadfdafddafdfdfadfsdfadfaadfadf
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
		playerField.setPreferredSize(new Dimension(50, 50));
		playerField.setMinimumSize(new Dimension(50,50));  

		
		PlayingFieldPanel computerField = new PlayingFieldPanel();
		computerField.setBackground(Color.BLACK);
		computerField.setPreferredSize(new Dimension(100, 100));
		computerField.setMinimumSize(new Dimension(50,50));

		
		LayoutManager gridBagLayout = new GridBagLayout();
		GridBagConstraints gconstr = new GridBagConstraints();
		gconstr.fill = GridBagConstraints.BOTH;
		gconstr.gridx = 0;
		gconstr.weightx = 0.5;
		gconstr.weighty = 0.5;
		gconstr.anchor = GridBagConstraints.FIRST_LINE_START;
		
		
		mainContainer.setLayout(gridBagLayout);
		mainContainer.setBackground(Color.GRAY);
		mainContainer.add(playerField, gconstr);
		gconstr.gridx=1;
		mainContainer.add(computerField, gconstr);
		frame.setSize(400, 400);
		frame.setVisible(true);
	}
}
