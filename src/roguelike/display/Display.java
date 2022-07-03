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


public class Display extends Frame {
	private static final long serialVersionUID = 1L;
	
	private Tile[][] grid;
	private int tileSize;
	private int tileHalfSize;
	private Color bgCol = Color.BLACK;
	private Font font;
	private boolean drawDebugWireframe = false;

	public Display(String title, int width, int height, int tileSize, Font font) {
		this.tileSize = tileSize;
		this.tileHalfSize = tileSize/2;
		this.font = font;
		this.grid = new Tile[width][height];
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
			public void windowActivated(WindowEvent e) {
				System.out.println("windowActivated");
			}
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			@Override
			public void windowGainedFocus(WindowEvent e) {
				System.out.println("windowGainedFocus");
			}
			@Override
			public void windowOpened(WindowEvent e) {
				System.out.println("windowOpened");
			}
			@Override
			public void windowStateChanged(WindowEvent e) {
				System.out.println("windowStateChanged");
			}
		});
	}
	
	public Point gridCoordinatesOf(int screenX, int screenY) {
		Point gridCoords = new Point();
		Insets insets = getInsets();
		gridCoords.x = (screenX - insets.left) / tileSize;
		gridCoords.y = (screenY - insets.top) / tileSize;
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
	}
	
	private void paintGrid(Graphics2D g) {
		g.setFont(font);
		g.translate(-tileHalfSize, -tileHalfSize);
		for(int x = 0; x < grid.length; x ++) {
			g.translate(tileSize, 0);
			for(int y = 0; y < grid[x].length; y ++) {
				g.translate(0, tileSize);
				paintTile(grid[x][y], g);
			}
			g.translate(0, -tileSize*grid[x].length);
		}
		g.translate(-tileSize*grid.length, 0);
		g.translate(tileHalfSize, tileHalfSize);
	}
	
	private void paintTile(Tile tile, Graphics2D g) {
		g.setColor(tile.bgCol);
		g.fillRect(-tileHalfSize, -tileHalfSize, tileSize, tileSize);
		g.setColor(tile.fgCol);
		if(drawDebugWireframe)
			g.drawRect(-tileHalfSize, -tileHalfSize, tileSize, tileSize);
		Util.drawStringCenteredXY(String.valueOf(tile.character), 0, 0, g);
	}
	
	private void resetSize() {
		Insets insets = getInsets();
		setSize(insets.left + gridWidth()*tileSize + insets.right, insets.top + gridHeight()*tileSize + insets.bottom);
	}
	
	public void set(int x, int y, char character, Color bgCol, Color fgCol) {
		if(x < 0 || y < 0 || x >= grid.length || y >= grid[x].length) return;
		grid[x][y].set(character, bgCol, fgCol);
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
	
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		resetSize();
	}
}
