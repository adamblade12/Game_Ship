package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import GameObjects.Constants;
import GameObjects.Message;
import GameObjects.Meteor;
import GameObjects.MovingObject;
import GameObjects.Player;
import GameObjects.PowerUp;
import GameObjects.PowerUpTypes;
import GameObjects.Size;
import GameObjects.Ufo;
import graphics.Animation;
import graphics.Assets;
import graphics.Sound;
import io.JSONParser;
import io.ScoreData;
import main.Window;
import math.Vector2D;
import ui.Action;

public class GameState extends State{
	
	private Player player;
	private ArrayList<MovingObject> movingobjects = new ArrayList<MovingObject>();
	private ArrayList<Animation> explosions = new ArrayList<Animation>();
	private ArrayList<Message> messages = new ArrayList<Message>();
	
	private int score = 0;
	
	private int lifes = 3;
	
	private int meteors;
	
	private int waves = 1;
	
	private Sound backGroundMusic;
	
	private long ufoSpawner;
	private long gameOverTimer;
	boolean gameOver;
	private long powerUpSpawner;
	
	public GameState() {
		player = new Player(new Vector2D(100,500), new Vector2D(), 7, Assets.player, this);
		
		gameOver = false;
		movingobjects.add(player);
		
		meteors = 1;
		startWave();
		backGroundMusic = new Sound(Assets.backGroundMusic);
		backGroundMusic.loop();
		backGroundMusic.changeVolume(-10.0f);
		
		
		ufoSpawner = 0;
		gameOverTimer = 0;
		powerUpSpawner = 0;
	}
	
	public void addScore(int value, Vector2D position) {
		Color c = Color.WHITE;
		String text = "+ "+value+" score";
		if(player.isDoubleScoreOn()) {
			c = Color.YELLOW;
			value = value *2;
			text = "+ "+value+" score"+" x2";
		}
		score += value;
		messages.add(new Message(position,true,text,
				c,false,Assets.fontMed));
	}
	
	public void divideMeteor(Meteor meteor) {
		Size size = meteor.getSize();
		BufferedImage[] textures = size.tecturas;
		Size newsize = null;
		
		switch(size) {
		case BIG:
			newsize = Size.MED;
			break;
		case MED:
			newsize = Size.SMALL;
			break;
		case SMALL:
			newsize = Size.TINY;
			break;
		default:
			return;
		}
		
		for(int i = 0; i < size.quantity; i++) {
			movingobjects.add(new Meteor(
					meteor.getPosition(),
					new Vector2D(0,1).setDirection(Math.random()*Math.PI*2),
					Constants.METEOR_VEL * Math.random() + 1,
					textures[(int) (Math.random()* textures.length)],
					this,
					newsize
					));
		}
	}
	
	private void startWave() {
		
		messages.add(
				new Message(new Vector2D(Constants.WIDTH/2,Constants.HEIGHT/2),
				true,
				"WAVE "+waves,
				Color.WHITE,
				true,
				Assets.fontBig
				));
		double x, y;
		
		for(int i = 0; i < meteors; i++){
			 
			x = i % 2 == 0 ? Math.random()*Window.WIDTH : 0;
			y = i % 2 == 0 ? 0 : Math.random()*Window.HEIGHT;
			
			BufferedImage texture = Assets.bigs[(int)(Math.random()*Assets.bigs.length)];
			
			movingobjects.add(new Meteor(
					new Vector2D(x, y),
					new Vector2D(0, 1).setDirection(Math.random()*Math.PI*2),
					Constants.METEOR_VEL*Math.random() + 1,
					texture,
					this,
					Size.BIG
					));
			
		}
		waves++;
		meteors ++;
	}
	
	public void playExplosion(Vector2D position) {
		explosions.add(new Animation(
				Assets.exp,
				50,
				position.substract(new Vector2D(Assets.exp[0].getWidth()/2, Assets.exp[0].getHeight()/2))
				));
	}

	private void spawnUfo() {
		int rand = (int) (Math.random()*2);
		
		double x = rand == 0 ? (Math.random()*Constants.WIDTH): 0;
		double y = rand == 0 ? 0 : (Math.random()*Constants.HEIGHT);
		
		ArrayList<Vector2D> path = new ArrayList<Vector2D>();
		
		double posX, posY;
		
		posX = Math.random()*Constants.WIDTH/2;
		posY = Math.random()*Constants.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));

		posX = Math.random()*(Constants.WIDTH/2) + Constants.WIDTH/2;
		posY = Math.random()*Constants.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		
		posX = Math.random()*Constants.WIDTH/2;
		posY = Math.random()*(Constants.HEIGHT/2) + Constants.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		
		posX = Math.random()*(Constants.WIDTH/2) + Constants.WIDTH/2;
		posY = Math.random()*(Constants.HEIGHT/2) + Constants.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		
		movingobjects.add(new Ufo(
				new Vector2D(x, y),
				new Vector2D(),
				Constants.UFO_MAXVEL,
				Assets.ufo,
				path,
				this
				));
	}
	
