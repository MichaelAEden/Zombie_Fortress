package tileMap;

import entities.Entity;
import entities.EntityMap;
import entities.living.EntityLiving;
import entities.particles.EntityParticleSmoke;

public class TileFire extends TileAnimated 
{

	public TileFire(String name, int cost, double hardness, double explosiveResistance) 
	{
		super(name, cost, hardness, explosiveResistance, 16, 1, false);
	}
	
	public boolean canBePlacedAt(TileMap tileMap, int x, int y) 
	{
		if (super.canBePlacedAt(tileMap, x, y))
		{
			return (tileMap.getTileIDAt(x, y - 1) != 0 && tileMap.getTileAt(x, y - 1).isFlammable() && tileMap.getTileAt(x, y - 1).isSolid());
		}
		return false;
	}
	
	public void onEntityEntered(TileMap tileMap, Entity entity, int x, int y)
	{
		if(entity instanceof EntityLiving)
		{
			((EntityLiving) entity).burn(1);
		}
		else if(!(entity instanceof EntityParticleSmoke))
		{
			entity.setDead();
		}
	}
	
	public void randomDisplayUpdate(TileMap tileMap, EntityMap entityMap, int x, int y)
	{
		EntityParticleSmoke particle = new EntityParticleSmoke(tileMap, entityMap);
		particle.setLocation(x + rand.nextDouble(), y + rand.nextDouble());
		particle.spawn();
	}
	
	public void neighbourUpdate(TileMap tileMap, EntityMap entityMap, int x, int y)
	{
		if(!canBePlacedAt(tileMap, x, y))
		{
			tileMap.destroyTileAt(entityMap, x, y);
		}
	}
	
	public void onDestruction(TileMap tileMap, EntityMap entityMap, int x, int y)
	{
	}
}
