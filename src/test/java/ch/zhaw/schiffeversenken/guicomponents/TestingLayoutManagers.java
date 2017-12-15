package ch.zhaw.schiffeversenken.guicomponents;
<<<<<<< HEAD
=======
>>>>>>> branch 'develop' of https://github.com/thomas-gantenbein-tga/zhaw_schiffeversenken.git
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		GridBagConstraints gconstr = new GridBagConstraints();
		gconstr.fill = GridBagConstraints.BOTH;
		gconstr.gridx = 0;
		gconstr.insets = new Insets(10,10,10,10);
		gconstr.weightx = 1;
		gconstr.weighty = 1;
		
		
		mainContainer.setLayout(gridBagLayout);
		mainContainer.setBackground(Color.GRAY);
		mainContainer.add(playerField, gconstr);
		gconstr.gridx=1;
		mainContainer.add(computerField, gconstr);
		gconstr.gridy = 1;
		gconstr.gridx = 0;
		gconstr.weightx = 0;
		gconstr.weighty = 0;
		gconstr.anchor = GridBagConstraints.FIRST_LINE_START;
		gconstr.fill = GridBagConstraints.NONE;
		JLabel label1 = new JLabel("TestTest1");
		mainContainer.add(label1, gconstr);
		JLabel label2 = new JLabel("TestTest2");
		gconstr.gridy = 1;
		gconstr.gridx = 1;
		gconstr.anchor = GridBagConstraints.FIRST_LINE_START;
		mainContainer.add(label2, gconstr);
		frame.setSize(1000, 500);
		frame.setVisible(true);
	}
}
