package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.lang.annotation.Target;

import javax.swing.JFrame;

import GameObjects.Constants;
import graphics.Assets;
import input.KeyBoard;
import input.MouseInput;
import states.GameState;
import states.LoadingState;
import states.MenuState;
import states.State;

public class Window extends JFrame implements Runnable{
	
	public static final int WIDTH = Constants.WIDTH, HEIGHT = Constants.HEIGHT;
	private Canvas canvas;
	private Thread thread;
	private boolean running = false;
	
	private BufferStrategy bs;
	private Graphics g;
	
	private final int FPS = 60;
	private double TARGETTIME = 1000000000/FPS;
	private double delta = 0;
	private int AVERAGEFPS = FPS;
	
	private KeyBoard keyBoard;
	private MouseInput mouseInput;
	public Window() {
		setTitle("Space Ship Game");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		
		canvas = new Canvas();
		keyBoard = new KeyBoard();
		mouseInput = new MouseInput();
		
		canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		canvas.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		canvas.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		canvas.setFocusable(true);
		
		add(canvas);
		canvas.addKeyListener(keyBoard);
		canvas.addMouseListener(mouseInput);
		canvas.addMouseMotionListener(mouseInput);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new Window().start();
	}

	private void update(float dt) {
		keyBoard.update();
		State.getCurrentState().update(dt);
		
	}
	
	private void draw() {
		bs = canvas.getBufferStrategy();
		
		if(bs == null)
		{
			canvas.createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		
		//-----------------------
		
		g.setColor(Color.BLACK);
		
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		State.getCurrentState().draw(g);
		
		g.setColor(Color.WHITE);
		
		g.drawString(""+AVERAGEFPS, 10, 20);
		
		//---------------------
		
		g.dispose();
		bs.show();
	}
	
	public void init() {
		Thread loadThread = new Thread(new Runnable() {

			@Override
			public void run() {
				Assets.init();
			}
		});
		State.changeState(new LoadingState(loadThread));	}
	
	@Override
	public void run() {
		long now = 0;
		long lastTime = System.nanoTime();
		int frames = 0;
		long time = 0;
		
		init();
		
		while(running)
		{
			now = System.nanoTime();
			delta += (now - lastTime)/TARGETTIME;
			time += (now - lastTime);
			lastTime = now;
			
			
			
			if(delta >= 1)
			{		
				update((float)(delta + TARGETTIME*0.000001f));
				draw();
				delta --;
				frames ++;
			}
			if(time >= 1000000000)
			{
				AVERAGEFPS = frames;
				frames = 0;
				time = 0;
				
			}
			
			
		}
		
		stop();
	}
	
	private void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	private void stop() {
		try {
			thread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
