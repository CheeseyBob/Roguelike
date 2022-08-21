package japaneseRoguelike;

import java.awt.Color;

import roguelike.Actor;
import roguelike.scene.Entity;
import roguelike.ui.HoverAction;


public class Terrain extends Entity implements HoverAction, MovementBlocker {
	public static final int DRAW_LAYER = 0;
	public static final boolean IMPASSABLE = false;
	public static final boolean PASSABLE = true;
	
	char defaultCharacter;
	char hoverCharacter;
	String furigana;
	
	boolean isPassable;
	
	public Terrain(char hoverCharacter, String furigana, char defaultCharacter, Color bgCol, Color fgCol, boolean isPassable) {
		super(defaultCharacter, bgCol, fgCol, DRAW_LAYER);
		this.defaultCharacter = defaultCharacter;
		this.hoverCharacter = hoverCharacter;
		this.furigana = furigana;
		this.isPassable = isPassable;
	}

	@Override
	public boolean blocks(Actor actor) {
		return !isPassable;
	}

	@Override
	public boolean hover() {
		character = hoverCharacter;
		((GameScene)scene).setFurigana(furigana);
		scene.paint();
		return false;
	}

	@Override
	public void unhover() {
		character = defaultCharacter;
		((GameScene)scene).setFurigana("");
		scene.paint();
	}
}