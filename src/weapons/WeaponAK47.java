package weapons;

import tileMap.TileMap;
import entities.EntityMap;
import entities.living.EntityPlayer;

public class WeaponAK47 extends WeaponGun
{

	public WeaponAK47(EntityPlayer player, TileMap tileMap, EntityMap entityMap) 
	{
		super(player, tileMap, entityMap, "AK47");

		reloadTime = 2;
		cost = 100;
		
		recoil = 0.005;
		power = 0.5;
		accuracy = 0.95;
		damage = 2;
		restackTime = 120;
		magazineSize = 100;
		
		stackNumber = magazineSize;
	}

}
