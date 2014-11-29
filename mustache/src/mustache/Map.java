package mustache;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Map {

	private Image background;
	
	public void init() {
		try
		{
			background = new Image("res/back/space.jpg");
		}
		
		catch (SlickException e)
		
		{
			e.printStackTrace();
		}
	}

	public void update(int delta)
	{
		
    }
	
    public void render(Graphics g)
    {
    	background.draw();
    }
}