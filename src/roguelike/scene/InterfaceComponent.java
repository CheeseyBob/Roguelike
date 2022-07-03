package roguelike.scene;

import java.awt.Color;

import roguelike.LocatableTile;


public class InterfaceComponent extends LocatableTile {
	Scene scene;
	int drawLayer;
	
	public InterfaceComponent(char character, Color bgCol, Color fgCol, int drawLayer) {
		super(character, bgCol, fgCol);
		this.drawLayer = drawLayer;
	}
	
	public InterfaceComponent(char character, Color bgCol, Color fgCol) {
		this(character, bgCol, fgCol, 0);
	}
}
