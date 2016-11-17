package tileMap;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import entities.Entity;
import entities.EntityMap;
import entities.living.EntityLiving;

public class TileTrapDoor extends Tile 
{
	protected int rotateSpeed = 2;
	
	public TileTrapDoor(String name, int cost, double hardness, double explosiveResistance) 
	{
		super(name, cost, hardness, explosiveResistance);
	}
	
	public void onEntityWalkedOn(TileMap tileMap, Entity entity, int x, int y)
	{
		super.onEntityWalkedOn(tileMap, entity, x, y);
		if(entity instanceof EntityLiving)
		{
			tileMap.scheduleUpdateAt(x, y, 10, 1);
		}
	}
	
	public void scheduledUpdate(TileMap tileMap, EntityMap entityMap, int x, int y, int hint)
	{
		tileMap.setMetadataAt(x, y, Math.min(tileMap.getMetadataAt(x, y) + rotateSpeed * hint, 90));
		if(tileMap.getMetadataAt(x, y) > 0 && tileMap.getMetadataAt(x, y) < 90)
		{
			tileMap.scheduleUpdateAt(x, y, 1, hint);
		}
		else
		{
			if(tileMap.getMetadataAt(x, y) == 90) tileMap.scheduleUpdateAt(x, y, 50, -1);
		}
	}
	
	public Rectangle2D getCollisionBoxAt(TileMap tileMap, EntityMap entityMap,int x, int y) 
	{
		if(tileMap.getMetadataAt(x, y) == 0)
		{
			return new Rectangle2D.Double(x + collisionBox.getMinX(), y + collisionBox.getMinY(), collisionBox.getWidth(), collisionBox.getHeight());
		}
		else
		{
			return null;
		}
	}
	
	public void draw(Graphics2D g, TileMap tileMap, int x, int y)
	{
		AffineTransform at = new AffineTransform();
		at.translate(tileMap.getPixelFromTileX(x), tileMap.getPixelFromTileY(y));
		at.rotate(Math.toRadians(tileMap.getMetadataAt(x, y)), 0, tileMap.getTileSize() * 0.25); //, tileMap.getPixelFromTileX(x) + (), tileMap.getPixelFromTileY(y) + (tileMap.getTileSize() * 0.25)
		g.drawImage(texture.getImage(tileMap.getTileSize(), tileMap.getTileSize()), at, null);
	}
}
