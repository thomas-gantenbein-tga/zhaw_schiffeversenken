package ch.zhaw.schiffeversenken.guicomponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
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
import ch.zhaw.schiffeversenken.data.Game;
import ch.zhaw.schiffeversenken.data.PlayField;

public class Maingui implements Display {
	private PlayingFieldPanel playerField;
	private PlayingFieldPanel computerField;
	private Game game;
	private int rowCount;
	private int columnCount;

	public Maingui (int rowCount, int columnCount) {
		//TODO: comments for this class
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		
		JFrame frame = new JFrame("Schiffe versenken");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentPane = frame.getContentPane();

		JLabel labelPlayer = new JLabel("Player");
		JLabel labelComputer = new JLabel("Computer");
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem fileMenuOpen = new JMenuItem("Open");
		fileMenu.add(fileMenuOpen);
		menuBar.add(fileMenu);
		
		playerField = new PlayingFieldPanel();
		playerField.setBackground(Color.WHITE);
		playerField.setPreferredSize(new Dimension(300, 300));

		computerField = new PlayingFieldPanel();
		computerField.setBackground(Color.WHITE);
		computerField.setPreferredSize(new Dimension(300, 300));


		
		//set up layout for computer and player field
		LayoutManager gridBagLayout = new GridBagLayout();
		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.fill = GridBagConstraints.NONE;
		gbConstraints.gridx = 0;
		gbConstraints.weightx = 1;
		gbConstraints.weighty = 0;
		gbConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		contentPane.setLayout(gridBagLayout);
		
		contentPane.add(menuBar, gbConstraints);
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		gbConstraints.insets = new Insets(10,10,0,10);
		contentPane.add(labelPlayer, gbConstraints);
		gbConstraints.gridx = 1;
		contentPane.add(labelComputer, gbConstraints);
		
		
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 2;
		gbConstraints.weighty = 1;
		
		gbConstraints.fill = GridBagConstraints.BOTH;
		contentPane.add(playerField, gbConstraints);
		gbConstraints.gridx = 1;
		contentPane.add(computerField, gbConstraints);
		
		//contentPane contains game panel (computer/player + labels) and menu
		
		frame.setVisible(true);
		frame.setSize(1200, 600);
		
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
			if (posX <= columnCount-1 && posY <= rowCount-1) {	
				System.out.print(posX);
				System.out.println(", " + posY);
			}
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

	public void update() {
		//update computer field
		int rowCountComputer = game.getComputerField().getRowCount();
		int columnCountComputer = game.getComputerField().getColumnCount();
		for(Coordinate coordinate : game.getComputerField().getShipsCoordinates()) {
			if(coordinate.isHit()) {
				Shape hitShip = ShapeFactory.createShipHit(coordinate, rowCountComputer, columnCountComputer);
				computerField.addShape(hitShip);
			}
		}
		
		for(Coordinate coordinate : game.getComputerField().getFreeSea()) {
			if(coordinate.isHit()) {
				Shape hitSea = ShapeFactory.createSeaHit(coordinate, rowCountComputer, columnCountComputer);
				computerField.addShape(hitSea);
			}
		}
		computerField.repaint();
		
		//update player field
		int rowCountPlayer = game.getPlayerField().getRowCount();
		int columnCountPlayer = game.getPlayerField().getColumnCount();
		for(Coordinate coordinate : game.getPlayerField().getShipsCoordinates()) {
			if(coordinate.isHit()) {
				Shape hitShip = ShapeFactory.createShipHit(coordinate, rowCountPlayer, columnCountPlayer);
				playerField.addShape(hitShip);
			} else {
				Shape intactShip = ShapeFactory.createShipIntact(coordinate, rowCountPlayer, columnCountPlayer);
				playerField.addShape(intactShip);
			}
		}
		
		for(Coordinate coordinate : game.getPlayerField().getFreeSea()) {
			if(coordinate.isHit()) {
				Shape hitSea = ShapeFactory.createSeaHit(coordinate, rowCountPlayer, columnCountPlayer);
				playerField.addShape(hitSea);
			}
		}
		playerField.repaint();
	}

	
}
