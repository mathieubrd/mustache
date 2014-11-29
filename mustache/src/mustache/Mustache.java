package mustache;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Mustache
{
	private float x;
	private float y;
	private boolean moving;
	private double speed;
	private int width;
	private int height;
	private double rotation;
	private ArrayList<Bullet> bullets;
	private Animation anim;
	private SpriteSheet sprite;

	public void init(float x, float y)
	{	
		speed = 0.35;
		
		try
		{
			sprite = new SpriteSheet("res/sprites/moustache.png", 64, 32);
			anim   = new Animation  (sprite, 200);
			
			this.x = x-(sprite.getWidth()/2);
			this.y = y-(sprite.getHeight()/2);
			
			width = sprite.getSprite(0, 0).getWidth();
			height = sprite.getSprite(0, 0).getHeight();
			
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
		
		anim.getCurrentFrame().setRotation((float) rotation);
		
		// Rotate la frame apres la frame courante pour eviter le clignotement
		if (anim.getFrame() == anim.getFrameCount()-1)
			anim.getImage(0).setRotation((float) rotation);
		else
			anim.getImage(anim.getFrame()+1).setRotation((float) rotation);
			
	}
	
	public void deplacer(char dir, int delta)
	{
		switch (dir)
		{
			case 'N': 
				y -= speed * delta; 
				if(getY() < 0) y = 0 - height/2;
				break;
			case 'S': 
				y += speed * delta; 
				if(getY() > Game.HEIGHT) y = Game.HEIGHT - height/2;
				break;
			case 'E': 
				x += speed * delta; 
				if(getX() > Game.WIDTH) x = Game.WIDTH - width/2;
				break;
			case 'O': 
				x -= speed * delta; 
				if(getX() < 0) x = 0 - width/2;
				break;
		}
	}
	
	public void shoot()
	{
		bullets.add(new Bullet(this, (float) rotation, getX(), getY()));
		
		SoundEffect.play("piou");
	}
	
	public void update(GameContainer gc, int delta)
	{
		Input key = gc.getInput();
		long  currentTime = System.currentTimeMillis();
		ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
		
		int mouseX = key.getMouseX();
		int mouseY = key.getMouseY();
		
		rotate(mouseX, mouseY);
		
		if (key.isKeyDown(Input.KEY_UP) || key.isKeyDown(Input.KEY_Z) || key.isKeyDown(Input.KEY_W)) deplacer('N', delta);
		if (key.isKeyDown(Input.KEY_DOWN) || key.isKeyDown(Input.KEY_S)) deplacer('S', delta);
		if (key.isKeyDown(Input.KEY_LEFT) || key.isKeyDown(Input.KEY_Q) || key.isKeyDown(Input.KEY_A)) deplacer('O', delta);
		if (key.isKeyDown(Input.KEY_RIGHT) || key.isKeyDown(Input.KEY_D)) deplacer('E', delta);
		
		// Click souris
		if (key.isMousePressed(Input.MOUSE_LEFT_BUTTON)) shoot();
		
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
		anim.draw(x, y);
		g.drawString("Instance bullets "+bullets.size(), 10, 25);
		
		for (Bullet b:bullets)
			b.render(g);
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
