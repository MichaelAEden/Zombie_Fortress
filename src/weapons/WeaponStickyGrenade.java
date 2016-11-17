package weapons;

import tileMap.TileMap;
import entities.EntityMap;
import entities.living.EntityPlayer;
import entities.weapons.EntityStickyGrenade;

public class WeaponStickyGrenade extends WeaponGrenade 
{
	
	protected int stackNumber;
	protected boolean isEmpty;

	public WeaponStickyGrenade(EntityPlayer player, TileMap tileMap, EntityMap entityMap)
	{
		super(player, tileMap, entityMap, "Sticky Grenade");
		
		cost = 5;

		stackNumber = 1;
		reloadTime = 120;
	}
	
	protected void createGrenade()
	{
		grenade = new EntityStickyGrenade(tileMap, entityMap);
	}
	
	public int getStock()
	{
		return stackNumber;
	}
}
