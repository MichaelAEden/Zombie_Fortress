package gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import main.GamePanel;
import animation.Animation;
import entities.living.EntityPlayer;


public class PlayerHUD
{
	private static Animation heartFull = new Animation("/PlayerHUD/HealthFull");
	private static Animation heartEmpty = new Animation("/PlayerHUD/HealthEmpty");
	private int heartWidth = GamePanel.WIDTH / 10;
	private int heartHeight = GamePanel.HEIGHT / 5;
	private int heartIndent = GamePanel.WIDTH / 20;
	
	private int indent;
	
	private int weaponContainerSize = GamePanel.WIDTH / 12;
	private int weaponIconSize = weaponContainerSize;// + GamePanel.WIDTH / 60;
	
	private EntityPlayer player;

	public PlayerHUD(EntityPlayer player)
	{	
		this.player = player;
	}
	
	public void draw(Graphics2D g)
	{
		drawWeapons(g);
		drawMoney(g);
		drawStats(g);
	}

	public void drawWeapons(Graphics2D g)
	{
		Composite dc = g.getComposite();
		
		for(int weapon = 0; weapon < player.getWeapons().size(); weapon++)
		{
			int x = GamePanel.WIDTH / 2 - ((int)(player.getWeapons().size() / 2.0 * (weaponContainerSize + indent))) + weapon * (weaponContainerSize + indent);
			int y = GamePanel.HEIGHT - GamePanel.HEIGHT / 20 - weaponContainerSize;
			if(weapon != player.getWeaponInUse())
			{
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F));
			}
			//g.fillRect(x - weaponContainerSize / 2, y - weaponContainerSize / 2, weaponContainerSize, weaponContainerSize);
			//g.drawImage(weaponContainer.getImage(weaponContainerSize), x - weaponContainerSize / 2, y - weaponContainerSize / 2, null);
			
			AffineTransform at = new AffineTransform();
			at.setToTranslation(x - weaponContainerSize / 2, y- weaponContainerSize / 2);
			at.rotate(Math.toRadians(45), weaponContainerSize / 2, weaponContainerSize / 2);
			g.drawImage(player.getWeapons().get(weapon).getIcon(weaponIconSize), at, null);
			
			if(player.getWeapons().get(weapon).getStock() != -1)
			{
				g.setColor(Color.RED);
				g.setFont(new Font("Times New Roman", Font.BOLD, 16 / GamePanel.SCALE));
				g.drawString("" + player.getWeapons().get(weapon).getStock(), x + weaponIconSize / 2 - GamePanel.WIDTH / 50, y + weaponIconSize / 2 - GamePanel.HEIGHT / 50);
			}
			
			g.setComposite(dc);
		}
	}
	
	public void drawMoney(Graphics2D g)
	{
		String money = "$" + player.getMoney();
		int indent = GamePanel.WIDTH / 50;

		g.setColor(Color.WHITE);
		g.setFont(new Font("Times New Roman", Font.BOLD, 50 / GamePanel.SCALE));
		for(int color = 0; color < 10; color++)
		{
			g.setColor(new Color(color * 25, color * 5, color * 5, 255 - color * 10));
			g.drawString(money, indent + color, indent * 2 + color);
		}
	}
	
	public void drawDebugScreen(Graphics2D g) 
	{
		int indent = GamePanel.HEIGHT / 40;

		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesNewRoman", Font.BOLD, 20 / GamePanel.SCALE));
		g.drawString("X: " + player.getX(), indent, indent * 5);
		g.drawString("Y: " + player.getY(), indent, indent * 6);
		g.drawString("DX: " + player.getDx(), indent, indent * 7);
		g.drawString("DY: " + player.getDy(), indent, indent * 8);
		g.drawString("Health: " + player.getHealth(), indent, indent * 9);
	}
	
	public void drawStats(Graphics2D g)
	{
		int heartCapacity = (int)(player.getHealthPercentage() * heartHeight);
		if(heartCapacity <= 0) heartCapacity = 1;
		g.drawImage(heartEmpty.getImage(heartWidth, heartHeight), heartIndent, GamePanel.HEIGHT - heartIndent - heartHeight, null);
		g.drawImage(heartFull.getImage(heartWidth, heartHeight).getSubimage(0, heartHeight - heartCapacity, 
				heartWidth, heartCapacity), 
				heartIndent, GamePanel.HEIGHT - heartIndent - 
				heartCapacity, null);
	}
}

