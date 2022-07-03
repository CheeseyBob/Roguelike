package roguelike.controls;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import roguelike.display.Display;


class MouseManager implements MouseListener, MouseMotionListener {
	
	ControlManager controlManager;
	
	MouseManager(ControlManager controlManager) {
		this.controlManager = controlManager;
	}
	
	private void log(MouseEvent e) {
		System.out.println("--- MouseEvent ---");
		System.out.println("e.getSource()="+e.getSource());
		System.out.println("e.getButton()="+e.getButton());
		System.out.println("e.getX()="+e.getX());
		System.out.println("e.getY()="+e.getY());
		System.out.println("e.getModifiersEx()="+e.getModifiersEx());
		System.out.println("-------------");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Not used.
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// Not used.
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Not used.
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Not used.
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point position = new Point(e.getX(), e.getY());
		if(e.getSource() instanceof Display) {
			Display display = (Display) e.getSource();
			position = display.gridCoordinatesOf(position);
			controlManager.handleMouseMoved(position);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(controlManager.printDebugLogs) log(e);
		Point position = new Point(e.getX(), e.getY());
		if(e.getSource() instanceof Display) {
			Display display = (Display) e.getSource();
			position = display.gridCoordinatesOf(position);
		}
		controlManager.handleMousePressed(e.getButton(), position);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Not used.
	}
}
