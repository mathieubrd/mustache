package mustache;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
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
		mustache.render(g);
		monster.render();
	}

	public void init(GameContainer gc) throws SlickException
	{
		map = new Map();
		mustache = new Mustache();
		monster = new Monster();
		sound = new Sound("res/sound/piou.ogg");
		
		map.init();
		mustache.init(gc.getWidth()/2, gc.getHeight()/2);
		
		sound.play();
	}

	public void update(GameContainer gc, int delta) throws SlickException
	{
		mustache.update(gc, delta);
		monster.update(gc, delta, mustache.getX(), mustache.getY());
	}
	
	public void keyReleased(int key, char c) {
		if(key == Input.KEY_UP    || key == Input.KEY_Z || key == Input.KEY_W) c = 'N';
		if(key == Input.KEY_RIGHT || key == Input.KEY_D || key == Input.KEY_A) c = 'E';
		if(key == Input.KEY_LEFT  || key == Input.KEY_Q) c = 'O';
		if(key == Input.KEY_DOWN  || key == Input.KEY_S) c = 'S';
		
		mustache.inertie(c);
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
