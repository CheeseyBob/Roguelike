package roguelike.controls;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;


public class ControlManager {
	private static boolean printDebugLogs = false;
	
	private List<Control> controlList = new ArrayList<Control>();

	private KeyListener keyListener = new KeyListener() {
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
			printDebugLog(e);
			char typedCharacter = e.getKeyChar();
			for(Control control : controlList) {
				if(typedCharacter == control.activationCharacter) {
					control.activate();
				}
			}
		}
		
		private void printDebugLog(KeyEvent e) {
			if(!printDebugLogs) return;
			System.out.println("-------------");
			System.out.println("e.getKeyCode()="+e.getKeyCode());
			System.out.println("e.getKeyChar()="+e.getKeyChar());
			System.out.println("(int)e.getKeyChar()="+(int)e.getKeyChar());
			System.out.println("e.getExtendedKeyCode()="+e.getExtendedKeyCode());
			System.out.println("e.getModifiersEx()="+e.getModifiersEx());
			System.out.println("-------------");
		}
	};
	
	public void add(Control control) {
		controlList.add(control);
	}
	
	public void listenTo(Frame frame) {
		frame.addKeyListener(keyListener);
	}
	
	public void stopListening(Frame frame) {
		frame.removeKeyListener(keyListener);
	}
}
