package entities.living;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import tileMap.TileMap;
import weapons.Weapon;
import weapons.WeaponHandgun;
import animation.Animation;
import entities.EntityMap;

public class EntityPlayer extends EntityLiving
{	
	private boolean willRespawn;
	private int respawnTick;
	
	private int money;
	private int lives;
	
	private boolean isClimbing;
	
	private int currentWeapon = 0;
	private boolean usingWeapon = false;
	private MouseEvent lastClick;
	private ArrayList<Weapon> purchasedWeapons = new ArrayList<Weapon>();
	private ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	
	protected Animation animation;

	public EntityPlayer(TileMap tileMap, EntityMap entityMap)
	{
		super(tileMap, entityMap);
		
		money = 500;
		lives = 4;
		weapons.add(new WeaponHandgun(this, tileMap, entityMap));
		
		name = "Player"; //Marcus Giles
		width = 1;
		height = 1.85;
		
		maxHealth = 100;
		groundAccel = 0.02;
		maxGroundSpeed = 0.2;
		airAccel = 0.005;
		maxAirSpeed = 0.1;
		jump = 0.2;
		weight = 0.01;
		
		health = maxHealth;
		
		animation = new Animation("/Sprites/Player/Player", 7, 2);
	}

	public void draw(Graphics2D g) 
	{
		if(respawnTick > 0 && respawnTick % 6 > 2) return;
		if(damageTick > 0)
		{
			g.setColor(Color.RED);
		}
		else
		{
			g.setColor(Color.GREEN);
		}
		BufferedImage image;
		if(usingWeapon)
		{
			image = animation.getFrame(0, 1, (int)(width * tileMap.getTileSize()), (int)(height * tileMap.getTileSize()));
		}
		else if(walkingDirection == LEFT || walkingDirection == RIGHT)
		{
			image = animation.getFrame(age / 5, 0, (int)(width * tileMap.getTileSize()), (int)(height * tileMap.getTileSize()));
		}
		else
		{
			image = animation.getFrame(1, 1, (int)(width * tileMap.getTileSize()), (int)(height * tileMap.getTileSize()));
		}
		if(direction == LEFT)
		{
			AffineTransform affineTransform = AffineTransform.getScaleInstance(-1, 1);
			affineTransform.translate(-image.getWidth(null), 0);
			AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			image = affineTransformOp.filter(image, null);
		}
		g.drawImage(image, tileMap.getPixelFromTileX(x), (int)(tileMap.getPixelFromTileY(y) - (height - 1) * tileMap.getTileSize()), null);
			
	}
	
	public void update()
	{
		super.update();
		if(willRespawn)
		{
			respawn();
		}
		if(respawnTick > 0)
		{
			respawnTick--;
		}
		if(usingWeapon)
		{
			weapons.get(currentWeapon).onUse(lastClick);
		}
		weapons.get(currentWeapon).update();
		//animation.setWalking(isWalkingLeft || isWalkingRight);
		//animation.update();
	}
	
	public void respawn()
	{
		setLocation(tileMap.getWidth() / 2, 30);
		health = maxHealth;
		respawnTick = 120;
		willRespawn = false;
	}
	
	public void setClimbing(boolean isClimbing) 
	{
		this.isClimbing = isClimbing;
	}
	public boolean isClimbing() 
	{
		return isClimbing;
	}
	
	public void setWeaponInUse(MouseEvent mouse) 
	{
		usingWeapon = true;
		lastClick = mouse;
	}
	public void setWeaponNotInUse() 
	{
		usingWeapon = false;
	}
	
	public void changeWeapon(int index) 
	{
		currentWeapon += index;
		if(currentWeapon >= weapons.size()) currentWeapon = 0;
		if(currentWeapon < 0) currentWeapon = weapons.size() - 1;
	}
	
	public int getMoney() 
	{
		return money;
	}
	
	public ArrayList<Weapon> getWeapons() 
	{
		return weapons;
	}
	public ArrayList<Weapon> getPurchasedWeapons() 
	{
		return purchasedWeapons;
	}
	public int getWeaponInUse() 
	{
		return currentWeapon;
	}

	
	public void setMoney(int money) 
	{
		this.money = money;
	}
	
	public void changeMoney(int amount) 
	{
		money += amount;
	}
	
	public void setDead() 
	{
		lives--;
		if(lives == 0)
		{
			isDead = true;
		}
		else
		{
			willRespawn = true;
		}
	}
}
