package mustache;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState {

    public int id = 0;
    private StateBasedGame game;

    public Menu(int id) {
    	this.id = id;
    }
    
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = game;
        
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException {
        g.drawString("Appuyer sur une touche", 300, 300);
    }

    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException {
    }

    public void keyReleased(int key, char c) {
        game.enterState(Game.ID);
    }

	public int getID() {
		return this.id;
	}
}