package mustache;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Bullet
{
	private Mustache mustache;
	private Vector2f vector;
	private float    x;
	private float    y;
	private double   speed;
	private long     timeCreation;
	
	public Bullet(Mustache mustache, float angle, float x, float y)
	{
		this.mustache = mustache;
		vector = new Vector2f(angle+90);
		speed  = 1;
		this.x = x;
		this.y = y;
		timeCreation = System.currentTimeMillis();
	}
	
	public void render(Graphics g)
	{
		g.fillRect(x, y, 16, 16);
	}
	
	public void update(int delta)
	{
		x -= (float) (vector.getX()*speed*delta);
		y -= (float) (vector.getY()*speed*delta);
		
		long currentTime = System.currentTimeMillis();
	}
	
	public long getTimeCreation() { return timeCreation; }
}
