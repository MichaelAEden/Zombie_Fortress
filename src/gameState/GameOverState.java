package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import main.GamePanel;

public class GameOverState extends GameState 
{
	
	private int textTransparency = 1;
	private int textTransparencyIncrement = 2;

	public GameOverState(GameStateManager gsm) 
	{
		super(gsm);
	}

	@Override
	public void update() 
	{
		// TODO Auto-generated method stub

	}

	public void draw(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(new Color(122, 10, 10, textTransparency));
		g.setFont(new Font("Century Gothic", Font.PLAIN, 120 / GamePanel.SCALE));
		g.drawString("GAME OVER", GamePanel.WIDTH / 2 - (int)(g.getFontMetrics().getStringBounds("GAME OVER", g).getWidth() / 2), GamePanel.HEIGHT / 3);
		textTransparency = Math.min(textTransparency + textTransparencyIncrement, 255);
	}
}
