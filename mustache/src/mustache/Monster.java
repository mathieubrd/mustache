package mustache;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Monster
{
	private float x;
	private float y;
	private double vitesse;
	private Image sprite;
	
	public Monster()
	{
		try {
			sprite = new Image("res/ia.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		vitesse = 0.2;
	}
	
	public void deplacer(char dir, int delta)
	{
		switch (dir)
		{
			case 'N': y -= vitesse * delta; break;
			case 'S': y += vitesse * delta; break;
			case 'E': x += vitesse * delta; break;
			case 'O': x -= vitesse * delta; break;
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
