package gameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import main.GamePanel;

import animation.BackgroundSky;
import tileMap.TileMap;
import entities.EntityMap;
import entities.living.EntityPlayer;
import gui.ControlsGui;
import gui.InventoryGUI;
import gui.PlayerHUD;
import gui.Tool;
import gui.ToolDeleter;

public class ConstructionState extends GameState
{	
	private TileMap tileMap;
	private EntityMap entityMap;
	private EntityPlayer player;
	
	private BackgroundSky bg;
	
	private PlayerHUD hud;
	
	private boolean showDebug;
	
	private InventoryGUI inventory;
	private Tool currentTool = new ToolDeleter();
	private int selectedTileX = 0;
	private int selectedTileY = 0;
	
	//scrolling
	private double scrollOffsetX = -3; //Measured in tiles, not pixels
	private double scrollOffsetY = -3; //Measured in tiles, not pixels
	private double scrollSpeedX = 0;
	private double scrollSpeedY = 0;
	private double scrollAcceleration = 0.35;
	private int scrollLimitX;
	private int scrollLimitY;
	
	//tile selection
	private int tileSelectedTransparency = 50;
	private int transparencyIncrement = 4;
	private int transparencyMax = 200;
	private int transparencyMin = 50;
	private MouseEvent lastMouseMove;
	
	private boolean isInventoryOpen = false;

	public ConstructionState(GameStateManager gsm) 
	{
		super(gsm);
		entityMap = new EntityMap();
		tileMap = new TileMap(65, 30);
		tileMap.generateTerrain(entityMap);
		player = new EntityPlayer(tileMap, entityMap);
		
		init();
	}
	
	public ConstructionState(GameStateManager gsm, EntityPlayer player) 
	{
		super(gsm);
		this.player = player;
		tileMap = player.getTileMap();
		entityMap = player.getEntityMap();
		entityMap.clear();
		
		init();
	}
	
	private void init()
	{	
		bg = new BackgroundSky();
		bg.setTime(BackgroundSky.DAY);
		inventory = new InventoryGUI(gsm);
		hud = new PlayerHUD(player);
		
		tileMap.setConstructionState(true);
	}

	public void update() 
	{
		if (isInventoryOpen)
		{
			inventory.update();
		}
		
		updateScrolling();
		tileMap.update(entityMap);
		entityMap.update();
		
		tileSelectedTransparency += transparencyIncrement;
		if (tileSelectedTransparency >= transparencyMax || tileSelectedTransparency <= transparencyMin) transparencyIncrement *= -1;
		
		bg.update();
	}

	public void draw(Graphics2D g) 
	{	
		bg.draw(g);
		
		tileMap.draw(g);
		entityMap.draw(g);
		drawSelectedBlock(g);
		
		if (isInventoryOpen)
		{
			inventory.draw(g);
		}
		
		hud.drawMoney(g);
		
		if(showDebug)
		{
			hud.drawDebugScreen(g);
		}
	}

	private void drawSelectedBlock(Graphics2D g) 
	{ 
		g.setColor(tileMap.checkForValidLocation(selectedTileX, selectedTileY, currentTool) ? 
				new Color(250, 250, 250, tileSelectedTransparency) : 
				new Color(250, 30, 30, tileSelectedTransparency));     
		g.fillRect(tileMap.getPixelFromTileX(selectedTileX), tileMap.getPixelFromTileY(selectedTileY), tileMap.getTileSize(), tileMap.getTileSize());
	}

	public void keyPressed(int k) 
	{
		if (k == ControlsGui.OPENINVENTORY)
		{
			isInventoryOpen = !isInventoryOpen;
			if(!isInventoryOpen) currentTool = (inventory.getSelectedTool() == null ? new ToolDeleter() : inventory.getSelectedTool());
		}
		else if (k == KeyEvent.VK_Z)
		{
			gsm.setState(GameStateManager.WEAPONSSHOPSTATE, player);
		}
		else if (k == ControlsGui.OPENDEBUG)
		{
			showDebug = !showDebug;
		}
		
		if (isInventoryOpen)
		{
			inventory.keyPressed(k);
		}

	}

	public void mouseClicked(MouseEvent m)
	{
		if (isInventoryOpen)
		{
			inventory.mouseClicked(m);
		}
		else if(m.getButton() == MouseEvent.BUTTON1)
		{
			placeBlock(m);
			System.out.println("Clicked1");
		}
		else
		{
			System.out.println("Clicked2");
			tileMap.getTileAt(selectedTileX, selectedTileY).onMouseClick(tileMap, entityMap, selectedTileX, selectedTileY);
		}
	}
	
	public void mouseMoved(MouseEvent m) 
	{
		if (isInventoryOpen)
		{
			inventory.mouseMoved(m);
		}
		else
		{
			lastMouseMove = m;
			if(m.getX() < GamePanel.WIDTH / 10)
			{
				scrollSpeedX = scrollAcceleration;
			}
			else if(m.getX() > GamePanel.WIDTH - GamePanel.WIDTH / 10)
			{
				scrollSpeedX = -scrollAcceleration;
			}
			else
			{
				scrollSpeedX = 0;
			}
			if(m.getY() < GamePanel.HEIGHT / 10)
			{
				scrollSpeedY = -scrollAcceleration;
			}
			else if(m.getY() > GamePanel.HEIGHT - GamePanel.HEIGHT / 10)
			{
				scrollSpeedY = scrollAcceleration;
			}
			else
			{
				scrollSpeedY = 0;
			}
		}
	}

	public void mouseDragged(MouseEvent m) 
	{
		mouseMoved(m);
		placeBlock(m);
	}
	
	public void placeBlock(MouseEvent mouse)
	{
		int cost = tileMap.getCostToPlace(currentTool, selectedTileX, selectedTileY, mouse);
		if(player.getMoney() - cost > 0)
		{
			player.changeMoney(-cost);
			tileMap.tryToPlace(currentTool, entityMap, selectedTileX, selectedTileY, mouse);
		}
	}
	
	private void updateScrolling()
	{	
		scrollLimitX = tileMap.getWidth() - (int)tileMap.getVisibleAreaWidth();
		scrollLimitY = tileMap.getHeight();
		if (scrollOffsetX < 0 || scrollOffsetX > scrollLimitX)
		{	
			scrollSpeedX = 0;
			if (scrollOffsetX < 0)
			{
				scrollOffsetX = 0;
			}
			else
			{
				scrollOffsetX = scrollLimitX;
			}
		}
		if (scrollOffsetY < 0 || scrollOffsetY > scrollLimitY)
		{
			scrollSpeedY = 0;
			if (scrollOffsetY < 0)
			{
				scrollOffsetY = 0;
			}
			else
			{
				scrollOffsetY = scrollLimitY;
			}
		}
		
		scrollOffsetX -= scrollSpeedX;
		scrollOffsetY -= scrollSpeedY;
		
		if(lastMouseMove != null)
		{
			selectedTileX = (int)tileMap.getTileXFromMouse(lastMouseMove);
			selectedTileY = (int)tileMap.getTileYFromMouse(lastMouseMove);
		}
	
		tileMap.setPosition(scrollOffsetX, scrollOffsetY);
		
		bg.setPosition(tileMap.getXOffset(), tileMap.getYOffset());
	}
	
	public void keyReleased(int k){}
	public void mousePressed(MouseEvent m) {}
	public void mouseReleased(MouseEvent m) {}
}
