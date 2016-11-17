package tileMap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import main.GamePanel;
import animation.Animation;
import entities.EntityMap;
import gui.Tool;

public class TileMap 
{
	private Random rand = new Random();
	
	private int width;
	private int height;
	
	private int time;
	
	private ArrayList<int[]> tileUpdates = new ArrayList<int[]>();
	private int[][] map;
	private int[][] primaryMetadataMap;
	private int[][] secondaryMetadataMap;
	private double[][] damageMap;
	private boolean[][] structureMap;
	
	private int tileSize = (int)(GamePanel.WIDTH / 24);
	private int colsToDraw;
	private int rowsToDraw;
	
	private Animation damageStages = new Animation("/Tiles/damageStages", 10, 1);
	
	private double xOffset;
	private double yOffset;
	private double visualxOffset;
	private double visualyOffset;
	private double tween = 0.07;
	private double scrollTween = 0.09;
	private double shake;
	
	private boolean constructionState;
	
	private boolean renderMiniMap = true;
	private int miniMapMargin = GamePanel.WIDTH / 60;
	private int miniMapMarginWidth;
	private int miniMapMarginHeight;
	private int miniMapTileSize;
	private int miniMapWidth;
	private int miniMapHeight;
	
	
	//private float transparencyIncrement = 2;

	public TileMap(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		init();
	}
	
	private void init()
	{
		miniMapTileSize = GamePanel.WIDTH / 180;
		miniMapWidth = miniMapTileSize * width;
		miniMapHeight = miniMapTileSize * height;
		
		miniMapMarginWidth = GamePanel.WIDTH - (miniMapMargin + miniMapWidth);
		miniMapMarginHeight = miniMapMargin;
		
		map = new int[width][height];
		primaryMetadataMap = new int[width][height];
		secondaryMetadataMap = new int[width][height];
		damageMap = new double[width][height];
		structureMap = new boolean[width][height];
		for(int x = 0; x < width; x++)
		{
			map[x] = new int[height];
			primaryMetadataMap[x] = new int[height];
			secondaryMetadataMap[x] = new int[height];
			damageMap[x] = new double[height];
			structureMap[x] = new boolean[height];
		}
	}
	
