package roguelike.controls;

import java.awt.Point;
import java.util.*;

import roguelike.display.Display;


public class ControlManager {
	Optional<Display> connectedDisplay = Optional.empty();
	boolean printDebugLogs = false;
	
	private Set<KeyControl> controlSet = new HashSet<KeyControl>();
	private Set<MouseControl> mouseControls = new HashSet<MouseControl>();
	private KeyManager keyManager = new KeyManager(this);
	private MouseManager mouseManager = new MouseManager(this);
	
	public void add(KeyControl control) {
		controlSet.add(control);
	}
	
	public void add(MouseControl control) {
		mouseControls.add(control);
	}
	
	public void connect(Display display) {
		if(connectedDisplay.isPresent())
			throw new IllegalStateException(this+" is already connected to "+connectedDisplay.get());
		display.addKeyListener(keyManager);
		display.addMouseListener(mouseManager);
		connectedDisplay = Optional.of(display);
	}
	
	public void disconnect() {
		connectedDisplay.get().removeKeyListener(keyManager);
		connectedDisplay.get().removeMouseListener(mouseManager);
		connectedDisplay = Optional.empty();
	}
	
	protected void handleKeyTyped(char typedCharacter) {
		for(KeyControl control : controlSet)
			if(typedCharacter == control.activationCharacter)
				if(control.activate())
					return;
	}
	
	protected void handleMousePressed(int pressedButton, int x, int y) {
		for(MouseControl control : mouseControls)
			if(pressedButton == control.activationButton)
				if(control.activate(x, y))
					return;
	}
	
	protected void handleMousePressed(int pressedButton, Point position) {
		handleMousePressed(pressedButton, position.x, position.y);
	}
}
