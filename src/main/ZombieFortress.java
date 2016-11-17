package main;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ZombieFortress 
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Zombie Fortress");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(new GamePanel(frame), BorderLayout.CENTER);
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}

