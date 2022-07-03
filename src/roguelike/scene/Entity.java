package roguelike.scene;

import java.awt.Color;

import roguelike.LocatableTile;


public class Entity extends LocatableTile {
	Scene scene;
	int drawLayer;
	
	public Entity(char character, Color bgCol, Color fgCol, int drawLayer) {
		super(character, bgCol, fgCol);
		this.drawLayer = drawLayer;
	}
}
