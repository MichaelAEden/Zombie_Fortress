package entities.living;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import tileMap.TileMap;
import animation.Animation;
import entities.CollisionTester;
import entities.EntityMap;

public class EntityZombie extends EntityEnemy 
{
	//protected AnimationEntityLiving animation;
	protected static Animation animation;

	public EntityZombie(TileMap tileMap, EntityMap entityMap)
	{
		super(tileMap, entityMap);
		
		name = "Zombie";
		width = 1.0;
		height = 2.0;
		
		maxHealth = 20;
		groundAccel = 0.002;
		maxGroundSpeed = 0.05;
		airAccel = 0.001;
		maxAirSpeed = 0.03;
		jump = 0.2;
		weight = 0.01;
		
		destroyNexus = rand.nextInt(1) > 0;
		attackStrength = 5;
		damageStrength = 0.15 / 60;
		climbSpeed = 0.015;
		coinPayoff = rand.nextInt(2) + 1;
		coinValue = 3;
		
		health = maxHealth;
		
		if (animation == null)
		{
			animation = new Animation("/Sprites/Zombie/Zombie", 4, 1);
		}	
		//animation.setWalking(true);
	}
	
	public void update()
	{
		super.update();
		if(attackTick > 0)
		{
			attackTick--;
		}
	}
	
	protected void updateMotion()
	{
		if(isDestroying)
		{
			if(tileMap.isAir(targettedTileX, targettedTileY))
			{
				isClimbing = rand.nextBoolean();
				isDestroying = !isClimbing;
			}
			else
			{
				tileMap.damageTileAt(entityMap, targettedTileX, targettedTileY, damageStrength * (1 - tileMap.getTileAt(targettedTileX, targettedTileY).getHardness()));    
			}
		}
		if(isClimbing)
		{
			dy = climbSpeed;
			if ((!isHittingLeftWall() && direction == LEFT) || (!isHittingRightWall() && direction == RIGHT))
			{
				if (direction == LEFT) x -= 0.02;
				if (direction == RIGHT) x += 0.02;
			}
		}
		else if(isOnGround())
		{
			if(walkingDirection == NODIRECTION)
			{
				dx *= (getTileBelowEntity().getSlipperiness());
				if(dx < 0.001) dx = 0;
			}
			else
			{
				if(walkingDirection == LEFT) dx *= -1;
				if(dx <= maxGroundSpeed)
				{
					dx = Math.min(dx + groundAccel * 
							(Math.min((1 - getTileBelowEntity().getSlipperiness()) * 10, 1)),
							maxGroundSpeed);
				}
				if(walkingDirection == LEFT) dx *= -1;
			}
		}
		else
		{
			dy -= gravity * weight;
			if(walkingDirection == LEFT || walkingDirection == RIGHT)
			{
				if(walkingDirection == LEFT) dx *= -1;
				if(dx <= maxAirSpeed)
				{
					dx = Math.min(dx + airAccel, maxAirSpeed);
				}
				if(walkingDirection == LEFT) dx *= -1;
			}
		}
		destX = x + dx;
		destY = y + dy;
	}

	public void draw(Graphics2D g) 
	{
		if(walkingDirection == RIGHT)
		{
			BufferedImage image = animation.getFrame(age / 5, 1, (int)(width * tileMap.getTileSize()), (int)(height * tileMap.getTileSize()));
			AffineTransform affineTransform = AffineTransform.getScaleInstance(-1, 1);
			affineTransform.translate(-image.getWidth(null), 0);
			AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			image = affineTransformOp.filter(image, null);
			g.drawImage(image, tileMap.getPixelFromTileX(x), (int)(tileMap.getPixelFromTileY(y) - (height - 1) * tileMap.getTileSize()), null);
		}
		else
		{
			g.drawImage(animation.getFrame(age / 5, 1, (int)(width * tileMap.getTileSize()), (int)(height * tileMap.getTileSize())), tileMap.getPixelFromTileX(x), (int)(tileMap.getPixelFromTileY(y) - (height - 1) * tileMap.getTileSize()), null);
		}
	}

	public void updateAI(EntityPlayer player) 
	{
		//if(destroyNexus)
		if(true)
		{
			if(CollisionTester.entityCollidesWithEntity(this, player) && attackTick == 0)
			{
				stopMoving();
				attack(player);
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
				
				if(dx == 0 && ((this.isHittingLeftWall() && direction == LEFT) || (this.isHittingRightWall() && direction == RIGHT)))
				{
					if(!(isClimbing || isDestroying))      
					{
						isClimbing = rand.nextBoolean();
						isDestroying = !isClimbing;
						if(isDestroying)
						{
							if(walkingDirection == LEFT)
							{
								targettedTileX = (int)(x - 0.01);
							}
							else
							{
								targettedTileX = (int)(x + width);
							}
							targettedTileY = (int)(y) + rand.nextInt((int)(height + 0.5));
						}
					}
				}
				else
				{
					isClimbing = false;
					isDestroying = false;
				}
			}
		}
	}
	
	private void attack(EntityPlayer player)
	{
		player.damage(attackStrength);
		player.setVector(x < player.getX() ? 0.1 : -0.1, 0.1);
		attackTick = 60;
	}
}
