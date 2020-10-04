package kz.gamelogic;

import kz.engine.Menu;
import kz.graphics.Graphics;
import kz.graphics.ImageResource;
import kz.graphics.Renderer;
//игровая клетка
public class Cell {
	//переменная содержащая в себе вид клетки
	private int type;
	//константы видов клеток
	public static final int REGULAR = 1;
	public static final int BORDER = 2;
	public static final int SHIELD = 3;
	public static final int OCCUPIED_DEVIL = 4;
	public static final int OCCUPIED_HUMAN = 5;
	public static final int DIR_UL = 7;
	public static final int DIR_UU = 8;
	public static final int DIR_UR = 9;
	public static final int DIR_CL = 10;
	public static final int DIR_CR = 11;
	public static final int DIR_DL = 12;
	public static final int DIR_DD = 13;
	public static final int DIR_DR = 14;
	//максимальная дистанция до игрока
	public static final int MAX_DIST = 99;
	//номер клетки (не координаты)
	private int x;
	private int y;
	//дистанция до игрока
	private int dist;
	//текстура
	private ImageResource image;
	//угол поворота текстуры
	private float rotation = 0;
	//светлая или темная текстура
	private static final int DARK_INDEX = 1;
	//переменные для анимации смены текстуры
	private boolean isAnimated = false;
	private float deltaSize = 0;
	private float dDeltaSize = 0;
	public static final int S_TO_MS = 1000;
	//геттер для типа
	public int getType() {
		return type;
	}
	//смена типа 
	public void setType(int type) {
		this.type = type;
		//если тип существует, тогда меняет текстуру
		if(type != -1)
			updateTexture();
	}
	//смена типа с анимацией 
	public void animateType(int type, float time) {
		//меняем переменную
		this.type = type;
		//количество кадров для ПОЛОВИНЫ анимации (т.к
		//анимация состоит из двух этапов)
		int animFrames = (int)Math.ceil(time * Renderer.FPS / (2 * S_TO_MS));
		//на сколько пикселей за один кадр нужно сжать (выглядит как повернуть) клетку
		dDeltaSize = Renderer.HEIGHT / Board.BOARD_SIZE / animFrames;
		//включаем анимацию
		isAnimated = true;
	}
	//смена текстуры
	private void updateTexture() {
		//текстур стрелок только две
		//остальное зависит от поворота
		rotation = 0;
		switch(type) {
		case DIR_UR:
		case DIR_CR:
			rotation = 90;
			break;
		case DIR_DR:
		case DIR_DD:
			rotation = 180;
			break;
		case DIR_DL:
		case DIR_CL:
			rotation = -90;
			break;
		}
		//каждый тип имеет свою текстуру
		switch(type) {
		case REGULAR:
			//каждая клетка чередуется ТЕМНАЯ или СВЕТЛАЯ
			if((x + y) % 2 == DARK_INDEX)
				image = Menu.REG0;
			else
				image = Menu.REG1;
		break;
		case BORDER:
			if((x + y) % 2 == DARK_INDEX)
				image = Menu.BOR0;
			else
				image = Menu.BOR1;
		break;
		case SHIELD:
			if((x + y) % 2 == DARK_INDEX)
				image = Menu.SHI0;
			else
				image = Menu.SHI1;
		break;
		case OCCUPIED_DEVIL:
			if((x + y) % 2 == DARK_INDEX)
				image = Menu.DEV0;
			else
				image = Menu.DEV1;
		break;
		case OCCUPIED_HUMAN:
			if((x + y) % 2 == DARK_INDEX)
				image = Menu.HUM0;
			else
				image = Menu.HUM1;
		break;
		case DIR_UL:
		case DIR_UR:
		case DIR_DL:
		case DIR_DR:
			if((x + y) % 2 == DARK_INDEX)
				image = Menu.COR0;
			else
				image = Menu.COR1;
		break;
		case DIR_UU:
		case DIR_CL:
		case DIR_CR:
		case DIR_DD:
			if((x + y) % 2 == DARK_INDEX)
				image = Menu.FOR0;
			else
				image = Menu.FOR1;
		break;
		}
	}
	//геттер для дистанции
	public int getDistance() {
		return dist;
	}
	//сеттер для дистанции
	public void setDistance(int dist) {
		this.dist = dist;
	}
	//очиста дистанции
	public void clearDistance() {
		int dist;
		//если на клетку нельзя вставать устанавлием максимальное значение
		//иначе очищаем (0 нельзя так как 0 это игрок)
		if(isBorder() || isShield())
			dist = MAX_DIST;
		else
			dist = -1;
		setDistance(dist);
	}
	//геттер для строки
	public int getX() {
		return x;
	}
	//геттер для столбца
	public int getY() {
		return y;
	}
	//конструктор
	Cell(int type, int _x, int _y) {
		x = _x;
		y = _y;
		setType(type);
		setDistance(-1);
	}
	//если тип не задан то оставляем -1 
	Cell(int _x, int _y) {
		setType(-1);
		setDistance(-1);
		x = _x;
		y = _y;
	}
	//проверка на инициализированность клетки
	public boolean isInitialized() {
		boolean isInit = (type != 1);
		return isInit;
	}
	//проверки на тип клеток...
	
