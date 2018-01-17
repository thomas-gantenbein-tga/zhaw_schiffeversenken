package ch.zhaw.schiffeversenken.guicomponents;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import ch.zhaw.schiffeversenken.guicomponents.shapes.Shape;

/**
 * Graphical representation of a PlayField object. Has a given size and holds a
 * list of Shapes, which can represent intact parts of a ship, hit parts of a
 * ship, hit sea, "intact" sea, sunk ship.
 *
 */
public class PlayingFieldPanel extends JPanel {

	private List<Shape> shapes = new ArrayList<Shape>();
	private int playingFieldSize;

	/**
	 * All shapes held by this object are drawn when the window is resized or
	 * the repaint method is called. To adjust the size of shapes to changing
	 * window sizes, the width or the height (whatever is smaller) is given to
	 * the Shape objects together with the Graphics object. The Shapes, then,
	 * "draw" themselves by calling their "draw" method.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));

		if (this.getWidth() > this.getHeight()) {
			playingFieldSize = this.getHeight();
		} else {
			playingFieldSize = this.getWidth();
		}

		for (Shape shape : shapes) {
			shape.draw(playingFieldSize, g2);
		}
	}

	/**
	 * Adds a Shape object to the list of shapes to be drawn on this panel.
	 * 
	 * @param shape
	 *            The Shape object to be added to the list of to-be-drawn
	 *            shapes.
	 */
	protected void addShape(Shape shape) {
		shapes.add(shape);
	}
	
	protected void removeShape(Shape shape) {
		shapes.remove(shape);
	}

	protected int getSquareSize() {
		return playingFieldSize;
	}

	protected List<Shape> getShapes() {
		return shapes;
	}

}
