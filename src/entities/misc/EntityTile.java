package entities.misc;

import java.awt.Graphics2D;

import entities.Entity;
import entities.EntityMap;

import tileMap.Tile;
import tileMap.TileMap;

public class EntityTile extends Entity 
{
	private Tile tile;

	public EntityTile(TileMap tileMap, EntityMap entityMap, Tile tile)
	{
		super(tileMap, entityMap);
		name = "Airbourne" + tile.Name;
		width = 1;
		height = 1;
		
		this.tile = tile;
	}

	public void draw(Graphics2D g) 
	{
		g.drawImage(tile.getTexture(tileMap, 10, 10),
				tileMap.getPixelFromTileX(x),
				(int)(tileMap.getPixelFromTileY(y) - (height - 1) * tileMap.getTileSize()),
				null);
	}
	
	protected void onCollisionWithTile() 
	{
		if(isDead) return;
		setDead();
		tileMap.setTileAt(entityMap, (int)x, (int)y + 1, tile.ID);
	}
	
}

