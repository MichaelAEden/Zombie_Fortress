package gui;

import gameState.GameStateManager;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

public class ControlsGui extends GUI 
{
	public static int OPENINVENTORY = KeyEvent.VK_E;
	public static int OPENDEBUG = KeyEvent.VK_O;
	public static int PAUSE = KeyEvent.VK_P;

	public static int ZOOMIN = KeyEvent.VK_UP;
	public static int ZOOMOUT = KeyEvent.VK_DOWN;
	
	public static int WALKLEFT = KeyEvent.VK_A;
	public static int WALKRIGHT = KeyEvent.VK_D;
	public static int CLIMB = KeyEvent.VK_W;
	public static int JUMP = KeyEvent.VK_SPACE;
	public static int CROUCH = KeyEvent.VK_S;
	public static int CHANGEWEAPONLEFT = KeyEvent.VK_Q;
	public static int CHANGEWEAPONRIGHT = KeyEvent.VK_E;
	
	public ControlsGui(GameStateManager gsm)
	{
		super(gsm);
	}

	@Override
	public void update() 
	{

	}

	@Override
	public void draw(Graphics2D g) 
	{

	}
}
