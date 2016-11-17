package gameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class GameState 
{
	protected GameStateManager gsm;
	
	public GameState(GameStateManager gsm)
	{
		this.gsm = gsm;
	}
	
	public abstract void update();
	public abstract void draw(Graphics2D g);
	
	public void keyPressed(int key) 
	{
	}
	
	public void keyReleased(int key) 
	{
	}

	public void mouseClicked(MouseEvent mouse) 
	{
	}

	public void mousePressed(MouseEvent mouse) 
	{
	}

	public void mouseReleased(MouseEvent mouse)
	{
	}
	
	public void mouseMoved(MouseEvent mouse) 
	{
	}
	
	public void mouseDragged(MouseEvent mouse)
	{
	}
}
