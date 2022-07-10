package roguelike;

import java.awt.Color;
import java.awt.Point;

import roguelike.display.Tile;


public class LocatableTile extends Tile {
	int x, y;
	
	public LocatableTile(char character, Color bgCol, Color fgCol) {
		this.character = character;
		this.bgCol = bgCol;
		this.fgCol = fgCol;
	}
	
	public Point getLocation() {
		return new Point(x, y);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isAt(int x, int y) {
		return (this.x == x && this.y == y);
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
