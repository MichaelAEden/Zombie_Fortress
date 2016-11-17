package tileMap;

import entities.EntityMap;

public class TileDirt extends Tile
{

	public TileDirt(String name, int cost, double hardness, double explosiveResistance) 
	{
		super(name, cost, hardness, explosiveResistance);
	}
	
	public void randomDisplayUpdate(TileMap tileMap, EntityMap entityMap, int x, int y)
	{
		if(tileMap.getTileAt(x, y + 1) == null || !tileMap.getTileAt(x, y + 1).isSolid) tileMap.setTileAt(entityMap, x, y, Tile.grass.ID);	
	}
}
