package mustache;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Bullet
{
	private Mustache mustache;
	private Vector2f vector;
	private float    x;
	private float    y;
	private int width;
	private int height;
	private double   speed;
	private long     timeCreation;
	private Rectangle hitbox;
	private Image bullet;
	private float angle;
	private Game game;
	
	public Bullet(Game game, Mustache mustache, float angle, float x, float y)
	{
		this.mustache = mustache;
		vector = new Vector2f(angle+90);
		speed  = 1;
		this.x = x;
		this.y = y;
		width = 8;
		height = 8;
		timeCreation = System.currentTimeMillis();
		hitbox = new Rectangle(x, y, width, height);
		this.angle = angle;
		
		try
		{
			bullet = new Image("res/sprites/bulletMustache.png");
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g)
	{
		g.drawImage(bullet, hitbox.getX()-width/2, hitbox.getY()-height/2);
		
		bullet.setRotation(this.angle);
	}
	
	public void update(Game game, int delta)
	{
		x -= (float) (vector.getX()*speed*delta);
		y -= (float) (vector.getY()*speed*delta);
		
		// Update hitbox
		hitbox.setX(x);
		hitbox.setY(y);
		
		// Collision avec monstre
		for (Monster m:game.getMonsters())
		{
			if (m.getHitbox().intersects(hitbox))
			{
				mustache.destroyBullet(this);
				game.destroyMonster(m);
			}
		}
	}
	
	public long getTimeCreation() { return timeCreation; }
	
	public Rectangle getHitbox() {
		return hitbox;
	}
}
