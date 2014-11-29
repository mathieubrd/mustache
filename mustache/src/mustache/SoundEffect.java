package mustache;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public abstract class SoundEffect
{
	public static void play(String name)
	{
		try
		{
			Sound sound = new Sound("res/sound/"+name+".ogg");
			
			sound.play();
		} catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
}
