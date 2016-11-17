package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import animation.BackgroundSky;
import main.GamePanel;
import tileMap.TileMap;
import tileMap.TileNexus;
import entities.EntityMap;
import entities.living.EntityGiant;
import entities.living.EntityJetpackZombie;
import entities.living.EntityKamikaze;
import entities.living.EntityPlayer;
import entities.living.EntityZombie;
import entities.misc.EntityTile;
import gui.ControlsGui;
import gui.PlayerHUD;

public class AttackState extends GameState 
{	
	private Random rand = new Random();
	
	private boolean spawnMobs = true;
	
	private int time;
	
	private static int wave;
	
	private TileMap tileMap;
	private EntityPlayer player;
	private EntityMap entityMap;
	
	private BackgroundSky bg;
	
	private boolean showDebug;
	
	private PlayerHUD hud;
	
	//private AudioPlayer bgMusic;
	
	private int textTransparency = 1;
	private int textTransparencyMax = 350;
	private int textTransparencyIncrement = 2;

	public AttackState(GameStateManager gsm, EntityPlayer player)
	{
		super(gsm);
		this.player = player;
		
		bg = new BackgroundSky(); // "/Backgrounds/MountainBG.png"
		bg.setTime(BackgroundSky.DUSK);
		hud = new PlayerHUD(player);
		
		tileMap = player.getTileMap();
		tileMap.setConstructionState(false);
		entityMap = player.getEntityMap();
		entityMap.clear();
		player.setLocation(tileMap.getWidth() / 2, 10);
		player.spawn();
		
		time = bg.getTime();
		wave++;
		
		//bgMusic = new AudioPlayer("/Music/level1-1.mp3");
		//bgMusic.play();
	}

	public void update() 
	{
		time += 1;
		bg.setTime(time);
		bg.update();
		if(bg.getTime() > BackgroundSky.DAY && bg.getTime() < BackgroundSky.DUSK)
		{
			gsm.setState(GameStateManager.CONSTRUCTIONSTATE, player);
		}
		
		if(spawnMobs) spawnEnimiesWithChance();
		tileMap.update(entityMap);
		entityMap.update();
		boolean[][] unsupportedStructures = tileMap.checkStructure();
		for(int x = 0; x < tileMap.getWidth(); x++)
		{
			for(int y = 0; y < tileMap.getHeight(); y++)
			{
				if(!unsupportedStructures[x][y])
				{
					if(tileMap.isAir(x, y))
					{
						continue;
					}
					EntityTile entityTile = new EntityTile(tileMap, entityMap, tileMap.getTileAt(x, y));
					entityTile.setLocation(x, y);
					tileMap.setTileAt(entityMap, x, y, 0);
					entityMap.spawn(entityTile);
				}
			}
		}
		
		if(player.isDead() || TileNexus.isDestroyed())
		{
			gsm.setState(GameStateManager.GAMEOVERSTATE);
			wave = 0;
			return;
		}
	}

	public void draw(Graphics2D g) 
	{
		double scrollOffsetX = player.getX() - tileMap.getVisibleAreaWidth() / 2;
		double scrollOffsetY = player.getY() - tileMap.getVisibleAreaHeight() / 3;
		bg.setPosition(scrollOffsetX, scrollOffsetY);
		tileMap.setPosition(scrollOffsetX, scrollOffsetY);
		bg.draw(g);
		tileMap.draw(g);
		entityMap.draw(g);
		hud.draw(g);
		
		drawHud(g);
		if(showDebug)
		{
			hud.drawDebugScreen(g);
		}
	}

	public void keyPressed(int k) 
	{
		if (k == ControlsGui.JUMP)
		{
			player.jump();
		}
		else if(k == ControlsGui.WALKLEFT)
		{
			player.moveLeft();
		}
		else if(k == ControlsGui.WALKRIGHT)
		{
			player.moveRight();
		}
		else if(k == ControlsGui.CLIMB)
		{
			player.setClimbing(true);
		}
		else if(k == ControlsGui.CHANGEWEAPONLEFT)
		{
			player.changeWeapon(-1);
		}
		else if(k == ControlsGui.CHANGEWEAPONRIGHT)
		{
			player.changeWeapon(1);
		}
		else if(k == ControlsGui.OPENDEBUG)
		{
			showDebug = !showDebug;
		}
		else if(k == KeyEvent.VK_Z)
		{
			gsm.setState(GameStateManager.CONSTRUCTIONSTATE, player);
		}
	}

	public void keyReleased(int k) 
	{
		if(k == ControlsGui.WALKLEFT)
		{
			player.stopMovingLeft();
		}
		if(k == ControlsGui.WALKRIGHT)
		{
			player.stopMovingRight();
		}
		if(k == ControlsGui.CLIMB)
		{
			player.setClimbing(false);
		}
	}

	public void mousePressed(MouseEvent m) 
	{
		player.setWeaponInUse(m);
	}

	public void mouseReleased(MouseEvent m) 
	{
		player.setWeaponNotInUse();
	}

	public void mouseMoved(MouseEvent m) {}

	public void mouseDragged(MouseEvent m) 
	{
		player.setWeaponInUse(m);
	}
	
	private void spawnEnimiesWithChance()
	{
		try
		{
			if(rand.nextInt(300 - wave * 10) == 0)
			{
				EntityZombie zombie = new EntityZombie(tileMap, entityMap);
				zombie.setLocation(tileMap.getWidth() / 2 + ((rand.nextInt(tileMap.getWidth() / 2) + 10) * (rand.nextBoolean() ? 1 : -1)), 10);
				zombie.spawn();
			}
			if(rand.nextInt(800 - wave * 50) == 0)
			{
				EntityKamikaze zombie = new EntityKamikaze(tileMap, entityMap);
				zombie.setLocation(tileMap.getWidth() / 2 + ((rand.nextInt(tileMap.getWidth() / 2) + 10) * (rand.nextBoolean() ? 1 : -1)), 10);
				zombie.spawn();
			}
			if(rand.nextInt(1500 - wave * 50) == 0)
			{
				EntityGiant zombie = new EntityGiant(tileMap, entityMap);
				zombie.setLocation(tileMap.getWidth() / 2 + ((rand.nextInt(tileMap.getWidth() / 2) + 10) * (rand.nextBoolean() ? 1 : -1)), 10);
				zombie.spawn();
			}
			if(rand.nextInt(300 - wave * 50) == 0)
			{
				EntityJetpackZombie zombie = new EntityJetpackZombie(tileMap, entityMap);
				zombie.setLocation(tileMap.getWidth() / 2 + ((rand.nextInt(tileMap.getWidth() / 2) + 10) * (rand.nextBoolean() ? 1 : -1)), rand.nextInt(7) + 13);
				zombie.spawn();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void drawHud(Graphics2D g) 
	{
		if(textTransparency != 0)
		{
			textTransparency += textTransparencyIncrement;
			g.setFont(new Font("TimesNewRoman", Font.BOLD, 80 / GamePanel.SCALE));
			g.setColor(new Color(0, 0, 0, Math.min(textTransparency, 255)));
			g.drawString("Wave " + wave, 
					GamePanel.WIDTH / 2 - (int)(g.getFontMetrics().getStringBounds("Wave " + wave, g).getWidth()) / 2,
					GamePanel.HEIGHT / 2 - (int)(g.getFontMetrics().getStringBounds("Wave " + wave, g).getHeight()) / 2);
			if(textTransparency > textTransparencyMax) textTransparencyIncrement = -3;
		}
	}
	

	
	public void mouseClicked(MouseEvent m) {}

}
