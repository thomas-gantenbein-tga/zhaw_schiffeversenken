package schiffeversenken;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class drawPanel extends JPanel{
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(10, 10, 100, 50);
	}
}
