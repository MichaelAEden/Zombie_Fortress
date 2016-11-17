package entities;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

import tileMap.Tile;
import tileMap.TileMap;
import animation.Animation;

public abstract class Entity 
{
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	public static final int NODIRECTION = 4;
	public static final int LEASTDRAWPRIORITY = 10;
	
	protected double smallestGap = 0.005;
	
	protected String name;
	protected Animation animation;
	protected int drawPriority = 1;
	
	protected TileMap tileMap;
	protected EntityMap entityMap;
	
	protected double x; //Measured from the top left corner
	protected double y; //Measured from the top left corner
	protected double destX;
	protected double destY;
	protected double nextX;
	protected double nextY;
	protected double tempY;
	protected double dx = 0;
	protected double dy = 0;
	
	protected double weight = 0.01;
	protected double gravity = 1;
	protected double bounce = 0;
	
	protected double width;
	protected double height;

	protected boolean canCollideWithTiles = true;
	protected boolean canCollideWithEntities = false;
	
	protected int age;
	protected boolean isDead = false;
	
	protected static Random rand = new Random();
	protected int collisionIterations = 1;

	public Entity(TileMap tileMap, EntityMap entityMap)
	{
		this.tileMap = tileMap;
		this.entityMap = entityMap;
	}
	
	public abstract void draw(Graphics2D g);
	
	public void update()
	{
		age++;
		updateMotion();
		checkCollision();
		updateTiles();
		checkForMapBounds();
	}

	protected void updateMotion()
	{
		if(isOnGround())
		{
			dx *= (getTileBelowEntity().getSlipperiness());
		}
		else
		{
			dy -= gravity * weight;
		}
		if(Math.abs(dx) < 0.00001) dx = 0;
		if(Math.abs(dy) < 0.00001) dy = 0;
		destX = x + dx;
		destY = y + dy;
	}
	
	protected void checkCollision()
	{
		if(canCollideWithEntities)
		{
			ArrayList<Entity> collidedEntites = entityMap.getEntitiesWithin(getCollisionBox());
			for(int entity = 0; entity < collidedEntites.size(); entity++)
			{
				if(onCollisionWithEntity(collidedEntites.get(entity))) return;
			}
		}
		if(!canCollideWithTiles)
		{
			x = destX;
			y = destY;
			return;
		}
		
		/*for(int collisionIteration = 0; collisionIteration < collisionIterations; collisionIteration++)
		{
			System.out.println(collisionIteration);
			nextX = destX * 5 / collisionIteration;
			nextY = destY * 5 / collisionIteration;
		}*/
		
		nextX = destX;
		nextY = destY;
		
		tempY = y;
		y = destY;
		
		double collisionPoint = CollisionTester.entityWillCollideWithTiles(this, tileMap, UP, true);
		if(collisionPoint != -1)
		{
			onCollisionWithTile();
			nextY = collisionPoint - height;
			dy *= -bounce;
			if(Math.abs(dy) < 0.005) dy = 0;
		}
		collisionPoint = CollisionTester.entityWillCollideWithTiles(this, tileMap, DOWN, true);
		if(collisionPoint != -1)
		{
			onCollisionWithTile();
			/*System.out.println("Down:" + collisionPoint);
			System.out.println(getCollisionBox().getMinY());*/
			nextY = collisionPoint;
			dy *= -bounce;
			if(Math.abs(dy) < 0.005) dy = 0;
		}
		
		y = tempY;
		x = destX;
		
		collisionPoint = CollisionTester.entityWillCollideWithTiles(this, tileMap, LEFT, true);
		if(collisionPoint != -1)
		{
			onCollisionWithTile();
			/*System.out.println("Left:" + collisionPoint);
			System.out.println(getCollisionBox().getMinX());*/
			nextX = collisionPoint;
			dx *= -bounce;
			if(Math.abs(dx) < 0.005) dx = 0;
		}
		collisionPoint = CollisionTester.entityWillCollideWithTiles(this, tileMap, RIGHT, true);
		if(collisionPoint != -1)
		{
			onCollisionWithTile();
			/*System.out.println("Right:" + collisionPoint);
			System.out.println(getCollisionBox().getMaxX());*/
			nextX = collisionPoint - width;
			dx *= -bounce;
			if(Math.abs(dx) < 0.005) dx = 0;
		}
		
		x = nextX;
		y = nextY;
	}
	
	public void updateTiles()
	{	
		for(int tx = (int)(x); tx <= (int)(x + width); tx++)
		{
			for(int ty = (int)(y); ty <= (int)(y + height); ty++)
			{
				if(!tileMap.isAir(tx, ty)) tileMap.getTileAt(tx, ty).onEntityEntered(tileMap, this, tx, ty);
			}
		}
	}
	
	protected void checkForMapBounds() 
	{
		if((x + width < 0 || x > tileMap.getWidth()) || (y + height < -1 || y > tileMap.getHeight() + 250))
		{
			setDead();
		}
	}
	
	protected boolean onCollisionWithEntity(Entity entity) 
	{
		return false;
	}
	
	protected void onCollisionWithTile() {}

	protected boolean isOnGround()
	{
		boolean willCollide = true;
		y -= smallestGap;
		if(CollisionTester.entityWillCollideWithTiles(this, tileMap, DOWN, false) == -1)
		{
			willCollide = false;
		}
		y += smallestGap;
		return willCollide;
	}
	
	protected boolean isHittingCeiling()
	{
		boolean willCollide = true;
		y += smallestGap;
		if(CollisionTester.entityWillCollideWithTiles(this, tileMap, UP, false) == -1)
		{
			willCollide = false;
		}
		y -= smallestGap;
		return willCollide;
	}
	
	protected boolean isHittingLeftWall()
	{
		boolean willCollide = true;
		x -= smallestGap;
		if(CollisionTester.entityWillCollideWithTiles(this, tileMap, LEFT, false) == -1)
		{
			willCollide = false;
		}
		x += smallestGap;
		return willCollide;
	}
	
	protected boolean isHittingRightWall()
	{
		boolean willCollide = true;
		x += smallestGap;
		if(CollisionTester.entityWillCollideWithTiles(this, tileMap, RIGHT, false) == -1)
		{
			willCollide = false;
		}
		x -= smallestGap;
		return willCollide;
	}
	
	protected Tile getTileBelowEntity()
	{
		if(tileMap.getTileAt((int)x, (int)(y - 0.0001)) == null) return Tile.bedrock;
		return tileMap.getTileAt((int)x, (int)(y - 0.0001));
	}
	
	public void spawn()
	{
		entityMap.spawn(this);
	}
	
	public void setLocation(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void setVector(double dx, double dy)
	{
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setDead()
	{
		isDead = true;
	}
	
	
	public boolean isDead()
	{
		return isDead;
	}
	
	public double getX()
	{
		return x;
	}
	public double getY()
	{
		return y;
	}
	public double getDx()
	{
		return dx;
	}
	public double getDy()
	{
		return dy;
	}
	public double getWidth()
	{
		return width;
	}
	public double getHeight()
	{
		return height;
	}
	
	public String getName()
	{
		return name;
	}
	public Rectangle2D getCollisionBox() 
	{
		return new Rectangle2D.Double(x, y, width, height);
	}
	public TileMap getTileMap() 
	{
		return tileMap;
	}
	public EntityMap getEntityMap() 
	{
		return entityMap;
	}
	public int getDrawPriority() 
	{
		return drawPriority;
	}
}
