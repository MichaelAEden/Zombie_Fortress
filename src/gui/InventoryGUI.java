package gui;

import gameState.GameStateManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import main.GamePanel;
import tileMap.Tile;
import animation.Animation;

public class InventoryGUI extends GUI
{
	
	private int inventoryWidth = GamePanel.WIDTH / 4 * 3;
	private int inventoryHeight = GamePanel.HEIGHT / 4 * 3;
	private int inventoryMarginWidth = (GamePanel.WIDTH - inventoryWidth) / 2;
	private int inventoryMarginHeight = (GamePanel.HEIGHT - inventoryHeight) / 2;
	private int inventoryColumns = 10;
	private int inventoryRows = 5;
	private int textureSize = inventoryWidth / (inventoryColumns + 2);
	private int iconSize = textureSize - 20;
	
	private static Animation inventoryContainer = new Animation("/Inventory/tileSelectionInterface");
	private static Animation tileContainer = new Animation("/Inventory/tileContainer");
	
	private ArrayList<Tool> tools = new ArrayList<Tool>();
	
	private Tool currentSelection;
	private Tool hoveredSelection;
	private int tagX = 0;
	private int tagY = 0;

	public InventoryGUI(GameStateManager gsm)
	{
		super(gsm);
		
		tools.add(new ToolDeleter());
		tools.add(new ToolRepairer());
		
		int id = 0;
		while (true)
		{
			id++;
				while (Tile.getTileFromID(id) == null || !Tile.getTileFromID(id).getAccessability())
				{
					if (Tile.getTileFromID(id) == null)
					{
						return;
					}
					id++;
				}
			tools.add(new ToolTilePlacer(Tile.getTileFromID(id)));
		}
	}

	public void draw(Graphics2D g)
	{	
		g.drawImage(inventoryContainer.getImage(inventoryWidth, inventoryHeight), (GamePanel.WIDTH - inventoryWidth) / 2, (GamePanel.HEIGHT - inventoryHeight) / 2, null);
		
		int id = 0;
		
		for(int y = inventoryMarginHeight + textureSize; (y - inventoryMarginHeight) / textureSize <= inventoryRows; y += textureSize)
		{
			for(int x = inventoryMarginWidth + textureSize; (x - inventoryMarginWidth) / textureSize <= inventoryColumns; x += textureSize)
			{
				if(id > tools.size() - 1)
				{
					drawTag(g);
					return;
				}
				if(currentSelection == tools.get(id))
				{
					g.setColor(Color.LIGHT_GRAY);
				}
				else
				{
					g.setColor(new Color(133, 133, 133));
				}
				g.fillRect(x, y, textureSize, textureSize);
				g.drawImage(tileContainer.getImage(textureSize, textureSize), x, y, null);
				g.drawImage(tools.get(id).getIcon(this), x + (textureSize - iconSize) / 2, y + (textureSize - iconSize) / 2, null);
				id++;
			}
		}
	}
	
	private void drawTag(Graphics2D g)
	{	
		if (hoveredSelection == null) return;
		String name = hoveredSelection.getName();
		String cost = hoveredSelection.getCost();
		g.setFont(new Font("Times New Roman", Font.BOLD, 20 / GamePanel.SCALE));
		int tagWidth = Math.max((int)g.getFontMetrics().getStringBounds(name, g).getWidth(),
				(int)g.getFontMetrics().getStringBounds(cost, g).getWidth()) + 10;
		int tagHeight = (int)g.getFontMetrics().getStringBounds(name, g).getHeight() * 2;
		
		g.setColor(Color.BLACK);
		g.fillRoundRect(tagX, tagY, tagWidth, tagHeight, 20, 20);
		g.setColor(Color.GRAY);
		g.drawRoundRect(tagX, tagY, tagWidth, tagHeight, 20, 20);
		g.setColor(Color.GRAY);
		g.drawRoundRect(tagX + 1, tagY + 1, tagWidth - 2, tagHeight - 2, 20, 20);
		g.setColor(Color.LIGHT_GRAY);
		g.drawString(name, tagX + 5, tagHeight / 2 + tagY - 5);
		g.setColor(Color.LIGHT_GRAY);
		g.drawString(cost, tagX + 5, tagY + tagHeight - 5);
	}
	
	public void mouseClicked(MouseEvent m) 
	{
		currentSelection = getSelectionFromMouse(m);
	}
	
	public void mouseMoved(MouseEvent m) 
	{
		tagX = m.getX() / GamePanel.SCALE;
		tagY = m.getY() / GamePanel.SCALE;
		hoveredSelection = getSelectionFromMouse(m);
	}
	
	public Tool getSelectedTool() 
	{
		return currentSelection;
	}
	
	public int getIconSize() 
	{
		return iconSize;
	}
	
	private Tool getSelectionFromMouse(MouseEvent m)
	{
		int index = ((processMouseCoordY(m) - 1) * inventoryColumns + processMouseCoordX(m)) - 1;
		if (index < 0 || index >= tools.size() ||
			processMouseCoordX(m) < 0 || processMouseCoordX(m) > inventoryColumns ||
			processMouseCoordY(m) < 0 || processMouseCoordY(m) > inventoryRows)
		{
			return null;
		}
		return tools.get(index);
	}
	
	
	
	private int processMouseCoordX(MouseEvent m)
	{
		return (int)((m.getX() / GamePanel.SCALE - inventoryMarginWidth) / textureSize);
	}
	
	private int processMouseCoordY(MouseEvent m)
	{
		return (int)((m.getY() / GamePanel.SCALE - inventoryMarginHeight) / textureSize);
	}
	
	public void update() {}
	public void keyPressed(int k) {}
	public void keyReleased(int k) {}
	public void mousePressed(MouseEvent m) {}
	public void mouseReleased(MouseEvent m) {}
	public void mouseDragged(MouseEvent m) {}

}
