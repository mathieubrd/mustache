package mustache;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Mustache
{
	private float x;
	private float y;
	private Image mustache;
	private boolean moving;
	private double speed;
	private int width;
	private int height;
	private double rotation;
	private ArrayList<Bullet> bullets;
	
	public void init(float x, float y)
	{	
		speed = 0.35;
		
		try
		{
			mustache = new Image("res/sprites/mustache.jpg");
			
			this.x = x-(mustache.getWidth()/2);
			this.y = y-(mustache.getHeight()/2);
			
			width = mustache.getWidth();
			height = mustache.getHeight();
			
			bullets = new ArrayList<Bullet>();
		}
		
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
	
	public void rotate(int mouseX, int mouseY)
	{
		rotation = Math.atan2(mouseY-getY(), mouseX-getX());
		rotation = Math.toDegrees(rotation)+90;
		
		mustache.setRotation((float) rotation);
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
	
	public void tirer()
	{
		bullets.add(new Bullet(this, (float) rotation, getX(), getY()));
	}
	
	public void update(GameContainer gc, int delta)
	{
		Input key = gc.getInput();
		long  currentTime = System.currentTimeMillis();
		ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
		
		int mouseX = key.getMouseX();
		int mouseY = key.getMouseY();
		
		if (key.isKeyDown(Input.KEY_UP) || key.isKeyDown(Input.KEY_Z) || key.isKeyDown(Input.KEY_W)) deplacer('N', delta);
		if (key.isKeyDown(Input.KEY_DOWN) || key.isKeyDown(Input.KEY_S)) deplacer('S', delta);
		if (key.isKeyDown(Input.KEY_LEFT) || key.isKeyDown(Input.KEY_Q) || key.isKeyDown(Input.KEY_A)) deplacer('O', delta);
		if (key.isKeyDown(Input.KEY_RIGHT) || key.isKeyDown(Input.KEY_D)) deplacer('E', delta);
		
		// Click souris
		if (key.isMousePressed(Input.MOUSE_LEFT_BUTTON)) tirer();
		
		rotate(mouseX, mouseY);
		
		for (Bullet b:bullets)
		{		
			b.update(delta);
			
			if (currentTime - b.getTimeCreation() >= 1000)
				bulletsToRemove.add(b);
		}
		
		for (Bullet b:bulletsToRemove)
			bullets.remove(b);
	}
	
	public void destroyBullet(Bullet bullet)
	{
		bullets.remove(bullet);
	}
	
	public void render(Graphics g) {
		g.drawImage(getMustache(), x, y);
		g.drawString("Instance bullets "+bullets.size(), 10, 25);
		
		for (Bullet b:bullets)
			b.render(g);
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
