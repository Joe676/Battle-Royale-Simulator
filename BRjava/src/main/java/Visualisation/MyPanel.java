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
	Timer tm = new Timer(25, this);
	
	Vector testPt;
	
	private Simulation sim;
	
	public MyPanel() {
		this.sim = new Simulation(30, 20, 600, 600);
        tm.start();

    	addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
            	//movePt(e.getX(), e.getY());
            	shoot();
            }
        });
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(600,600);
    }
	
	
	public void actionPerformed(ActionEvent e) {
		sim.step();
		repaint();
	}

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        sim.show(g);
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
