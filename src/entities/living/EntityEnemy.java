package entities.living;

import java.awt.Graphics2D;

import entities.EntityMap;
import entities.misc.EntityCoin;

import tileMap.TileMap;

public abstract class EntityEnemy extends EntityLiving
{
	protected boolean destroyNexus;
	protected int attackStrength;
	protected double damageStrength;
	protected double climbSpeed;
	
	protected boolean isClimbing;
	protected boolean isDestroying;
	protected int attackTick;
	
	protected int coinPayoff;
	protected int coinValue;
	
	protected int targettedTileX;
	protected int targettedTileY;

	public EntityEnemy(TileMap tileMap, EntityMap entityMap)
	{
		super(tileMap, entityMap);
	}

	public abstract void draw(Graphics2D g);
	
	public abstract void updateAI(EntityPlayer player);
	
	public void setDead()
	{
		super.setDead();
		EntityCoin coin;
		for(int coins = 0; coins < coinPayoff; coins++)
		{
			coin = new EntityCoin(tileMap, entityMap);
			coin.setLocation(x + (width / 2), y + (height / 2));
			coin.setValue(coinValue);
			coin.spawn();
		}
	}

}
