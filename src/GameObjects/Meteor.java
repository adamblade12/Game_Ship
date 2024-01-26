package GameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import graphics.Assets;
import main.Window;
import math.Vector2D;
import states.GameState;

public class Meteor extends MovingObject{
	
	private final double DELTAANGLE = 0.1/2;
	private Size size;

	public Meteor(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState, Size size) {
		super(position, velocity, maxVel, texture, gameState);
		this.size = size;
		this.velocity =velocity.scale(maxVel);
	}

	@Override
	public void update(float dt) {
		
		Vector2D playerPos = new Vector2D(gameState.getPlayer().center());
		
		int distanceOfPlayer = (int) playerPos.substract(center()).getMagnitude();
		
		if(distanceOfPlayer < Constants.SHIELD_DISTANCE/2 + width/2) {
			if(gameState.getPlayer().isShieldOn()) {
				Vector2D fleeForce = fleeForce();
				velocity = velocity.add(fleeForce);
			}
		}
		
		if(velocity.getMagnitude() >= this.maxVel) {
			Vector2D reverseVelocity = new Vector2D(-velocity.getX(),-velocity.getY());
			velocity = velocity.add(reverseVelocity.normalize().scale(0.01f));
		}
		
		velocity = velocity.limit(maxVel);
		
		position = position.add(velocity);
		
		if(position.getX() > Window.WIDTH)
			position.setX(-width);
		if(position.getY() > Window.HEIGHT)
			position.setY(-height);
		if(position.getX() < -width)
			position.setX(Window.WIDTH);
		if(position.getY() < -height)
			position.setY(Window.HEIGHT);
		
		angle += DELTAANGLE;
	}
	
	private Vector2D fleeForce() {
		Vector2D desiredVelocity = gameState.getPlayer().center().substract(center());
		desiredVelocity = (desiredVelocity.normalize()).scale(maxVel);
		Vector2D v = new Vector2D(velocity);
		return v.substract(desiredVelocity);
	}
	
	@Override
	protected void destroy() {
		gameState.divideMeteor(this);
		gameState.playExplosion(position);
		gameState.addScore(Constants.METEOR_SCORE, position);
		super.destroy();
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
		at.rotate(angle,width/2,height/2);
		g2d.drawImage(texture, at , null);
	}
	
	public Size getSize() {
		return size;
	}

}
