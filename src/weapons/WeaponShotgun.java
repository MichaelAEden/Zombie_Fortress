package weapons;

import java.awt.event.MouseEvent;

import tileMap.TileMap;
import entities.Entity;
import entities.EntityMap;
import entities.living.EntityPlayer;

public class WeaponShotgun extends WeaponGun 
{
	protected int spray;
	
	protected Entity bullet;

	public WeaponShotgun(EntityPlayer player, TileMap tileMap, EntityMap entityMap)
	{
		super(player, tileMap, entityMap, "Shotgun");
		
		stackNumber = 2000;
		reloadTime = 60;
		cost = 50;
		
		recoil = 0.005;
		power = 0.5;
		accuracy = 0.5;
		damage = 2;
		restackTime = 120;
		magazineSize = 8;
		
		spray = 15;
		
		stackNumber = magazineSize;
	}
	
	public boolean onUse(MouseEvent mouse)
	{
		if(!super.onUse(mouse)) return false;
		if(stackNumber % magazineSize == 0)
		{
			reloadTick = restackTime;
		}
		for(int pellet = 0; pellet < spray; pellet++)
		{
			createBullet();
			spawnBullet(mouse);
		}
		return true;
	}
}
