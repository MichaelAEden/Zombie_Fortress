package animation;

import java.awt.Graphics2D;
import java.util.Random;

import main.GamePanel;

public class Background 
{
	protected Animation bg;
	
	protected double x;
	protected double y;
	
	protected Random rand = new Random();
	
	public Background(String s) 
	{
		bg = new Animation("/Backgrounds/" + s);
	}
	
	protected Background() 
	{
		
	}
	
	public void setPosition(double x, double y)
	{
		this.x = x;
		this.y = y;
		fixPosition();
	}
	
	protected void fixPosition() 
	{
		while(x <= -GamePanel.WIDTH) x += GamePanel.WIDTH;
		while(x >= GamePanel.WIDTH) x -= GamePanel.WIDTH;
		while(y <= -GamePanel.HEIGHT) y += GamePanel.HEIGHT;
		while(y >= GamePanel.HEIGHT) y -= GamePanel.HEIGHT;
	}
	
	public void draw(Graphics2D g)
	{
		g.drawImage(bg.getImage((int)GamePanel.WIDTH, (int)GamePanel.HEIGHT), (int)x, (int)y, null);
		if (x < 0)
		{
			g.drawImage(bg.getImage((int)GamePanel.WIDTH, (int)GamePanel.HEIGHT), (int)x + GamePanel.WIDTH, (int)y, null);
		}
		if (x > 0)
		{
			g.drawImage(bg.getImage((int)GamePanel.WIDTH, (int)GamePanel.HEIGHT), (int)x - GamePanel.WIDTH, (int)y, null);
		}
	}

}
