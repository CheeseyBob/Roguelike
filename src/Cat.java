import java.awt.Color;

import maths.M;

import roguelike.Actor;


class Cat extends Actor {

	public Cat() {
		super('猫', Color.BLACK, Color.WHITE);
	}
	
	@Override
	public void step() {
		super.step();
		move(M.randInt(-1, 1), M.randInt(-1, 1));
	}
}