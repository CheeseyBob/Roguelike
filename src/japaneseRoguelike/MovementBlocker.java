package japaneseRoguelike;
import roguelike.Actor;


public interface MovementBlocker {
	
	public abstract boolean blocks(Actor actor);
	
}
