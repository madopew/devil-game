package kz.engine;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

import kz.graphics.EventListener;
//��������� ������� ����
public class MouseInput implements MouseListener{

	//��� ���������� ������� ���������� � ����
	public void mouseClicked(MouseEvent event) {
		EventListener.getMenu().click(event.getX(), event.getY());
	}

	public void mouseDragged(MouseEvent event) {
		
	}

	public void mouseEntered(MouseEvent event) {
		
	}

	public void mouseExited(MouseEvent event) {
		
	}

	public void mouseMoved(MouseEvent event) {
		
	}

	public void mousePressed(MouseEvent event) {
		
	}

	public void mouseReleased(MouseEvent event) {
		
	}

	public void mouseWheelMoved(MouseEvent event) {
		
	}
}
