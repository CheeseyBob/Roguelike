package roguelike;

import java.awt.Color;

import roguelike.scene.Entity;
import roguelike.scene.Stepable;


public class Actor extends Entity implements Stepable {
	public static final int DRAW_LAYER = 100;
	
	public Actor(char character, Color bgCol, Color fgCol) {
		super(character, bgCol, fgCol, DRAW_LAYER);
	}
	
	/**
	 * @return whether the movement was carried out successfully.
	 */
	public boolean move(int dx, int dy) {
		setLocation(getX() + dx, getY() + dy);
		return true;
	}
	
	@Override
	public void step() {
		// To be overridden by subclasses.
	}
}