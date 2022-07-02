package roguelike.display;

import java.awt.Color;


public class Tile {
	static Color defaultBgCol = Color.BLACK;
	static Color defaultFgCol = Color.WHITE;
	static char defaultCharacter = ' ';

	protected Color bgCol = defaultBgCol;
	protected Color fgCol = defaultFgCol;
	protected char character = defaultCharacter;
	
	public void set(char character, Color bgCol, Color fgCol) {
		this.character = character;
		this.bgCol = bgCol;
		this.fgCol = fgCol;
	}
	
	public void set(Tile tile) {
		set(tile.character, tile.bgCol, tile.fgCol);
	}
}
