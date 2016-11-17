package weapons;

import tileMap.TileMap;
import entities.EntityMap;
import entities.living.EntityPlayer;
import entities.weapons.EntityRPG;

public class WeaponRPGLauncher extends WeaponGun 
{
	public WeaponRPGLauncher(EntityPlayer player, TileMap tileMap, EntityMap entityMap)
	{
		super(player, tileMap, entityMap, "RPG Launcher");
		
		reloadTime = 20;
		stackNumber = 2000;
		cost = 300;
		
		recoil = 0.1;
		power = 0.1;
		accuracy = 1;
		restackTime = reloadTime;
		magazineSize = 1;
		
		stackNumber = magazineSize;
	}

	public void createBullet()
	{
		bullet = new EntityRPG(tileMap, entityMap);
	}
}
