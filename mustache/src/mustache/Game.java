package mustache;

import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Game extends BasicGame
{
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	private Mustache mustache;
	private ArrayList<Monster> monsters;
	private Sound   sound;
	private Image background;
	
	public Game(String title)
	{
		super(title);
	}

	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		g.drawImage(background, 0, 0);
		g.setColor(Color.red);
		mustache.render(g);
		g.setColor(Color.white);
		
		for (Monster m:monsters) m.render(gc, g);
	}

	public void init(GameContainer gc) throws SlickException
	{
		mustache = new Mustache();
		monsters = new ArrayList<Monster>();
		sound = new Sound("res/sound/piou.ogg");
		
		background = new Image("res/background.png");
		
		mustache.init(this, gc.getWidth()/2, gc.getHeight()/2);
		
		sound.play();
		
		for (int i = 0; i < 10; i++)
		{
			int depX = (int)(Math.random() * (gc.getWidth()-0)) + 0;
			int depY = (int)(Math.random() * (gc.getHeight()-0)) + 0;
		
			monsters.add(new Monster(this,depX, depY));
		}
		
		// Son de fond
		SoundEffect.play("MusiqueLoop", true, 100);
	}
	
	public ArrayList<Monster> getMonsters()
	{
		return monsters;
	}

	public void update(GameContainer gc, int delta) throws SlickException
	{
		Input key = gc.getInput();
		
		if (key.isKeyPressed(Input.KEY_ESCAPE)) gc.exit();
		
		mustache.update(gc, delta);
		
		for (Monster m:monsters) {
			m.update(gc, delta, mustache.getX(), mustache.getY());
			mustache.collision(m.getHitbox());
		}
	}
	
	public static void main(String[] args)
	{
		Game game = new Game("Mustache Invaders");
		
		try
		{
			AppGameContainer agc = new AppGameContainer(game);
			
			agc.setDisplayMode(Game.WIDTH, Game.HEIGHT, false);
			agc.start();
		}
		
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
}
