package gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import main.GamePanel;
import weapons.*;
import animation.Animation;
import entities.living.EntityPlayer;
import gameState.GameStateManager;

public class WeaponsShopGUI extends GUI
{
	private int iconSize = GamePanel.WIDTH / 8;
	
	private static Animation shopBackground = new Animation("/Backgrounds/weaponsShopBG");
	
	private ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	private ArrayList<Rectangle> weaponsBounds = new ArrayList<Rectangle>();
	
	private Weapon hoveredSelection;
	private Weapon draggedSelection;
	private int draggedWeaponX;
	private int draggedWeaponY;
	private boolean mouseDragged;
	
	private EntityPlayer player;
	
	private PlayerHUD weaponsHUD;

	public WeaponsShopGUI(GameStateManager gsm, EntityPlayer player)
	{
		super(gsm);
		
		this.player = player;
		weaponsHUD = new PlayerHUD(player);
		
		addWeaponAt(new WeaponAK47(player, player.getTileMap(), player.getEntityMap()), 10, 10);
		addWeaponAt(new WeaponBaseballBat(player, player.getTileMap(), player.getEntityMap()), 300, 10);
		addWeaponAt(new WeaponGrenade(player, player.getTileMap(), player.getEntityMap()), 600, 10);
		addWeaponAt(new WeaponShotgun(player, player.getTileMap(), player.getEntityMap()), 300, 300);
		addWeaponAt(new WeaponRPGLauncher(player, player.getTileMap(), player.getEntityMap()), 700, 300);
		addWeaponAt(new WeaponStickyGrenade(player, player.getTileMap(), player.getEntityMap()), 900, 10);
		addWeaponAt(new WeaponHandgun(player, player.getTileMap(), player.getEntityMap()), 900, 300);
	}

	public void draw(Graphics2D g)
	{	
		g.drawImage(shopBackground.getImage(GamePanel.WIDTH, GamePanel.HEIGHT), 0, 0, null);
		weaponsHUD.draw(g);
		
		for(int weapon = 0; weapon < weapons.size(); weapon++)
		{
			if(hoveredSelection == weapons.get(weapon))
			{
				g.setColor(new Color(230, 230, 230, 200));
				g.fill(weaponsBounds.get(weapon));
			}
			g.drawImage(weapons.get(weapon).getShopIcon(iconSize), weaponsBounds.get(weapon).x, weaponsBounds.get(weapon).y, null);
			if(draggedSelection != null && mouseDragged)
			{
				Composite dc = g.getComposite();
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4F));
				g.drawImage(draggedSelection.getShopIcon(iconSize), draggedWeaponX, draggedWeaponY, null);
				g.setComposite(dc);
			}
		}
	}
	
	public void mouseClicked(MouseEvent m) 
	{
		Weapon weapon = getSelectionFromMouse(m);
		if(weapon == null) return;
		if(!player.getPurchasedWeapons().contains(weapon))
		{
			if(player.getMoney() >= weapon.getCost())
			{
				player.getPurchasedWeapons().add(weapon);
				player.changeMoney(-weapon.getCost());
				player.getWeapons().add(weapon);	
			}
		}
	}
	
	public void mouseMoved(MouseEvent m) 
	{
		hoveredSelection = getSelectionFromMouse(m);
	}
	
	public void mouseDragged(MouseEvent m) 
	{
		if(!player.getPurchasedWeapons().contains(getSelectionFromMouse(m)))
		{
			mouseClicked(m);
		}
		if(player.getPurchasedWeapons().contains(getSelectionFromMouse(m)))
		{
			draggedSelection = getSelectionFromMouse(m);
		}
		if(draggedSelection != null)
		{
			mouseDragged = true;
			draggedWeaponX = m.getX() - iconSize / 2;
			draggedWeaponY = m.getY() - iconSize / 2;
		}
	}
	
	public void mouseReleased(MouseEvent m) 
	{
		mouseDragged = false;
		draggedSelection = null;
	}
	
	public void keyPressed(int k) 
	{
		if (k == KeyEvent.VK_Z)
		{
			gsm.setState(GameStateManager.ATTACKSTATE, player);
		}
	}
	
	private void addWeaponAt(Weapon weapon, int x, int y)
	{
		weapons.add(weapon);
		weaponsBounds.add(new Rectangle(x, y, iconSize, iconSize));
	}
	
	public int getIconSize() 
	{
		return iconSize;
	}
	
	private Weapon getSelectionFromMouse(MouseEvent m)
	{
		for(int weapon = 0; weapon < weapons.size(); weapon++)
		{
			if(weaponsBounds.get(weapon).contains(new Point(m.getX(), m.getY())))
			{
				return weapons.get(weapon);
			}
		}
		return null;
	}
	
	public void update() {}
	public void keyReleased(int k) {}
	public void mousePressed(MouseEvent m) {}

}
