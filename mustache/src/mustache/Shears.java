package mustache;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Shears extends Monster
{
	protected Shears(float x, float y) {
		super(x, y);
		
		try {
			SpriteSheet sprite = new SpriteSheet("res/sprites/shears.png", 64, 64);
			
			setSprite(sprite);
			setWidth(sprite.getWidth());
			setHeight(sprite.getHeight());
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
}