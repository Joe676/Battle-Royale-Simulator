package Visualisation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

import Simulation.Simulation;
import Vectors.Vector;

/**
 * Panel of Java Swing, canvas on which the simulation is shown
 * @author Jozef Bossowski
 *
 */
public class MyPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;

	Timer tm = new Timer(5, this);//Timer for invoking steps of the simulation
	
	Vector testPt;//Point for testing movement behaviours
	private int tickNumber = 0;
	
	private Simulation sim;//Main simulation
	
	public MyPanel(int width, int height, int playerCount, int wallCount, int gSpeed, int zSpeed) {
		this.setPreferredSize(new Dimension(width, height));
        this.sim = new Simulation(playerCount, wallCount, width, height, gSpeed, zSpeed);
		tm.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		tickNumber++;
		sim.step(tickNumber);//Simulation step
		repaint();//Refreshing window
	}

	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        sim.show(g);//visualising current simulation state
    	if(testPt!=null) {
    		g.setColor(Color.BLUE);
    		g.fillOval((int)testPt.getX(), (int)testPt.getY(), 2, 2);
    	}
    } 
    
}
