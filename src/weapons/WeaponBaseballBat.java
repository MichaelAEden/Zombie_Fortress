package weapons;

import tileMap.TileMap;
import entities.EntityMap;
import entities.living.EntityPlayer;

public class WeaponBaseballBat extends WeaponMelee 
{

	public WeaponBaseballBat(EntityPlayer player, TileMap tileMap, EntityMap entityMap) 
	{
		super(player, tileMap, entityMap, "Baseball Bat");
		
		reloadTime = 20;
		cost = 10;
		
		range = 0.5;
		knockback = 0.3;
		damage = 5;
	}

}
