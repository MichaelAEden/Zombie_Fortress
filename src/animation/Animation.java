package animation;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Animation 
{
	private String name;
	
	private BufferedImage defaultTexture;
	private BufferedImage textures;
	
	private int framesX;
	private int framesY;
	private int frameWidth;
	private int frameHeight;

	public Animation(String name)
	{
		this.name = name + ".png";
		this.framesX = 1;
		this.framesY = 1;
		
		try
		{
			defaultTexture = ImageIO.read(getClass().getResourceAsStream(this.name)); //.read(getClass().getResourceAsStream(this.name));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(this.name + " not found");
		}
	}
	
	public Animation(String name, int framesX, int framesY)
	{
		this.name = name + ".png";
		this.framesX = framesX;
		this.framesY = framesY;
		
		try
		{
			defaultTexture = ImageIO.read(getClass().getResourceAsStream(this.name));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(this.name + " not found");
		}
	}
	
	public Animation(BufferedImage image) 
	{
		defaultTexture = image;
		this.framesX = 1;
		this.framesY = 1;
	}
	
	public Animation(BufferedImage image, int framesX, int framesY) 
	{
		defaultTexture = image;
		this.framesX = framesX;
		this.framesY = framesY;
	}
	
	public BufferedImage getImage(int width, int height) 
	{
		loadTextures(width, height);
		return textures;     
	}
	
	public BufferedImage getFrame(int frameX, int frameY, int width, int height) 
	{
		loadTextures(width * framesX, height * framesY);
		frameX = frameX % framesX;
		frameY = frameY % framesY;
		frameWidth = textures.getWidth(null) / this.framesX;
		frameHeight = textures.getHeight(null) / this.framesY;
		return textures.getSubimage(frameX * frameWidth, frameY * frameHeight, frameWidth, frameHeight);      
	}
	
	private void loadTextures(int width, int height)
	{
		if(textures == null || (textures.getWidth() != width || textures.getHeight() != height))
		{
			try
			{
				System.out.println("Too much Loading!!");
				textures = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				textures.getGraphics().drawImage(defaultTexture.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH), 0, 0, null);
				if(name != null) System.out.println("Loaded " + name + " successfully");
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Failed to load " + name);
			}
		}
	}
}
