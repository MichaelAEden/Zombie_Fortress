package tileMap;

import entities.EntityMap;

public class TileNexus extends Tile 
{
	private static boolean isDestroyed;

	public TileNexus(String name, int cost, double hardness, double explosiveResistance) 
	{
		super(name, cost, hardness, explosiveResistance);
	}
	
	/*public void onEntityCollided(TileMap tileMap, Entity entity, int x, int y)
	{
		if(!(entity instanceof EntityEnemy)) return;
		new Explosion(tileMap, entity.getEntityMap(), x, y, 10);
		isDestroyed = true;
	}*/
	
	public void onDestruction(TileMap tileMap, EntityMap entityMap, int x, int y)
	{
		isDestroyed = true;
	}
	
	public static boolean isDestroyed()
	{
		if(isDestroyed)
		{
			isDestroyed = false;
			return true;
		}
		return false;
	}

}
