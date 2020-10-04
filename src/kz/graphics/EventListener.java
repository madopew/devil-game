package kz.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import kz.engine.Menu;
//opengl функции для отображения и инициализации
public class EventListener implements GLEventListener {
	//хранит переменную gl2 (саму графическую оболочку)
	private static GL2 gl;
	//главное меню
	private static Menu menu;
	//вызывается каждый раз при отображении
	//зависит от фпс
	//в данном случае 60 раз в секунду
	public void display(GLAutoDrawable drawable) {
		//очищаются все пиксели, устанавливается дефолтный цвет
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		//отображается меню
		menu.display();
		//очищается буфер
		gl.glFlush();
	}

	public void dispose(GLAutoDrawable drawable) {
	}
	//вызывается вместе с программой
	public void init(GLAutoDrawable drawable) {
		//при инициализации устанавливаются настройки
		//для оболочки
		//такие как цвет очистки пикселей, режим прорисовки и тд 
		gl = drawable.getGL().getGL2();
		gl.glClearColor(0.1f,0.1f,0.1f, 1);
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		//а также инициализируется меню
		menu = new Menu(drawable);
	}
	//вызывается при первом отображении окна
	//а также каждый раз при изменении его размеров
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		//устанавливается режим отображения и координатная сетка
		//левый верхний угол имеет координаты 0 и 0
		//правый нижний ШИРИНА и ВЫСОТА
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(0, width, height, 0, -1, 1);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
	}
	//геттер для gl
	public static GL2 getGL() {
		return gl;
	}
	//геттер для меню
	public static Menu getMenu() {
		return menu;
	}
}
