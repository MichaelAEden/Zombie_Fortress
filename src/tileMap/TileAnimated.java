package tileMap;

import gui.InventoryGUI;

import java.awt.image.BufferedImage;

import main.GamePanel;
import animation.Animation;

public class TileAnimated extends Tile 
{
	protected int frames;
	protected int textureUpdateRate;
	protected boolean isAnimationSynced;

	public TileAnimated(String name, int cost, double hardness, double explosiveResistance, int frames, int textureUpdateRate, boolean isAnimationSynced) 
	{
		super(name, cost, hardness, explosiveResistance);
		this.frames = frames;
		this.textureUpdateRate = textureUpdateRate;
		this.isAnimationSynced = isAnimationSynced;
		
		texture = new Animation("/Tiles/" + name, frames, 1);
		mapIcon = new Animation("/Tiles/" + name, frames, 1);
		icon = new Animation("/Tiles/" + name, frames, 1);
	}
	
	public BufferedImage getTexture(TileMap tileMap, int x, int y) 
	{
		int frame = GamePanel.getTimeInTicks() / textureUpdateRate;
		if (!isAnimationSynced)
		{
			frame += x*x + y*y;
		}
		return texture.getFrame(frame, 1, tileMap.getTileSize(), tileMap.getTileSize());
	}
	
	public BufferedImage getMapIcon(TileMap tileMap, int x, int y)
	{
		int frame = GamePanel.getTimeInTicks() / textureUpdateRate;
		if (!isAnimationSynced)
		{
			frame += x*x + y*y;
		}
		return mapIcon.getFrame(frame, 1, tileMap.getMapTileSize(), tileMap.getMapTileSize());  
	}
	
	public BufferedImage getIcon(InventoryGUI inventory) 
	{
		int frame = (GamePanel.getTimeInTicks() / textureUpdateRate) % frames;
		return icon.getFrame(frame, 1, inventory.getIconSize(), inventory.getIconSize());  	
	}

}
