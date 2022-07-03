package roguelike.controls;


public class FunctionalKeyControl extends KeyControl {
	
	KeyControlFunction action;
	
	public FunctionalKeyControl(char activationCharacter, KeyControlFunction action) {
		super(activationCharacter);
		this.action = action;
	}
	
	@Override
	public boolean activate() {
		return action.activate();
	}
}
