package gui;

import java.awt.event.MouseEvent;

import tileMap.TileMap;
import entities.EntityMap;

public class ToolRepairer extends Tool 
{

	public ToolRepairer() 
	{
		super("Repairer", "Tile Cost x Amount of Damage");
	}
	
	public int getToolCost(TileMap tileMap, int x, int y, MouseEvent mouse)
	{
		if(!canBeUsedAt(tileMap, x, y)) return 0;
		return (int)(tileMap.getTileAt(x, y).getCost() * tileMap.getTileDamageAt(x, y) + 0.5);
	}

	public void onToolUse(TileMap tileMap, EntityMap entityMap, int x, int y, MouseEvent m)
	{
		if(!canBeUsedAt(tileMap, x, y)) return;
		super.onToolUse(tileMap, entityMap, x, y, m);
		tileMap.damageTileAt(entityMap, x, y, -(tileMap.getTileDamageAt(x, y)));
	}

	public boolean canBeUsedAt(TileMap tileMap, int x, int y) 
	{
		if(super.canBeUsedAt(tileMap, x, y))
		{
			return tileMap.getTileAt(x, y) != null;
		}
		return false;
	}
}
