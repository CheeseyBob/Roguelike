package roguelike.controls;


public class FunctionalControl extends Control {
	
	Runnable action;
	
	public FunctionalControl(char activationCharacter, Runnable action) {
		super(activationCharacter);
		this.action = action;
	}
	
	public void activate() {
		action.run();
	}
}