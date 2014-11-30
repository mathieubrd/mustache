package mustache;

import org.newdawn.slick.Graphics;
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
	
	public Bullet(Mustache mustache, float angle, float x, float y)
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
	}
	
	public void render(Graphics g)
	{
		g.fillRect(x, y, width, height);
		
		// Rendu de la hitbox
		g.drawRect(hitbox.getX(), hitbox.getY(), hitbox.getWidth(), hitbox.getHeight());
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
				mustache.destroyBullet(this);
		}
	}
	
	public long getTimeCreation() { return timeCreation; }
}
