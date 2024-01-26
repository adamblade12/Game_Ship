package states;

import java.awt.Graphics;
import java.util.ArrayList;


import GameObjects.Constants;
import graphics.Assets;
import ui.Action;
import ui.Button;

public class MenuState extends State{

	private ArrayList<Button> buttons;
	
	public MenuState() {
		buttons = new ArrayList<Button>();
		
		buttons.add(new Button(
				Assets.greyButton,
				Assets.blueButton,
				Constants.WIDTH / 2 - Assets.greyButton.getWidth()/2,
				Constants.HEIGHT / 2 - Assets.greyButton.getHeight()*2,
				Constants.PLAY,
				new Action() {
					@Override
					public void doAction() {
						State.changeState(new GameState());
					}
				}
				));
		
		buttons.add(new Button(
				Assets.greyButton,
				Assets.blueButton,
				Constants.WIDTH / 2 - Assets.greyButton.getWidth()/2,
				Constants.HEIGHT / 2 + Assets.greyButton.getHeight()*3,
				Constants.EXIT,
				new Action() {
					@Override
					public void doAction() {
						System.exit(0);
					}
				}
				));
		
		buttons.add(new Button(
				Assets.greyButton,
				Assets.blueButton,
				Constants.WIDTH / 2 - Assets.greyButton.getWidth()/2,
				Constants.HEIGHT / 2,
				Constants.HIGHSCORES,
				new Action() {
					@Override
					public void doAction() {
						State.changeState(new ScoreState());
					}
				}
				));
		
	}
	
	@Override
	public void update(float dt) {
		for(Button b : buttons) {
			b.update();
		}
	}

	@Override
	public void draw(Graphics g) {
		for(Button b : buttons) {
			b.draw(g);
		}
	}

}
