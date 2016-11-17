package entities.particles;

import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import tileMap.TileMap;
import animation.Animation;
import entities.EntityMap;

public class EntityParticleDebree extends EntityParticle
{
	private int textureWidth;
	private int minWidth = tileMap.getTileSize() / 8;
	private int maxWidth = tileMap.getTileSize() / 4;

	public EntityParticleDebree(TileMap tileMap, EntityMap entityMap, int x, int y) 
	{
		super(tileMap, entityMap);
		
		name = "Particle";
		bounce = 0.2;
		
		lifeSpan = rand.nextInt(500) + 100;
		textureWidth = rand.nextInt(maxWidth - minWidth) + minWidth;
		BufferedImage newTexture = new BufferedImage(textureWidth, textureWidth, BufferedImage.TYPE_INT_ARGB);      
		Graphics g = newTexture.getGraphics();
		g.setClip(new Ellipse2D.Double(0, 0, textureWidth, textureWidth));
		g.drawImage(tileMap.getTileAt(x, y).getTexture(tileMap, x, y).getSubimage(rand.nextInt(tileMap.getTileSize() - textureWidth), rand.nextInt(tileMap.getTileSize() - textureWidth), textureWidth, textureWidth), 0, 0, null);       
		animation = new Animation(newTexture);
		
		width = (double)textureWidth / tileMap.getTileSize();
		height = width;
	}
	
	public static void generateParticlesFromTile(TileMap tileMap, EntityMap entityMap, int x, int y) 
	{
		for(int particles = 0; particles < 10; particles++)
		{
			EntityParticle particle = new EntityParticleDebree(tileMap, entityMap, x, y);
			particle.setLocation(x + rand.nextDouble(), y + rand.nextDouble());
			particle.setVector((rand.nextDouble() - 0.5) * 0.1, (rand.nextDouble() - 0.1) * 0.2);
			particle.spawn();
		}
	}
}
