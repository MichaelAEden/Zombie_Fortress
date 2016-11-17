package entities.weapons;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import animation.Animation;

import tileMap.TileMap;
import entities.Entity;
import entities.EntityMap;
import entities.living.EntityEnemy;
import entities.misc.EntityExplosion;

public class EntityGrenade extends Entity
{
	protected int detonationTimer = 150;
	protected double rotation = 0;

	public EntityGrenade(TileMap tileMap, EntityMap entityMap)
	{
		super(tileMap, entityMap);
		
		name = "Grenade";
		bounce = 0.3;
		canCollideWithEntities = true;
		width = 0.4;
		height = 0.4;
		if(animation == null)
		{
			animation = new Animation("/PlayerWeapons/" + name);
		}
	}

	public void draw(Graphics2D g)
	{
		AffineTransform at = new AffineTransform();
		at.setToTranslation(tileMap.getPixelFromTileX(x), tileMap.getPixelFromTileY(y + height - 1));
		at.rotate(rotation);
		g.drawImage(animation.getImage((int)(width * tileMap.getTileSize()), (int)(height * tileMap.getTileSize())), at, null);
	}
	
	public void update()
	{
		super.update();
		detonationTimer--;
		rotation += dx;
		if(detonationTimer < 0) detonate();
	}
	
	protected boolean onCollisionWithEntity(Entity entity) 
	{
		if(!(entity instanceof EntityEnemy) || isOnGround()) return false;
		entity.setVector(dx, 0.1);
		dx *= -bounce;
		dy *= -bounce;
		return true;
	}
	
	protected void detonate()
	{
		new EntityExplosion(tileMap, entityMap, x, y, 5);
		isDead = true;
	}

}
