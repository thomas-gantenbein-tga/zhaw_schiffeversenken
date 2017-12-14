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
	public static void main (String[] args) {
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container contentPane = frame.getContentPane();
		final PlayingFieldPanel playerField = new PlayingFieldPanel();
		final PlayingFieldPanel computerField = new PlayingFieldPanel();
		GridLayout gridLayout = new GridLayout(1,2);
		gridLayout.setHgap(50);
		contentPane.setLayout(gridLayout);
		
		contentPane.setBackground(Color.WHITE);
		
		contentPane.add(playerField);
		contentPane.add(computerField);
		
		frame.setVisible(true);
		frame.setSize(800, 800);
		
		final int rowCount = 10;
		final int columnCount = 10;
		
		
		//draw lines for player field
		//first loop: horizontal lines
		for(int i = 0; i<=rowCount; i++) {
			Shape line = ShapeFactory.createGridLine(i, rowCount, columnCount, 100, 0);
			playerField.addShape(line);
		}
		
		//second loop: vertical lines
		for(int i = 0; i<=columnCount; i++) {
			Shape line = ShapeFactory.createGridLine(i, rowCount, columnCount, 0, 100);
			playerField.addShape(line);
		}
		
		//draw lines for player field
		//first loop: horizontal lines
		for(int i = 0; i<=rowCount; i++) {
			Shape line = ShapeFactory.createGridLine(i, rowCount, columnCount, 100, 0);
			computerField.addShape(line);
		}
		
		//second loop: vertical lines
		for(int i = 0; i<=columnCount; i++) {
			Shape line = ShapeFactory.createGridLine(i, rowCount, columnCount, 0, 100);
			computerField.addShape(line);
		}
		
		//draw ship
		Shape intactShip = ShapeFactory.createShipIntact(new Coordinate(3,3,null), rowCount, columnCount);
		playerField.addShape(intactShip);
		
		playerField.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				int size = playerField.getSquareSize();
				
				
				int posX = (int)((double)e.getX()/size * columnCount);
				int posY = (int)((double)e.getY()/size * rowCount);
				System.out.print(posX);
				System.out.println(", " + posY);
			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
}
