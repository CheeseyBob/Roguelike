import java.awt.Color;

import roguelike.Actor;
import roguelike.scene.Entity;
import roguelike.ui.HoverAction;


class Creature extends Actor implements HoverAction, MovementBlocker {
	
	private String furigana;

	public Creature(char character, String furigana, Color col) {
		super(character, Color.BLACK, col);
		this.furigana = furigana;
	}

	@Override
	public boolean blocks(Actor actor) {
		return true;
	}
	
	public boolean canMove(int dx, int dy) {
		for(Entity entity : map.get(getX() + dx, getY() + dy))
			if(entity instanceof MovementBlocker && ((MovementBlocker)entity).blocks(this))
				return false;
		return true;
	}

	@Override
	public boolean hover() {
		((GameScene)scene).furigana = furigana;
		scene.paint();
		return false;
	}
	
	@Override
	public boolean move(int dx, int dy) {
		if(canMove(dx, dy))
			return super.move(dx, dy);
		else return false;
	}

	@Override
	public void unhover() {
		((GameScene)scene).furigana = "";
		scene.paint();
	}
}
