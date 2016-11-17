package tileMap;

import entities.Entity;
import entities.EntityMap;
import entities.particles.EntityParticleDebree;
import gui.InventoryGUI;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import animation.Animation;

public class Tile
{
	protected static ArrayList<Tile> tiles = new ArrayList<Tile>();
	
	public final int ID;
	public final String Name;
	protected double widthHeightRatio;
	protected Animation texture;
	protected Animation icon;
	protected Animation mapIcon;
	
	protected double hardness;
	protected double explosiveResistance;
	protected double slipperiness;
	protected int cost;
	protected boolean isAccessable = true;
	protected boolean isSolid = true;
	protected boolean isFlammable = true;
	
	protected Rectangle2D collisionBox;
	protected boolean usesDefaultCollision = true;
	
	protected Random rand = new Random();
	
	//stats
	//collision
	//accessable
	
	//Technical / Unnaccessable Tiles
	public static final Tile nexus = new TileNexus("Nexus", -51, 0.5, 0.9).setAccessability(false).setCollisionBox(0, 0, 1, 0.8);
	public static final Tile bedrock = new Tile("Bedrock", -11, 1, 1).setAccessability(false);
	public static final Tile stone = new TileStone("Stone", -7, 0.5, 0.9).setAccessability(false);
	public static final Tile dirt = new TileDirt("Dirt", -3, 0.1, 0.6).setAccessability(false);
	public static final Tile grass = new TileGrass("Grass", -3, 0.1, 0.6).setAccessability(false);

	//Building Tiles
	public static final Tile glass = new Tile("Glass", 1, 0.05, 0.05).setFlammability(false);
	public static final Tile ice = new Tile("Ice", 1, 0.1, 0.1).setSlipperiness(0.9);
	public static final Tile buildingDirt = new TileDirt("Dirt", 2, 0.1, 0.1);
	public static final Tile wood = new Tile("Wood", 4, 0.3, 0.3);
	public static final Tile cobblestone = new Tile("Cobblestone", 6, 0.3, 0.6);
	public static final Tile brick = new Tile("Brick", 8, 0.4, 0.5);
	public static final Tile obsidian = new Tile("Obsidian", 12, 0.6, 0.9);
	public static final Tile gold = new Tile("Gold", 15, 0.8, 0.8);
	public static final Tile diamond = new Tile("Diamond", 20, 0.9, 0.9);
	public static final Tile slab = new TileSlab("Slab", 1, 0.1, 0.1).setCollisionBox(0, 0, 1, 0.5);
	
	//Misc. Tiles
	public static final Tile conveyorBelt = new TileConveyorBelt("Conveyor Belt", 10, 0.1, 0.1).setCollisionBox(0, 0, 1, 0.4);
	public static final Tile trapDoor = new TileTrapDoor("Trap Door", 10, 0.1, 0.1).setSolid(false).setCollisionBox(0, 0.8, 1, 1);
	public static final Tile mine = new TileMine("Landmine", 20, 0.1, 0.1).setSolid(false).setCollisionBox(0.3, 0, 0.6, 0.2);
	public static final Tile ladder = new TileLadder("Ladder", 2, 0.1, 0.1).setSolid(false).setNoCollision();
	public static final Tile fire = new TileFire("Fire", 30, 0, 0).setFlammability(false).setSolid(false).setNoCollision();
	public static final Tile flypaper = new Tile("Flypaper", 30, 0, 0).setSlipperiness(0);
	
	//THE GILES TILE
	//public static final Tile gilesTile = new TileGiles("999Giles", 1000, 1, 1);
	
	public Tile(String name, int cost, double hardness, double explosiveResistance) 
	{
		if (tiles.size() == 0)
		{
			tiles.add(null);
		}
		ID = tiles.size();
		this.Name = name;
		this.cost = cost;
		this.hardness = hardness;
		this.explosiveResistance = explosiveResistance;
		
		slipperiness = 0.8;
		
		setCollisionBox(0, 0, 1, 1);
		
		texture = new Animation("/Tiles/" + name);
		mapIcon = new Animation("/Tiles/" + name);
		icon = new Animation("/Tiles/" + name);
		
		tiles.add(this);
	}
	
	public static Tile getTileFromID(int id) 
	{
		if (id >= tiles.size() || id == 0) return null;
		return tiles.get(id); //Maybe clone?
	}
	
