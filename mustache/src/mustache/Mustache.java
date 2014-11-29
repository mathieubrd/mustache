package mustache;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class Mustache
{
	private float x;
	private float y;
	private int   largeur;
	private int   hauteur;
	private SpriteSheet sprite;
	
	public void init()
	{
		x = 0;
		y = 0;
		
		try
		{
			SpriteSheet spriteSheet = new SpriteSheet("res/sprites/mustache.png", 64, 64);
		}
		
		catch (SlickException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
