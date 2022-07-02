package roguelike.controls;


public abstract class Control {
	
	public char activationCharacter;
	
	public Control(char activationCharacter) {
		this.activationCharacter = activationCharacter;
	}
	
	public abstract void activate();
	
}