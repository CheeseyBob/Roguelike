import java.awt.Color;

import maths.M;

import roguelike.ui.LeftClickAction;


class Cat extends Creature implements LeftClickAction {

	public Cat() {
		super('猫', "ねこ", Color.WHITE);
	}
	
	@Override
	public void step() {
		super.step();
		move(M.randInt(-1, 1), M.randInt(-1, 1));
	}

	@Override
	public boolean leftClick() {
		System.out.println("Cat.leftClick()");
		bgCol = Color.RED;
		scene.paint();
		return true;
	}
}
