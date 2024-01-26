package GameObjects;

import javax.swing.filechooser.FileSystemView;

public class Constants {

	// frame dimensions
	
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600;
	
	//Meteor propieties
	
	public static final double METEOR_VEL = 2.0;
	public static final int SHIELD_DISTANCE = 150;
	
	//Player properties
	
	public static final long FLICKER_TIME = 200;
	public static final long SPAWN_TIME = 3000;
	public static final long GAME_OVER_TIME = 3000;
	public static final int FIRE_RATE = 300;
	
	// Laser properties
	
	public static final double LASER_VEL = 15.0;
	
	//Ufo properties
	
	public static final int NODE_RADIUS = 160;
	public static final double UFO_MASS = 60; 
	public static final int UFO_MAXVEL = 3;
	public static final long UFO_FIRERATE = 1000;
	public static double UFO_ANGLE_RANGE = Math.PI / 2;
	public static final long UFO_SPAWN_RATE = 10000; 
	
	//Scorres
	
	public static final int UFO_SCORE = 40;
	public static final int METEOR_SCORE = 20; 
	
	//Buttons
	
	public static final String PLAY = "PLAY";
	public static final String EXIT = "EXIT";
	public static final String RETUR = "RETURN";
	public static final String HIGHSCORES = "HIGH SCORES";
	
	//Load Bar properties
	
	public static final int LOADING_BAR_WIDTH = 500;
	public static final int LOADING_BAR_HEIGHT = 50;
	
	//Messages
	public static final String SCORE = "SCORE";
	public static final String DATE = "DATE";
	
	public static final String SCORE_PATH = FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+
			"\\SpaceShipGame\\data.json";
	
	//Others
	public static final long POWERUP_DURATION = 10000;
	public static final long POWERUP_SPWNTIME = 10000;
	
	public static final long SHIELD_TIME = 12000;
	public static final long DOUBLE_SCORE_TIME = 10000;
	public static final long FAST_FIRE_TIME = 14000;
	public static final long DOUBLE_GUN_TIME = 12000;
	
	public static final int SCORE_STACK = 1000;
	
	
}
