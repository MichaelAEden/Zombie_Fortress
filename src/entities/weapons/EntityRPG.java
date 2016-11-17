package entities.weapons;

import java.awt.Color;
import java.awt.Graphics2D;

import entities.Entity;
import entities.EntityMap;
import entities.living.EntityEnemy;
import entities.misc.EntityExplosion;
import tileMap.TileMap;

public class EntityRPG extends Entity
{
	double accelX = 1.05;
	double accelY = 1.05;

	public EntityRPG(TileMap tileMap, EntityMap entityMap)
	{
		super(tileMap, entityMap);
		
		name = "RPG";
		bounce = 0.3;
		canCollideWithEntities = true;
		width = 0.2;
		height = 0.3;
	}

	public void draw(Graphics2D g)
	{
		g.setColor(new Color(30,150,30));
		g.fillOval(tileMap.getPixelFromTileX(x), 
				(int)(tileMap.getPixelFromTileY(y) - (height - 1) * tileMap.getTileSize()),
				(int)(width * tileMap.getTileSize()),
				(int)(height * tileMap.getTileSize()));
	}
	
	protected void updateMotion()
	{
		dx *= accelX;
		dy *= accelX;
		destX = x + dx;
		destY = y + dy;
	}
	
	protected boolean onCollisionWithEntity(Entity entity) 
	{
		if(!(entity instanceof EntityEnemy)) return false;
		detonate();
		setDead();
		return true;
	}
	
	protected void onCollisionWithTile() 
	{
		detonate();
	}
	
	private void detonate()
	{
		new EntityExplosion(tileMap, entityMap, x, y, 4);
		isDead = true;
	}
}
