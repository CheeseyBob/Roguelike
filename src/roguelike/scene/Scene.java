package roguelike.scene;

import java.awt.Color;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import roguelike.controls.Control;
import roguelike.controls.ControlManager;
import roguelike.controls.FunctionalControl;
import roguelike.display.Display;


public class Scene {
	private ControlManager controlManager = new ControlManager();
	private Optional<Display> connectedDisplay = Optional.empty();
	private DrawLayers drawLayers = new DrawLayers();
	private EntityMap entityMap = new EntityMap();
	private Set<Stepable> stepables = new HashSet<Stepable>();
	public int viewX = 0, viewY = 0;
	
	public void addControl(Control control) {
		controlManager.add(control);
	}
	
	public void addControl(char character, Runnable action) {
		controlManager.add(new FunctionalControl(character, action));
	}
	
	public void connect(Display display) {
		disconnect();
		controlManager.listenTo(display);
		connectedDisplay = Optional.of(display);
	}
	
	public void disconnect() {
		if(connectedDisplay.isPresent())
			controlManager.stopListening(connectedDisplay.get());
		connectedDisplay = Optional.empty();
	}
	
	public Optional<Display> getDisplay() {
		return connectedDisplay;
	}
	
	public void paint() {
		if(connectedDisplay.isPresent())
			paint(connectedDisplay.get());	
	}
	
	public void paint(Display display) {
		for(int x = 0; x < display.gridWidth(); x ++)
			for(int y = 0; y < display.gridHeight(); y ++)
				display.set(x, y, ' ', Color.BLACK, Color.WHITE);
		
		for(DrawLayer drawLayer : drawLayers.getLayersInOrder())
			for(Entity entity : drawLayer.getEntities())
				display.set(entity.x + display.gridWidth()/2 - viewX, entity.y + display.gridHeight()/2 - viewY, entity);
		
		display.repaint();
	}
	
	private void place(Entity entity) {
		if(entity.scene != null)
			throw new IllegalArgumentException("Cannot add entity to scene "+this+" when this entity belongs to scene "+entity.scene);
		
		entity.scene = this;
		drawLayers.add(entity);
		entityMap.add(entity);
	}
	
	private void place(Stepable stepable) {
		stepables.add(stepable);
	}
	
	public void place(Object obj) {
		if(obj instanceof Entity)
			place((Entity)obj);
		if(obj instanceof Stepable)
			place((Stepable)obj);
	}
	
	public void place(Entity entity, int x, int y) {
		entity.setLocation(x, y);
		place((Object)entity);
	}
	
	private void remove(Entity entity) {
		if(entity.scene != this)
			throw new IllegalArgumentException("Cannot remove entity from scene "+this+" when this entity belongs to scene "+entity.scene);
		
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
