package entities;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import entities.living.EntityEnemy;
import entities.living.EntityPlayer;

public class EntityMap 
{
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	private EntityPlayer player;

	public EntityMap() 
	{
	}
	
	public void update()
	{
		for(int entity = 0; entity < entities.size(); entity++)
		{
			entities.get(entity).update();
			if(entities.get(entity) instanceof EntityEnemy)
			{
				((EntityEnemy)entities.get(entity)).updateAI(player);
			}
			if(entities.get(entity).isDead())
			{
				entities.remove(entity);
			}
		}
	}
	
	public void draw(Graphics2D g)
	{
		for(int drawPriority = 0; drawPriority <= Entity.LEASTDRAWPRIORITY; drawPriority++)
		{
			for(int entity = 0; entity < entities.size(); entity++)
			{
				if(entities.get(entity).getDrawPriority() == drawPriority) entities.get(entity).draw(g);
			}
		}	
	}
	
	public void spawn(Entity entity)
	{
		if(entity instanceof EntityPlayer)
		{
			this.player = (EntityPlayer) entity;
		}
		entities.add(entity);
	}
	
	public void clear()
	{
		entities.clear();
	}
	
	public ArrayList<Entity> getEntitiesWithin(Rectangle2D rect)
	{
		ArrayList<Entity> collidedEntities = new ArrayList<Entity>();
		for(int entity = 0; entity < entities.size(); entity++)
		{
			if(entities.get(entity) == null) continue;
			if(entities.get(entity).getCollisionBox().intersects(rect))
			{
				collidedEntities.add(entities.get(entity));
			}
		}
		return collidedEntities;
	}
}
