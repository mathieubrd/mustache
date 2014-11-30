package mustache;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Monster
{
	private float x;
	private float y;
	private double speed;
	private SpriteSheet deadSprite;
	private SpriteSheet sprite;
	private Animation anim;
	private Animation deadAnim;
	private int width;
	private int height;
	private Rectangle hitbox;
	private float persoX;
	private float persoY;
	private Game game;
	private boolean isDead;
	
	public Monster(Game game, float x, float y)
	{
		try
		{
			this.x = x;
			this.y = y;
			sprite = new SpriteSheet("res/sprites/shears.png", 64, 64);
			deadSprite = new SpriteSheet("res/sprites/dead_shears.png", 64, 64);
			anim = new Animation(sprite, 200);
			deadAnim = new Animation(deadSprite, 100);
			width = sprite.getSprite(0, 0).getWidth();
			height = sprite.getSprite(0, 0).getHeight();
			hitbox = new Rectangle(x, y, width, height);
			this.game = game;
			isDead = false;
			
			deadAnim.setLooping(false);
		}catch (SlickException e)
		{
			e.printStackTrace();
		}

		speed = 0.01+(Math.random()*(0.15-0.01));
	}
	
	public void rotate()
	{
		double rotation;
		
		rotation = Math.atan2(persoY-y, persoX-x);
		rotation = Math.toDegrees(rotation);
		
		anim.getCurrentFrame().setRotation((float) rotation);
		
		// Rotate la frame apres la frame courante pour eviter le clignotement
		if (anim.getFrame() == anim.getFrameCount()-1)
			anim.getImage(0).setRotation((float) rotation);
		else
			anim.getImage(anim.getFrame()+1).setRotation((float) rotation);
	}
	
	public void render(GameContainer gc, Graphics g)
	{
		if (!isDead) anim.draw(x ,y);
		else
		{
			deadAnim.draw(x, y);
			
			if (deadAnim.getFrame() == deadAnim.getFrameCount()-1)
				game.removeMonster(this);
		}
	}
	
	public float getX()
	{
		return x+(width/2);
	}
	
	public float getY()
	{
		return y+(height/2);
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	public void deplacer(char dir, int delta)
	{
		switch (dir)
		{
			case 'N':
				y -= speed*delta;
				break;
			case 'S':
				y += speed*delta;
				break;
			case 'E':
				x += speed*delta;
				break;
			case 'O':
				x -= speed*delta;
				break;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void kill()
	{
		isDead = true;
		
		SoundEffect.play("mort", false, 1);
	}

	public void update(GameContainer gc, int delta, float persoX, float persoY)
	{
		if (!isDead)
		{
			if (persoX > getX()) deplacer('E', delta);
			else deplacer('O', delta);
			
			if (persoY > getY()) deplacer('S', delta);
			else deplacer('N', delta);
		}
		
		this.persoX = persoX;
		this.persoY = persoY;
		
		rotate();
		
		// Update hitbox
		hitbox.setX(x);
		hitbox.setY(y);
	}
}
