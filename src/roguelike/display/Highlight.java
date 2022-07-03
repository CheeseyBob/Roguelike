package roguelike.display;

import java.awt.Color;


public class Highlight {
	protected int x, y;
	protected Color col;
	
	public Highlight(int x, int y, Color col) {
		this.x = x;
		this.y = y;
		this.col = col;
	}
}