	public boolean isRegular() {
		boolean isReg = (type == REGULAR);
		return isReg;
	}
	
	public boolean isBorder() {
		boolean isBor = (type == BORDER);
		return isBor;
	}
	
	public boolean isShield() {
		boolean isShi = (type == SHIELD);
		return isShi;
	}
	
	public boolean isDevil() {
		boolean isDev = (type == OCCUPIED_DEVIL);
		return isDev;
	}
	
	public boolean isHuman() {
		boolean isHum = (type == OCCUPIED_HUMAN);
		return isHum;
	}
	//можно ли передвинуться на это клетку?
	public boolean isMoveable() {
		//OCCUPIED_DEVIL, т.к. демоны могут вставать на одну клетку
		boolean isMov = (type == REGULAR || type == OCCUPIED_DEVIL || type == OCCUPIED_HUMAN);
		return isMov;
	}
	//устанавливаем соответстующие направления для стрелки _i _j
	public void setDirectionType(int _i, int _j, float time) {
		int dir = DIR_UL;
		for(int i = -1; i <=1; i++) {
			for(int j = -1; j <=1; j++) {
				if(i == 0 && j == 0) continue;
				if(i == _i && j == _j) {
					animateType(dir, time);
					break;
				}
				dir++;
			}
		}
	}
	//проверка на тип (является ли стрелкой)
	public boolean isDirectional() {
		boolean isDir = (type >= DIR_UL && type <= DIR_DR);
		return isDir;
	}
	//отображение с анимацией
	public void display(float cellSize) {	
		//если анимация включена
		if(isAnimated) {
			//ширина отображаем клеткой
			deltaSize += dDeltaSize;
			//для стрелок повернутые на 90 градусов
			//ширина становится высотой
			if(rotation == 90 || rotation == -90)
				Graphics.drawImageRot(image, y*cellSize, x*cellSize + deltaSize / 2, cellSize, cellSize- deltaSize, rotation, 0, 0, 1);
			else
				Graphics.drawImageRot(image, y*cellSize + deltaSize / 2, x*cellSize, cellSize - deltaSize, cellSize, rotation, 0, 0, 1);
			//если ширина больше размера клетки
			//меняем текстуру и разоварачиваем клетку в
			//противоположенное направление
			if(deltaSize >= cellSize) {
				dDeltaSize = -dDeltaSize;
				if(type != -1) {
					updateTexture();
				}
			}
			//как только ширина становится меньше нуля (т.е. анимация закончилась)
			//устанавливаем ее в ноль и выклюаччем анимацию
			if(deltaSize <= 0) {
				deltaSize = 0;
				isAnimated = false;
			}
			
			/*
			 * по итогу это будет выглядеть так
			 * 1) клетка уменьшается по ширине 
			 * до тех пор пока ширина не станет равна 0
			 * 2) меняется текстура клетки
			 * 3) клетка расширяется обратно до первоначальных размеров
			 * 
			 * для пользователя это выглядит так
			 * будто клетка развернулась по оси Х
			 * */
		}
		else
			//без анимации просто рисуем
			Graphics.drawImageRot(image, y*cellSize, x*cellSize, cellSize, cellSize, rotation, 0, 0, 1);
	}
}
