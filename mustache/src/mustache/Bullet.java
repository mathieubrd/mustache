package mustache;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
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
	private boolean byMustache;
	
	public Bullet(Game game, Mustache mustache, float angle, float x, float y, boolean byMustache)
	{
		this.mustache = mustache;
		vector = new Vector2f(angle+90);
		
		if (byMustache) speed = 1;
		else speed  = 0.5;
		this.x = x;
		this.y = y;
		width = 8;
		height = 8;
		timeCreation = System.currentTimeMillis();
		hitbox = new Rectangle(x, y, width, height);
		this.angle = angle;
		this.byMustache = byMustache;
		
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
		bullet.draw(x, y);
		bullet.setRotation(this.angle);
	}
	
	public void update(Game game, int delta)
	{
		x -= (float) (vector.getX()*speed*delta);
		y -= (float) (vector.getY()*speed*delta);
		
		// Update hitbox
		hitbox.setX(x);
		hitbox.setY(y);
		
		// Collision avec monstre si by mustache
		if (byMustache)
		{
			for (Monster m:game.getMonsters())
			{
				if (m.getHitbox().intersects(hitbox))
				{
					mustache.destroyBullet(this);
					game.destroyMonster(m);
				}
			}
		}
	}
	
	public long getTimeCreation() { return timeCreation; }
	
	public Rectangle getHitbox() {
		return hitbox;
	}
}
