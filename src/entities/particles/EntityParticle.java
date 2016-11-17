package entities.particles;

import java.awt.Graphics2D;
import entities.Entity;
import entities.EntityMap;

import tileMap.TileMap;

public class EntityParticle extends Entity
{	
	protected int lifeSpan;

	public EntityParticle(TileMap tileMap, EntityMap entityMap) 
	{
		super(tileMap, entityMap);
	}
	
	public void update() 
	{
		super.update();
		if(age > lifeSpan)
		{
			setDead();
		}
	}

	public void draw(Graphics2D g) 
	{
		/*g.drawImage(animation.getImage((int)(width * tileMap.getTileSize()), (int)(height * tileMap.getTileSize())),
				tileMap.getPixelFromTileX(x), 
				tileMap.getPixelFromTileY(y + height - 1),
				null);*/
	}

}
