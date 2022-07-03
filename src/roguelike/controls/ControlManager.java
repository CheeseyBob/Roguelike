package roguelike.controls;

import java.awt.Point;
import java.util.*;

import roguelike.display.Display;


public class ControlManager {
	Optional<Display> connectedDisplay = Optional.empty();
	boolean printDebugLogs = false;
	Point curserPosition = new Point(0, 0);
	
	private Set<KeyControl> keyControlSet = new HashSet<KeyControl>();
	private Set<MouseControl> mouseControlSet = new HashSet<MouseControl>();
	private Set<HoverControl> hoverControlSet = new HashSet<HoverControl>();
	private KeyManager keyManager = new KeyManager(this);
	private MouseManager mouseManager = new MouseManager(this);
	
	public void add(KeyControl control) {
		keyControlSet.add(control);
	}
	
	public void add(MouseControl control) {
		mouseControlSet.add(control);
	}
	
	public void add(HoverControl control) {
		hoverControlSet.add(control);
	}
	
	public void connect(Display display) {
		if(connectedDisplay.isPresent())
			throw new IllegalStateException(this+" is already connected to "+connectedDisplay.get());
		display.addKeyListener(keyManager);
		display.addMouseListener(mouseManager);
		display.addMouseMotionListener(mouseManager);
		connectedDisplay = Optional.of(display);
	}
	
	public void disconnect() {
		connectedDisplay.get().removeKeyListener(keyManager);
		connectedDisplay.get().removeMouseListener(mouseManager);
		connectedDisplay.get().removeMouseMotionListener(mouseManager);
		connectedDisplay = Optional.empty();
	}
	
	protected void handleKeyTyped(char typedCharacter) {
		for(KeyControl control : keyControlSet)
			if(typedCharacter == control.activationCharacter)
				if(control.activate())
					return;
	}
	
	protected void handleMouseMoved(Point position) {
		if(position.equals(curserPosition))
			return;
		curserPosition.setLocation(position);
		
		for(HoverControl control : hoverControlSet)
			if(control.activate(position.x, position.y))
				return;
	}
	
	protected void handleMousePressed(int pressedButton, int x, int y) {
		for(MouseControl control : mouseControlSet)
			if(pressedButton == control.activationButton)
				if(control.activate(x, y))
					return;
	}
	
	protected void handleMousePressed(int pressedButton, Point position) {
		handleMousePressed(pressedButton, position.x, position.y);
	}
}
