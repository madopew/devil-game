package kz.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import kz.engine.Menu;
//opengl ������� ��� ����������� � �������������
public class EventListener implements GLEventListener {
	//������ ���������� gl2 (���� ����������� ��������)
	private static GL2 gl;
	//������� ����
	private static Menu menu;
	//���������� ������ ��� ��� �����������
	//������� �� ���
	//� ������ ������ 60 ��� � �������
	public void display(GLAutoDrawable drawable) {
		//��������� ��� �������, ��������������� ��������� ����
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		//������������ ����
		menu.display();
		//��������� �����
		gl.glFlush();
	}

	public void dispose(GLAutoDrawable drawable) {
	}
	//���������� ������ � ����������
	public void init(GLAutoDrawable drawable) {
		//��� ������������� ��������������� ���������
		//��� ��������
		//����� ��� ���� ������� ��������, ����� ���������� � �� 
		gl = drawable.getGL().getGL2();
		gl.glClearColor(0.1f,0.1f,0.1f, 1);
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		//� ����� ���������������� ����
		menu = new Menu(drawable);
	}
	//���������� ��� ������ ����������� ����
	//� ����� ������ ��� ��� ��������� ��� ��������
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		//��������������� ����� ����������� � ������������ �����
		//����� ������� ���� ����� ���������� 0 � 0
		//������ ������ ������ � ������
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(0, width, height, 0, -1, 1);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
	}
	//������ ��� gl
	public static GL2 getGL() {
		return gl;
	}
	//������ ��� ����
	public static Menu getMenu() {
		return menu;
	}
}
