package roguelike.display;

import java.awt.Color;


public class Tile {
	static char defaultCharacter = ' ';
	static Color defaultBgCol = Color.BLACK;
	static Color defaultFgCol = Color.WHITE;

	protected char character;
	protected Color bgCol, fgCol;
	
	public Tile() {
		set(defaultCharacter, defaultBgCol, defaultFgCol);
	}
	
	public Tile(char character, Color bgCol, Color fgCol) {
		set(character, bgCol, fgCol);
	}
	
	public void set(char character, Color bgCol, Color fgCol) {
		this.character = character;
		this.bgCol = bgCol;
		this.fgCol = fgCol;
	}
	
	public void set(Tile tile) {
		set(tile.character, tile.bgCol, tile.fgCol);
	}
}
