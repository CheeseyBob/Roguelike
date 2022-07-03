package roguelike.controls;


public class FunctionalMouseControl extends MouseControl {
	
	MouseControlFunction action;
	
	public FunctionalMouseControl(int activationButton, MouseControlFunction action) {
		super(activationButton);
		this.action = action;
	}
	
	@Override
	public boolean activate(int x, int y) {
		return action.activate(x, y);
	}
}
