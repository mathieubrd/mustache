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
	private int   width;
	private int   height;
	private Image mustache;
	private boolean moving;
	private double speed;
	
	public Mustache(int w, int h) {
		this.width  = w;
		this.height = h;
	}
	
	public void init()
	{
		this.moving = false;
				
		this.speed = 0.2;
		
		x = this.width/2 - 90;
		y = this.height/2 - 90;
		
		try
		{
			mustache = new Image("res/sprites/mustache.jpg");
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
		g.drawImage(getMustache(), getX(), getY());
	}
	
	public Image getMustache() {
		return this.mustache;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public boolean getMoving() {
		return this.moving;
	}
}
