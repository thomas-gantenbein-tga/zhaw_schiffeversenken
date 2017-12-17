package ch.zhaw.schiffeversenken.guicomponents;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class TestingLayoutManagers {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container mainContainer = frame.getContentPane();
		
		
		PlayingFieldPanel playerField = new PlayingFieldPanel();
		playerField.setBackground(Color.RED);
		playerField.setPreferredSize(new Dimension(300, 300));
		//playerField.setMinimumSize(new Dimension(50,50));  

		
		PlayingFieldPanel computerField = new PlayingFieldPanel();
		computerField.setBackground(Color.BLACK);
		computerField.setPreferredSize(new Dimension(300, 300));
		//computerField.setMinimumSize(new Dimension(50,50));

		
		LayoutManager gridBagLayout = new GridBagLayout();
		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.fill = GridBagConstraints.BOTH;
		gbConstraints.gridx = 0;
		gbConstraints.insets = new Insets(10,10,10,10);
		gbConstraints.weightx = 1;
		gbConstraints.weighty = 1;
		
		
		mainContainer.setLayout(gridBagLayout);
		mainContainer.setBackground(Color.GRAY);
		mainContainer.add(playerField, gbConstraints);
		gbConstraints.gridx=1;
		mainContainer.add(computerField, gbConstraints);
		gbConstraints.gridy = 1;
		gbConstraints.gridx = 0;
		gbConstraints.weightx = 0;
		gbConstraints.weighty = 0;
		gbConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		gbConstraints.fill = GridBagConstraints.NONE;
		JLabel label1 = new JLabel("TestTest1");
		mainContainer.add(label1, gbConstraints);
		JLabel label2 = new JLabel("TestTest2");
		gbConstraints.gridy = 1;
		gbConstraints.gridx = 1;
		gbConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		mainContainer.add(label2, gbConstraints);
		frame.setSize(1000, 500);
		frame.setVisible(true);
	}
}
