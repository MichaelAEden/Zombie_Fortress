package tileMap;

import java.awt.geom.Rectangle2D;

import entities.EntityMap;

public class TileSlab extends Tile 
{

	public TileSlab(String name, int cost, double hardness, double explosiveResistance) 
	{
		super(name, cost, hardness, explosiveResistance);
	}
	
	public void onMouseClick(TileMap tileMap, EntityMap entityMap, int x, int y) 
	{
		tileMap.setMetadataAt(x, y, tileMap.getMetadataAt(x, y) + 1 % 4);
		System.out.println("Changed");
	}
	
	public Rectangle2D getCollisionBoxAt(TileMap tileMap, EntityMap entityMap, int x, int y) 
	{
		if(tileMap.getMetadataAt(x, y) == 0)
		{
			return new Rectangle2D.Double(x + collisionBox.getMinX(), y + collisionBox.getMinY(), collisionBox.getWidth(), collisionBox.getHeight());
		}
		else if(tileMap.getMetadataAt(x, y) == 1)
		{
			return new Rectangle2D.Double(x + collisionBox.getMinX(), y + collisionBox.getMinY(), collisionBox.getWidth(), collisionBox.getHeight());
		}
		else if(tileMap.getMetadataAt(x, y) == 2)
		{
			return new Rectangle2D.Double(x + collisionBox.getMinX(), y + 1 - collisionBox.getHeight(), collisionBox.getWidth(), 1 - collisionBox.getMinY());
		}
		else if(tileMap.getMetadataAt(x, y) == 3)
		{
			return new Rectangle2D.Double(x + collisionBox.getMinX(), y + collisionBox.getMinY(), collisionBox.getWidth(), collisionBox.getHeight());
		}
		return null;
	}

}
