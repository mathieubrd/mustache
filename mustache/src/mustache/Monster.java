package mustache;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Monster
{
	private float x;
	private float y;
	private double speed;
	private Image sprite;
	
	public Monster()
	{
		try {
			sprite = new Image("res/ia.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		speed = 0.1;
	}
	
	public void render()
	{
		sprite.draw(x ,y);
	}
	
	public void update(GameContainer gc, int delta, float persoX, float persoY)
	{
		if (persoX > x) x += speed*delta;
		else x -= speed*delta;
		
		if (persoY > y) y += speed*delta;
		else y -= speed*delta;
	}
}
