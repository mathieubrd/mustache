package mustache;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame
{
	private Map map;
	
	public Game(String title)
	{
		super(title);
	}

	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		map.render(g);
	}

	public void init(GameContainer gc) throws SlickException
	{
		map = new Map();
		
		map.init();
	}

	public void update(GameContainer gc, int delta) throws SlickException
	{
		
	}
	
	public static void main(String[] args)
	{
		Game game = new Game("Mustache Invaders");
		
		try
		{
			AppGameContainer agc = new AppGameContainer(game);
			
			agc.setDisplayMode(800, 600, false);
			agc.start();
		}
		
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
}
