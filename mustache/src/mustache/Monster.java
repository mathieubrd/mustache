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
	private Game game;
	
	public Monster(Game game, float x, float y)
	{
		try
		{
			this.x = x;
			this.y = y;
			sprite = new Image("res/ia.png");
			width = sprite.getWidth();
			height = sprite.getHeight();
			hitbox = new Rectangle(x, y, width, height);
			this.game = game;
			
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

	public void update(GameContainer gc, int delta, float persoX, float persoY)
	{
		if (persoX > getX()) deplacer('E', delta);
		else deplacer('O', delta);
		
		if (persoY > getY()) deplacer('S', delta);
		else deplacer('N', delta);
		
		// Update hitbox
		hitbox.setX(x);
		hitbox.setY(y);
	}
}