private void spawnPowerUp() {
		
		final int x = (int) ((Constants.WIDTH - Assets.orb.getWidth()) * Math.random());
		final int y = (int) ((Constants.HEIGHT - Assets.orb.getHeight()) * Math.random());
		
		int index = (int) (Math.random() * (PowerUpTypes.values().length));
		
		PowerUpTypes p = PowerUpTypes.values()[index];
		
		final String text = p.text;
		Action action = null;
		Vector2D position = new Vector2D(x , y);
		
		switch(p) {
		case LIFE:
			action = new Action() {
				@Override
				public void doAction() {
					
					lifes ++;
					messages.add(new Message(
							position,
							false,
							text,
							Color.GREEN,
							false,
							Assets.fontMed
							));
				}
			};
			break;
		case SHIELD:
			action = new Action() {
				@Override
				public void doAction() {
					player.setShield();
					messages.add(new Message(
							position,
							false,
							text,
							Color.DARK_GRAY,
							false,
							Assets.fontMed
							));
				}
			};
			break;
		case SCORE_X2:
			action = new Action() {
				@Override
				public void doAction() {
					player.setDoubleScore();
					messages.add(new Message(
							position,
							false,
							text,
							Color.YELLOW,
							false,
							Assets.fontMed
							));
				}
			};
			break;
		case FASTER_FIRE:
			action = new Action() {
				@Override
				public void doAction() {
					player.setFastFire();
					messages.add(new Message(
							position,
							false,
							text,
							Color.BLUE,
							false,
							Assets.fontMed
							));
				}
			};
			break;
		case SCORE_STACK:
			action = new Action() {
				@Override
				public void doAction() {
					score += 1000;
					messages.add(new Message(
							position,
							false,
							text,
							Color.MAGENTA,
							false,
							Assets.fontMed
							));
				}
			};
			break;
		case DOUBLE_GUN:
			action = new Action() {
				@Override
				public void doAction() {
					player.setDoubleGun();
					messages.add(new Message(
							position,
							false,
							text,
							Color.ORANGE,
							false,
							Assets.fontMed
							));
				}
			};
			break;
		default:
			break;
		}
		
		this.movingobjects.add(new PowerUp(
				position,
				p.texture,
				this,
				action
				));
		
		
	}
	
	public void update(float dt) {
		
		if(gameOver)
			gameOverTimer += dt;
		
		powerUpSpawner += dt;
		ufoSpawner += dt;
		
		for(int i = 0; i < movingobjects.size(); i++){
			MovingObject mo = movingobjects.get(i);
			mo.update(dt);
			if(mo.isDead()) {
				movingobjects.remove(i);
				i--;
			}
			
		}
		
		for(int i = 0; i < explosions.size(); i++){
			Animation anim = explosions.get(i);
			anim.update(dt);
			if(!anim.isRunning()){
				explosions.remove(i);
			}
			
		}

		if(gameOverTimer > Constants.GAME_OVER_TIME) {
			
			try {
				ArrayList<ScoreData> dataList = JSONParser.readFile();
				dataList.add(new ScoreData(score));
				JSONParser.writeFile(dataList);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			backGroundMusic.stop();
			State.changeState(new MenuState());
		}
		
		if(powerUpSpawner > Constants.POWERUP_SPWNTIME) {
			spawnPowerUp();
			powerUpSpawner = 0;
		}
		
		if(ufoSpawner > Constants.UFO_SPAWN_RATE) {
			spawnUfo();
			ufoSpawner = 0;
		}
		
		for(int i = 0; i < movingobjects.size(); i++)
			if(movingobjects.get(i) instanceof Meteor)
				return;
		
		startWave();
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		for(int i = 0; i < messages.size(); i++) {
			messages.get(i).draw(g2d);
			if(messages.get(i).isDead())
				messages.remove(i);
		}
		
		for(int i = 0; i < movingobjects.size(); i++)
			movingobjects.get(i).draw(g);
		
		for(int i = 0; i < explosions.size(); i++){
			Animation anim = explosions.get(i);
			g2d.drawImage(anim.getCurrentFrame(), (int)anim.getPosition().getX(), (int)anim.getPosition().getY(),
					null);	
		}
		drawScore(g);
		drawLifes(g);
	}
	
	private void drawScore(Graphics g) {
		Vector2D pos = new Vector2D(850,25);
		String scoreToString = Integer.toString(score);
		
		for(int i = 0; i < scoreToString.length(); i++) {
			g.drawImage(Assets.numbers[Integer.parseInt(scoreToString.substring(i,i+1))],
					(int) pos.getX(), 
					(int) pos.getY(), null);
			pos.setX(pos.getX() + 20);
		}
	}
	
	private void drawLifes(Graphics g) {
		if(lifes < 1)
			return;
		
		Vector2D lifesPosition = new Vector2D( 25,25);
		
		g.drawImage(Assets.life, (int)lifesPosition.getX(), (int)lifesPosition.getY(), null);
		g.drawImage(Assets.numbers[10], (int)lifesPosition.getX() + 40, (int)lifesPosition.getY() + 5, null);
		
		String lifesToString = Integer.toString(lifes);
		
		Vector2D pos = new Vector2D(lifesPosition.getX(),lifesPosition.getY());
		
		for(int i = 0; i < lifesToString.length(); i++) {
			int number = Integer.parseInt(lifesToString.substring(i,i+1));
			
			if(number <= 0)
				break;
			g.drawImage(Assets.numbers[number],
					(int)pos.getX() + 60, (int)pos.getY()+ 5, null);
			pos.setX(pos.getX() + 20);
		}
		
	}
	
	public boolean subtractLife(Vector2D position) {
		lifes--;
		
		
		Message lifeLostMesg = new Message(
				position,
				false,
				"-1 LIFE",
				Color.RED,
				false,
				Assets.fontMed
				);
		messages.add(lifeLostMesg);
		
		return lifes > 0;
	}
	
	public void gameOver() {
		
		Message msgGameOver=new Message(
			new Vector2D(Constants.WIDTH/2,Constants.HEIGHT/2),
			true,
			"GAME OVER",
			Color.WHITE,
			true,
			Assets.fontBig
				);
		this.messages.add(msgGameOver);
		gameOver = true;
	}
	
	public ArrayList<Message> getMessages(){
		return messages;
	}

	public ArrayList<MovingObject> getMovingobjects() {
		return movingobjects;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getLifes() {
		return lifes;
	}

}
