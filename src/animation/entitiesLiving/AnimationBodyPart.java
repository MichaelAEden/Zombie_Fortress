package animation.entitiesLiving;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import tileMap.TileMap;
import animation.Animation;
import entities.living.EntityLiving;

public class AnimationBodyPart 
{
	protected EntityLiving entity;
	protected TileMap tileMap;
	protected String name;
	protected Animation animation;
	
	protected double width;
	protected double height;
	protected double jointOnEntityX;
	protected double jointOnEntityY;
	protected double jointOnLimbX;
	protected double jointOnLimbY;
	
	protected double rotation;
	
	
	public AnimationBodyPart(TileMap tileMap, EntityLiving entity, String name, double width, double height)
	{
		this.tileMap = tileMap;
		this.entity = entity;
		this.name = name;
		this.width = width;
		this.height = height;
		
		//animation = new Animation("/Sprites/" + entity.getName() + "/" + name, 1);
	}
	
	public void setJointOnEntity(double x, double y)
	{
		jointOnEntityX = x;
		jointOnEntityY = y;
	}
	public void setJointOnLimb(double x, double y)
	{
		jointOnLimbX = jointOnEntityX + x;
		jointOnLimbY = jointOnEntityY + y;
	}

	public void draw(Graphics2D g) 
	{
		AffineTransform at = new AffineTransform();
		at.setToTranslation(tileMap.getPixelFromTileX(entity.getX() + jointOnEntityX), tileMap.getPixelFromTileY(entity.getY() + jointOnEntityY + height - 1));
		at.rotate(rotation, (int)(tileMap.getTileSize() * jointOnLimbX), (int)(tileMap.getTileSize() * jointOnLimbY + height - 1));
		//g.drawImage(animation.getImage((int)(tileMap.getTileSize() * width)), at, null);
		g.setColor(Color.cyan);
		g.fillRect(tileMap.getPixelFromTileX(entity.getX() + jointOnLimbX), tileMap.getPixelFromTileY(entity.getY() + jointOnLimbY + height - 1), 10, 10);
	}
	
	public void setRotation(double rotation)
	{
		this.rotation = rotation;
	}
	public void addRotation(double rotation)
	{
		this.rotation += rotation;
	}
	public double getRotation()
	{
		return rotation;
	}
	
	

}
