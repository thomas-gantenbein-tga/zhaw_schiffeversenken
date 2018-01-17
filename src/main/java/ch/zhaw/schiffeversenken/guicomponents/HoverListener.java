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

	private int previousPosX = -1;
	private int previousPosY = -1;
	private boolean mouseEntered = false;
	private int columnCount;
	private int rowCount;

	Shape previousHoverShape = null;
	PlayingFieldPanel panel;

	public HoverListener(PlayingFieldPanel panel, int columnCount, int rowCount) {
		this.panel = panel;
		this.columnCount = columnCount;
		this.rowCount = rowCount;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int size = panel.getSquareSize();
		int posX = (int) ((double) e.getX() / size * columnCount);
		int posY = (int) ((double) e.getY() / size * rowCount);

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
	
	@Override
	public void mouseExited(MouseEvent e) {
		if (previousHoverShape != null) {
			panel.getShapes().remove(previousHoverShape);
			panel.repaint();
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		mouseEntered = true;
	}

}