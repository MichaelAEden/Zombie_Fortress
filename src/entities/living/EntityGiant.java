package entities.living;

import java.awt.Graphics2D;

import animation.Animation;

import tileMap.TileMap;

import entities.EntityMap;

public class EntityGiant extends EntityZombie 
{
	protected static Animation animation;

	public EntityGiant(TileMap tileMap, EntityMap entityMap)
	{
		super(tileMap, entityMap);
		
		name = "Giant";
		width *= 3;
		height *= 3;
		
		maxHealth = 200;
		groundAccel = 0.0008;
		maxGroundSpeed = 0.015;
		airAccel = 0.0001;
		maxAirSpeed = 0.01;
		jump = 0.2;
		weight = 0.1;
		
		destroyNexus = rand.nextInt(1) > 0;
		attackStrength = 10;
		damageStrength = 0.75 / 60;
		climbSpeed = 0.005;
		coinPayoff = rand.nextInt(5) + 10;
		coinValue = 3;
		
		health = maxHealth;
		
		if (animation == null)
		{
			animation = new Animation("/Sprites/Giant/Zombie", 4, 1);
		}	
	}
	
	public void draw(Graphics2D g) 
	{
		g.drawImage(animation.getFrame(age / 8, 1, (int)(width * tileMap.getTileSize()), (int)(height * tileMap.getTileSize())), tileMap.getPixelFromTileX(x), (int)(tileMap.getPixelFromTileY(y) - (height - 1) * tileMap.getTileSize()), null);
	}
}
