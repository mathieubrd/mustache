package mustache;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends BasicGameState
{	
	public static int ID;
	
	private Mustache mustache;
	private ArrayList<Monster> monsters;
	private Sound   sound;
	private Image background;
	private ArrayList<Monster> monstersToRemove;
	
	public Game(int id)
	{
		Game.ID = id;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
	{
		g.drawImage(background, 0, 0);
		g.setColor(Color.red);
		mustache.render(g);
		g.setColor(Color.white);
		
		for (Monster m:monsters) m.render(gc, g);
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		mustache = new Mustache();
		monsters = new ArrayList<Monster>();
		sound = new Sound("res/sound/piou.ogg");
		
		monstersToRemove = new ArrayList<Monster>();
		
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
	
	public void destroyMonster(Monster monster)
	{
		monstersToRemove.add(monster);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
	{
		Input key = gc.getInput();
		
		if (key.isKeyPressed(Input.KEY_ESCAPE)) gc.exit();
		
		mustache.update(gc, delta);
		
		for (Monster m:monsters) {
			m.update(gc, delta, mustache.getX(), mustache.getY());
			mustache.collision(m.getHitbox());
		}
		
		for(Monster m : monstersToRemove) {
			monsters.remove(m);
		}
	}

	public int getID() {
		return Game.ID;
	}
}
