package roguelike.controls;


public abstract class MouseControl {
	public static final int LEFT_BUTTON = 1;
	public static final int MIDDLE_BUTTON = 2;
	public static final int RIGHT_BUTTON = 3;
	
	public int activationButton;
	
	public MouseControl(int activationButton) {
		this.activationButton = activationButton;
	}
	
	/**
	 * The method called when this control is activated.
	 * @param x Coordinate of the tile on the display.
	 * @param y Coordinate of the tile on the display.
	 * @return Whether to consume the event (preventing other controls from being activated).
	 */
	public abstract boolean activate(int x, int y);
	
}