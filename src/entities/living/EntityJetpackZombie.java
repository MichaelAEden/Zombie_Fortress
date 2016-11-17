package entities.living;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import tileMap.TileMap;
import animation.Animation;
import entities.CollisionTester;
import entities.EntityMap;

public class EntityJetpackZombie extends EntityEnemy 
{
	protected Animation animation;
	protected double verticalAccel;
	protected double travelLine;


	public EntityJetpackZombie(TileMap tileMap, EntityMap entityMap)
	{
		super(tileMap, entityMap);
		
		name = "Zombie";
		width = 1;
		height = 2;
		
		maxHealth = 20;
		verticalAccel = 0.04;
		airAccel = 0.015;
		maxAirSpeed = 0.065;
		jump = 0;
		weight = 0;
		
		attackStrength = 5;
		damageStrength = 0.15 / 60;
		coinPayoff = 0;
		coinValue = 0;
		
		health = maxHealth;
		if (animation == null)
		{
			animation = new Animation("/Sprites/ZombieJetpack/Zombie", 8, 1);
		}
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
		if(age == 1)
		{
			travelLine = y;
		}
		if(isDestroying)
		{
			if (tileMap.isAir(targettedTileX, targettedTileY))
			{
				if(this.isHittingLeftWall() && direction == LEFT)
				{
					isDestroying = true;
				}
				else if(this.isHittingRightWall() && direction == RIGHT)
				{
					isDestroying = true;
				}
			}
			else
			{
				tileMap.damageTileAt(entityMap, targettedTileX, targettedTileY, damageStrength * (1 - tileMap.getTileAt(targettedTileX, targettedTileY).getHardness()));    
			}
		}
		else
		{
			if(direction == LEFT) dx *= -1;
			if(dx <= maxAirSpeed)
			{
				dx = Math.min(dx + airAccel, maxAirSpeed);
			}
			if(direction == LEFT) dx *= -1;
			dy = 0;
		}
		dy = verticalAccel;
		verticalAccel += (y >= travelLine ? -0.002 : 0.002);
		destX = x + dx;
		destY = y + dy;
	}

	public void draw(Graphics2D g) 
	{
		if(direction == RIGHT)
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
				if(x > tileMap.getWidth() / 2 + 3)
				{
					direction = LEFT;
				}
				else if(x < tileMap.getWidth() / 2 - 3)
				{
					direction = RIGHT;
				}
				else
				{
					setDead();
				}
				if(dx == 0 && ((this.isHittingLeftWall() && direction == LEFT) || (this.isHittingRightWall() && direction == RIGHT)))
				{
					if(!isDestroying)      
					{
						if(direction == LEFT)
						{
							targettedTileX = (int)(x - 0.01);
						}
						else
						{
							targettedTileX = (int)(x + width);
						}
						targettedTileY = (int)(y) + rand.nextInt((int)(height + 0.5));
						isDestroying = true;
					}
				}
				else
				{
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
	
	public void setDead()
	{
		super.setDead();
		EntityZombie zombie = new EntityZombie(tileMap, entityMap);
		zombie.setLocation(x, y);
		entityMap.spawn(zombie);
	}
}
