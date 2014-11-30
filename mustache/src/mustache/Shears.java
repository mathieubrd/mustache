package mustache;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Shears extends Monster
{
	protected Shears(float x, float y, Game game) {
		super(x, y, game);
		
		try {
			setSpeed(0.04+(Math.random()*(0.24-0.04)));
			setSprite(new SpriteSheet("res/sprites/shears.png", 64, 64));
			setDeadSprite(new SpriteSheet("res/sprites/dead_shears.png", 64, 64));
			setAnim(new Animation(getSprite(), 200));
			setDeadAnim(new Animation(getDeadSprite(), 200));
			setHitbox(new Rectangle(x, y, 64, 64));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void update(GameContainer gc, int delta, Mustache mustache)
	{
		setMustache(mustache);
		
		if (!getIsDead())
		{
			if (mustache.getX() > getX()) deplacer('E', delta);
			else deplacer('O', delta);
			
			if (mustache.getY() > getY()) deplacer('S', delta);
			else deplacer('N', delta);
		}
		
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
	}
	
	public void rotate()
	{
		double rotation;
		
		rotation = Math.atan2(getMustache().getY()-getY(), getMustache().getX()-getX());
		rotation = Math.toDegrees(rotation);
		
		getAnim().getCurrentFrame().setRotation((float) rotation);
		
		// Rotate la frame apres la frame courante pour eviter le clignotement
		if (getAnim().getFrame() == getAnim().getFrameCount()-1)
			getAnim().getImage(0).setRotation((float) rotation);
		else
			getAnim().getImage(getAnim().getFrame()+1).setRotation((float) rotation);
	}
}