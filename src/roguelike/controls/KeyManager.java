package roguelike.controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


class KeyManager implements KeyListener {
	
	ControlManager controlManager;
	
	KeyManager(ControlManager controlManager) {
		this.controlManager = controlManager;
	}
	
	private void log(KeyEvent e) {
		System.out.println("--- KeyEvent ---");
		System.out.println("e.getKeyCode()="+e.getKeyCode());
		System.out.println("e.getKeyChar()="+e.getKeyChar());
		System.out.println("(int)e.getKeyChar()="+(int)e.getKeyChar());
		System.out.println("e.getExtendedKeyCode()="+e.getExtendedKeyCode());
		System.out.println("e.getModifiersEx()="+e.getModifiersEx());
		System.out.println("-------------");
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// Not used.
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Not used.
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(controlManager.printDebugLogs) log(e);
		controlManager.handleKeyTyped(e.getKeyChar());
	}
}
