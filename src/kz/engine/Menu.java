package kz.engine;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.jogamp.opengl.GLAutoDrawable;

import kz.gamelogic.Board;
import kz.graphics.Graphics;
import kz.graphics.ImageResource;
import kz.graphics.Renderer;
//главное меню: поле и правила
public class Menu {
	//размер окна
	private float sizeOfWindow;
	//данная переменная хранит текущий уровень
	private Board board;
	//текущий номер уровная
	private int currentLevel = 0;
	//кнопка "начать заново"
	private GameObject reset;
	//лампочки индикации номера уровня
	private GameObject[] lights;
	//максимальное количесвто уровней
	private static final int MAX_LEVELS = 9;
	//все используемые текстуры (подгружаются один раз)
	public static final ImageResource REG0 = new ImageResource("/res/regular0.png");
	public static final ImageResource REG1 = new ImageResource("/res/regular1.png");
	public static final ImageResource BOR0 = new ImageResource("/res/border0.png");
	public static final ImageResource BOR1 = new ImageResource("/res/border1.png");
	public static final ImageResource SHI0 = new ImageResource("/res/shield0.png");
	public static final ImageResource SHI1 = new ImageResource("/res/shield1.png");
	public static final ImageResource DEV0 = new ImageResource("/res/devil0.png");
	public static final ImageResource DEV1 = new ImageResource("/res/devil1.png");
	public static final ImageResource HUM0 = new ImageResource("/res/human0.png");
	public static final ImageResource HUM1 = new ImageResource("/res/human1.png");
	public static final ImageResource COR0 = new ImageResource("/res/corner0.png");
	public static final ImageResource COR1 = new ImageResource("/res/corner1.png");
	public static final ImageResource FOR0 = new ImageResource("/res/forward0.png");
	public static final ImageResource FOR1 = new ImageResource("/res/forward1.png");
	private static final ImageResource RESB = new ImageResource("/res/reset.png");
	private static final ImageResource MENU = new ImageResource("/res/menu.png");
	private static final ImageResource LIG0 = new ImageResource("/res/light0.png");
	private static final ImageResource LIG1 = new ImageResource("/res/light1.png");
	private static final ImageResource BACK = new ImageResource("/res/backTile.png");
	//константы координат, размеров
	private static final int RESET_X = 740;
	private static final int RESET_Y = 400;
	private static final int RESET_W = 200;
	private static final int RESET_H = 50;
	private static final int FIRST_LIGHT_X = 740;
	private static final int FIRST_LIGHT_Y = 462;
	private static final int LIGHT_SIZE = 35;
	private static final int X_DIST_LIGHTS = 83;
	private static final int Y_DIST_LIGHTS = 48;
	private static final int LIGHTS_ROW_AMOUNT = 3;
	private static final int MENU_WIDTH = 400;
	//строковые константы
	private static final String CONGRATS_TEXT_STR = "Ты прошел все уровни! Игра будет перезапущена.";	
	private static final String CONGRATS_STR = "Поздравляем!";
	
	public Menu(GLAutoDrawable drawable) {
		//в размер окна сохраняем высоту окна (т.к. поле имеет
		//размер высота*высота)
		this.sizeOfWindow = drawable.getSurfaceHeight();
		//подгружаем первый уровень
		board = getLevel(currentLevel);
		//инициализируем кнопку и лампочки
		reset = new GameObject(RESET_X, RESET_Y, RESET_W, RESET_H, RESB);
		lights = new GameObject[MAX_LEVELS];
		for(int i = 0; i < MAX_LEVELS; i++) {
			//получение координат лампочек в двухмерном пространстве
			int x = FIRST_LIGHT_X + i % LIGHTS_ROW_AMOUNT * X_DIST_LIGHTS;
			int y = FIRST_LIGHT_Y + i / LIGHTS_ROW_AMOUNT * Y_DIST_LIGHTS;
			lights[i] = new GameObject(x, y, LIGHT_SIZE, LIGHT_SIZE, LIG0);
		}
	}
	//обработка нажатия
	public void click(int x, int y) {
		//если нажато в области поля, обрабатываем нажатие в поле
		//иначе если нажата кнопка перезапуска, перезапускаем уровень
		if(x <= sizeOfWindow) {	
			board.step(x, y);	
		} else if (reset.isPressed(x, y)){
			restartLevel();
		}
	}
	
