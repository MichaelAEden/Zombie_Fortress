package entities.weapons;

import java.awt.Graphics2D;

import entities.Entity;
import entities.EntityMap;

import tileMap.TileMap;
import animation.Animation;

public class EntityThrownFlame extends Entity
{
	private Animation animation;
	private int textureWidth;
	private int minWidth = 5;
	private int maxWidth = 60;
	private int lifeSpan = 60;
	private int lifeTick = lifeSpan;

	public EntityThrownFlame(TileMap tileMap, EntityMap entityMap) 
	{
		super(tileMap, entityMap);
		
		name = "Thrownflame";
		
		textureWidth = rand.nextInt(maxWidth - minWidth) + minWidth;
		animation = new Animation("/Tiles/Fire", 16, 1);
		
		width = (double)textureWidth / tileMap.getTileSize();
		height = width;
	}
	
	public void update() 
	{
		super.update();
		lifeTick--;
		if(lifeTick == 0)
		{
			setDead();
		}
	}

	public void draw(Graphics2D g) 
	{
		g.drawImage(animation.getFrame(lifeSpan - lifeTick, 1, textureWidth, textureWidth),
				tileMap.getPixelFromTileX(x), 
				tileMap.getPixelFromTileY(y + height - 1),
				null);
		//System.out.println();
	}
}	
