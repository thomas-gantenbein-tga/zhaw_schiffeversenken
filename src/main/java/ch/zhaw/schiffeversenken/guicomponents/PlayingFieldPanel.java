package ch.zhaw.schiffeversenken.guicomponents;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class PlayingFieldPanel extends JPanel {
	
	private List<Shape> shapes = new ArrayList<Shape>();
	private int playingFieldSize;
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
	    g2.setStroke(new BasicStroke(2));
		
		
		if(this.getWidth()>this.getHeight()) {
			playingFieldSize = this.getHeight();
		} else {
			playingFieldSize = this.getWidth();
		}
		
		
		for(Shape shape : shapes) {
			shape.draw(playingFieldSize, g2);
		}
	}
	
	public void addShape(Shape shape) {
		shapes.add(shape);
	}

	public int getSquareSize() {
		return playingFieldSize;
	}

	public List<Shape> getShapes() {
		return shapes;
	}

	
	
	
	
}
