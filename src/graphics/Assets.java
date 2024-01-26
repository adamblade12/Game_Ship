package graphics;

import java.awt.Font;
import java.awt.image.BufferedImage;
import javax.sound.sampled.Clip;

public class Assets {
	
	public static boolean loaded = false;
	public static float count = 0;
	public static float maxCount = 46;

	public static BufferedImage player;
	
	//Effects
	
	public static BufferedImage speed;
	
	public static BufferedImage[] shieldEffect = new BufferedImage[3];
	
	//Explosions
	
	public static BufferedImage[] exp = new BufferedImage[9];
	
	//Numbers
	
	public static BufferedImage[] numbers = new BufferedImage[11];
	
	//Lasers
	
	public static BufferedImage blueLaser,greenLaser,redLaser;
	
	//Meteors
	
	public static BufferedImage[] bigs = new BufferedImage[4];
	public static BufferedImage[] meds = new BufferedImage[2];
	public static BufferedImage[] small = new BufferedImage[2];
	public static BufferedImage[] tinies = new BufferedImage[2];
	
	//Enemies
	
	public static BufferedImage ufo;
	
	//Others
	
	public static BufferedImage life;
	
	//Fonts
	
	public static Font fontBig;
	public static Font fontMed;
	
	//Sounds
	
	public static Clip backGroundMusic , explosion, playerLoose, playerShoot, ufoShoot, powerup;
	
	//Buttons
	
	public static BufferedImage greyButton;
	public static BufferedImage blueButton; 
	
	//Power ups
	
	public static BufferedImage orb,doubleGunPlayer,doubleScore,doubleGun, fastFire, shield,star;
	public static void init() {
		player = loadImage("/ship/player.png");
		speed = loadImage("/effects/fire08.png");
		blueLaser = loadImage("/lasers/laserBlue01.png");
		greenLaser = loadImage("/lasers/laserGreen11.png");
		redLaser = loadImage("/lasers/laserRed01.png");
		doubleGunPlayer = loadImage("/ship/doubleGunPlayer.png");
		
		for(int i = 0; i<bigs.length; i++) 
			bigs[i] = loadImage("/meteors/big"+(i+1)+".png");
		for(int i = 0; i<meds.length; i++) 
			 meds[i] = loadImage("/meteors/med"+(i+1)+".png");
		for(int i = 0; i<small.length; i++) 
			small[i] = loadImage("/meteors/small"+(i+1)+".png");
		for(int i = 0; i<tinies.length; i++) 
			tinies[i] = loadImage("/meteors/tiny"+(i+1)+".png");
		
		for (int i = 0; i<exp.length; i++) {
			exp[i] = loadImage("/explosion/"+i+".png");	
		}
		
		ufo = loadImage("/ship/ufo.png");
		
		fontBig = loadFont("/fonts/futureFont.ttf", 42);
		fontMed = loadFont("/fonts/futureFont.ttf", 20);
		
		for(int i = 0; i<numbers.length; i++) {
			numbers[i] = loadImage("/numbers/"+i+".png");
		}
		
		for(int i = 0; i<shieldEffect.length; i++) {
			shieldEffect[i] = loadImage("/effects/shield"+(i+1)+".png");
		}
		life = loadImage("/other/life.png");
		
		backGroundMusic = loadSound("/sounds/backGroundMusic.wav");
		explosion = loadSound("/sounds/explosion.wav");
		playerLoose = loadSound("/sounds/playerLoose.wav");
		playerShoot = loadSound("/sounds/playerShoot.wav");
		ufoShoot = loadSound("/sounds/ufoShoot.wav");
		powerup = loadSound("/sounds/powerUp.wav");
		
		greyButton = loadImage("/ui/grey_button.png");
		blueButton = loadImage("/ui/blue_button.png");
		
		orb = loadImage("/powers/orb.png");
		doubleScore = loadImage("/powers/doubleScore.png");
		doubleGun = loadImage("/powers/doubleGun.png");
		fastFire = loadImage("/powers/fastFire.png");
		shield = loadImage("/powers/shield.png");
		star = loadImage("/powers/star.png");
		
		loaded = true;
	}
	
	public static BufferedImage loadImage(String path) {
		count++;
		return Loader.ImageLoader(path);
	}
	
	public static Font loadFont(String path,int size) {
		count++;
		return Loader.loadFont(path, size);
	}
	
	public static Clip loadSound(String path) {
		count++;
		return Loader.loadSound(path);
	}
}
