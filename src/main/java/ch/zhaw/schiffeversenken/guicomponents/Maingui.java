package ch.zhaw.schiffeversenken.guicomponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import ch.zhaw.schiffeversenken.Coordinate;

public class Maingui {
	private PlayingFieldPanel playerField;
	private PlayingFieldPanel computerField;
	private int rowCount;
	private int columnCount;

	public Maingui (int rowCount, int columnCount) {
		JFrame frame = new JFrame("Schiffe versenken");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		
		
		JPanel fieldsPanel = new JPanel();
		JPanel labelsPanel = new JPanel();
		JPanel gamePanel = new JPanel();
		
		Container contentPane = frame.getContentPane();
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem fileMenuOpen = new JMenuItem("Open");
		fileMenu.add(fileMenuOpen);
		menuBar.add(fileMenu);
		
		playerField = new PlayingFieldPanel();
		computerField = new PlayingFieldPanel();
		GridLayout gridLayoutFields = new GridLayout(1,2);
		JLabel labelPlayer = new JLabel("Player");
		JLabel labelComputer = new JLabel("Computer");
		
		gridLayoutFields.setHgap(50);
		fieldsPanel.setLayout(gridLayoutFields);
		gamePanel.setLayout(new BorderLayout());
		GridLayout gridLayoutLabels = new GridLayout();
		gridLayoutLabels.setHgap(50);
		labelsPanel.setLayout(gridLayoutLabels);
		
		fieldsPanel.setBackground(Color.WHITE);
		
		labelsPanel.add(labelPlayer);
		labelsPanel.add(labelComputer);
		gamePanel.add(labelsPanel, BorderLayout.NORTH);
		gamePanel.add(fieldsPanel);
		fieldsPanel.add(playerField);
		fieldsPanel.add(computerField);
		
		contentPane.add(gamePanel, BorderLayout.CENTER);
		contentPane.add(menuBar, BorderLayout.NORTH);
		
		frame.setVisible(true);
		frame.setSize(800, 800);
		
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
		
		playerField.addMouseListener(new ShootListener());
		computerField.addMouseListener(new ShootListener());
		computerField.addMouseMotionListener(new HoverListener(computerField));
		
		
	}

	public PlayingFieldPanel getPlayerField() {
		return playerField;
	}

	public PlayingFieldPanel getComputerField() {
		return computerField;
	}
	
	private class ShootListener extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			int size = playerField.getSquareSize();
			int posX = (int)((double)e.getX()/size * columnCount);
			int posY = (int)((double)e.getY()/size * rowCount);
			System.out.print(posX);
			System.out.println(", " + posY);
		}
	}
	
	private class HoverListener extends MouseAdapter {
		//TODO: comments for this inner class
		int posX = -1;
		int posY = -1;
		Shape previousHoverShape = null;
		PlayingFieldPanel panel;

		public HoverListener(PlayingFieldPanel panel) {
			this.panel = panel;
		}

		public void mouseMoved(MouseEvent e) {
			int size = panel.getSquareSize();
			int posX = (int)((double)e.getX()/size * columnCount);
			int posY = (int)((double)e.getY()/size * rowCount);
			
			if(posX < columnCount && posY < rowCount && (posX != this.posX || posY != this.posY)) {
				this.posX = posX;
				this.posY = posY;
				panel.getShapes().remove(previousHoverShape);
				Shape hoverShape = ShapeFactory.createHoverShape(new Coordinate(posX, posY,null), rowCount, columnCount);
				panel.addShape(hoverShape);
				previousHoverShape = hoverShape;
				panel.repaint();
			}
		}
		
	}
}
