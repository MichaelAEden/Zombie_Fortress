package entities.living;

import java.awt.Graphics2D;
import animation.Animation;

import entities.CollisionTester;
import entities.EntityMap;
import entities.misc.EntityExplosion;

import tileMap.TileMap;

public class EntityKamikaze extends EntityEnemy 
{
	
	protected static Animation animation;
	
	public EntityKamikaze(TileMap tileMap, EntityMap entityMap)
	{
		super(tileMap, entityMap);
		
		name = "Kamikaze Zombie";
		width = 1;
		height = 2;
		
		maxHealth = 20;
		groundAccel = 0.001;
		maxGroundSpeed = 0.04;
		airAccel = 0.001;
		maxAirSpeed = 0.03;
		jump = 0.2;
		weight = 0.01;
		
		destroyNexus = rand.nextInt(1) > 0;
		attackStrength = 5;
		climbSpeed = 0.015;
		coinPayoff = rand.nextInt(3) + 2;
		coinValue = 3;
		
		health = maxHealth;
		
		if (animation == null)
		{
			animation = new Animation("/Sprites/Kamikaze/Zombie", 4, 1);
		}	
	}

	public void draw(Graphics2D g) 
	{
		g.drawImage(animation.getFrame(age / 8, 1, (int)(width * tileMap.getTileSize()), (int)(height * tileMap.getTileSize())), tileMap.getPixelFromTileX(x), (int)(tileMap.getPixelFromTileY(y) - (height - 1) * tileMap.getTileSize()), null);
	}

	public void updateAI(EntityPlayer player) 
	{
		//if(destroyNexus)
		if(true)
		{
			if(CollisionTester.entityCollidesWithEntity(this, player) && attackTick == 0)
			{
				stopMoving();
				detonate();
			}
			else
			{
				if(x + width < player.getX())
				{
					moveRight();
				}
				else if(x > player.getX() + player.getWidth())
				{
					moveLeft();
				}
				
				if(dx == 0 && (this.isHittingLeftWall() && walkingDirection == LEFT) || (this.isHittingRightWall() && walkingDirection == RIGHT))
				{
					detonate();
				}
			}
		}
	}
	
	private void detonate()
	{
		new EntityExplosion(tileMap, entityMap, x, y, 4);
		isDead = true;
	}

}
