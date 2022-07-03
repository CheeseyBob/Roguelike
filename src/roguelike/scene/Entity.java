package roguelike.scene;

import java.awt.Color;

import roguelike.LocatableTile;


public class Entity extends LocatableTile {
	EntityMap map;
	Scene scene;
	int drawLayer;
	
	public Entity(char character, Color bgCol, Color fgCol, int drawLayer) {
		super(character, bgCol, fgCol);
		this.drawLayer = drawLayer;
	}
	
	@Override
	public void setLocation(int x, int y) {
		if(map == null) {
			super.setLocation(x, y);
		} else {
			map.remove(this);
			super.setLocation(x, y);
			map.add(this);
		}
	}
}
