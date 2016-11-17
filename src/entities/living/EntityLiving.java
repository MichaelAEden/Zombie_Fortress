package entities.living;

import java.awt.Graphics2D;

import entities.Entity;
import entities.EntityMap;

import tileMap.TileMap;

public abstract class EntityLiving extends Entity
{	
	protected int walkingDirection = NODIRECTION;
	protected int direction = LEFT;
	
	protected int maxHealth;
	protected double groundAccel;
	protected double maxGroundSpeed;
	protected double airAccel;
	protected double maxAirSpeed;
	protected double jump;
	
	protected int damageTick = 0;
	protected int fireDamageTick = 0;
	
	protected int health;

	public EntityLiving(TileMap tileMap, EntityMap entityMap)
	{
		super(tileMap, entityMap);
	}

	public abstract void draw(Graphics2D g);
	
	public void update()
	{
		super.update();
		if(damageTick > 0)
		{
			damageTick--;
		}
		if(fireDamageTick > 0)
		{
			fireDamageTick--;
		}
	}
	
	protected void updateMotion()
	{
		if(isOnGround())
		{
			if(walkingDirection == NODIRECTION)
			{
				dx *= (getTileBelowEntity().getSlipperiness());
				if(Math.abs(dx) < 0.001) dx = 0;
			}
			else
			{
				if(walkingDirection == RIGHT)
				{
					if(dx < 0)
					{
						dx += (Math.abs(dx) * getTileBelowEntity().getSlipperiness());
					}
					dx = Math.min(dx + groundAccel * (1 - getTileBelowEntity().getSlipperiness()), maxGroundSpeed);           
				}
				if(walkingDirection == LEFT)
				{
					if(dx > 0)
					{
						dx -= (Math.abs(dx) * getTileBelowEntity().getSlipperiness());
					}
					dx = Math.max(dx - groundAccel * (1 - getTileBelowEntity().getSlipperiness()), -maxGroundSpeed);          
				}
				/*boolean movingLeft = isWalkingLeft;
				if(movingLeft) dx *= -1;
				if(dx <= maxGroundSpeed)
				{
					dx = Math.min(dx + groundSpeed * 
							(Math.min((1 - getTileBelowEntity().getSlipperiness()), 1)),
							maxGroundSpeed);
				}
				else
				{
					dx = (dx * getTileBelowEntity().getSlipperiness());
				}
				if(movingLeft) dx *= -1;*/
				/*if(dx < 0)
				{// + groundSpeed * (1 - getTileBelowEntity().getSlipperiness())
					dx *= (getTileBelowEntity().getSlipperiness());
				}
				else if(dx > maxGroundSpeed)
				{
					//dx *= (getTileBelowEntity().getSlipperiness());
				}
				else
				{
									//System.out.println("DX: " + dx);
				//System.out.println(Math.pow(1 - Math.abs(dx), -1) * getTileBelowEntity().getSlipperiness());
				}*/
				/*
				else
				{
					dx = Math.max(dx - groundSpeed * 
							(Math.min((1 - getTileBelowEntity().getSlipperiness()), 1)),
							-maxGroundSpeed);
				}*/
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
	
	protected void checkForMapBounds() 
	{
		if(x < 0)
		{
			x = 0;
			dx = 0;
		}
		if(x + width > tileMap.getWidth())
		{
			x = tileMap.getWidth() - width;
			dx = 0;
		}
		if (y + height < -10)
		{
			setDead();
		}
	}
	
	protected void onCollisionWithTile() 
	{
		if(dy < -0.3)
		{
			damage((int)(-dy * 20));
		}
	}
	
	public int getDirection()
	{
		return direction;
	}
	
	public int getHealth() 
	{
		return health;
	}
	public int getMaxHealth() 
	{
		return maxHealth;
	}
	
	public double getHealthPercentage() 
	{
		return (double)health / maxHealth;
	}
	
	public void jump()
	{
		if (isOnGround())
		{
			dy = jump;
		}
	}
	public void moveLeft()
	{
		direction = LEFT;
		walkingDirection = LEFT;
	}
	public void moveRight()
	{
		direction = RIGHT;
		walkingDirection = RIGHT;
	}
	
	public void stopMovingLeft()
	{
		if (walkingDirection == LEFT) walkingDirection = NODIRECTION;
	}
	public void stopMovingRight()
	{
		if (walkingDirection == RIGHT) walkingDirection = NODIRECTION;
	}
	public void stopMoving()
	{
		walkingDirection = NODIRECTION;
	}
	
	public void burn(int damage)
	{
		if(fireDamageTick == 0)
		{
			fireDamageTick = 6;
			damage(damage);
		}
	}
	
	public void damage(int damage)
	{
		health -= damage;
		damageTick = 20;
		if (health < 0)
		{
			setDead();
		}
	}

	public double getSpeed()
	{
		return maxGroundSpeed;
	}
}
