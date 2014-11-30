package mustache;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Monster
{
	private float x;
	private float y;
	private double speed;
	private Image sprite;
	private int width;
	private int height;
	private Rectangle hitbox;
	
	public Monster()
	{
		try
		{
			sprite = new Image("res/ia.png");
			width = sprite.getWidth();
			height = sprite.getHeight();
			hitbox = new Rectangle(x, y, width, height);
			
		}catch (SlickException e)
		{
			e.printStackTrace();
		}
		
		speed = 0.07;
	}
	
	public void render(GameContainer gc, Graphics g)
	{
		sprite.draw(x ,y);
		
		// Rendu de la hitbox
		g.drawRect(hitbox.getX(), hitbox.getY(), hitbox.getWidth(), hitbox.getHeight());
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

	public void update(GameContainer gc, int delta, float persoX, float persoY)
	{
		if (persoX > getX()) x += speed*delta;
		else x -= speed*delta;
		
		if (persoY > getY()) y += speed*delta;
		else y -= speed*delta;
		
		// Update hitbox
		hitbox.setX(x);
		hitbox.setY(y);
	}
}
