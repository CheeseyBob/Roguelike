package roguelike.controls;


public abstract class HoverControl {
	
	/**
	 * The method called when this control is activated.
	 * @param x Coordinate of the tile on the display.
	 * @param y Coordinate of the tile on the display.
	 * @return Whether to consume the event (preventing other controls from being activated).
	 */
	public abstract boolean activate(int x, int y);
	
}