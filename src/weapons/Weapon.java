package weapons;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import tileMap.TileMap;
import animation.Animation;
import entities.EntityMap;
import entities.living.EntityPlayer;

public abstract class Weapon
{
	protected String name;
	
	protected int reloadTime;
	protected int reloadTick;
	protected int cost;
	
	protected String textureFile;
	protected Animation playerIcon;
	protected Animation shopIcon;
	protected Animation icon;
	
	protected EntityPlayer player;
	protected EntityMap entityMap;
	protected TileMap tileMap;
	
	protected Random rand = new Random();

	public Weapon(EntityPlayer player, TileMap tileMap, EntityMap entityMap, String name)
	{
		this.player = player;
		this.entityMap = entityMap;
		this.tileMap = tileMap;
		this.name = name;
		
		playerIcon = new Animation("/PlayerWeapons/" + name);
		shopIcon = new Animation("/PlayerWeapons/" + name);
		icon = new Animation("/PlayerWeapons/" + name);
	}
	
	public void update()
	{
		if(reloadTick > 0) reloadTick--;	
	}
	
	public boolean onUse(MouseEvent mouse)
	{
		if(reloadTick == 0)
		{
			reloadTick = reloadTime;
			return true;
		}
		return false;
	}
	
	public String getName()
	{
		return name;
	}
	public int getCost()
	{
		return cost;
	}
	
	public int getStock()
	{
		return -1;
	}
	
	public BufferedImage getPlayerIcon(int size)
	{
		return playerIcon.getImage(size, size);
	}
	public BufferedImage getShopIcon(int size)
	{
		return shopIcon.getImage(size, size);
	}
	public BufferedImage getIcon(int size)
	{
		return icon.getImage(size, size);
	}
}
