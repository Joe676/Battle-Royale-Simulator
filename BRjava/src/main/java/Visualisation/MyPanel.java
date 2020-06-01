package Visualisation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

import Simulation.Simulation;
import Vectors.Vector;

public class MyPanel extends JPanel implements ActionListener{
	Timer tm = new Timer(5, this);//Timer for invoking steps of the simulation
	
	Vector testPt;//Point for testing movement behaviours
	private int tickNumber = 0;
	
	private Simulation sim;//Main simulation
	
	public MyPanel(int width, int height, int playerCount, int wallCount, int gSpeed, int zSpeed) {
		//this.setBackground(new Color(204, 255, 204));
		//this.sim = new Simulation(30, 40, 600, 600, 9, 1);
		this.setPreferredSize(new Dimension(width, height));
        this.sim = new Simulation(playerCount, wallCount, width, height, gSpeed, zSpeed);
		tm.start();

    	addMouseListener(new MouseAdapter() {//testing movement and shooting
            public void mousePressed(MouseEvent e) {
            	//movePt(e.getX(), e.getY());
            	//shoot();
            }
        });
	}
	
	//public Dimension getPreferredSize() {
      //  return new Dimension(600,600);
    //}
	
	
	public void actionPerformed(ActionEvent e) {
		tickNumber++;
		sim.step(tickNumber);//Simulation step
		repaint();//Refreshing window
	}

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        sim.show(g);//visualising current simulation state
    	if(testPt!=null) {
    		g.setColor(Color.BLUE);
    		g.fillOval((int)testPt.getX(), (int)testPt.getY(), 2, 2);
    	}
    } 
    
    private void movePt(int x, int y) {
    	if(this.testPt == null) {
    		this.testPt = new Vector(x, y);
    	}
    	else {
    		this.testPt.setX(x);
    		this.testPt.setY(y);
    	}
    }
    
    private void shoot() {
    	this.sim.testShoot();
    }
}
