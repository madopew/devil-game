package kz.graphics;

import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.event.WindowListener;
import com.jogamp.newt.event.WindowUpdateEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;

import kz.engine.MouseInput;
//����������� ���� 
public class Renderer {
	//���������� ��� �������� ����
	private static GLWindow window = null;
	private static GLProfile profile = null;
	//�������� ����
	private static final String GAME_NAME = "���� ������� @����� ���� 951007, 2020";
	//���
	public static final int FPS = 60;
	//�������
	public static final int WIDTH = 1040;
	public static final int HEIGHT = 640;
	//������
	public static GLProfile getProfile() {
		return profile;
	}
	//�������������
	public static void init() {
		//��������� ����� ������� opengl ������ 2
		GLProfile.initSingleton();
		profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities caps = new GLCapabilities(profile);
		//��������� ����� ���� � ���������� �������
		//� �������
		//� ����� ���������
		window = GLWindow.create(caps);
		window.setSize(WIDTH, HEIGHT);
		window.setResizable(false);
		window.setSurfaceSize(WIDTH, HEIGHT);
		window.addGLEventListener(new EventListener());
		window.addMouseListener(new MouseInput());
		window.setTitle(GAME_NAME);
		//��������� ������ ������� �������� �������
		//����� ��� init � display
		//� ��� 60 ������ � �������
		FPSAnimator animator = new FPSAnimator(window, FPS);
		animator.start();		
		window.setVisible(true);
		window.addWindowListener(new WindowListener() {
			//��� �������� ���� ����������� ������ ��������� �����
			//� ���������� �����������
			@Override
			public void windowDestroyed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				animator.stop();
				System.exit(0);
			}

			@Override
			public void windowGainedFocus(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowLostFocus(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowMoved(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowRepaint(WindowUpdateEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowResized(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDestroyNotify(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	//������� ����� ���������
	public static void main(String[] args) {
		init();
	}
}
