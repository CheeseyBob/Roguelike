package japaneseRoguelike;
import java.awt.Font;

import roguelike.display.Display;
import roguelike.scene.Scene;


class JapaneseRoguelike {
	public static Display display;
	
	public static Scene gameScene = new GameScene();
	
	public static void main(String[] args) {
		//String[] fontList = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		
		Font font = new Font("Monospaced", Font.PLAIN, 32);
		
		display = new Display("Japanese Roguelike", 15, 15, 32, font);
		display.setVisible(true);
		
		gameScene.connect(display);
		gameScene.step();
	}
	
	public static String numberAsKanji(int n) {
		String[] digits = {"0", "一", "二", "三", "四", "伍", "六", "七", "八", "九", "十"};
		
		if(0 <= n && n <= 10)
			return digits[n];
		
		throw new RuntimeException("Unimplemented for n > 10");
	}
}
