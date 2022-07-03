package roguelike.controls;


public class FunctionalHoverControl extends HoverControl {
	
	MouseControlFunction action;
	
	public FunctionalHoverControl(MouseControlFunction action) {
		this.action = action;
	}
	
	@Override
	public boolean activate(int x, int y) {
		return action.activate(x, y);
	}
}
