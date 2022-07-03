package roguelike.scene;

import java.util.*;

import roguelike.controls.*;
import roguelike.display.Display;
import roguelike.ui.LeftClickAction;
import roguelike.ui.RightClickAction;


public class Scene {
	
	public int viewX = 0, viewY = 0;
	
	private ControlManager controlManager = new ControlManager();
	private Optional<Display> connectedDisplay = Optional.empty();
	private DrawLayers drawLayers = new DrawLayers();
	private EntityMap entityMap = new EntityMap();
	private Set<InterfaceComponent> interfaceComponents = new HashSet<InterfaceComponent>();
	private Set<Stepable> stepables = new HashSet<Stepable>();
	
	public Scene() {
		addControl(MouseControl.LEFT_BUTTON, this::clickLeftMouse);
		addControl(MouseControl.RIGHT_BUTTON, this::clickRightMouse);
	}
	
	public void addControl(KeyControl control) {
		controlManager.add(control);
	}
	
	public void addControl(MouseControl control) {
		controlManager.add(control);
	}
	
	public void addControl(char character, KeyControlFunction action) {
		controlManager.add(new FunctionalKeyControl(character, action));
	}
	
	public void addControl(int button, MouseControlFunction action) {
		controlManager.add(new FunctionalMouseControl(button, action));
	}
	
	private boolean clickLeftMouse(int x, int y) {
		for(InterfaceComponent component : interfaceComponents)
			if(component instanceof LeftClickAction && component.isAt(x, y))
				if( ((LeftClickAction) component).leftClick() )
					return true;
		return false;
	}
	
	private boolean clickRightMouse(int x, int y) {
		for(InterfaceComponent component : interfaceComponents)
			if(component instanceof RightClickAction && component.isAt(x, y))
				if( ((RightClickAction) component).rightClick() )
					return true;
		return false;
	}
	
	public void connect(Display display) {
		disconnect();
		controlManager.connect(display);
		connectedDisplay = Optional.of(display);
	}
	
	public void disconnect() {
		if(connectedDisplay.isPresent())
			controlManager.disconnect();
		connectedDisplay = Optional.empty();
	}
	
	public Display getDisplay() {
		return connectedDisplay.get();
	}
	
	public void paint() {
		if(connectedDisplay.isPresent()) {
			paint(getDisplay());	
			getDisplay().repaint();
		}
	}
	
	public void paint(Display display) {
		for(int x = 0; x < display.gridWidth(); x ++)
			for(int y = 0; y < display.gridHeight(); y ++)
				display.set(x, y, ' ');
		
		for(DrawLayer drawLayer : drawLayers.getLayersInOrder())
			for(Entity entity : drawLayer.getEntities())
				display.set(entity.getX() + display.gridWidth()/2 - viewX, entity.getY() + display.gridHeight()/2 - viewY, entity);
		
		for(InterfaceComponent component : interfaceComponents)
			display.set(component.getX(), component.getY(), component);
	}
	
	private void place(Entity entity) {
		if(entity.scene != null)
			throw new IllegalArgumentException("Cannot add "+entity+" to "+this+" as it already belongs to scene "+entity.scene);
		
		entity.scene = this;
		drawLayers.add(entity);
		entityMap.add(entity);
	}
	
	private void place(InterfaceComponent component) {
		interfaceComponents.add(component);
	}
	
	private void place(Stepable stepable) {
		stepables.add(stepable);
	}
	
	public void place(Object obj) {
		if(obj instanceof Entity)
			place((Entity)obj);
		if(obj instanceof InterfaceComponent)
			place((InterfaceComponent)obj);
		if(obj instanceof Stepable)
			place((Stepable)obj);
	}
	
	public void place(Entity entity, int x, int y) {
		entity.setLocation(x, y);
		place((Object)entity);
	}
	
	public void place(InterfaceComponent component, int x, int y) {
		component.setLocation(x, y);
		place((Object)component);
	}
	
	private void remove(Entity entity) {
		if(entity.scene != this)
			throw new IllegalArgumentException("Cannot remove "+entity+" from "+this+" as it belongs to scene "+entity.scene);
		
		entity.scene = null;
		drawLayers.remove(entity);
		entityMap.remove(entity);
	}
	
	private void remove(Stepable stepable) {
		stepables.remove(stepable);
	}
	
	public void remove(Object obj) {
		if(obj instanceof Entity)
			remove((Entity)obj);
		if(obj instanceof Stepable)
			remove((Stepable)obj);
	}
	
	public void step() {
		for(Stepable stepable : stepables)
			stepable.step();
	}
}
