package weapons;

import java.awt.event.MouseEvent;

import tileMap.TileMap;
import entities.Entity;
import entities.EntityMap;
import entities.living.EntityPlayer;
import entities.weapons.EntityGrenade;

public class WeaponGrenade extends Weapon 
{
	
	private double power = 0.5;
	
	protected int stackNumber;
	protected boolean isEmpty;
	
	protected Entity grenade;

	public WeaponGrenade(EntityPlayer player, TileMap tileMap, EntityMap entityMap)
	{
		super(player, tileMap, entityMap, "Grenade");
		
		cost = 5;

		stackNumber = 1;
		reloadTime = 120;
	}
	
	public WeaponGrenade(EntityPlayer player, TileMap tileMap, EntityMap entityMap, String name)
	{
		super(player, tileMap, entityMap, name);
	}
	
	public boolean onUse(MouseEvent mouse)
	{
		if(!super.onUse(mouse)) return false;
		createGrenade();
		spawnGrenade(mouse);
		return true;
	}
	
	protected void createGrenade()
	{
		grenade = new EntityGrenade(tileMap, entityMap);
	}
	
	protected void spawnGrenade(MouseEvent mouse)
	{
		grenade.setLocation(player.getX(), player.getY() + player.getHeight());
		double x = tileMap.getTileXFromMouse(mouse) - grenade.getX();
		double y = tileMap.getTileYFromMouse(mouse) - grenade.getY();
		double distanceFromCentre = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		grenade.setVector(power / distanceFromCentre * x, power / distanceFromCentre * y);
		grenade.spawn();
	}
	
	public int getStock()
	{
		return stackNumber;
	}
}
