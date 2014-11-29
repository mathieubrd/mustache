package mustache;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Game extends BasicGame
{
	private Map      map;
	private Mustache mustache;
	private Monster monster;
	private Sound   sound;
	
	public Game(String title)
	{
		super(title);
	}

	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		map.render(g);
		g.drawImage(mustache.getMustache(), mustache.getX(), mustache.getY());
		monster.render();
	}

	public void init(GameContainer gc) throws SlickException
	{
		map = new Map();
		mustache = new Mustache(gc.getWidth(), gc.getHeight());
		monster = new Monster();
		sound = new Sound("res/sound/piou.ogg");
		
		map.init();
		mustache.init();
		
		sound.play();
	}

	public void update(GameContainer gc, int delta) throws SlickException
	{
		mustache.update(gc, delta);
		monster.update(gc, delta, mustache.getX(), mustache.getY());
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
