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

	public void init(Game game, float x, float y)
	{	
		this.speed = 0.35;
		this.life = 4;
		this.lose = false;
		this.isAtackable = true;
		this.lastCurrentTime = (long) 0;
		this.bool = false;
		
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
		rotation = Math.atan2(mouseY-getY(), mouseX-getX());
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
				if(getY() < 0) y = 0 - height/2;
				break;
			case 'S': 
				y += speed * delta; 
				if(getY() > Game.HEIGHT) y = Game.HEIGHT - height/2;
				break;
			case 'E': 
				x += speed * delta; 
				if(getX() > Game.WIDTH) x = Game.WIDTH - width/2;
				break;
			case 'O': 
				x -= speed * delta; 
				if(getX() < 0) x = 0 - width/2;
				break;
		}
	}
	
	public void shoot()
	{
		bullets.add(new Bullet(game, this, (float) rotation, getX(), getY()));
		
		SoundEffect.play("piou", false, (float) 0.4);
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
	}
	
	public void destroyBullet(Bullet bullet)
	{
		bulletsToRemove.add(bullet);
	}
	
	public void render(Graphics g) {
		anim.draw(x, y);
		g.drawString("Instance bullets "+bullets.size(), 10, 25);
		
		for (Bullet b:bullets)
			b.render(g);
		
		g.drawString("" + life, 100, 90);
		if(this.lose) g.drawString("LOSER", 100,100);
		
		// Ajout du shield
		if (!isAtackable)
			g.drawImage(shield, x, y-20);
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	public float getX() {
		return this.x+(width/2);
	}
	
	public float getY() {
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
}
