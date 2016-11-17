package tileMap;

import entities.Entity;
import entities.misc.EntityExplosion;

public class TileMine extends Tile
{
	public TileMine(String name, int cost, double hardness, double explosiveResistance) 
	{
		super(name, cost, hardness, explosiveResistance);
	}
	
	public void onEntityCollided(TileMap tileMap, Entity entity, int x, int y)
	{
		new EntityExplosion(tileMap, entity.getEntityMap(), x, y, 6);
	}
}
