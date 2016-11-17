package weapons;

import java.awt.event.MouseEvent;

import tileMap.TileMap;
import entities.Entity;
import entities.EntityMap;
import entities.living.EntityPlayer;
import entities.weapons.EntityThrownFlame;

public class WeaponFlamethrower extends Weapon 
{
	int flameSpray = 2;

	public WeaponFlamethrower(EntityPlayer player, TileMap tileMap, EntityMap entityMap) 
	{
		super(player, tileMap, entityMap, "AK47");
		
		cost = 200;
	}
	
	public boolean onUse(MouseEvent mouse)
	{
		for(int flame = 0; flame < flameSpray; flame++)
		{
			EntityThrownFlame fire = new EntityThrownFlame(tileMap, entityMap);
			fire.setLocation(player.getX(), player.getY() / 2);
			if(player.getDirection() == Entity.LEFT)
			{
				fire.setLocation(player.getX(), player.getY() + player.getHeight() / 2);
				fire.setVector(-0.3, (rand.nextDouble() - 0.5) * 0.2);
			}
			else
			{
				fire.setLocation(player.getX() + player.getWidth(), player.getY() + player.getHeight() / 2);
				fire.setVector(0.3, (rand.nextDouble() - 0.5) * 0.2);
			}
			fire.spawn();
		}
		return true;
	}

}
