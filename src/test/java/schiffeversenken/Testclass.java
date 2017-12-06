package schiffeversenken;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Testclass {
	public static void main (String[] args) {
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container contentPane = frame.getContentPane();
		final JPanel drawPanel = new drawPanel();
		drawPanel.setSize(200, 400);
		contentPane.add(drawPanel, BorderLayout.CENTER);
		
		frame.setVisible(true);
		frame.setSize(200, 200);
		
		drawPanel.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				int width = drawPanel.getWidth();
				System.out.println((double)e.getX()/width*100);
				
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
