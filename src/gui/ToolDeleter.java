package gui;

import java.awt.event.MouseEvent;

import tileMap.Tile;
import tileMap.TileMap;
import entities.EntityMap;

public class ToolDeleter extends Tool 
{

	public ToolDeleter()
	{
		super("Deleter", "Tile Cost / 2");
	}
	
	public int getToolCost(TileMap tileMap, int x, int y, MouseEvent mouse)
	{
		if(!canBeUsedAt(tileMap, x, y)) return 0;
		return -(int)(tileMap.getTileAt(x, y).getCost() * (1 - tileMap.getTileDamageAt(x, y)) / 2 + 0.5);
	}

	public void onToolUse(TileMap tileMap, EntityMap entityMap, int x, int y, MouseEvent m)
	{
		if(!canBeUsedAt(tileMap, x, y)) return;
		super.onToolUse(tileMap, entityMap, x, y, m);
		tileMap.destroyTileAt(entityMap, x, y);
	}

	public boolean canBeUsedAt(TileMap tileMap, int x, int y) 
	{
		if(super.canBeUsedAt(tileMap, x, y))
		{
			return tileMap.getTileAt(x, y) != null && tileMap.getTileAt(x, y).ID != Tile.nexus.ID;
		}
		return false;
	}
}
