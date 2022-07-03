package roguelike.ui;

import java.awt.Color;

import roguelike.scene.InterfaceComponent;


public class FunctionalButton extends InterfaceComponent implements LeftClickAction {
	private static final int DRAW_LAYER = 0;
	
	private InterfaceFunction action;

	public FunctionalButton(char character, Color bgCol, Color fgCol, InterfaceFunction action) {
		super(character, bgCol, fgCol, DRAW_LAYER);
		this.action = action;
		
	}

	@Override
	public boolean leftClick() {
		return action.activate(this);
	}
}
