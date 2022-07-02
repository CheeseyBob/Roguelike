import java.awt.Color;

import roguelike.Actor;
import roguelike.scene.Scene;


class GameScene extends Scene {
	Actor player = new Actor('@', Color.BLACK, Color.WHITE);
	
	GameScene() {
		addControl('1', this::moveSW);
		addControl('2', this::moveS);
		addControl('3', this::moveSE);
		addControl('4', this::moveW);
		addControl('5', this::moveX);
		addControl('6', this::moveE);
		addControl('7', this::moveNW);
		addControl('8', this::moveN);
		addControl('9', this::moveNE);

		for(int x = 0; x < 10; x ++)
			for(int y = 0; y < 10; y ++)
				place(new Grass(), x, y);
		place(player, 5, 5);
		place(new Cat(), 2, 2);
		place(new Cat(), 7, 7);
	}
	
	private void move(int dx, int dy) {
		player.move(dx, dy);
		step();
	}
	
	private void moveSW() {
		move(-1, +1);
	}
	
	private void moveS() {
		move(+0, +1);
	}
	
	private void moveSE() {
		move(+1, +1);
	}
	
	private void moveW() {
		move(-1, +0);
	}
	
	private void moveX() {
		move(+0, +0);
	}
	
	private void moveE() {
		move(+1, +0);
	}
	
	private void moveNW() {
		move(-1, -1);
	}
	
	private void moveN() {
		move(+0, -1);
	}
	
	private void moveNE() {
		move(+1, -1);
	}
	
	@Override
	public void step() {
		super.step();
		viewX = player.getX();
		viewY = player.getY();
		paint();
	}
}
