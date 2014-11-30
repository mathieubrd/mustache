package mustache;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Shears extends Monster
{
	protected Shears(float x, float y, Game game) {
		super(x, y, game);
		
		try {
			setSprite(new SpriteSheet("res/sprites/shears.png", 64, 64));
			setDeadSprite(new SpriteSheet("res/sprites/dead_shears.png", 64, 64));
			setAnim(new Animation(getSprite(), 200));
			setDeadAnim(new Animation(getDeadSprite(), 200));
			setHitbox(new Rectangle(x, y, 64, 64));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
}