package Visualisation;

import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Visualisation.MyPanel;

public class Demo {
	private static int width, height, playerCount, wallCount, gSpeed, zSpeed;
	public static void main(String[] args) {
		getInput();
		SwingUtilities.invokeLater(new Runnable() {//Running Swing window app
            public void run() {
                createAndShowGUI(width, height, playerCount, wallCount, gSpeed, zSpeed);
            }
        });
		
	}
	
	private static void createAndShowGUI(int width, int height, int playerCount, int wallCount, int gSpeed, int zSpeed) {//Creating window
		JFrame f = new JFrame("Battle Royale Simulator");
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    f.add(new MyPanel(width, height, playerCount, wallCount, gSpeed, zSpeed));//Inserting the canvas
	    f.pack();
	    f.setVisible(true);
	}
	
	private static void getInput() {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Enter Arena width (400 - 1000): ");
		width = scan.nextInt();
		while(width < 400 || width > 1000) {
			System.out.println("Enter a value between 400 and 1000: ");
			width = scan.nextInt();
		}
		
		System.out.println("Enter Arena height (400 - 1000): ");
		height = scan.nextInt();
		while(height < 400 || height > 1000) {
			System.out.println("Enter a value between 400 and 1000: ");
			height = scan.nextInt();
		}
		
		System.out.println("Enter number of players (2 - 100): ");
		playerCount = scan.nextInt();
		while(playerCount < 2 || playerCount > 100) {
			System.out.println("Enter a value between 2 and 100: ");
			playerCount = scan.nextInt();
		}

		System.out.println("Enter number of walls in the arena (2 - 40): ");
		wallCount = scan.nextInt();
		while(wallCount < 2 || wallCount > 40) {
			System.out.println("Enter a value between 2 and 40: ");
			wallCount = scan.nextInt();
		}

		System.out.println("Enter game speed(1 - 10): ");
		gSpeed = scan.nextInt();
		while(gSpeed < 1 || gSpeed > 10) {
			System.out.println("Enter a value between 1 and 10: ");
			gSpeed = scan.nextInt();
		}

		System.out.println("Enter zone speed(1 - 10): ");
		zSpeed = scan.nextInt();
		while(zSpeed < 1 || zSpeed > 10) {
			System.out.println("Enter a value between 1 and 10: ");
			zSpeed = scan.nextInt();
		}
	}
}
