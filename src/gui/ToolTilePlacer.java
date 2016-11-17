package gui;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import tileMap.Tile;
import tileMap.TileMap;
import entities.EntityMap;

public class ToolTilePlacer extends Tool 
{
	private Tile tile;

	public ToolTilePlacer(Tile tile) 
	{
		super(tile.Name, "$" + tile.getCost());
		this.tile = tile;
	}

	public int getToolCost(TileMap tileMap, int x, int y, MouseEvent mouse)
	{
		if(!canBeUsedAt(tileMap, x, y)) return 0;
		return tile.getCost();
	}

	public void onToolUse(TileMap tileMap, EntityMap entityMap, int x, int y, MouseEvent m)
	{
		if(!canBeUsedAt(tileMap, x, y)) return;
		super.onToolUse(tileMap, entityMap, x, y, m);
		tileMap.setTileAt(entityMap, x, y, tile.ID);
	}

	public boolean canBeUsedAt(TileMap tileMap, int x, int y) 
	{
		if(super.canBeUsedAt(tileMap, x, y))
		{
			return tile.canBePlacedAt(tileMap, x, y);
		}
		return false;
	}
	
	public BufferedImage getIcon(InventoryGUI inventory)
	{
		return tile.getIcon(inventory);
	}

}