	protected Tile setCollisionBox(double minX, double minY, double maxX, double maxY) 
	{
		collisionBox = new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);
		return this;
	}
	
	protected Tile setExplosiveResistance(double explosiveResistance) 
	{
		this.explosiveResistance = explosiveResistance;
		return this;
	}
	protected Tile setSlipperiness(double slipperiness) 
	{
		this.slipperiness = slipperiness;
		return this;
	}
	protected Tile setAccessability(boolean isAccessable) 
	{
		this.isAccessable = isAccessable;
		return this;
	}
	protected Tile setSolid(boolean isSolid)
	{
		this.isSolid = isSolid;
		return this;
	}
	protected Tile setFlammability(boolean setFlammability)
	{
		this.isFlammable = setFlammability;
		return this;
	}
	protected Tile setNoCollision()
	{
		collisionBox = null;
		return this;
	}
	
	public BufferedImage getTexture(TileMap tileMap, int x, int y) 
	{
		return texture.getImage(tileMap.getTileSize(), tileMap.getTileSize());
	}
	public BufferedImage getMapIcon(TileMap tileMap, int x, int y) 
	{
		return mapIcon.getImage(tileMap.getMapTileSize(), tileMap.getMapTileSize());
	}
	public BufferedImage getIcon(InventoryGUI inventory) 
	{
		return icon.getImage(inventory.getIconSize(), inventory.getIconSize());
	}
	
	public boolean getAccessability() 
	{
		return isAccessable;
	}
	public int getCost() 
	{
		return cost;
	}
	public double getHardness() 
	{
		return hardness;
	}
	public double getExplosiveResistance() 
	{
		return explosiveResistance;
	}
	public double getSlipperiness() 
	{
		return slipperiness;
	}
	public boolean isSolid() 
	{
		return isSolid;
	}
	public boolean isFlammable() 
	{
		return isFlammable;
	}
	public Rectangle2D getCollisionBoxAt(TileMap tileMap, EntityMap entityMap, int x, int y) 
	{
		if(collisionBox != null)
		{
			return new Rectangle2D.Double(x + collisionBox.getMinX(), y + collisionBox.getMinY(), collisionBox.getWidth(), collisionBox.getHeight());
		}
		return null;
	}
	
	public boolean entityCollidesWith(TileMap tileMap, Entity entity, int x, int y)
	{
		if(getCollisionBoxAt(tileMap, entity.getEntityMap(), x, y) == null) return false;         
		return entity.getCollisionBox().intersects(getCollisionBoxAt(tileMap, entity.getEntityMap(), x, y));
	}
	public double entityCollidesWithTileAt(TileMap tileMap, Entity entity, int x, int y, int direction)
	{
		if(getCollisionBoxAt(tileMap, entity.getEntityMap(), x, y) == null) return -1;
		Rectangle2D collisionArea = getCollisionBoxAt(tileMap, entity.getEntityMap(), x, y);
		if (entity.getCollisionBox().intersects(collisionArea))  
		{
			if(direction == Entity.NODIRECTION) return 1;
			if(direction == Entity.LEFT) return collisionArea.getMaxX();
			if(direction == Entity.RIGHT) return collisionArea.getMinX();
			if(direction == Entity.DOWN) return collisionArea.getMaxY();
			if(direction == Entity.UP) return collisionArea.getMinY();
		}
		return -1;
	}
	
	public void draw(Graphics2D g, TileMap tileMap, int x, int y)
	{
		g.drawImage(getTexture(tileMap, x, y), tileMap.getPixelFromTileX(x), tileMap.getPixelFromTileY(y), null);
	}
	public void onCreation(TileMap tileMap, EntityMap entityMap, int x, int y){}
	public void onMouseClick(TileMap tileMap, EntityMap entityMap, int x, int y) {}
	public void neighbourUpdate(TileMap tileMap, EntityMap entityMap, int x, int y){}
	public void randomDisplayUpdate(TileMap tileMap, EntityMap entityMap, int x, int y){}
	public void scheduledUpdate(TileMap tileMap, EntityMap entityMap, int x, int y, int hint){}
	public void onEntityCollidesWith(TileMap tileMap, Entity entity, int x, int y){}
	public void onEntityWalkedOn(TileMap tileMap, Entity entity, int x, int y){}
	public void onEntityEntered(TileMap tileMap, Entity entity, int x, int y){}
	public void onEntityCollided(TileMap tileMap, Entity entity, int x, int y){}
	public void onDestructionByExplosion(TileMap tileMap, EntityMap entityMap, int x, int y){}
	public void onDestructionByEnemy(TileMap tileMap, Entity entity, int x, int y){}
	public void onDestruction(TileMap tileMap, EntityMap entityMap, int x, int y)
	{
		EntityParticleDebree.generateParticlesFromTile(tileMap, entityMap, x, y);
		updateNeighbours(tileMap, entityMap, x, y);
	}
	
	
	public void updateNeighbours(TileMap tileMap, EntityMap entityMap, int x, int y)
	{
		if(!tileMap.isAir(x, y + 1)) tileMap.getTileAt(x, y + 1).neighbourUpdate(tileMap, entityMap, x, y + 1);
		if(!tileMap.isAir(x, y - 1)) tileMap.getTileAt(x, y - 1).neighbourUpdate(tileMap, entityMap, x, y - 1);
		if(!tileMap.isAir(x + 1, y)) tileMap.getTileAt(x + 1, y).neighbourUpdate(tileMap, entityMap, x + 1, y);
		if(!tileMap.isAir(x - 1, y)) tileMap.getTileAt(x - 1, y).neighbourUpdate(tileMap, entityMap, x - 1, y);
	}
	
	
	public boolean canBePlacedAt(TileMap tileMap, int x, int y) 
	{
		return tileMap.getTileAt(x, y) == null;
	}
}
