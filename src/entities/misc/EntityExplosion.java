package entities.misc;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import animation.Animation;

import tileMap.TileMap;
import entities.Entity;
import entities.EntityMap;
import entities.living.EntityLiving;
import entities.particles.EntityParticle;
import entities.particles.EntityParticleSmokeExplosion;

public class EntityExplosion extends Entity
{
	protected static Random rand = new Random();
	
	protected static HashMap<Double, Animation> animation = new HashMap<Double, Animation>();
	
	protected double power;

	public EntityExplosion(TileMap tileMap, EntityMap entityMap, double x, double y, double power) 
	{
		super(tileMap, entityMap);
		spawn();
		
		drawPriority = 9;
		
		this.power = power;
		width = power * 2;
		height = power * 2;
		this.x = x - power;
		this.y = y - power;
		
		for(int tx = (int)(x - power - 0.5); tx <= (int)(x + power + 0.5); tx++)
		{
			for(int ty = (int)(y - power - 0.5); ty <= (int)(y + power + 0.5); ty++)
			{
				double dx = tx - x;
				double dy = ty - y;
				double distanceFromExplosion = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
				if(distanceFromExplosion > power) continue;
				if(!tileMap.isAir(tx, ty))
				{
					if(tileMap.getTileAt(tx, ty).getExplosiveResistance() == 0)
					{
						tileMap.destroyTileAt(entityMap, tx, ty);
						continue;
					}
					tileMap.damageTileAt(entityMap, tx, ty, (1 - tileMap.getTileAt(tx, ty).getExplosiveResistance()) * (power - distanceFromExplosion));
				}
			}
		}
		
		for(int smokeParticles = 0; smokeParticles < 500; smokeParticles++)
		{
			double px = (rand.nextDouble() - 0.5) * 10;
			double py = Math.sqrt(1 - Math.pow(px, 2)) * (rand.nextBoolean() ? 1 : -1);
			EntityParticle particle = new EntityParticleSmokeExplosion(tileMap, entityMap);
			particle.setLocation(px + x, py + y);
			particle = new EntityParticleSmokeExplosion(tileMap, entityMap);
			particle.setLocation(py + x, px + y);
			particle.spawn();
		}
		
		ArrayList<Entity> collidedEntites = entityMap.getEntitiesWithin(new Rectangle2D.Double(x - power, y - power, x + power, y + power));
		Entity entity;
		for(int entityCounter = 0; entityCounter < collidedEntites.size(); entityCounter++)
		{
			entity = collidedEntites.get(entityCounter);
			double dx = (entity.getX() + entity.getWidth() / 2) - x;
			double dy = (entity.getY() + entity.getHeight() / 2) - y;
			double distanceFromExplosion = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
			if(distanceFromExplosion > power) continue;
			entity.setVector(dx * (power - distanceFromExplosion) * 0.1, dy * (power - distanceFromExplosion) * 0.1);
			if(entity instanceof EntityLiving) ((EntityLiving) entity).damage((int)(power - distanceFromExplosion) * 10);
		}
		
		tileMap.shake(power / 2);
		
		if (animation.get(power) == null) animation.put(power, new Animation("/Particles/Explosion", 12, 1));
	}
	
	public void update()
	{
		age++;
		if (age > 36) setDead();
	}

	public void draw(Graphics2D g)
	{
		g.drawImage(animation.get(power).getFrame(age / 3, 1, (int)(width * tileMap.getTileSize()), (int)(height * tileMap.getTileSize())), tileMap.getPixelFromTileX(x), (int)(tileMap.getPixelFromTileY(y) - (height - 1) * tileMap.getTileSize()), null);
	}
}
