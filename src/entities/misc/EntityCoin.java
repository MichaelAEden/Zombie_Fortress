package entities.misc;

import java.awt.Graphics2D;

import tileMap.TileMap;
import animation.Animation;
import entities.Entity;
import entities.EntityMap;
import entities.living.EntityPlayer;

public class EntityCoin extends Entity
{
	private static Animation animation;
	
	private int value;

	public EntityCoin(TileMap tileMap, EntityMap entityMap)
	{
		super(tileMap, entityMap);
		
		name = "Coin";
		bounce = 0.3;
		canCollideWithEntities = true;
		width = 0.5;
		height = 0.5;
		
		if(animation == null)
		{
			animation = new Animation("/Particles/Spinning Coin", 8, 1);
		}
	}

	public void draw(Graphics2D g)
	{
		g.drawImage(animation.getFrame(age / 2, 1, (int)(tileMap.getTileSize() * width), (int)(tileMap.getTileSize() * height)), tileMap.getPixelFromTileX(x), (tileMap.getPixelFromTileY(y + height - 1)) , null);
	}
	
	protected boolean onCollisionWithEntity(Entity entity) 
	{
		if(!(entity instanceof EntityPlayer)) return false;
		((EntityPlayer) entity).changeMoney(value);
		setDead();
		return true;
	}
	
	public void spawn() 
	{
		super.spawn();
		setVector(rand.nextDouble() * 0.25 - 0.125, rand.nextDouble() * 0.25);
	}
	
	public void setValue(int value) 
	{
		this.value = value;
	}
}
