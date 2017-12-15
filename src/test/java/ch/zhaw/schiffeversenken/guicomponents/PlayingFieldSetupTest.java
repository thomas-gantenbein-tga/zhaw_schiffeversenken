package ch.zhaw.schiffeversenken.guicomponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ch.zhaw.schiffeversenken.Coordinate;

public class PlayingFieldSetupTest {
	
		public static void main(String[] args) {
		
		int rowCount = 20;
		int columnCount = 20;
			
		Maingui gui = new Maingui(rowCount, columnCount);
		PlayingFieldPanel playerField = gui.getPlayerField();
		PlayingFieldPanel computerField = gui.getComputerField();
		
		//draw ships
		Shape intactShip = ShapeFactory.createShipIntact(new Coordinate(3,3,null), rowCount, columnCount);
		playerField.addShape(intactShip);
		
		Shape hitShip = ShapeFactory.createShipHit(new Coordinate(4,4,null), rowCount, columnCount);
		playerField.addShape(hitShip);
		computerField.addShape(hitShip);
		
		Shape hitSea = ShapeFactory.createSeaHit(new Coordinate(5,5,null), rowCount, columnCount);
		playerField.addShape(hitSea);
		
		
		
	}
}

