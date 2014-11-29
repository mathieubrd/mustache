package mustache;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.Animation;

public class Mustache
{
	private float x;
	private float y;
	private Image mustache;
	private boolean moving;
	private double speed;
	private int width;
	private int height;
	
	public void init(float x, float y)
	{	
		speed = 0.2;
		
		try
		{
			mustache = new Image("res/sprites/mustache.jpg");
			
			this.x = x-(mustache.getWidth()/2);
			this.y = y-(mustache.getHeight()/2);
			
			width = mustache.getWidth();
			height = mustache.getHeight();
		}
		
		catch (SlickException e)
		{
			e.printStackTrace();
		}
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
	
	public void update(GameContainer gc, int delta)
	{
		Input key = gc.getInput();
		
		int mouseX = key.getMouseX();
		int mouseY = key.getMouseY();
		
		if (key.isKeyDown(Input.KEY_UP)) deplacer('N', delta);
		if (key.isKeyDown(Input.KEY_DOWN)) deplacer('S', delta);
		if (key.isKeyDown(Input.KEY_LEFT)) deplacer('O', delta);
		if (key.isKeyDown(Input.KEY_RIGHT)) deplacer('E', delta);
		
	}
	
	public void render(Graphics g) {
		g.drawImage(getMustache(), x, y);
	}
	
	public Image getMustache() {
		return this.mustache;
	}
	
	public float getX() {
		return this.x+(width/2);
	}
	
	public float getY() {
		return this.y+(height/2);
	}
	
	public boolean getMoving() {
		return this.moving;
	}
}
