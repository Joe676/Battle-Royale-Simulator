package Visualisation;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Visualisation.MyPanel;

public class Demo {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
		
	}
	
	 private static void createAndShowGUI() {
	        JFrame f = new JFrame("Battle Royale Simulator");
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	        f.add(new MyPanel());
	        f.pack();
	        f.setVisible(true);
	    }
}
