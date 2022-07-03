package roguelike.controls;


public abstract class KeyControl {
	
	public char activationCharacter;
	
	public KeyControl(char activationCharacter) {
		this.activationCharacter = activationCharacter;
	}
	
	/**
	 * The method called when this control is activated.
	 * @return Whether to consume the event (preventing other controls from being activated).
	 */
	public abstract boolean activate();
	
}