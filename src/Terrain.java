import java.awt.Color;

import roguelike.scene.Entity;
import roguelike.ui.HoverAction;


public class Terrain extends Entity implements HoverAction {
	public static final int DRAW_LAYER = 0;
	
	char defaultCharacter;
	char hoverCharacter;
	
	public Terrain(char hoverCharacter, char defaultCharacter, Color bgCol, Color fgCol) {
		super(defaultCharacter, bgCol, fgCol, DRAW_LAYER);
		this.defaultCharacter = defaultCharacter;
		this.hoverCharacter = hoverCharacter;
	}

	@Override
	public boolean hover() {
		character = hoverCharacter;
		scene.paint();
		return false;
	}

	@Override
	public void unhover() {
		character = defaultCharacter;
		scene.paint();
	}
}