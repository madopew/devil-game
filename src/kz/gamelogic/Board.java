package kz.gamelogic;

import java.util.LinkedList;
import java.util.Queue;

import kz.graphics.EventListener;

public class Board {
	//поле как массив клеток
	private Cell[][] gameBoard;
	//размер поля (10*10 т.к. краевые клетки всегда стены)
	public static final int BOARD_SIZE = 10;
	//массив с демонами
	private Devil[] devils;
	//игрок
	private Human human;
	//переменная хранящая режим поля
	//если true, то нажатия мыши не обрабатываются
	//т.к. играет анимация
	private boolean isSleeping = false;
	//размер клетки
	private float cellSize;
	//константы на анимации
	private static final float ANIM_TIME = 250;
	private static final int ACT_DEVIL_STEP = 0;
	private static final int ACT_DEVIL_CELL = 1;
	private static final int ACT_HUMAN = 2;
	private static final int ACT_NEXT = 3;
	private static final int ACT_RESET = 4;
	private static final int ACT_CHANGE_STATE = 5;
	//инициализируем поле
	public Board(Devil[] devils, Human human, int[][] borders, float sizeOfWindow) {
		init(devils, human, borders, sizeOfWindow);
	}
	//переводим массивы чисел в соответствующий объекты и инициализируем поле
	public Board(int[][] devils, int[] human, int[][] borders, float sizeOfWindow) {
		Devil[] _devils = new Devil[devils.length];
		for(int i = 0; i < devils.length; i++)
			_devils[i] = new Devil(devils[i][0], devils[i][1]);
		Human _human = new Human(human[0], human[1]);
		init(_devils, _human, borders, sizeOfWindow);
	}
	//инициализация
	private void init(Devil[] devils, Human human, int[][] borders, float sizeOfWindow) {
		//размер клетки = высота поля / количество клеток
		this.cellSize = sizeOfWindow / BOARD_SIZE;
		//устанавливаем демонов и игрока
		this.devils = devils;
		this.human = human;
		//инициализируем само поле
		gameBoard = new Cell[BOARD_SIZE][BOARD_SIZE];
		//устанавливаем краевые стены
		initBorders();
		//устанавливаем фишки демонов, игрока и НЕ краевых стен
		//с анимацией
		initDevils(devils);
		gameBoard[human.getRow()][human.getCol()].animateType(Cell.OCCUPIED_HUMAN, ANIM_TIME);
		initNRBorders(borders);
	}
	//установка краевых стен
	private void initBorders() {
		for(int i = 0; i < BOARD_SIZE; i++)
			for(int j = 0; j < BOARD_SIZE; j++) 
				//если клетка - краевая, сразу инициализируем как стену
				//иначе как обычную клетку
				if(i == 0 || i == BOARD_SIZE-1 || j == 0 || j == BOARD_SIZE-1)
					gameBoard[i][j] = new Cell(Cell.BORDER, i, j);
				else gameBoard[i][j] = new Cell(Cell.REGULAR, i, j);
	}
	//установка демонов
	private void initDevils(Devil[] devils) {
		for(int i = 0; i < devils.length; i++)
			gameBoard[devils[i].getRow()][devils[i].getCol()].animateType(Cell.OCCUPIED_DEVIL, ANIM_TIME);
	}
	//установка стен (не краевых)
	private void initNRBorders(int[][] borders) {
		for(int i = 0; i < borders.length; i++)
			gameBoard[borders[i][0]][borders[i][1]].animateType(Cell.BORDER, ANIM_TIME);
	}
	//обработка нажатия мыши и переход к следующему шагу
	public void step(int x, int y) {
		//если анимация в процессе нажатие не обрабатывается
		if(isSleeping) return;
		for(int i = 0; i < BOARD_SIZE; i++) {
			for(int j = 0; j < BOARD_SIZE; j++) {
				//находим именно ту клетку, которая была нажата
				//с помощью координат прямоугольника
				if(x > j*cellSize && x < cellSize*(j+1)
				&& y > i * cellSize && y < cellSize*(i+1)) {
					//если была нажата стена или демон никак не обрабатываем
					if(gameBoard[i][j].isBorder() || gameBoard[i][j].isDevil() || gameBoard[i][j].isShield())
						return;
					//если была нажатая свободная фишка
					//и игрок не ходит (т.е. он не выбирает одну из 8
					//соседних фишек для передвежения) то
					if(gameBoard[i][j].isRegular() && !human.isDirected()) {
						//включаем анимацию
						isSleeping = true;
						//устанавливаем фишку в щит с анимацией
						gameBoard[i][j].animateType(Cell.SHIELD, ANIM_TIME);
						//с задержкой с учетом анимаций ходим демонами
						waitAction((int)ANIM_TIME*2, ACT_DEVIL_STEP, 0, 0);
						//после демонов снова выключаем isSleeping
						waitAction((int)ANIM_TIME*4, ACT_CHANGE_STATE, 0, 0);
						return;
					}
					//обработка нажатия на игрока
					//переключает стрелки передвежений
					if(gameBoard[i][j].isHuman()) {
						humanStep(i, j);
						return;
					}
					//если нажата стрелка передвежений то
					if(gameBoard[i][j].isDirectional()) {
						//включаем анимацию
						isSleeping = true;
						//переходим на фишку
						directionalStep(i, j);
						//ходим демонами
						waitAction((int)ANIM_TIME * 3, ACT_DEVIL_STEP, 0, 0);
						//выключаем анимацию
						waitAction((int)ANIM_TIME*5, ACT_CHANGE_STATE, 0, 0);
						return;
					}
				}
			}
		}
	}
	//обработка задержки и следующего шага
	//по порядку
	// 1) задержка в миллисекундах
	// 2) номер следующего шага
	// 3 и 4) номер клетки
	private void waitAction(int ms, int action, int i, int j) {
		//задержка основана на создании нового потока...
		new Thread( new Runnable() {
	        public void run()  {
	        	//...с помощью функции sleep
	            try  { Thread.sleep( ms ); }
	            catch (InterruptedException ie)  {ie.printStackTrace();}
	            //после задержки переходим к следующему шагу
	            //в зависимости от его номера
	            switch(action) {
	            case ACT_DEVIL_STEP:
	            	//ходят демоны
	            	devilsStep();
	            	break;
	            case ACT_DEVIL_CELL:
	            	//установка клетки в демона с анимацией
		            gameBoard[i][j].animateType(Cell.OCCUPIED_DEVIL, ANIM_TIME);	
		            break;
	            case ACT_HUMAN:
	            	//установка клетки в человека с анимацией
		        	gameBoard[i][j].animateType(Cell.OCCUPIED_HUMAN, ANIM_TIME);
		        	break;
	            case ACT_NEXT:
	            	//переход на следующий уровень
		            EventListener.getMenu().nextLevel();
		            break;
	            case ACT_RESET:
	            	//перезагрузка уровня
		            EventListener.getMenu().restartLevel();
		            break;
	            case ACT_CHANGE_STATE:
	            	//убираем анимацию
	            	isSleeping = false;
	            	break;
	            }
	        }
	    } ).start();
	}	
		
