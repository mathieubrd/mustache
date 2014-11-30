package mustache;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Mustache
{
	private float x;
	private float y;
	private boolean moving;
	private double speed;
	private int width;
	private int height;
	private double rotation;
	private ArrayList<Bullet> bullets;
	private ArrayList<Bullet> bulletsToRemove;
	private Animation anim;
	private SpriteSheet sprite;
	private Game game;
	private Rectangle hitbox;
	private int life;
	private boolean lose;
	private boolean isAtackable;
	private Long lastCurrentTime;
	private boolean bool;
	private Image shield;
	private int score;
	private int wave;
	private int nbMonster;
	
	private int highscore;
	private int highwave;
	
	public void init(Game game, float x, float y)
	{	
		this.speed = 0.35;
		this.life = 3;
		this.lose = false;
		this.isAtackable = true;
		this.lastCurrentTime = (long) 0;
		this.bool = false;
		this.wave = 1;
		this.score = 0;
		this.nbMonster = 10;
		this.highscore = 0;
		this.highwave = 0;
		
		try
		{
			sprite = new SpriteSheet("res/sprites/moustache.png", 64, 32);
			anim   = new Animation  (sprite, 200);
			hitbox = new Rectangle(x, y, width, height);
			shield = new Image("res/sprites/shield.png");
			
			this.x = x-(sprite.getWidth()/2);
			this.y = y-(sprite.getHeight()/2);
			
			width = sprite.getSprite(0, 0).getWidth();
			height = sprite.getSprite(0, 0).getHeight();
			
			bullets = new ArrayList<Bullet>();
			bulletsToRemove = new ArrayList<Bullet>();
			this.game = game;
		}
		
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
	
	public void rotate(int mouseX, int mouseY)
	{
		rotation = Math.atan2(mouseY-getMidY(), mouseX-getMidX());
		rotation = Math.toDegrees(rotation)+90;
		
		anim.getCurrentFrame().setRotation((float) rotation);
		
		// Rotate la frame apres la frame courante pour eviter le clignotement
		if (anim.getFrame() == anim.getFrameCount()-1)
			anim.getImage(0).setRotation((float) rotation);
		else
			anim.getImage(anim.getFrame()+1).setRotation((float) rotation);
			
	}
	
	public void deplacer(char dir, int delta)
	{
		switch (dir)
		{
			case 'N': 
				y -= speed * delta; 
				if(getMidY() < 0) y = 0 - height/2;
				break;
			case 'S': 
				y += speed * delta; 
				if(getMidY() > Game.HEIGHT) y = Game.HEIGHT - height/2;
				break;
			case 'E': 
				x += speed * delta; 
				if(getMidX() > Game.WIDTH) x = Game.WIDTH - width/2;
				break;
			case 'O': 
				x -= speed * delta; 
				if(getMidX() < 0) x = 0 - width/2;
				break;
		}
	}
	
	public void shoot()
	{
		bullets.add(new Bullet(game, this, (float) rotation, getMidX(), getMidY()));
		
		SoundEffect.play("piou", false, (float) 0.3);
	}
	
	public void update(GameContainer gc, int delta)
	{
		Input key = gc.getInput();
		long  currentTime = System.currentTimeMillis();
		
		int mouseX = key.getMouseX();
		int mouseY = key.getMouseY();
		
		rotate(mouseX, mouseY);
		
		if (key.isKeyDown(Input.KEY_UP) || key.isKeyDown(Input.KEY_Z) || key.isKeyDown(Input.KEY_W)) deplacer('N', delta);
		if (key.isKeyDown(Input.KEY_DOWN) || key.isKeyDown(Input.KEY_S)) deplacer('S', delta);
		if (key.isKeyDown(Input.KEY_LEFT) || key.isKeyDown(Input.KEY_Q) || key.isKeyDown(Input.KEY_A)) deplacer('O', delta);
		if (key.isKeyDown(Input.KEY_RIGHT) || key.isKeyDown(Input.KEY_D)) deplacer('E', delta);
		
		// Click souris
		if (key.isMousePressed(Input.MOUSE_LEFT_BUTTON)) shoot();
		
		for (Bullet b:bullets)
		{		
			b.update(game, delta);
			
			if (currentTime - b.getTimeCreation() >= 1000)
				bulletsToRemove.add(b);
		}
		
		for (Bullet b:bulletsToRemove)
			bullets.remove(b);
		
		hitbox.setX(x);
		hitbox.setY(y);
		
		if(this.life <= 0) {
			this.lose = true;
			this.life = 0;
		}
		
		if(this.highscore < this.score) this.highscore = this.score;
		if(this.highwave  < this.wave ) this.highwave = this.wave;
	}
	
	public void destroyBullet(Bullet bullet)
	{
		bulletsToRemove.add(bullet);
	}
	
	public void render(Graphics g) throws SlickException {
		if(this.lose) {
			Highscore.write(getHighscore(), gethighwave());
			game.setMenu();
			game.init(game.getGc());
			game.setSentence(this.score + " points, recommencez !");
		}
		
		anim.draw(x, y);
			
		// Ajout du shield
		if (!isAtackable)
			g.drawImage(shield, x, y-20);
		
		for (Bullet b:bullets)
			b.render(g);
		
		g.drawString("Vie   : " + life, 5, 10);
		g.drawString("Score : " + this.score, 5, 30);
		g.drawString("Vague : " + this.wave, 5, 50);
		g.drawString("Highscore : " + this.highscore + ", wave : " + this.highwave, Game.WIDTH/2-110,4);
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	public float getMidX() {
		return this.x+(width/2);
	}
	
	public float getMidY() {
		return this.y+(height/2);
	}
	
	public boolean getMoving() {
		return this.moving;
	}

	public void collision(Rectangle hbM) {
		Long currentTime = System.currentTimeMillis();
		
		if(isAtackable && bool)
		{
			this.life--;
			lastCurrentTime = currentTime;
			isAtackable = false;
		}
		else bool = hitbox.intersects(hbM);
		
		if(currentTime - lastCurrentTime >= 2000) {
			isAtackable = true;
		}
	}

	public void setScore(int s) {
		this.score += s;
	}

	public int getNbMonster() {
		return this.nbMonster;
	}
	
	public void setNbMonster() {
		this.nbMonster *= 1.2;
	}

	public void setWave() {
		this.wave += 1;
	}

	public void setHighscore(int h) {
		this.highscore = h;
	}

	public void setHighwave(int h) {
		this.highwave = h;
	}

	public int gethighwave() {
		return highwave;
	}

	public int getHighscore() {
		return highscore;
	}
	
	public float getX() { return x; }
	public float getY() { return y; }
}
