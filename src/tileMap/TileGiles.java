package tileMap;

import entities.EntityMap;

public class TileGiles extends Tile
{

	public TileGiles(String name, int cost, double hardness, double explosiveResistance) 
	{
		super(name, cost, hardness, explosiveResistance);
	}
	
	public void randomDisplayUpdate(TileMap tileMap, EntityMap entityMap, int x, int y)
	{
		convertTileWithChance(tileMap, entityMap, x + 1, y);
		convertTileWithChance(tileMap, entityMap, x - 1, y);
		convertTileWithChance(tileMap, entityMap, x, y + 1);
		convertTileWithChance(tileMap, entityMap, x, y - 1);
	}
	
	private void convertTileWithChance(TileMap tileMap, EntityMap entityMap, int x, int y)
	{
		if(rand.nextInt(200) == 0 && !tileMap.isAir(x, y)) tileMap.setTileAt(entityMap, x, y, ID);
	}
}
