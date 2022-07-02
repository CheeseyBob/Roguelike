import roguelike.display.Display;
import roguelike.scene.Scene;


class JapaneseRoguelike {
	public static Display display;
	
	public static Scene gameScene = new GameScene();
	
	public static void main(String[] args) {
		display = new Display("Japanese Roguelike", 15, 15, 32);
		display.setVisible(true);
		
		gameScene.connect(display);
		gameScene.step();
	}
}