	public void display() {
		//отрисовываем в порядке: что рисуется первее, то находится сзади
		//сначала задний фон поля
		Graphics.drawImage(BACK, 0, 0, Renderer.HEIGHT, Renderer.HEIGHT);
		//потом само поле
		board.display();
		//меню
		Graphics.drawImage(MENU, Renderer.HEIGHT, 0, MENU_WIDTH, Renderer.HEIGHT);
		//кнопку перезапуска
		reset.display();
		//все лампочки
		for(int i = 0; i < MAX_LEVELS; i++) 
			lights[i].display();
	}
	//возвращает уровень в зависимости от его номера
	private Board getLevel(int level) {
		switch(level) {
		default:
		case 0:
			//последовательно:
			// 1) массив хранящий в себе номера ячеек с демонами
			// 2) номер ячейки с игроком
			// 3) номера ячеек со стенами
			// 4) размер поля
			return new Board(new int[][] { {3, 2}, {6, 6} }, 
					new int[] {6, 3}, 
					new int[][] { {7, 1}, {7, 4}, {8,4 }, {8, 3} },
					sizeOfWindow);
		case 1:
			return new Board(new int[][] {{2,1}, {4,8}},
					new int[] {6,5},
					new int[][] {{3,4},{3,8},{5,7},{5,8},{6,8},{8,4}},
					sizeOfWindow);
		case 2:
			return new Board(new int[][] {{3,4},{8,1}},
					new int[] {6,4},
					new int[][] {{5,3},{5,6},{6,6}},
					sizeOfWindow);
		case 3:
			return new Board(new int[][] {{3,7},{5,2},{7,2}},
					new int[] {5,6},
					new int[][] {{3,6},{4,5},{4,7},{5,8},{6,5},{6,7},{7,6}},
					sizeOfWindow);
		case 4:
			return new Board(new int[][] {{2,7},{8,1}},
					new int[] {5,5},
					new int[][] {{3,5},{5,2},{5,8},{7,5}, {3,2}, {3,1}},
					sizeOfWindow);
		case 5:
			return new Board(new int[][] {{1,1},{8,1},{8,7}},
					new int[] {4,4},
					new int[][] {{3,6},{4,1},{6,2},{7,5},{3,3}},
					sizeOfWindow);
		case 6:
			return new Board(new int[][] {{2,1},{2,8},{7,2}},
					new int[] {5,5},
					new int[][] {{2,2},{2,7},{3,2},{3,7},{5,8},{7,7},{8,7}},
					sizeOfWindow);
		case 7:
			return new Board(new int[][] { {2, 8}, {4, 1} }, 
					new int[] {4, 5}, 
					new int[][] { {4, 6}, {6, 6}},
					sizeOfWindow);
		case 8:
			return new Board(new int[][] {{2,8}, {7,8}, {7,1}},
					new int[] {5, 4},
					new int[][] {{4,3},{4,4},{5,1},{6,1},{6,5},{7,2},{8,6}},
					sizeOfWindow);		
		}
	}
	//при перезапуске уровня перезаписываем текущий уровень
	public void restartLevel() {
		board = getLevel(currentLevel);
	}
	private void restartGame() {
		//выключаем все лампочки
		for(GameObject l : lights)
			l.setTexture(LIG0);
		//выводим сообщение с победой
		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessage(CONGRATS_TEXT_STR);
		JDialog dialog = optionPane.createDialog(CONGRATS_STR);
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
		dialog.setResizable(false);
		//устанавливаем текущий уровень в начало
		currentLevel = 0;
	}
	//следующий уровень
	public void nextLevel() {
		//зажигаем лампочку с текущим уровнем
		lights[currentLevel].setTexture(LIG1);
		//если текущий уровень последний начинаем заново
		if(++currentLevel == MAX_LEVELS) {
			restartGame();
		}
		//перезапускаем новый уровень
		restartLevel();
	}
}
