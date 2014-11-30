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
	private float rotation;
	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	private Game game;

	protected Monster(float x, float y, Game game) {
		this.x = x;
		this.y = y;
		this.game = game;
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
	
	public abstract void rotate();
	
	public void kill()
	{
		isDead = true;
		
		SoundEffect.play("mort", false, (float)0.9);
	}
	
	public abstract void render(GameContainer gc, Graphics g);
	
	public int getDistanceX()
	{
		return (int) Math.abs(getX()-mustache.getX());
	}
	
	public int getDistanceY()
	{
		return (int) Math.abs(getY()-mustache.getY());
	}
	
	public abstract void update(GameContainer gc, int delta, Mustache mustache);

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

	public void setMustache(Mustache mustache) {
		this.mustache = mustache;
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

	public Mustache getMustache() {
		return mustache;
	}
	
	public void setIsDead(boolean isDead)
	{
		this.isDead = isDead;
	}
	
	public boolean getIsDead()
	{
		return isDead;
	}
	
	public Game getGame()
	{
		return game;
	}
}
