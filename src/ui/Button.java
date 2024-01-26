package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.Text;
import input.MouseInput;
import math.Vector2D;

public class Button {
	
	private BufferedImage mouseOutimg;
	private BufferedImage mouseInimg;
	private boolean mouseIn;
	private Rectangle boundingBox;
	private String text;
	private Action action;
	
	public Button(BufferedImage mouseOutimg, BufferedImage mouseInimg, int x, int y, String text, Action action) {
		this.mouseOutimg = mouseOutimg;
		this.mouseInimg = mouseInimg;
		this.text = text;
		this.action = action;
		boundingBox = new Rectangle(x,y,mouseInimg.getWidth(),mouseInimg.getHeight());
	}

	public void update() {
		if(boundingBox.contains(MouseInput.x,MouseInput.y)) {
			mouseIn = true;
		}else {
			mouseIn = false;
		}
		
		if(mouseIn && MouseInput.MLB) {
			//Buttons action event
			action.doAction();
		}
	}
	
	public void draw(Graphics g) {
		
		if(mouseIn) {
			g.drawImage(mouseInimg, boundingBox.x, boundingBox.y, null);
		}else {
			g.drawImage(mouseOutimg, boundingBox.x, boundingBox.y, null);
		}
		
		Text.drawText(g, 
				text, 
				new Vector2D(boundingBox.getX() + boundingBox.getWidth()/2
				,boundingBox.getY() + boundingBox.getHeight()),
				true, 
				Color.BLACK, 
				Assets.fontMed);
	}
}
