package mustache;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public abstract class Monster {
	private float x;
	private float y;
	private double speed;
	private SpriteSheet sprite;
	private SpriteSheet deadSprite;
	private Animation anim;
	private Animation deadAnim;
	private float width;
	private float height;
	private boolean isDead;
	private Rectangle hitbox;
	private Mustache mustache;
	private Game game;

	protected Monster(float x, float y, Game game) {
		this.x = x;
		this.y = y;
		this.game = game;
		speed = 0.04+(Math.random()*(0.24-0.04));
	}
	
	public void deplacer(char dir, int delta)
	{
		switch (dir)
		{
			case 'N':
				y -= speed*delta;
				break;
			case 'S':
				y += speed*delta;
				break;
			case 'E':
				x += speed*delta;
				break;
			case 'O':
				x -= speed*delta;
				break;
		}
	}
	
	public void rotate()
	{
		double rotation;
		
		rotation = Math.atan2(mustache.getY()-y, mustache.getX()-x);
		rotation = Math.toDegrees(rotation);
		
		anim.getCurrentFrame().setRotation((float) rotation);
		
		// Rotate la frame apres la frame courante pour eviter le clignotement
		if (anim.getFrame() == anim.getFrameCount()-1)
			anim.getImage(0).setRotation((float) rotation);
		else
			anim.getImage(anim.getFrame()+1).setRotation((float) rotation);
	}
	
	public void kill()
	{
		isDead = true;
		
		SoundEffect.play("mort", false, (float)0.3);
	}
	
	public void update(GameContainer gc, int delta, Mustache mustache)
	{
		this.mustache = mustache;
		
		if (!isDead)
		{
			if (mustache.getX() > getX()) deplacer('E', delta);
			else deplacer('O', delta);
			
			if (mustache.getY() > getY()) deplacer('S', delta);
			else deplacer('N', delta);
		}
		
		rotate();
		
		if (!isDead)
		{
			hitbox.setX(x);
			hitbox.setY(y);
		}
		
		else
		{
			hitbox.setX(0);
			hitbox.setY(0);
		}
	}
	
	public void render(GameContainer gc, Graphics g)
	{
		if (isDead) {
			deadAnim.draw(x, y);
			
			if (deadAnim.getFrame() == deadAnim.getFrameCount()-1) {
				game.removeMonster(this);
				game.getMustache().setScore(10);
			}
		}
		else anim.draw(x ,y);
	}

	public float getX() {
		return x+width/2;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y+height/2;
	}

	public void setY(float y) {
		this.y = y;
	}

	public SpriteSheet getSprite() {
		return sprite;
	}

	public void setSprite(SpriteSheet sprite) {
		this.sprite = sprite;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}

	public void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public SpriteSheet getDeadSprite() {
		return deadSprite;
	}

	public void setDeadSprite(SpriteSheet deadSprite) {
		this.deadSprite = deadSprite;
	}

	public Animation getAnim() {
		return anim;
	}

	public void setAnim(Animation anim) {
		this.anim = anim;
	}

	public Animation getDeadAnim() {
		return deadAnim;
	}

	public void setDeadAnim(Animation deadAnim) {
		this.deadAnim = deadAnim;
	}
}
