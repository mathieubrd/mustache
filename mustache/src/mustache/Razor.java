package mustache;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Razor extends Monster
{
	private ArrayList<Bullet> bullets;
	private ArrayList<Bullet> bulletsToRemove;
	private long lastShoot;
	
	protected Razor(float x, float y, Game game) {
		super(x, y, game);
		
		try {
			setSpeed(0.05+(Math.random()*(0.09-0.05)));
			setSprite(new SpriteSheet("res/sprites/razor.png", 16, 48));
			setDeadSprite(new SpriteSheet("res/sprites/dead_razor.png", 16, 48));
			setAnim(new Animation(getSprite(), 200));
			setDeadAnim(new Animation(getDeadSprite(), 200));
			bullets = new ArrayList<Bullet>();
			bulletsToRemove = new ArrayList<Bullet>(); 
			setHitbox(new Rectangle(x, y, 64, 64));
			lastShoot = System.currentTimeMillis();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void update(GameContainer gc, int delta, Mustache mustache)
	{
		setMustache(mustache);
		
		if (!getIsDead())
		{
			if (getX() < mustache.getX() && getDistanceX() > 200)
				deplacer('E', delta);
			
			if (getX() > mustache.getX() && getDistanceX() > 200)
				deplacer('O', delta);
			
			if (getY() < mustache.getY() && getDistanceY() > 200)
				deplacer('S', delta);
			
			if (getY() > mustache.getY() && getDistanceY() > 200)
				deplacer('N', delta);
		}
		
		for (Bullet b:bullets) {
			b.update(getGame(), delta);
			
			if (b.getHitbox().intersects(getMustache().getHitbox()))
			{
				bulletsToRemove.add(b);
				mustache.collision(b.getHitbox());
			}
		}
		
		for (Bullet b:bulletsToRemove)
			bullets.remove(b);
		
		rotate();
		
		if (!getIsDead())
		{
			getHitbox().setX(getX());
			getHitbox().setY(getY());
		}
		
		else
		{
			getHitbox().setX(0);
			getHitbox().setY(0);
		}
		
		// Tirer
		long currentTime = System.currentTimeMillis();
		
		if (currentTime-lastShoot >= 2000)
		{
			shoot();
			lastShoot = currentTime;
		}
	}
	
	public void render(GameContainer gc, Graphics g)
	{
		if (getIsDead()) {
			getDeadAnim().draw(getX(), getY());
			
			if (getDeadAnim().getFrame() == getDeadAnim().getFrameCount()-1) {
				getGame().removeMonster(this);
				getGame().getMustache().setScore(10);
			}
		}
		else getAnim().draw(getX(), getY());
		
		for (Bullet b:bullets) b.render(g);
	}
	
	public void shoot()
	{
		if (!getIsDead())
			bullets.add(new Bullet(getGame(), getMustache(), getRotation(), getX(), getY(), false));
	}
	
	public void rotate()
	{
		double rotation;
		
		rotation = Math.atan2(getMustache().getY()-getY(), getMustache().getX()-getX());
		rotation = Math.toDegrees(rotation)+90;
		
		setRotation((float) rotation);
		
		getAnim().getCurrentFrame().setRotation((float) rotation);
		
		// Rotate la frame apres la frame courante pour eviter le clignotement
		if (getAnim().getFrame() == getAnim().getFrameCount()-1)
			getAnim().getImage(0).setRotation((float) rotation);
		else
			getAnim().getImage(getAnim().getFrame()+1).setRotation((float) rotation);
	}
}