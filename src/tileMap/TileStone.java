package tileMap;

import entities.EntityMap;
import gui.InventoryGUI;

import java.awt.image.BufferedImage;

public class TileStone extends TileAnimated
{

	public TileStone(String name, int cost, double hardness, double explosiveResistance)
	{
		super(name, cost, hardness, explosiveResistance, 4, 0, false);
	}
	
	public void onCreation(TileMap tileMap, EntityMap entityMap, int x, int y)
	{
		tileMap.setMetadataAt(x, y, rand.nextInt(frames));
	}
	
	public BufferedImage getTexture(TileMap tileMap, int x, int y) 
	{
		return texture.getFrame(tileMap.getMetadataAt(x, y), 1, tileMap.getTileSize(), tileMap.getTileSize());
	}
	
	public BufferedImage getMapIcon(TileMap tileMap, int x, int y)
	{
		return mapIcon.getFrame(1, 1, tileMap.getMapTileSize(), tileMap.getMapTileSize());  
	}
	
	public BufferedImage getIcon(InventoryGUI inventory) 
	{
		return icon.getFrame(1, 1, inventory.getIconSize(), inventory.getIconSize());  	
	}
	
	/*AffineTransform at = new AffineTransform();
	at.setToTranslation(0, 0);
	at.setToRotation(Math.toRadians(tileMap.getMetadataAt(x, y) / 4 * 90), tileMap.getTileSize() / 2, tileMap.getTileSize() / 2);
	BufferedImage texture = new BufferedImage(tileMap.getTileSize(), tileMap.getTileSize(), BufferedImage.TYPE_INT_ARGB);
	texture.createGraphics().drawImage(this.texture.getFrame(tileMap.getMetadataAt(x, y) % 4, tileMap.getTileSize()), at, null);
	return texture;*/

}
