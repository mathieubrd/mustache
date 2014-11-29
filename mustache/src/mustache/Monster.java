package mustache;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
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
		
		speed = 0.2;
	}
	
	public void deplacer(char dir, int delta)
	{
		switch (dir)
		{
			case 'N': y -= speed * delta; break;
			case 'S': y += speed * delta; break;
			case 'E': x += speed * delta; break;
			case 'O': x -= speed * delta; break;
		}
	}
	
	public void render()
	{
		sprite.draw(x ,y);
	}
	
	public void update(GameContainer gc, int delta)
	{
		Input key = gc.getInput();
		
		if (key.isKeyDown(Input.KEY_UP)) deplacer('N', delta);
		else if (key.isKeyDown(Input.KEY_DOWN)) deplacer('S', delta);
		else if (key.isKeyDown(Input.KEY_LEFT)) deplacer('O', delta);
		else if (key.isKeyDown(Input.KEY_RIGHT)) deplacer('E', delta);
	}
}
