package entities.misc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.GamePanel;

import tileMap.TileMap;
import entities.Entity;
import entities.EntityMap;

public class EntityFloatingText extends Entity 
{
	protected int lifeSpan = 60;
	protected int lifeTick = lifeSpan;
	protected double riseSpeed = 0.15;
	protected String text;

	public EntityFloatingText(TileMap tileMap, EntityMap entityMap, String text)
	{
		super(tileMap, entityMap);
		
		canCollideWithTiles = false;
		
		this.text = text;
	}
	
	public void update()
	{
		super.update();
		dy = Math.min(dy + 0.005, riseSpeed);
		lifeTick--;
		if(lifeTick == 0) setDead();
	}
	
	protected void updateMotion()
	{
		destX = x;
		destY = y + dy;
	}

	public void draw(Graphics2D g) 
	{
		g.setFont(new Font("Times New Roman", Font.BOLD, 25 / GamePanel.SCALE));
		int width = (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
		
		g.setColor(new Color(120, 50, 50, (int)(((float)lifeTick / lifeSpan) * 255)));
		g.drawString(text, tileMap.getPixelFromTileX(x + 0.5) - width / 2, tileMap.getPixelFromTileY(y - 0.5));
	}

}
