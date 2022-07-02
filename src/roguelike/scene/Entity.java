package roguelike.scene;

import java.awt.Color;
import java.awt.Point;

import roguelike.display.Tile;


public class Entity extends Tile {
	Scene scene;
	int x, y;
	int drawLayer;
	
	public Entity(char character, Color bgCol, Color fgCol, int drawLayer) {
		this.character = character;
		this.bgCol = bgCol;
		this.fgCol = fgCol;
		this.drawLayer = drawLayer;
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
	
	public void move(int dx, int dy) {
		setLocation(x + dx, y + dy);
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
}