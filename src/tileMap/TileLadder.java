package tileMap;

import entities.Entity;
import entities.living.EntityPlayer;

public class TileLadder extends Tile 
{
	
	protected double climbSpeed = 0.08;
	protected double climbRate = 0.1;
	protected double descendSpeed = 0.08;
	protected double descendRate = 0.0015;

	public TileLadder(String name, int cost, double hardness, double explosiveResistance)
	{
		super(name, cost, hardness, explosiveResistance);
	}
	
	public void onEntityEntered(TileMap tileMap, Entity entity, int x, int y)
	{
		if(entity instanceof EntityPlayer)
		{
			if(((EntityPlayer) entity).isClimbing())
			{
				entity.setVector(entity.getDx(), Math.min(entity.getDy() + climbRate, climbSpeed));
			}
			else
			{
				entity.setVector(entity.getDx(), Math.max(entity.getDy() - descendRate, -descendSpeed));
			}
		}
	}

}
