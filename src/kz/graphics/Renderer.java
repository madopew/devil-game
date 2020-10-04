package kz.graphics;

import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.event.WindowListener;
import com.jogamp.newt.event.WindowUpdateEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;

import kz.engine.MouseInput;
//отображение окна 
public class Renderer {
	//переменные для хранения окна
	private static GLWindow window = null;
	private static GLProfile profile = null;
	//название игры
	private static final String GAME_NAME = "Игра Дьявола @Бакыт Мади 951007, 2020";
	//фпс
	public static final int FPS = 60;
	//размеры
	public static final int WIDTH = 1040;
	public static final int HEIGHT = 640;
	//геттер
	public static GLProfile getProfile() {
		return profile;
	}
	//инициализация
	public static void init() {
		//создается новый профайл opengl версии 2
		GLProfile.initSingleton();
		profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities caps = new GLCapabilities(profile);
		//создается новое окно с обработкой нажатий
		//и событий
		//а также названием
		window = GLWindow.create(caps);
		window.setSize(WIDTH, HEIGHT);
		window.setResizable(false);
		window.setSurfaceSize(WIDTH, HEIGHT);
		window.addGLEventListener(new EventListener());
		window.addMouseListener(new MouseInput());
		window.setTitle(GAME_NAME);
		//создается объект который вызывает события
		//такие как init и display
		//с фпс 60 кадров в секунду
		FPSAnimator animator = new FPSAnimator(window, FPS);
		animator.start();		
		window.setVisible(true);
		window.addWindowListener(new WindowListener() {
			//при закрытии окна выключается объект созданный ранее
			//и приложение закрывается
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
	//входная точка программы
	public static void main(String[] args) {
		init();
	}
}
