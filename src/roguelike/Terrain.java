package roguelike;

import java.awt.Color;

import roguelike.scene.Entity;


public class Terrain extends Entity {
	public static final int DRAW_LAYER = 0;
	
	public Terrain(char character, Color bgCol, Color fgCol) {
		super(character, bgCol, fgCol, DRAW_LAYER);
	}
}