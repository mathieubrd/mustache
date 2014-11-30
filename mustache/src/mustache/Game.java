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
	public static boolean menu = true;
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	private Mustache mustache;
	private ArrayList<Monster> monsters;
	private Sound   sound;
	private Image background;
	private ArrayList<Monster> monstersToRemove;
	private GameContainer gc;
	private String sentence = null;
	
	public Game(String title)
	{
		super(title);
	}

	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		int[] tabInt = Highscore.read();

		if(!Game.menu) {
			mustache.setHighscore(tabInt[0]);
			mustache.setHighwave(tabInt[1]);
			g.drawImage(background, 0, 0);
			g.setColor(Color.red);
			mustache.render(g);
			g.setColor(Color.white);
			
			for (Monster m:monsters) m.render(gc, g);
		}
		else {
			g.drawString("Highscore : " + tabInt[0] + ", wave : " + tabInt[1], gc.getWidth()/2-110,4);
			g.drawString("Echap pour quitter", 4,4);
			g.drawImage(new Image("res/sprites/title.png"), 0, 35);
			g.drawString("Pressed SPACE for play", gc.getWidth()/2-100, gc.getHeight()/2);
			if(this.sentence != null) g.drawString("" + this.sentence, gc.getWidth()/2-120, gc.getHeight()/2+20);
		}
	}

	public void init(GameContainer gc) throws SlickException
	{
		if(!Game.menu) {
			mustache = new Mustache();
			monsters = new ArrayList<Monster>();
			sound = new Sound("res/sound/piou.ogg");
			
			monstersToRemove = new ArrayList<Monster>();
			
			background = new Image("res/background.png");
			
			mustache.init(this, gc.getWidth()/2, gc.getHeight()/2);
			
			sound.play();
			
			createMonster(gc);
			
			// Son de fond
			SoundEffect.play("MusiqueLoop", true, 100);
		}
		else {
			this.gc = gc;
		}
	}
	
	public void createMonster(GameContainer gc) {
		for (int i = 0; i < mustache.getNbMonster(); i++)
		{
			int depX = 0;
			int depY = 0;
			
			while(depX > -10 && depX < gc.getWidth() + 10) {
				depX = (int) (-100 + (Math.random()*((gc.getWidth()+300)-100)));
			} 
			
			depY = (int)(-100 + (Math.random() * (gc.getHeight()+300)-100));
		
			System.out.println(depX + " " + depY);

			monsters.add(new Shears(depX, depY, this));
		}
	}
	
	public ArrayList<Monster> getMonsters()
	{
		return monsters;
	}
	
	public void destroyMonster(Monster monster)
	{
		monster.kill();
	}

	public void removeMonster(Monster monster)
	{
		monstersToRemove.add(monster);
	}
	
	public Mustache getMustache() {
		return this.mustache;
	}
	
	public void keyPressed(int key, char c) {
		if(Game.menu && key == Input.KEY_SPACE) { 	
			Game.menu = false;
			try {
				this.init(gc);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
	
	public GameContainer getGc() {
		return gc;
	}
	
	public void setMenu() {
		Game.menu = true;
	}

	public void setSentence(String str) {
		this.sentence = str;
	}
	
	public void update(GameContainer gc, int delta) throws SlickException
	{
		if(!Game.menu) {
				
			mustache.update(gc, delta);
			
			for (Monster m:monsters) {
				m.update(gc, delta, mustache);
				
				mustache.collision(m.getHitbox());
			}
			
			for(Monster m : monstersToRemove)
			{
				monsters.remove(m);
			}
			
			if(getMonsters().size()<1) {
				createMonster(gc);
				mustache.setWave();
				mustache.setNbMonster();
				mustache.setScore(100);
			}
		}
		
		Input key = gc.getInput();
		if (key.isKeyPressed(Input.KEY_ESCAPE)) gc.exit();
	}

	public static void main(String[] args)
	{
		Game game = new Game("Mustache Invaders");
		
		try
		{
			AppGameContainer agc = new AppGameContainer(game);
			
			agc.setShowFPS(false);
			agc.setDisplayMode(Game.WIDTH, Game.HEIGHT, false);
			agc.setTargetFrameRate(60);
			agc.start();
		}
		
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
}
