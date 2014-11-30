package mustache;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public abstract class SoundEffect
{
	public static void play(String name, boolean loop, float volume)
	{
		try
		{
			Sound sound = new Sound("res/sound/"+name+".ogg");
			
			sound.play(1, volume);
			
			if (loop) sound.loop();
		} catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
}
