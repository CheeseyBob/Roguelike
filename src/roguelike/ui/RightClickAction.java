package roguelike.ui;


public interface RightClickAction {
	
	/**
	 * The method called when this object is clicked.
	 * @return Whether to consume the event (preventing other objects from being clicked).
	 */
	public boolean rightClick();
	
}
