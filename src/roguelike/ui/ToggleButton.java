package roguelike.ui;

import java.awt.Color;

import roguelike.display.Tile;


public class ToggleButton extends FunctionalButton {
	
	private boolean isOn;
	private Tile off, on;

	public ToggleButton(InterfaceFunction action) {
		super('?', null, null, action);
	}
	
	public boolean isOn() {
		return isOn;
	}

	@Override
	public boolean leftClick() {
		setOn(!isOn);
		super.leftClick();
		return true;
	}
	
	public ToggleButton setOn(boolean isOn) {
		this.isOn = isOn;
		set(isOn ? on : off);
		return this;
	}
	
	public ToggleButton whenOff(char character, Color bgCol, Color fgCol) {
		off = new Tile(character, bgCol, fgCol);
		return this;
	}
	
	public ToggleButton whenOn(char character, Color bgCol, Color fgCol) {
		on = new Tile(character, bgCol, fgCol);
		return this;
	}
}
