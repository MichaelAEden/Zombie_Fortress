package tileMap;

import entities.Entity;
import entities.EntityMap;

public class TileConveyorBelt extends Tile
{
	
	private double conveyerRate = 0.02;

	public TileConveyorBelt(String name, int cost, double hardness,	double explosiveResistance) 
	{
		super(name, cost, hardness, explosiveResistance);
	}
	
	public void onEntityWalkedOn(TileMap tileMap, Entity entity, int x, int y)
	{
		if(tileMap.getMetadataAt(x, y) == 1) entity.setLocation(entity.getX() - conveyerRate, entity.getY());
		if(tileMap.getMetadataAt(x, y) == 0) entity.setLocation(entity.getX() + conveyerRate, entity.getY());
		/*
		if(entity instanceof EntityLiving)
		{
			entity.setVector(Math.max(entity.getDx() - conveyerRate, conveyerSpeed - ((EntityLiving) entity).getSpeed()), entity.getDy());
		}
		else
		{
			entity.setVector(Math.max(entity.getDx() - conveyerRate, -conveyerSpeed), entity.getDy());
		}*/
	}
	
	public void onMouseClick(TileMap tileMap, EntityMap entityMap, int x, int y)
	{
		tileMap.setMetadataAt(x, y, tileMap.getMetadataAt(x, y) == 1 ? 0 : 1);
	}


}
