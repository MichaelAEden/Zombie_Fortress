package entities.particles;

import java.awt.Graphics2D;

import entities.EntityMap;
import animation.Animation;
import tileMap.TileMap;

public class EntityParticleSmoke extends EntityParticle 
{
	protected static Animation animation;
	protected double riseSpeed = 0.01;
	protected double maxRiseSpeed = 0.1;
	protected int frame;

	public EntityParticleSmoke(TileMap tileMap, EntityMap entityMap)
	{
		super(tileMap, entityMap);
		
		name = "Smoke";
		width = 0.2;
		height = 0.2;

		lifeSpan = rand.nextInt(50) + 50;
		if(animation == null)
		{
			animation = new Animation("/Particles/" + name, 8, 1);
		}
	}
	
	public void draw(Graphics2D g) 
	{
		/*g.drawImage(animation.getFrame((int)((((double)lifeSpan - age) / lifeSpan) * 8), 1, (int)(width * tileMap.getTileSize()), (int)(height * tileMap.getTileSize())),
				tileMap.getPixelFromTileX(x), 
				tileMap.getPixelFromTileY(y + height - 1),
				null);*/
	}
	
	public void updateMotion() 
	{
		dy += (riseSpeed - dy) * 0.7;
		destX = x + (dx * 0.2);
		destY = y + dy;
	}
}
