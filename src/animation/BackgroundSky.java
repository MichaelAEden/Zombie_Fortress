package animation;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class BackgroundSky extends Background
{
	private static int time;
	
	public static int DAYLENGTH = 12000;
	
	private static int sunriseDuration = DAYLENGTH / 8;
	public static int DAY = 0;
	public static int DUSK = DAYLENGTH / 2 - sunriseDuration;
	public static int NIGHT = DAYLENGTH / 2;
	public static int DAWN = DAYLENGTH - sunriseDuration;
	
	private Color dayColor = new Color(150,150,255);
	private Color nightColor = new Color(10,10,40);
	private GradientPaint sunriseGradient;
	private double starTransparency;
	
	private Animation sun = new Animation("/Backgrounds/Sun");
	private Animation moon = new Animation("/Backgrounds/Moon");
	private BufferedImage stars;
	
	private int sunSize = GamePanel.WIDTH / 10;
	private int orbitRadius = GamePanel.WIDTH / 2;
	private Point orbitPoint = new Point(GamePanel.WIDTH / 2, 1 * (GamePanel.HEIGHT / 2));
	
	public BackgroundSky() 
	{
		createStars();
		createClouds();
	}
	
	public void update()
	{
		time++;
	}
	
	public void setTime(int time)
	{
		BackgroundSky.time = time % DAYLENGTH;
	}
	public int getTime()
	{
		return time;
	}
	
	public double getBrightness()
	{
		if(time >= DAY && time <= DUSK)
		{
			return 1;
		}
		else if(time >= NIGHT && time <= DAWN)
		{
			return 0;
		}
		else
		{
			if(time > DAWN)
			{
				return (double)(time - DAWN) / sunriseDuration;
			}
			else
			{
				return 1 - (double)(time - DUSK) / sunriseDuration;
			}
		}
	}

	public void draw(Graphics2D g)
	{
		drawSky(g);
		//drawClouds(g);
	}

	private void drawSky(Graphics2D g)
	{
		/*time = time % DAYLENGTH;
		if(time >= DAY && time <= DUSK)
		{
			g.setColor(dayColor);
			starTransparency = 0;
		}
		else if(time >= NIGHT && time <= DAWN)
		{
			g.setColor(nightColor);
			starTransparency = 1;
		}
		else
		{
			if(time > DAWN)
			{
				double fade = (double)(time - DAWN) / sunriseDuration;
				double red = 1 - (Math.abs((double)(time - DAWN) - sunriseDuration / 2) / (sunriseDuration / 2));
				double green = red / 2;
				Color horizonColor = fadeColors(nightColor, dayColor, fade, red, green, 0);
				Color skyColor = fadeColors(horizonColor, dayColor, fade, 0, 0, 0);
				sunriseGradient = new GradientPaint(GamePanel.WIDTH, 0, skyColor, 0, GamePanel.HEIGHT, 
						horizonColor);
				g.setPaint(sunriseGradient);
				starTransparency = 1 - fade;
			}
			else
			{
				double fade = (double)(time - DUSK) / sunriseDuration;
				double red = 1 - (Math.abs((double)(time - DUSK) - sunriseDuration / 2) / (sunriseDuration / 2));
				double green = red / 2;
				Color horizonColor = fadeColors(dayColor, nightColor, fade, red, green, 0);
				Color skyColor = fadeColors(horizonColor, nightColor, fade, 0, 0, 0);
				sunriseGradient = new GradientPaint(0, 0, skyColor, GamePanel.WIDTH, GamePanel.HEIGHT, horizonColor);
				g.setPaint(sunriseGradient);
				starTransparency = fade;
			}
		}
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		Composite dc = g.getComposite();
		
		if(starTransparency > 0)
		{
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)starTransparency));
			AffineTransform at = new AffineTransform();
			at.setToTranslation(orbitPoint.getX() - stars.getWidth() / 2, orbitPoint.getY() - stars.getHeight() / 2);
			at.rotate(Math.toRadians((double)time / DAYLENGTH * 360), stars.getWidth() / 2, stars.getHeight() / 2);
			g.drawImage(stars, at, null);
			
		}*/
		
		Composite dc = g.getComposite();
		g.setComposite(dc);
		
		g.setColor(nightColor);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		/*AffineTransform at = new AffineTransform();
		at.setToTranslation(orbitPoint.getX() - orbitRadius, orbitPoint.getY());
		at.rotate(Math.toRadians((double)time / DAYLENGTH * 360), orbitPoint.getX(), orbitPoint.getY());
		g.drawImage(sun.getImage(sunSize, sunSize), at, null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)starTransparency));
		at.rotate(Math.toRadians(180), orbitPoint.getX(), orbitPoint.getY());
		g.drawImage(moon.getImage(sunSize, sunSize), at, null);*/
	}
	/*private void drawClouds(Graphics2D g) 
	{
		
	}*/
	
	private void createStars()
	{
		int length = (int)(Math.sqrt(Math.pow(GamePanel.WIDTH - orbitPoint.getX(), 2) + Math.pow(GamePanel.HEIGHT - orbitPoint.getY(), 2)) * 2);
		stars = new BufferedImage(length, length, BufferedImage.TYPE_INT_ARGB);
		Graphics g = stars.getGraphics();
		for(int star = 0; star < 1000; star++)
		{
			int x = rand.nextInt(length);
			int y = rand.nextInt(length);
			g.fillRect(x - 1, y, 3, 1);
			g.fillRect(x, y - 1, 1, 3);
		}
	}
	private void createClouds() 
	{
		
	}
	
	public static Color fadeColors(Color c1, Color c2, double fade, double addedRed, double addedGreen, double addedBlue)
	{
		//the smaller fade is, the more faded the second color is
		fade = 1 - fade;
		return new Color(Math.min((int)((c1.getRed() * fade + c2.getRed() * (1 - fade)) + 255 * addedRed), 255),
				Math.min((int)(c1.getGreen() * fade + c2.getGreen() * (1 - fade) + 255 * addedGreen), 255),
				Math.min((int)(c1.getBlue() * fade + c2.getBlue() * (1 - fade) + 255 * addedBlue), 255));
	}
}
