package weapons;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import tileMap.TileMap;
import entities.Entity;
import entities.EntityMap;
import entities.living.EntityEnemy;
import entities.living.EntityPlayer;

public abstract class WeaponMelee extends Weapon 
{
	protected double range;
	protected double knockback;
	protected int damage;

	public WeaponMelee(EntityPlayer player, TileMap tileMap, EntityMap entityMap, String name) 
	{
		super(player, tileMap, entityMap, name);
	}
	
	public void update()
	{
		if(reloadTick > 0) reloadTick--;	
	}
	
	public boolean onUse(MouseEvent mouse)
	{
		if(reloadTick == 0)
		{
			reloadTick = reloadTime;
		}
		else
		{
			return false;
		}
		
		ArrayList<Entity> entitiesToHit;
		Entity entityToHit;
		
		if(player.getDirection() == Entity.LEFT)
		{
			entitiesToHit = entityMap.getEntitiesWithin(new Rectangle2D.Double(player.getX() - range, player.getY(), player.getX(), player.getY() + player.getHeight()));
		}
		else
		{ 
   			entitiesToHit = entityMap.getEntitiesWithin(new Rectangle2D.Double(player.getX() + player.getWidth(), player.getY(), player.getX()  + player.getWidth() + range, player.getY() + player.getHeight()));       
		}
        
		for(int entity = 0; entity < entitiesToHit.size(); entity++)
		{
			entityToHit = entitiesToHit.get(entity);
			if(entityToHit instanceof EntityEnemy)
			{
				if(player.getDirection() == Entity.LEFT)
				{
					entityToHit.setVector(knockback * -1, knockback * 0.5);
				}
				else
				{
					entityToHit.setVector(knockback, knockback * 0.5);
				}
				((EntityEnemy) entityToHit).damage(damage);
			}
		}
		
		return true;
	}

}
