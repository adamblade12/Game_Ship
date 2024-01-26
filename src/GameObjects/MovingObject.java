package GameObjects;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.Assets;
import graphics.Sound;
import math.Vector2D;
import states.GameState;

public abstract class MovingObject extends GameObject{
	
	protected Vector2D velocity;
	protected AffineTransform at;
	protected double angle;
	protected double maxVel;
	protected int width;
	protected int height;
	protected GameState gameState;
	private Sound explosion, pickPowerUp;
	private boolean dead;

	public MovingObject(Vector2D position,Vector2D velocity,double maxVel, BufferedImage texture, GameState gameState) {
		super(position, texture);
		this.maxVel = maxVel;
		this.velocity = velocity;
		this.gameState = gameState;
		width = texture.getWidth();
		height = texture.getHeight();
		angle = 0;
		explosion = new Sound(Assets.explosion);
		pickPowerUp = new Sound(Assets.powerup);
		dead = false;
	}
	
	protected Vector2D center() {
		return new Vector2D(position.getX() + width/2, position.getY() + height/2);
	}
	
	protected void collidesWith() {
		ArrayList<MovingObject> movingObjects = gameState.getMovingobjects();
		
		for(int i=0 ; i<movingObjects.size() ; i++) {
			MovingObject m = movingObjects.get(i);
			
			if(m.equals(this))
				continue;
			
			double distance = m.center().substract(center()).getMagnitude();
			
			if(distance < m.width/2 + width/2 && movingObjects.contains(this) && !m.isDead() && !dead) {
				objectCollision(m, this);
			}
		}
	}
	
	private void objectCollision(MovingObject a, MovingObject b) {
		
		Player p = null;
		
		if(a instanceof Player)
			p = (Player)a;
		else if(b instanceof Player)
			p = (Player)b;
		
		if(p != null && p.isSpawning())
			return;
		
		if(a instanceof Meteor && b instanceof Meteor)
			return;
		
		if(a instanceof Meteor && b instanceof PowerUp)
			return;
		if(a instanceof PowerUp && b instanceof Meteor)
			return;
		
		
		if(a instanceof Laser && b instanceof PowerUp)
			return;
		if(a instanceof PowerUp && b instanceof Laser)
			return;
		
		if(a instanceof Ufo && b instanceof PowerUp)
			return;
		if(a instanceof PowerUp && b instanceof Ufo)
			return;
		
		if(!(a instanceof PowerUp || b instanceof PowerUp)) {
			a.destroy();
			b.destroy();
			return;
		}
		
		if(p != null) {
			if(a instanceof Player && b instanceof PowerUp) {
				((PowerUp)b).excecuteAction();
				b.destroy();
				pickPowerUp.play();
			}else if(b instanceof Player && a instanceof PowerUp) {
				((PowerUp)a).excecuteAction();
				a.destroy();
				pickPowerUp.play();
			}
				
		}
	}
	
	protected void destroy() {
		dead = true;
		if(!(this instanceof Laser) && !(this instanceof PowerUp))
			explosion.play();
		if(this instanceof Ufo)
			explosion.play();

	}
	
	public boolean isDead() {
		return dead;
	}
}
