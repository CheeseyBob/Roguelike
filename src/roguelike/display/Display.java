package roguelike.display;

import general.Util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Set;


public class Display extends Frame {
	private static final long serialVersionUID = 1L;

	private Tile[][] grid;
	private boolean[][] needsRepaint;
	private Set<Highlight> highlightSet = new HashSet<Highlight>();
	private Highlight hoverHighlight = new Highlight(0, 0, Color.DARK_GRAY);
	private int tileSize;
	private int tileHalfSize;
	private Color bgCol = Color.BLACK;
	private Font font;

	public Display(String title, int width, int height, int tileSize, Font font) {
		this.tileSize = tileSize;
		this.tileHalfSize = tileSize/2;
		this.font = font;
		this.grid = new Tile[width][height];
		this.needsRepaint = new boolean[width][height];
		for(int x = 0; x < width; x ++)
			for(int y = 0; y < height; y ++)
				grid[x][y] = new Tile();
		
		setTitle(title);
		setLayout(null);
		setResizable(false);
		resetSize();
		setVisible(false);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	public void add(Highlight highlight) {
		highlightSet.add(highlight);
	}
	
	public Point gridCoordinatesOf(int pixelX, int pixelY) {
		Point gridCoords = new Point();
		Insets insets = getInsets();
		gridCoords.x = (pixelX - insets.left) / tileSize;
		gridCoords.y = (pixelY - insets.top) / tileSize;
		return gridCoords;
	}
	
	public Point gridCoordinatesOf(Point screenCoordinates) {
		return gridCoordinatesOf(screenCoordinates.x, screenCoordinates.y);
	}
	
	public int gridHeight() {
		return grid[0].length;
	}
	
	public int gridWidth() {
		return grid.length;
	}
	
	@Override
	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setBackground(bgCol);
		g.clearRect(0, 0, getWidth(), getHeight());
		Insets insets = getInsets();
		g.translate(insets.left, insets.top);
		paintGrid(g);
		paintHighlights(g);
	}
	
	private void paintGrid(Graphics2D g) {
		g.setFont(font);
		g.translate(-tileHalfSize, -tileHalfSize);
		for(int x = 0; x < grid.length; x ++) {
			g.translate(tileSize, 0);
			for(int y = 0; y < grid[x].length; y ++) {
				g.translate(0, tileSize);
				if(needsRepaint[x][y])
					paintTile(grid[x][y], g);
			}
			g.translate(0, -tileSize*grid[x].length);
		}
		g.translate(-tileSize*grid.length, 0);
		g.translate(tileHalfSize, tileHalfSize);
	}
	
	private void paintHighlight(Highlight highlight, Graphics2D g) {
		g.setColor(highlight.col);
		g.drawRect(highlight.x*tileSize, highlight.y*tileSize, tileSize - 1, tileSize - 1);
	}
	
	private void paintHighlights(Graphics2D g) {
		for(Highlight highlight : highlightSet)
			paintHighlight(highlight, g);
		paintHighlight(hoverHighlight, g);
	}
	
	private void paintTile(Tile tile, Graphics2D g) {
		g.setColor(tile.bgCol);
		g.fillRect(-tileHalfSize, -tileHalfSize, tileSize, tileSize);
		g.setColor(tile.fgCol);
		Util.drawStringCenteredXY(String.valueOf(tile.character), 0, 0, g);
	}
	
	public void remove(Highlight highlight) {
		highlightSet.remove(highlight);
	}
	
	private void resetSize() {
		Insets insets = getInsets();
		setSize(insets.left + gridWidth()*tileSize + insets.right, insets.top + gridHeight()*tileSize + insets.bottom);
	}
	
	public void set(int x, int y, char character, Color bgCol, Color fgCol) {
		if(x < 0 || y < 0 || x >= grid.length || y >= grid[x].length) return;
		grid[x][y].set(character, bgCol, fgCol);
		needsRepaint[x][y] = true;
	}
	
	public void set(int x, int y, char character) {
		set(x, y, character, Tile.defaultBgCol, Tile.defaultFgCol);
	}
	
	public void set(int x, int y, Tile tile) {
		set(x, y, tile.character, tile.bgCol, tile.fgCol);
	}
	
	public void set(int x, int y, String string, Color bgCol, Color fgCol, boolean horizontal) {
		int dx = horizontal ? 1 : 0;
		int dy = horizontal ? 0 : 1;
		for(char character : string.toCharArray()) {
			set(x, y, character, bgCol, fgCol);
			x += dx;
			y += dy;
		}
	}
	
	public void set(int x, int y, String string, boolean horizontal) {
		set(x, y, string, Tile.defaultBgCol, Tile.defaultFgCol, horizontal);
	}
	
	public void setHoverHighlight(int x, int y) {
		hoverHighlight.x = x;
		hoverHighlight.y = y;
	}
	
	public void setHoverHighlight(int x, int y, Color col) {
		setHoverHighlight(x, y);
		hoverHighlight.col = col;
	}
	
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		resetSize();
	}
}