	public void generateTerrain(EntityMap entityMap)
	{
		int dirtHeight = 9;
		int stoneHeight = 7;
		int bedrockHeight = 4;
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y <= dirtHeight; y++)
			{
				setTileAt(entityMap, x, y, y <= bedrockHeight ? Tile.bedrock.ID : y <= stoneHeight ? Tile.stone.ID : y < dirtHeight ? Tile.dirt.ID : Tile.grass.ID);
			}
		}
		setTileAt(entityMap, (int)(width / 2 + 0.5), dirtHeight + 1, Tile.nexus.ID);
	}
	
	public int getTileIDAt(int x, int y)
	{
		return (isInBounds(x, y) ? map[x][y] : 0);
	}
	public Tile getTileAt(int x, int y)
	{
		return (isInBounds(x, y) ? Tile.getTileFromID(getTileIDAt(x, y)) : null);
	}
	public void setTileAt(EntityMap entityMap, int x, int y, int id)
	{
		if (!isInBounds(x, y)) return;
		map[x][y] = id;
		primaryMetadataMap[x][y] = 0;
		secondaryMetadataMap[x][y] = 0;
		damageMap[x][y] = 0;
		if(!isAir(x, y)) getTileAt(x, y).onCreation(this, entityMap, x, y);
	}
	public void destroyTileAt(EntityMap entityMap, int x, int y)
	{
		if (!isInBounds(x, y) || isAir(x, y)) return;
		getTileAt(x, y).onDestruction(this, entityMap, x, y);
		setTileAt(entityMap, x, y, 0);
	}
	public void damageTileAt(EntityMap entityMap, int x, int y, double damage)
	{
		if (!isInBounds(x, y) || isAir(x, y)) return;
		damageMap[x][y] += damage;
		if(damageMap[x][y] > 1) destroyTileAt(entityMap, x, y);
	}
	public double getTileDamageAt(int x, int y)
	{
		return (isInBounds(x, y) && !isAir(x, y) ? damageMap[x][y] : 0);
	}
	public void setMetadataAt(int x, int y, int data)
	{
		if (isInBounds(x, y) && !isAir(x, y)) primaryMetadataMap[x][y] = data;
	}
	public int getMetadataAt(int x, int y)
	{
		return (isInBounds(x, y) && !isAir(x, y) ? primaryMetadataMap[x][y] : 0);
	}
	public void scheduleUpdateAt(int x, int y, int time, int hint)
	{
		Iterator<int[]> iterator = tileUpdates.iterator();
		while(iterator.hasNext())
		{
			int[] update = iterator.next();
			if(update[0] == x && update[1] == y) return;
		}
		tileUpdates.add(new int[]{x, y, getTileIDAt(x, y), this.time + time, hint});
	}
	
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	
	public int getTileSize()
	{
		return tileSize;
	}
	public int getMapTileSize()
	{
		return miniMapTileSize;
	}
	
	public boolean isAir(int x, int y)
	{
		return (getTileIDAt(x, y) == 0);
	}
	public boolean isInBounds(int x, int y)
	{
		return (x < width && y < height) && (x >= 0 && y >= 0);
	}
	
	
	public boolean getConstructionState() 
	{
		return constructionState;
	}
	
	public void setConstructionState(boolean flag) 
	{
		constructionState = flag;
	}
	
	
	public int getVisibleAreaWidth()
	{
		return GamePanel.WIDTH / tileSize;
	}
	public int getVisibleAreaHeight()
	{
		return GamePanel.HEIGHT / tileSize;
	}
	
	public void setPosition(double x, double y)
	{
		if (!constructionState)
		{
			xOffset += (x - xOffset) * tween;
			yOffset += (y - yOffset) * tween;
		}
		else
		{
			xOffset += (x - xOffset) * scrollTween;
			yOffset += (y - yOffset) * scrollTween;
		}
		
		if (xOffset > width - colsToDraw + 1) xOffset = width - colsToDraw + 1;
		if (yOffset > height) yOffset = height;
		if (xOffset < 0) xOffset = 0;
		if (yOffset < 0) yOffset = 0;
		
		visualxOffset = xOffset + (shake * (rand.nextDouble() - 0.5));
		visualyOffset = yOffset + (shake * (rand.nextDouble() - 0.5));
		
		if (visualxOffset > width - colsToDraw + 1) visualxOffset = width - colsToDraw + 1;
		if (visualyOffset > height) visualyOffset = height;
		if (visualxOffset < 0) visualxOffset = 0;
		if (visualyOffset < 0) visualyOffset = 0;
	}
	
	public double getXOffset()
	{
		return visualxOffset;
	}
	public double getYOffset()
	{
		return visualyOffset;
	}
	
	public void shake(double shake)
	{    
		this.shake = shake;
	}
	
	
	public void update(EntityMap entityMap)
	{     
		time++;
		for(int tile = 0; true; tile++)
		{
			if(tile >= tileUpdates.size()) break;
			int[] update = tileUpdates.get(tile);
			if(update[2] == getTileIDAt(update[0], update[1]))
			{
				if(update[3] <= time)
				{
					tileUpdates.remove(tile);
					getTileAt(update[0], update[1]).scheduledUpdate(this, entityMap, update[0], update[1], update[4]);
				}
			}
			else
			{
				tileUpdates.remove(tile);
			}
		}
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				if(!isAir(x, y)) getTileAt(x, y).randomDisplayUpdate(this, entityMap, x, y);
			}
		}
		shake = Math.max(shake * 0.95, 0);
		if(shake < 0.01) shake = 0;
	}
	
	public void draw(Graphics2D g)
	{	
		drawTileMap(g);
		//drawMiniMap(g);
	}
	
	private void drawTileMap(Graphics2D g)
	{
		colsToDraw = GamePanel.WIDTH / tileSize + 1;
		rowsToDraw = GamePanel.HEIGHT / tileSize + 1;
		
		for(int x = (int)visualxOffset; x < visualxOffset + colsToDraw; x++)
		{
			for(int y = (int)visualyOffset; y < visualyOffset + rowsToDraw; y++)
			{
				if (!isAir(x, y))
				{
					getTileAt(x, y).draw(g, this, x, y);
					if(getTileDamageAt(x, y) > 0.01)
					{ 
						g.drawImage(damageStages.getFrame(Math.min(9, (int)(getTileDamageAt(x, y) * (11))), 1, tileSize, tileSize), getPixelFromTileX(x), getPixelFromTileY(y), null);
					}
				}
			}
		}
	}
	
	private void drawMiniMap(Graphics2D g)
	{
		if (!constructionState || !renderMiniMap) return;
		
		g.setColor(new Color(100, 150, 225, 150));
		g.fillRoundRect(miniMapMarginWidth, miniMapMarginHeight, miniMapWidth, miniMapHeight, 0, 0);
		
		Image texture;
		int xCoord;
		int yCoord;
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				xCoord = (int)(x * miniMapTileSize + miniMapMarginWidth);
				yCoord = (int)((height - y - 1) * miniMapTileSize + miniMapMarginHeight);
				if (!isAir(x, y))
				{
					texture = getTileAt(x, y).getMapIcon(this, x, y);
					g.drawImage(texture, xCoord, yCoord, null);
				}
			}
		}
		
		g.setColor(Color.ORANGE);
		g.drawRoundRect(miniMapMarginWidth, miniMapMarginHeight, miniMapWidth, miniMapHeight, 0, 0);
	}
	
	//Used only during the construction state
	public int getCostToPlace(Tool currentSelection, int selectedTileX, int selectedTileY, MouseEvent mouse)
	{
		return currentSelection.getToolCost(this, selectedTileX, selectedTileY, mouse);
	}
	//Used only during the construction state
	public void tryToPlace(Tool currentSelection, EntityMap entityMap, int selectedTileX, int selectedTileY, MouseEvent mouse)
	{
		currentSelection.onToolUse(this, entityMap, selectedTileX, selectedTileY, mouse);
	}
	//Used only during the construction state
	public boolean checkForValidLocation(int x, int y, Tool currentSelection) 
	{
		return currentSelection.canBeUsedAt(this, x, y);
	}
	
	public boolean[][] checkStructure() 
	{
		for(int tx = 0; tx < width; tx++)
		{
			for(int ty = 0; ty < height; ty++)
			{
				structureMap[tx][ty] = isAir(tx, ty);
			}
		}
		checkStructureAt(1, 1);
		return structureMap;
	}
	
	public void checkStructureAt(int x, int y) 
	{
		if(!isInBounds(x, y) || isAir(x, y) || structureMap[x][y]) return;
		structureMap[x][y] = true;
		checkStructureAt(x - 1, y);
		checkStructureAt(x, y - 1);
		checkStructureAt(x, y + 1);
		checkStructureAt(x + 1, y);
	}
	
	
	
	
	public double getTileXFromMouse(MouseEvent m)
	{
		return (((float)m.getX() / GamePanel.SCALE) / tileSize + visualxOffset);
	}
	
	public double getTileYFromMouse(MouseEvent m)
	{
		return ((GamePanel.HEIGHT - (float)m.getY() / GamePanel.SCALE) / tileSize + visualyOffset);
	}
	
	public int getPixelFromTileX(double x)
	{
		return (int)((x - visualxOffset) * tileSize);
	}
	
	public int getPixelFromTileY(double y)
	{
		return GamePanel.HEIGHT - (int)((y - visualyOffset + 1) * tileSize);
	}
}
