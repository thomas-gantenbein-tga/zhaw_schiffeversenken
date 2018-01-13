package ch.zhaw.schiffeversenken.guicomponents;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;

import ch.zhaw.schiffeversenken.guicomponents.shapes.Shape;
import ch.zhaw.schiffeversenken.guicomponents.shapes.ShapeFactory;
import ch.zhaw.schiffeversenken.helpers.Coordinate;

/**
 * Draws a rectangular at the current position of the mouse over the
 * computer field.
 *
 */
public class HoverListener extends MouseAdapter {
	// TODO: comments for class
	private int posX = -1;
	private int posY = -1;
	private int columnCount;
	private int rowCount;
	
	Shape previousHoverShape = null;
	PlayingFieldPanel panel;

	public HoverListener(PlayingFieldPanel panel, int columnCount, int rowCount) {
		this.panel = panel;
		this.columnCount = columnCount;
		this.rowCount = rowCount;
	}

	public void mouseMoved(MouseEvent e) {
		int size = panel.getSquareSize();
		int posX = (int) ((double) e.getX() / size * columnCount);
		int posY = (int) ((double) e.getY() / size * rowCount);

		if (posX < columnCount && posY < rowCount && (posX != this.posX || posY != this.posY)) {
			this.posX = posX;
			this.posY = posY;
			panel.getShapes().remove(previousHoverShape);
			Shape hoverShape = ShapeFactory.createHoverShape(new Coordinate(posX, posY, null, null), rowCount,
					columnCount);
			panel.addShape(hoverShape);
			//Shapes are sorted z-a by their width; makes the hover shape appear behind other shapes
			Collections.sort(panel.getShapes());
			previousHoverShape = hoverShape;
			panel.repaint();
		}
	}

}