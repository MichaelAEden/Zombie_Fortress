package entities;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

import tileMap.TileMap;
import entities.living.EntityLiving;

public class CollisionTester
{	
	
	public static boolean entityCollidesWithEntity(Entity entity1, Entity entity2)
	{
		return entity1.getCollisionBox().intersects(entity2.getCollisionBox());
	}
	
	public static double getCollisionPointBetween(Entity entity, TileMap tileMap, int x, int y, int direction)
	{
		if(tileMap.isAir(x, y))
		{
			return -1;
		}
		return tileMap.getTileAt(x, y).entityCollidesWithTileAt(tileMap, entity, x, y, direction);
	}
	
	public static double entityWillCollideWithTiles(Entity entity, TileMap tileMap, int direction, boolean updateTiles)
	{	
		double target = direction == Entity.RIGHT || direction == Entity.UP ? 1000 : -1;
		
		ArrayList<Point> updates = new ArrayList<Point>();
		
		if(direction == Entity.UP || direction == Entity.DOWN)
		{
			int y = direction == Entity.DOWN ? (int)entity.getY() : (int)(entity.getY() + entity.getHeight());
			for(int x = (int)(entity.getX()); x <= (int)(entity.getX() + entity.getWidth()); x++)
			{
				double collisionPoint = getCollisionPointBetween(entity, tileMap, x, y, direction);
				if(collisionPoint != -1)
				{
					if(direction == Entity.DOWN)
					{
						if(collisionPoint > target) updates.clear();
						if(collisionPoint >= target) updates.add(new Point(x, y));
						target = Math.max(collisionPoint, target);
					}
					else
					{
						if(collisionPoint < target) updates.clear();
						if(collisionPoint <= target) updates.add(new Point(x, y));
						target = Math.min(collisionPoint, target);
					}
				}
			}
		}
		else
		{
			int x = direction == Entity.LEFT ? (int)entity.getX() : (int)(entity.getX() + entity.getWidth());
			for(int y = (int)entity.getY(); y <= (int)(entity.getY() + entity.getHeight()); y++)
			{
				double collisionPoint = getCollisionPointBetween(entity, tileMap, x, y, direction);
				if(collisionPoint != -1)
				{
					if(direction == Entity.LEFT)
					{
						if(collisionPoint > target) updates.clear();
						if(collisionPoint >= target) updates.add(new Point(x, y));
						target = Math.max(collisionPoint, target);
					}
					else
					{
						if(collisionPoint < target) updates.clear();
						if(collisionPoint <= target) updates.add(new Point(x, y));
						target = Math.min(collisionPoint, target);
					}
				}
			}
		}
		Iterator<Point> iterator = updates.iterator();
		while(iterator.hasNext())
		{
			Point point = iterator.next();
			if(!tileMap.isAir(point.x, point.y))
			{
				if(entity instanceof EntityLiving && direction == Entity.DOWN)
				{
					tileMap.getTileAt(point.x, point.y).onEntityWalkedOn(tileMap, entity, point.x, point.y);
				}
				else
				{
					tileMap.getTileAt(point.x, point.y).onEntityCollided(tileMap, entity, point.x, point.y);
				}
			}
		}
		return target == 1000 || target == -1 ? -1 : target;
	}
}
