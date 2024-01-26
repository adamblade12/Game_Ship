package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoard implements KeyListener{

	private boolean[] keys = new boolean[256];
	
	public static boolean UP,DOWN,LEFT,RIGHT,SHOOT;
	
	public KeyBoard() {
		UP = false;
		DOWN = false;
		LEFT = false;
		RIGHT = false;
		SHOOT = false;
	
	}

	public void update() {
		UP = keys[KeyEvent.VK_UP];
		DOWN = keys[KeyEvent.VK_DOWN];
		LEFT = keys[KeyEvent.VK_LEFT];
		RIGHT = keys[KeyEvent.VK_RIGHT];
		SHOOT = keys[KeyEvent.VK_P];
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyCode());
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}