	private void devilsStep() {
		//обновляем дистанции до человека
		updateDistances();
		//количество дистанций до человека
		//после всех демонов если меньше нуля
		//следовательно игрок выиграл
		int sumOfDist = 0;
		for(Devil d : devils) {
			//устанавливаем место где стоял демон в обычную фишку с анимацией
			gameBoard[d.getRow()][d.getCol()].animateType(Cell.REGULAR, ANIM_TIME);
			//переходим в наиболее эффективную клетку
			int dist = d.step(gameBoard, human);
			//если дистанция равна нулю то демоны победили
			if(dist == 0) {
				//завершаем анимацию
				gameBoard[d.getRow()][d.getCol()].animateType(Cell.OCCUPIED_DEVIL, ANIM_TIME);
				//очищаем поле
				clearBoard();
				//перезапускаем уровень
				waitAction((int)(ANIM_TIME*1.5), ACT_RESET, 0, 0);
				break;
			}
			//иначе добавляем в переменную
			sumOfDist += dist;
			//завершаем анимацию
			waitAction((int) (ANIM_TIME*1.5), ACT_DEVIL_CELL, d.getRow(), d.getCol());
		}
		//проверка на победу игрока
		if(sumOfDist < 0) {
			//если игрок победил ставим следующий уровень
			clearBoard();
			waitAction((int)ANIM_TIME, ACT_NEXT, 0, 0);
		}
	}
	//очистка поля (анимация)
	private void clearBoard() {
		//устанавливаем все НЕ обычные ячейки поля в обычные
		for(int i = 1; i < BOARD_SIZE-1; i++) {
			for(int j = 1; j < BOARD_SIZE-1; j++) {
				if(!gameBoard[i][j].isRegular()) {
					gameBoard[i][j].animateType(Cell.REGULAR, ANIM_TIME);
				}
			}
		}
	}
	//шаг для игрока
	private void humanStep(int i, int j) {
		boolean hasChanged = false;
		//устанавливаем все соседнии ячейки в:
		for(int k = -1; k <= 1; k++) {
			for(int l = -1; l <= 1; l++) {
				if(k == 0 && l == 0) continue;
				//если стрелки не отображены и на это поле можно ходить - в стрелку
				if(gameBoard[i+k][j+l].isRegular() && !human.isDirected()) {
					gameBoard[i+k][j+l].setDirectionType(k,l, ANIM_TIME);
					hasChanged = true;
				}
				//если стрелки отображены и на это поле можно было хоидть - в обычную
				if(gameBoard[i+k][j+l].isDirectional() && human.isDirected()) {
					gameBoard[i+k][j+l].animateType(Cell.REGULAR, ANIM_TIME);
					hasChanged = true;
				}
			}
		}
		//если хоть что то изменилось
		//а не изменится может если игрока окружили
		//меняем логическую переменную с отображением стрелок на обратную
		if(hasChanged)
			human.setDirected(!human.isDirected());
	}
	//шаг на стрелку
	private void directionalStep(int i, int j) {
		//после нажатия ставим все стрелки в обычные
		for(int k = -1; k <= 1; k++) {
			for(int l = -1; l <= 1; l++) {
				if(k == 0 && l == 0) continue;
				if(gameBoard[human.getRow()+k][human.getCol()+l].isDirectional())
					gameBoard[human.getRow()+k][human.getCol()+l].animateType(Cell.REGULAR, ANIM_TIME);
			}
		}
		//меняем направленние на false
		human.setDirected(false);
		//прошлое положение игрока ставим на обычную
		gameBoard[human.getRow()][human.getCol()].animateType(Cell.REGULAR, ANIM_TIME);
		//ходим игроком с анимацией
		human.setRow(i);
		human.setCol(j);
		waitAction((int)(ANIM_TIME*1.5), ACT_HUMAN, i, j);
	}
	//обновление дистанций с использованием алгоритма кратчайшего
	//пути breadth first search alghorithm
	private void updateDistances() {
		//очищаем все предыдущие дистанции
		clearDistances();
		//инициализируем очередь
		Queue<Cell> q = new LinkedList<>();
		//наименьшую дистанцию ставим на фишке с человеком
		gameBoard[human.getRow()][human.getCol()].setDistance(0);
		//добавляем в очередь фишку с игроком
		q.add(gameBoard[human.getRow()][human.getCol()]);
		while(!q.isEmpty()) {
			//достаем из очереди клетку
			Cell curr = q.peek();
			q.remove();
			//все клетки вокруг этой фишки имеют дистанцию на одну больше
			for(int i = -1; i <= 1; i++) {
				for(int j = -1; j <= 1; j++) {
					//если в эту клетку можно зайти то
					if(isValidCell(curr, i, j)) {
						//обновляем дистанцию
						gameBoard[curr.getX() + i][curr.getY() + j].setDistance(curr.getDistance() + 1);
						//добавляем в очередь клетку на рассмотрении 
						//в следующем прохождении цикла
						q.add(gameBoard[curr.getX() + i][curr.getY() + j]);
					}
				}
			}
		}
	}
	//очистка расстояний
	private void clearDistances() {
		for(int i = 0; i < BOARD_SIZE; i++)
			for(int j = 0; j < BOARD_SIZE; j++)
				gameBoard[i][j].clearDistance();
	}
	//проверка на валидность рассмотрения клетки
	//как места для хождения демона
	private boolean isValidCell(Cell curr, int dx, int dy) {
		//провряем:
		// 1) клетка в пределах поля 
		// 2) на нее можно ходить (в том числе и на самого игрока, т.к. демон может "ходить" на него
		// 3) дистанция не установлена
		boolean isValid = (curr.getX() + dx > 0 && curr.getX() + dx < BOARD_SIZE-1
				&& curr.getY() + dy > 0 && curr.getY() + dy < BOARD_SIZE-1
				&& gameBoard[curr.getX() + dx][curr.getY() + dy].isMoveable()
				&& gameBoard[curr.getX() + dx][curr.getY() + dy].getDistance() == -1);
		return isValid;
	}
	//отображаем все клетки
	public void display() {
		for(int i = 0; i < BOARD_SIZE; i++) {
			for(int j = 0; j < BOARD_SIZE; j++) {
				gameBoard[i][j].display(cellSize);
			}
		}
	}
}
