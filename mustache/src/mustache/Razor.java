package mustache;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Razor extends Monster
{
	protected Razor(float x, float y, Game game) {
		super(x, y, game);
		
		try {
			setSpeed(0.05+(Math.random()*(0.09-0.05)));
			setSprite(new SpriteSheet("res/sprites/razor.png", 16, 48));
			setDeadSprite(new SpriteSheet("res/sprites/dead_razor.png", 16, 48));
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
			if (getX() < mustache.getX() && getDistanceX() > 200)
				deplacer('E', delta);
			
			if (getX() > mustache.getX() && getDistanceX() > 200)
				deplacer('O', delta);
			
			if (getY() < mustache.getY() && getDistanceY() > 200)
				deplacer('S', delta);
			
			if (getY() > mustache.getY() && getDistanceY() > 200)
				deplacer('N', delta);
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
		rotation = Math.toDegrees(rotation)+90;
		
		getAnim().getCurrentFrame().setRotation((float) rotation);
		
		// Rotate la frame apres la frame courante pour eviter le clignotement
		if (getAnim().getFrame() == getAnim().getFrameCount()-1)
			getAnim().getImage(0).setRotation((float) rotation);
		else
			getAnim().getImage(getAnim().getFrame()+1).setRotation((float) rotation);
	}
}