package weapons;

import java.awt.event.MouseEvent;
import tileMap.TileMap;
import entities.Entity;
import entities.EntityMap;
import entities.living.EntityPlayer;
import entities.weapons.EntityBullet;

public abstract class WeaponGun extends Weapon 
{
	protected double recoil;
	protected double power;
	protected double accuracy;
	protected int damage;
	protected int magazineSize;
	protected int restackTime;
	
	protected int stackNumber;
	
	protected Entity bullet;

	public WeaponGun(EntityPlayer player, TileMap tileMap, EntityMap entityMap, String name)
	{
		super(player, tileMap, entityMap, name);
	}
	
	public void update()
	{
		if(reloadTick > 0) reloadTick--;	
		if(reloadTick == 0 && stackNumber == 0)
		{
			stackNumber = magazineSize;
		}
	}
	
	public boolean onUse(MouseEvent mouse)
	{
		if(reloadTick == 0)
		{
			stackNumber--;
			reloadTick = reloadTime;
		}
		else
		{
			return false;
		}
		if(stackNumber == 0)
		{
			reloadTick = restackTime;
		}
		createBullet();
		spawnBullet(mouse);
		player.setWeaponInUse(mouse);
		return true;
	}
	
	public void createBullet()
	{
		bullet = new EntityBullet(tileMap, entityMap, damage);
	}
	
	public void spawnBullet(MouseEvent mouse)
	{
		bullet.setLocation(player.getX() + (player.getWidth() / 2.0), player.getY() + (player.getHeight() * (2.0 / 3.0)));
		double x = tileMap.getTileXFromMouse(mouse) - bullet.getX();
		double y = tileMap.getTileYFromMouse(mouse) - bullet.getY();
		double distanceFromCentre = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		x += ((1 - accuracy) / 2 - (rand.nextDouble() * (1 - accuracy))) * distanceFromCentre;
		y += ((1 - accuracy) / 2 - (rand.nextDouble() * (1 - accuracy))) * distanceFromCentre;
		bullet.setVector(power / distanceFromCentre * x, power / distanceFromCentre * y);
		bullet.spawn();
		player.setVector(player.getDx() - (recoil / distanceFromCentre * x), player.getDy() - (recoil / distanceFromCentre * y));
	}
	
	public int getStock()
	{
		return stackNumber;
	}
}
