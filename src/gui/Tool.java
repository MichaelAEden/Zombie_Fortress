package gui;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import tileMap.TileMap;
import animation.Animation;
import entities.Entity;
import entities.EntityMap;
import entities.misc.EntityFloatingText;

public abstract class Tool 
{	
	protected BufferedImage defaultImage;
	protected Animation icon;
	
	protected String textureFile;
	protected String name;
	protected String cost;

	public Tool(String name, String cost) 
	{
		this.name = name;
		this.cost = cost;
		
		if(!(this instanceof ToolTilePlacer))
		{
			icon = new Animation("/Tools/" + name);
		}
	}
	
	public abstract int getToolCost(TileMap tileMap, int x, int y, MouseEvent mouse);
	public void onToolUse(TileMap tileMap, EntityMap entityMap, int x, int y, MouseEvent m)
	{
		Entity entity = new EntityFloatingText(tileMap, entityMap, "$" + getToolCost(tileMap, x, y, m));
		entity.setLocation(x, y);
		entity.spawn();
	}
	
	public boolean canBeUsedAt(TileMap tileMap, int x, int y)
	{
		return(tileMap.isInBounds(x, y));
	}
	
	public BufferedImage getIcon(InventoryGUI inventory)
	{
		return icon.getImage(inventory.getIconSize(), inventory.getIconSize());
	}
	
	public String getName()
	{
		return name;
	}
	public String getCost()
	{
		return cost;
	}
}
