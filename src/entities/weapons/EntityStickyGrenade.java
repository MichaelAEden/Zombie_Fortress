package entities.weapons;

import entities.Entity;
import entities.EntityMap;
import entities.living.EntityEnemy;
import tileMap.TileMap;

public class EntityStickyGrenade extends EntityGrenade
{
	
	private double stickPointX;
	private double stickPointY;
	private Entity entityStuckTo;

	public EntityStickyGrenade(TileMap tileMap, EntityMap entityMap) 
	{
		super(tileMap, entityMap);
	}
	
	public void update()
	{
		super.update();
		if(entityStuckTo != null && !entityStuckTo.isDead())
		{
			dx = 0;
			dy = 0;
			x = entityStuckTo.getX() + stickPointX;
			y = entityStuckTo.getY() + stickPointY;
		}
	}
	
	protected boolean onCollisionWithEntity(Entity entity) 
	{
		if(!(entity instanceof EntityEnemy) || isOnGround()) return false;
		entity.setVector(dx * 0.2, 0.05);
		entityStuckTo = entity;
		stickPointX = entity.getX() - x;
		stickPointY = entity.getY() - y;
		return true;
	}
	
	protected void onCollisionWithTile() 
	{
		dx = 0;
		dy = 0;
	}

}
