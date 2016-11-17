package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import animation.Background;

import main.GamePanel;

public class MenuState extends GameState 
{
	private Background bg;
	
	private String[] options = {
			"Start",
			"Help",
			"Quit"
	};
	
	private int currentChoice;
	
	private Color titleColor;
	
	private Font font;
		
	private int optionsHeight = GamePanel.HEIGHT / 3 + 10;
	private int optionsSpacing = GamePanel.HEIGHT / 5;
	
	public MenuState(GameStateManager gsm) 
	{
		super(gsm);
		try
		{
			bg = new Background("MenuBG");
			bg.setPosition(20, 20);
			
			titleColor = new Color(128, 0, 0);
			font = new Font("Times New Roman", Font.PLAIN, 48 / GamePanel.SCALE);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void update() 
	{
	}

	public void draw(Graphics2D g) 
	{
		bg.draw(g);
		
		g.setColor(titleColor);
		for(int color = 0; color < 10; color++)
		{
			g.setFont(new Font("Times New Roman", Font.PLAIN, (120 + color) / GamePanel.SCALE));
			g.setColor(new Color(color * 25, color * 5, color * 5, 255 - color * 10));
			g.drawString("Zombie Fortress", GamePanel.WIDTH / 2 - (int)(g.getFontMetrics().getStringBounds("Zombie Fortress", g).getWidth() / 2), GamePanel.HEIGHT / 5);
		}		
		g.setFont(font);
		for (int i = 0; i < options.length; i++)
		{
			if (i == currentChoice)
			{
				g.setColor(Color.BLACK);
			}
			else
			{
				g.setColor(Color.RED);
			}
			g.drawString(options[i], GamePanel.WIDTH / 2 - (int)(g.getFontMetrics().getStringBounds(options[i], g).getWidth() / 2), optionsHeight + i * optionsSpacing); 
		}
		
	}
	
	private void select() 
	{
		if (currentChoice == 0)
		{
			gsm.setState(GameStateManager.CONSTRUCTIONSTATE);
		}
		else if (currentChoice == 1) 
		{
			//help
		}
		else if (currentChoice == 2) 
		{
			System.exit(0);
		}
	}
	
	public void keyPressed(int key) 
	{
		if (key == KeyEvent.VK_UP)
		{
			currentChoice--;
			if (currentChoice < 0)
			{
				currentChoice = options.length - 1;
			}
		}
		if (key == KeyEvent.VK_DOWN)
		{
			currentChoice++;
			if (currentChoice >= options.length)
			{
				currentChoice = 0;
			}
		}
		
		if (key == KeyEvent.VK_ENTER);
		{
			select();
		}
	}
	
	public void keyReleased(int key) 
	{
	}

	public void mouseClicked(MouseEvent mouse) 
	{
	}

	public void mousePressed(MouseEvent mouse) 
	{
	}

	public void mouseReleased(MouseEvent mouse)
	{
		if (mouse.getButton() == MouseEvent.BUTTON1)
		{
			select();
		}
	}
	
	public void mouseMoved(MouseEvent mouse) 
	{
		int x = mouse.getX() / GamePanel.SCALE;
		int y = mouse.getY() / GamePanel.SCALE;
		if (y > optionsHeight - optionsSpacing / 2 && (x > GamePanel.WIDTH / 2 - 50 && x < GamePanel.WIDTH / 2 + 50))
		{
			currentChoice = (y - optionsHeight + optionsSpacing / 2) / optionsSpacing;
		}
		else
		{
			currentChoice = -1;
		}
	}
	
	public void mouseDragged(MouseEvent mouse)
	{
	}
}
