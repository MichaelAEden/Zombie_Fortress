package gameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.living.EntityPlayer;
import gui.WeaponsShopGUI;

public class GameStateManager 
{
	private GameState currentState;
	
	public static final int MENUSTATE = 0;
	public static final int CONSTRUCTIONSTATE = 1;
	public static final int WEAPONSSHOPSTATE = 2;
	public static final int ATTACKSTATE = 3;
	public static final int GAMEOVERSTATE = 4;
	
	public GameStateManager()
	{
		setState(MENUSTATE);
	}
	
	public void setState(int state)
	{
		if(state == MENUSTATE)
		{
			currentState = new MenuState(this);
		}
		else if(state == CONSTRUCTIONSTATE)
		{
			currentState = new ConstructionState(this);
		}
		else if(state == GAMEOVERSTATE)
		{
			currentState = new GameOverState(this);
		}
	}

	public void setState(int state, EntityPlayer entityPlayer)
	{
		if(state == CONSTRUCTIONSTATE)
		{
			currentState = new ConstructionState(this, entityPlayer);
		}
		else if(state == WEAPONSSHOPSTATE)
		{
			currentState = new WeaponsShopGUI(this, entityPlayer);
		}
		else if(state == ATTACKSTATE)
		{
			currentState = new AttackState(this, entityPlayer);
		}
	}
	
	
	public void update()
	{
		currentState.update();
	}
	
	public void draw(Graphics2D g)
	{
		currentState.draw(g);
	}
	
	public void keyPressed(int key) 
	{
		currentState.keyPressed(key);
	}
	
	public void keyReleased(int key) 
	{
		currentState.keyReleased(key);
	}

	public void mouseClicked(MouseEvent mouse) 
	{
		currentState.mouseClicked(mouse);
	}

	public void mousePressed(MouseEvent mouse) 
	{
		currentState.mousePressed(mouse);
	}

	public void mouseReleased(MouseEvent mouse)
	{
		currentState.mouseReleased(mouse);
	}
	
	public void mouseMoved(MouseEvent mouse) 
	{
		currentState.mouseMoved(mouse);
	}
	
	public void mouseDragged(MouseEvent mouse)
	{
		currentState.mouseDragged(mouse);
	}
}
