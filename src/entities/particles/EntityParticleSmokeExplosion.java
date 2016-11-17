package entities.particles;

import tileMap.TileMap;
import entities.EntityMap;

public class EntityParticleSmokeExplosion extends EntityParticleSmoke
{
	private int velocityTimer = 10;

	public EntityParticleSmokeExplosion(TileMap tileMap, EntityMap entityMap)
	{
		super(tileMap, entityMap);
		
		name = "Explosion Smoke";    
	}
	
	public void update() 
	{
		super.update();
		velocityTimer--;
	}
	
	public void updateMotion() 
	{
		if(velocityTimer < 0)
		{
			dx *= 0.5;
			dy += (riseSpeed - dy) * 0.7;
			destX = x + dx;
			destY = y + dy;
		}
		else
		{
			destX = x + dx;
			destY = y + dy;
		}
	}
}
