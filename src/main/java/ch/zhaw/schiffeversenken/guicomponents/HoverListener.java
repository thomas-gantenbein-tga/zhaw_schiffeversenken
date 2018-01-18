package ch.zhaw.schiffeversenken.guicomponents;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;

import ch.zhaw.schiffeversenken.guicomponents.shapes.Shape;
import ch.zhaw.schiffeversenken.guicomponents.shapes.ShapeFactory;
import ch.zhaw.schiffeversenken.helpers.Coordinate;

/**
 * Draws a rectangular at the current position of the mouse over the computer
 * field.
 *
 */
public class HoverListener extends MouseAdapter {
	// previousPosX and previousPosY are needed to draw a new rectangular only
	// when the mouse enteres a new position (in terms of "coordinate") of the
	// PlayingFieldPanel. Set to -1 at initialization so that always a new
	// rectangular is drawn when the event fires for the first time.
	private int previousPosX = -1;
	private int previousPosY = -1;
	// used to draw a new rectangular even if the mouse enters the
	// PlayingFieldPanel at the same position as it exited
	private boolean mouseEntered = false;
	private int columnCount;
	private int rowCount;

	Shape previousHoverShape = null;
	PlayingFieldPanel panel;

	/**
	 * Accepts a PlayingFieldPanel on which the hover shapes are to be drawn on.
	 * 
	 * @param panel
	 *            the Panel on which to draw on
	 * @param columnCount
	 *            the width of the panel
	 * @param rowCount
	 *            the height of the panel
	 */
	public HoverListener(PlayingFieldPanel panel, int columnCount, int rowCount) {
		this.panel = panel;
		this.columnCount = columnCount;
		this.rowCount = rowCount;
	}

	/**
	 * Draws a new hover shape when the mouse enters a new field within the
	 * PlayingFieldPanel.
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		int size = panel.getSquareSize();
		int posX = (int) ((double) e.getX() / size * columnCount);
		int posY = (int) ((double) e.getY() / size * rowCount);

		// checks whether the conditions are met to draw a new hover shape
		if (posX < columnCount && posY < rowCount && (posX != previousPosX || posY != previousPosY || mouseEntered)) {
			previousPosX = posX;
			previousPosY = posY;
			panel.getShapes().remove(previousHoverShape);
			Shape hoverShape = ShapeFactory.createHoverShape(new Coordinate(posX, posY, null, null), rowCount,
					columnCount);
			panel.addShape(hoverShape);
			// Shapes are sorted z-a by their width; makes the hover shape
			// appear behind other shapes
			Collections.sort(panel.getShapes());
			previousHoverShape = hoverShape;
			panel.repaint();
		}
	}

	/**
	 * Removes the hover shape if the mouse exits the PlayingFieldPanel
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		if (previousHoverShape != null) {
			panel.getShapes().remove(previousHoverShape);
			panel.repaint();
		}
	}

	/**
	 * Sets the mouseEntered field to true, which makes mouseMove() draw a new
	 * hover shape even if the mouse enters at the same place as it exited.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		mouseEntered = true;
	}

}