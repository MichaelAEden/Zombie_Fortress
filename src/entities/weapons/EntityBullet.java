package entities.weapons;

import java.awt.Color;
import java.awt.Graphics2D;

import entities.Entity;
import entities.EntityMap;
import entities.living.EntityEnemy;
import entities.living.EntityLiving;

import tileMap.TileMap;

public class EntityBullet extends Entity 
{
	
	protected int damage;

	public EntityBullet(TileMap tileMap, EntityMap entityMap, int damage) 
	{
		super(tileMap, entityMap);
		this.damage = damage;
		
		name = "Bullet";
		bounce = 0.2;
		canCollideWithEntities = true;
		width = 0.1;
		height = 0.1;
	}

	@Override
	public void draw(Graphics2D g) 
	{
		g.setColor(Color.darkGray);
		g.fillOval(tileMap.getPixelFromTileX(x), 
				(int)(tileMap.getPixelFromTileY(y) - (height - 1) * tileMap.getTileSize()),
				(int)(width * tileMap.getTileSize()),
				(int)(height * tileMap.getTileSize()));
	}
	
	protected void updateMotion()
	{
		destX = x + dx;
		destY = y + dy;
	}
	
	protected void onCollisionWithTile() 
	{
		setDead();
	}
	
	protected boolean onCollisionWithEntity(Entity entity) 
	{
		if(!(entity instanceof EntityEnemy)) return false;
		entity.setVector(entity.getDx() + dx * 0.05, 0.1);
		((EntityLiving) entity).damage(damage);
		setDead();
		return true;
	}

}
