package roguelike.scene;

import java.awt.Color;
import java.awt.Point;
import java.util.*;

import roguelike.controls.*;
import roguelike.display.Display;
import roguelike.ui.HoverAction;
import roguelike.ui.LeftClickAction;
import roguelike.ui.RightClickAction;


public class Scene {
	
	public int viewX = 0, viewY = 0;

	private Optional<Display> connectedDisplay = Optional.empty();
	
	private final ControlManager controlManager = new ControlManager();
	private final DrawLayers drawLayers = new DrawLayers();
	private final EntityMap entityMap = new EntityMap();
	private final Set<InterfaceComponent> interfaceComponents = new HashSet<InterfaceComponent>();
	private final Set<Stepable> stepables = new HashSet<Stepable>();
	private final Set<HoverAction> hoveringSet = new HashSet<HoverAction>();
	
	public Scene() {
		addControl(MouseControl.LEFT_BUTTON, this::clickLeftMouse);
		addControl(MouseControl.RIGHT_BUTTON, this::clickRightMouse);
		addControl(this::hover);
	}
	
	public void addControl(KeyControl control) {
		controlManager.add(control);
	}
	
	public void addControl(MouseControl control) {
		controlManager.add(control);
	}
	
	public void addControl(HoverControl control) {
		controlManager.add(control);
	}
	
	/**
	 * Adds a KeyControl which triggers the given action.
	 * @param character The character which is to trigger the control.
	 * @param action A method to be run when the control is triggered.
	 */
	public void addControl(char character, KeyControlFunction action) {
		controlManager.add(new FunctionalKeyControl(character, action));
	}
	
	/**
	 * Adds a MouseControl which triggers the given action.
	 * @param button Code for the mouse button which is to trigger the control.
	 * @param action A method to be run when the control is triggered.
	 */
	public void addControl(int button, MouseControlFunction action) {
		controlManager.add(new FunctionalMouseControl(button, action));
	}
	
	/**
	 * Adds a HoverControl which triggers the given action.
	 * @param action A method to be run when the control is triggered.
	 */
	public void addControl(MouseControlFunction action) {
		controlManager.add(new FunctionalHoverControl(action));
	}
	
	private boolean clickLeftMouse(int x, int y) {
		for(InterfaceComponent component : interfaceComponents)
			if(component instanceof LeftClickAction && component.isAt(x, y))
				if( ((LeftClickAction) component).leftClick() )
					return true;
		
		for(Entity entity : entityMap.get(mapCoordinatesOf(x, y)))
			if(entity instanceof LeftClickAction)
				if( ((LeftClickAction) entity).leftClick() )
					return true;

		return false;
	}
	
	private boolean clickRightMouse(int x, int y) {
		for(InterfaceComponent component : interfaceComponents)
			if(component instanceof RightClickAction && component.isAt(x, y))
				if( ((RightClickAction) component).rightClick() )
					return true;
		
		for(Entity entity : entityMap.get(mapCoordinatesOf(x, y)))
			if(entity instanceof RightClickAction)
				if( ((RightClickAction) entity).rightClick() )
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
	
	private boolean hover(HoverAction hoverable) {
		hoveringSet.add(hoverable);
		return hoverable.hover();
	}
	
	private boolean hover(int x, int y) {
		getDisplay().setHoverHighlight(x, y, Color.YELLOW);
		getDisplay().repaint();
		
		for(HoverAction hoverable : hoveringSet)
			hoverable.unhover();
		hoveringSet.clear();
		
		for(InterfaceComponent component : interfaceComponents)
			if(component instanceof HoverAction && component.isAt(x, y))
				if(hover((HoverAction) component))
					return true;
		
		for(Entity entity : entityMap.get(mapCoordinatesOf(x, y)))
			if(entity instanceof HoverAction)
				if(hover((HoverAction) entity))
					return true;

		return false;
	}
	
	public Point mapCoordinatesOf(int displayX, int displayY) {
		Point mapCoords = new Point();
		mapCoords.x = displayX + viewX - getDisplay().gridWidth()/2;
		mapCoords.y = displayY + viewY - getDisplay().gridHeight()/2;
		return mapCoords;
	}
	
	public Point mapCoordinatesOf(Point displayCoordinates) {
		return mapCoordinatesOf(displayCoordinates.x, displayCoordinates.y);
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
		entity.map = entityMap;
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
		entity.map = null;
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
