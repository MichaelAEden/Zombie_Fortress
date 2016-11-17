package weapons;

import tileMap.TileMap;
import entities.EntityMap;
import entities.living.EntityPlayer;

public class WeaponHandgun extends WeaponGun
{

	public WeaponHandgun(EntityPlayer player, TileMap tileMap, EntityMap entityMap) 
	{
		super(player, tileMap, entityMap, "Handgun");

		reloadTime = 30;
		cost = 50;
		
		recoil = 0.005;
		power = 0.5;
		accuracy = 0.5;
		damage = 5;
		restackTime = 90;
		magazineSize = 6;
		
		stackNumber = magazineSize;
	}

}
