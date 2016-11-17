package tileMap;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import entities.Entity;

public class TileMultipleCollisions extends Tile 
{
	protected ArrayList<Rectangle2D> collisionBoxes = new ArrayList<Rectangle2D>();

	public TileMultipleCollisions(String name, int cost, double hardness, double explosiveResistance) 
	{
		super(name, cost, hardness, explosiveResistance);
	}
	
	public boolean entityCollidesWith(TileMap tileMap, Entity entity, int x, int y)
	{
		Iterator<Rectangle2D> iterator = collisionBoxes.iterator();
		while(iterator.hasNext())
		{
			Rectangle2D defaultCollision = (Rectangle2D) iterator.next();
			Rectangle2D collisionArea = new Rectangle2D.Double(x + defaultCollision.getMinX(), y + defaultCollision.getMinY(), x + defaultCollision.getWidth(), y + defaultCollision.getHeight());         
			if (entity.getCollisionBox().intersects(collisionArea)) return true;
		}
		return false;
	}
	
	public double entityCollidesWithTileAt(TileMap tileMap, Entity entity, int x, int y, int direction)
	{
		double collisionPoint = direction == Entity.RIGHT || direction == Entity.UP ? 1000 : -1;
		Iterator<Rectangle2D> iterator = collisionBoxes.iterator();
		while(iterator.hasNext())
		{
			Rectangle2D defaultCollision = (Rectangle2D) iterator.next();
			Rectangle2D collisionArea = new Rectangle2D.Double(x + defaultCollision.getMinX(), y + defaultCollision.getMinY(), defaultCollision.getWidth(), defaultCollision.getHeight());         
			if (entity.getCollisionBox().intersects(collisionArea))  
			{
				if(direction == Entity.NODIRECTION)
				{
					return 1;
				}
				if(direction == Entity.LEFT) collisionPoint = Math.max(collisionPoint, collisionArea.getMaxX());
				if(direction == Entity.RIGHT) collisionPoint = Math.min(collisionPoint, collisionArea.getMinX());
				if(direction == Entity.DOWN) collisionPoint = Math.max(collisionPoint, collisionArea.getMaxY());
				if(direction == Entity.UP) collisionPoint = Math.min(collisionPoint, collisionArea.getMinY());
			}
		}
		return collisionPoint == 1000 || collisionPoint == -1 ? -1 : collisionPoint;
	}

}
