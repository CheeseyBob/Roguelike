import java.awt.Color;
import java.awt.Point;

import roguelike.Actor;
import roguelike.controls.MouseControl;
import roguelike.display.Display;
import roguelike.scene.InterfaceComponent;
import roguelike.scene.Scene;
import roguelike.ui.FunctionalButton;
import roguelike.ui.ToggleButton;


class GameScene extends Scene {
	Actor player = new Actor('私', Color.BLACK, Color.WHITE);
	
	InterfaceComponent decorationNW = new InterfaceComponent('#', Color.BLACK, Color.DARK_GRAY);
	InterfaceComponent decorationNE = new InterfaceComponent('#', Color.BLACK, Color.DARK_GRAY);
	InterfaceComponent decorationSW = new InterfaceComponent('#', Color.BLACK, Color.DARK_GRAY);
	InterfaceComponent decorationSE = new InterfaceComponent('#', Color.BLACK, Color.DARK_GRAY);

	FunctionalButton testButtonA = new FunctionalButton('A', Color.DARK_GRAY, Color.WHITE, this::testButtonA);
	ToggleButton testButtonB = new ToggleButton(this::testButtonB)
			.whenOff('B', Color.BLACK, Color.WHITE)
			.whenOn('B', Color.WHITE, Color.BLACK)
			.setOn(false);
	ToggleButton testButtonC = new ToggleButton(this::testButtonC)
			.whenOff('X', Color.DARK_GRAY, Color.LIGHT_GRAY)
			.whenOn('C', Color.LIGHT_GRAY, Color.WHITE)
			.setOn(true);
	
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
		addControl(MouseControl.RIGHT_BUTTON, this::click);
		
		place(decorationNW);
		place(decorationNE);
		place(decorationSW);
		place(decorationSE);
		place(testButtonA);
		place(testButtonB);
		place(testButtonC);

		for(int x = 0; x < 10; x ++)
			for(int y = 0; y < 10; y ++)
				place(new Grass(), x, y);
		place(player, 5, 5);
		place(new Cat(), 2, 2);
		place(new Cat(), 7, 7);
	}
	
	@Override
	public void connect(Display display) {
		super.connect(display);
		
		int N = 0, S = display.gridHeight() - 1, E = display.gridWidth() - 1, W = 0;
		decorationNW.setLocation(W, N);
		decorationNE.setLocation(E, N);
		decorationSW.setLocation(W, S);
		decorationSE.setLocation(E, S);
		
		testButtonA.setLocation(W, N + 2);
		testButtonB.setLocation(W, S - 3);
		testButtonC.setLocation(W, S - 2);
	}
	
	private boolean click(int x, int y) {
		System.out.println("GameScene.click("+x+", "+y+")");
		
		
		//place(new InterfaceComponent('o', Color.BLACK, Color.DARK_GRAY, 0), x, y);
		
		Point mapCoords = mapCoordinatesOf(x, y);
		System.out.println("mapCoords="+mapCoords);
		place(new Actor('x', Color.BLACK, Color.RED), mapCoords.x, mapCoords.y);
		
		paint();
		return true;
	}
	
	@Override
	public void paint(Display display) {
		super.paint(display);
		paintUI(display);
	}
	
	private void paintUI(Display display) {
		display.set(2, 0, "命:7|力:2|金:5", Color.BLACK, Color.GREEN, true);
	}
	
	private boolean move(int dx, int dy) {
		player.move(dx, dy);
		step();
		return true;
	}
	
	private boolean moveSW() {
		return move(-1, +1);
	}
	
	private boolean moveS() {
		return move(+0, +1);
	}
	
	private boolean moveSE() {
		return move(+1, +1);
	}
	
	private boolean moveW() {
		return move(-1, +0);
	}
	
	private boolean moveX() {
		return move(+0, +0);
	}
	
	private boolean moveE() {
		return move(+1, +0);
	}
	
	private boolean moveNW() {
		return move(-1, -1);
	}
	
	private boolean moveN() {
		return move(+0, -1);
	}
	
	private boolean moveNE() {
		return move(+1, -1);
	}
	
	@Override
	public void step() {
		super.step();
		viewX = player.getX();
		viewY = player.getY();
		paint();
	}
	
	private boolean testButtonA(InterfaceComponent sourceComponent) {
		System.out.println("GameScene.testButtonA()");
		return moveX();
	}
	
	private boolean testButtonB(InterfaceComponent sourceComponent) {
		ToggleButton toggle = (ToggleButton)sourceComponent;
		
		System.out.println("GameScene.testButtonA(): "+toggle.isOn());
		
		paint();
		return true;
	}
	
	private boolean testButtonC(InterfaceComponent sourceComponent) {
		ToggleButton toggle = (ToggleButton)sourceComponent;
		
		System.out.println("GameScene.testButtonA(): "+toggle.isOn());
		
		paint();
		return true;
	}
}
