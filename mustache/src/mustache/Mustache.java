import org.newdawn.slick.SpriteSheet;

ackage mustache;

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
		
		SpriteSheet spriteSheet = new SpriteSheet("res/sprites/mustache.png", 64, 64);
	}
}
