package GameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.Sound;
import math.Vector2D;
import states.GameState;
import ui.Action;

public class PowerUp extends MovingObject{
	
	private long duration;
	private Action action;
	private Sound powerUpPick;
	private BufferedImage textureType;
	

	public PowerUp(Vector2D position, BufferedImage texture, GameState gameState, Action action) {
		super(position, new Vector2D(), 0, Assets.orb, gameState);
		
		this.action = action;
		this.textureType = texture;
		duration = 0;
		powerUpPick = new Sound(Assets.powerup);
	}
	
	void excecuteAction() {
		action.doAction();
		powerUpPick.play();
	}

	@Override
	public void update(float dt) {
		angle = 0.1;
		duration+= dt;
		if(duration > Constants.POWERUP_DURATION) {
			this.destroy();
		}
		
		collidesWith();
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		at = AffineTransform.getTranslateInstance(
				position.getX() +Assets.orb.getWidth()/2 - textureType.getWidth()/2
				,position.getY() +Assets.orb.getHeight()/2 - textureType.getHeight());
		
		at.rotate(angle,
				textureType.getWidth()/2,
				textureType.getHeight()/2);
		
		g2d.drawImage(textureType, at, null);
	}
	

}
