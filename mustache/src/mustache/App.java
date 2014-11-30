package mustache;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class App extends StateBasedGame{

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	private static final int MENU = 0;
	private static final int JEUX = 1;
	
	public App(String title) {
		super(title);
	}

	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new Menu(MENU));
		this.addState(new Game(JEUX));
	}
	
	public static void main(String[] args)
	{
		try
		{
			AppGameContainer app = new AppGameContainer(new App("Mustache Invaders"));
			
			app.setDisplayMode(App.WIDTH, App.HEIGHT, false);
			app.setTargetFrameRate(60);
			app.start();
		}
		
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}

}
