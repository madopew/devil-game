package kz.gamelogic;

import java.util.LinkedList;
import java.util.Queue;

import kz.graphics.EventListener;

public class Board {
	//���� ��� ������ ������
	private Cell[][] gameBoard;
	//������ ���� (10*10 �.�. ������� ������ ������ �����)
	public static final int BOARD_SIZE = 10;
	//������ � ��������
	private Devil[] devils;
	//�����
	private Human human;
	//���������� �������� ����� ����
	//���� true, �� ������� ���� �� ��������������
	//�.�. ������ ��������
	private boolean isSleeping = false;
	//������ ������
	private float cellSize;
	//��������� �� ��������
	private static final float ANIM_TIME = 250;
	private static final int ACT_DEVIL_STEP = 0;
	private static final int ACT_DEVIL_CELL = 1;
	private static final int ACT_HUMAN = 2;
	private static final int ACT_NEXT = 3;
	private static final int ACT_RESET = 4;
	private static final int ACT_CHANGE_STATE = 5;
	//�������������� ����
	public Board(Devil[] devils, Human human, int[][] borders, float sizeOfWindow) {
		init(devils, human, borders, sizeOfWindow);
	}
	//��������� ������� ����� � ��������������� ������� � �������������� ����
	public Board(int[][] devils, int[] human, int[][] borders, float sizeOfWindow) {
		Devil[] _devils = new Devil[devils.length];
		for(int i = 0; i < devils.length; i++)
			_devils[i] = new Devil(devils[i][0], devils[i][1]);
		Human _human = new Human(human[0], human[1]);
		init(_devils, _human, borders, sizeOfWindow);
	}
	//�������������
	private void init(Devil[] devils, Human human, int[][] borders, float sizeOfWindow) {
		//������ ������ = ������ ���� / ���������� ������
		this.cellSize = sizeOfWindow / BOARD_SIZE;
		//������������� ������� � ������
		this.devils = devils;
		this.human = human;
		//�������������� ���� ����
		gameBoard = new Cell[BOARD_SIZE][BOARD_SIZE];
		//������������� ������� �����
		initBorders();
		//������������� ����� �������, ������ � �� ������� ����
		//� ���������
		initDevils(devils);
		gameBoard[human.getRow()][human.getCol()].animateType(Cell.OCCUPIED_HUMAN, ANIM_TIME);
		initNRBorders(borders);
	}
	//��������� ������� ����
	private void initBorders() {
		for(int i = 0; i < BOARD_SIZE; i++)
			for(int j = 0; j < BOARD_SIZE; j++) 
				//���� ������ - �������, ����� �������������� ��� �����
				//����� ��� ������� ������
				if(i == 0 || i == BOARD_SIZE-1 || j == 0 || j == BOARD_SIZE-1)
					gameBoard[i][j] = new Cell(Cell.BORDER, i, j);
				else gameBoard[i][j] = new Cell(Cell.REGULAR, i, j);
	}
	//��������� �������
	private void initDevils(Devil[] devils) {
		for(int i = 0; i < devils.length; i++)
			gameBoard[devils[i].getRow()][devils[i].getCol()].animateType(Cell.OCCUPIED_DEVIL, ANIM_TIME);
	}
	//��������� ���� (�� �������)
	private void initNRBorders(int[][] borders) {
		for(int i = 0; i < borders.length; i++)
			gameBoard[borders[i][0]][borders[i][1]].animateType(Cell.BORDER, ANIM_TIME);
	}
	//��������� ������� ���� � ������� � ���������� ����
	public void step(int x, int y) {
		//���� �������� � �������� ������� �� ��������������
		if(isSleeping) return;
		for(int i = 0; i < BOARD_SIZE; i++) {
			for(int j = 0; j < BOARD_SIZE; j++) {
				//������� ������ �� ������, ������� ���� ������
				//� ������� ��������� ��������������
				if(x > j*cellSize && x < cellSize*(j+1)
				&& y > i * cellSize && y < cellSize*(i+1)) {
					//���� ���� ������ ����� ��� ����� ����� �� ������������
					if(gameBoard[i][j].isBorder() || gameBoard[i][j].isDevil() || gameBoard[i][j].isShield())
						return;
					//���� ���� ������� ��������� �����
					//� ����� �� ����� (�.�. �� �� �������� ���� �� 8
					//�������� ����� ��� ������������) ��
					if(gameBoard[i][j].isRegular() && !human.isDirected()) {
						//�������� ��������
						isSleeping = true;
						//������������� ����� � ��� � ���������
						gameBoard[i][j].animateType(Cell.SHIELD, ANIM_TIME);
						//� ��������� � ������ �������� ����� ��������
						waitAction((int)ANIM_TIME*2, ACT_DEVIL_STEP, 0, 0);
						//����� ������� ����� ��������� isSleeping
						waitAction((int)ANIM_TIME*4, ACT_CHANGE_STATE, 0, 0);
						return;
					}
					//��������� ������� �� ������
					//����������� ������� ������������
					if(gameBoard[i][j].isHuman()) {
						humanStep(i, j);
						return;
					}
					//���� ������ ������� ������������ ��
					if(gameBoard[i][j].isDirectional()) {
						//�������� ��������
						isSleeping = true;
						//��������� �� �����
						directionalStep(i, j);
						//����� ��������
						waitAction((int)ANIM_TIME * 3, ACT_DEVIL_STEP, 0, 0);
						//��������� ��������
						waitAction((int)ANIM_TIME*5, ACT_CHANGE_STATE, 0, 0);
						return;
					}
				}
			}
		}
	}
	//��������� �������� � ���������� ����
	//�� �������
	// 1) �������� � �������������
	// 2) ����� ���������� ����
	// 3 � 4) ����� ������
	private void waitAction(int ms, int action, int i, int j) {
		//�������� �������� �� �������� ������ ������...
		new Thread( new Runnable() {
	        public void run()  {
	        	//...� ������� ������� sleep
	            try  { Thread.sleep( ms ); }
	            catch (InterruptedException ie)  {ie.printStackTrace();}
	            //����� �������� ��������� � ���������� ����
	            //� ����������� �� ��� ������
	            switch(action) {
	            case ACT_DEVIL_STEP:
	            	//����� ������
	            	devilsStep();
	            	break;
	            case ACT_DEVIL_CELL:
	            	//��������� ������ � ������ � ���������
		            gameBoard[i][j].animateType(Cell.OCCUPIED_DEVIL, ANIM_TIME);	
		            break;
	            case ACT_HUMAN:
	            	//��������� ������ � �������� � ���������
		        	gameBoard[i][j].animateType(Cell.OCCUPIED_HUMAN, ANIM_TIME);
		        	break;
	            case ACT_NEXT:
	            	//������� �� ��������� �������
		            EventListener.getMenu().nextLevel();
		            break;
	            case ACT_RESET:
	            	//������������ ������
		            EventListener.getMenu().restartLevel();
		            break;
	            case ACT_CHANGE_STATE:
	            	//������� ��������
	            	isSleeping = false;
	            	break;
	            }
	        }
	    } ).start();
	}	
		
	private void devilsStep() {
		//��������� ��������� �� ��������
		updateDistances();
		//���������� ��������� �� ��������
		//����� ���� ������� ���� ������ ����
		//������������� ����� �������
		int sumOfDist = 0;
		for(Devil d : devils) {
			//������������� ����� ��� ����� ����� � ������� ����� � ���������
			gameBoard[d.getRow()][d.getCol()].animateType(Cell.REGULAR, ANIM_TIME);
			//��������� � �������� ����������� ������
			int dist = d.step(gameBoard, human);
			//���� ��������� ����� ���� �� ������ ��������
			if(dist == 0) {
				//��������� ��������
				gameBoard[d.getRow()][d.getCol()].animateType(Cell.OCCUPIED_DEVIL, ANIM_TIME);
				//������� ����
				clearBoard();
				//������������� �������
				waitAction((int)(ANIM_TIME*1.5), ACT_RESET, 0, 0);
				break;
			}
			//����� ��������� � ����������
			sumOfDist += dist;
			//��������� ��������
			waitAction((int) (ANIM_TIME*1.5), ACT_DEVIL_CELL, d.getRow(), d.getCol());
		}
		//�������� �� ������ ������
		if(sumOfDist < 0) {
			//���� ����� ������� ������ ��������� �������
			clearBoard();
			waitAction((int)ANIM_TIME, ACT_NEXT, 0, 0);
		}
	}
	//������� ���� (��������)
	private void clearBoard() {
		//������������� ��� �� ������� ������ ���� � �������
		for(int i = 1; i < BOARD_SIZE-1; i++) {
			for(int j = 1; j < BOARD_SIZE-1; j++) {
				if(!gameBoard[i][j].isRegular()) {
					gameBoard[i][j].animateType(Cell.REGULAR, ANIM_TIME);
				}
			}
		}
	}
	//��� ��� ������
	private void humanStep(int i, int j) {
		boolean hasChanged = false;
		//������������� ��� �������� ������ �:
		for(int k = -1; k <= 1; k++) {
			for(int l = -1; l <= 1; l++) {
				if(k == 0 && l == 0) continue;
				//���� ������� �� ���������� � �� ��� ���� ����� ������ - � �������
				if(gameBoard[i+k][j+l].isRegular() && !human.isDirected()) {
					gameBoard[i+k][j+l].setDirectionType(k,l, ANIM_TIME);
					hasChanged = true;
				}
				//���� ������� ���������� � �� ��� ���� ����� ���� ������ - � �������
				if(gameBoard[i+k][j+l].isDirectional() && human.isDirected()) {
					gameBoard[i+k][j+l].animateType(Cell.REGULAR, ANIM_TIME);
					hasChanged = true;
				}
			}
		}
		//���� ���� ��� �� ����������
		//� �� ��������� ����� ���� ������ ��������
		//������ ���������� ���������� � ������������ ������� �� ��������
		if(hasChanged)
			human.setDirected(!human.isDirected());
	}
	//��� �� �������
	private void directionalStep(int i, int j) {
		//����� ������� ������ ��� ������� � �������
		for(int k = -1; k <= 1; k++) {
			for(int l = -1; l <= 1; l++) {
				if(k == 0 && l == 0) continue;
				if(gameBoard[human.getRow()+k][human.getCol()+l].isDirectional())
					gameBoard[human.getRow()+k][human.getCol()+l].animateType(Cell.REGULAR, ANIM_TIME);
			}
		}
		//������ ������������ �� false
		human.setDirected(false);
		//������� ��������� ������ ������ �� �������
		gameBoard[human.getRow()][human.getCol()].animateType(Cell.REGULAR, ANIM_TIME);
		//����� ������� � ���������
		human.setRow(i);
		human.setCol(j);
		waitAction((int)(ANIM_TIME*1.5), ACT_HUMAN, i, j);
	}
	//���������� ��������� � �������������� ��������� �����������
	//���� breadth first search alghorithm
	private void updateDistances() {
		//������� ��� ���������� ���������
		clearDistances();
		//�������������� �������
		Queue<Cell> q = new LinkedList<>();
		//���������� ��������� ������ �� ����� � ���������
		gameBoard[human.getRow()][human.getCol()].setDistance(0);
		//��������� � ������� ����� � �������
		q.add(gameBoard[human.getRow()][human.getCol()]);
		while(!q.isEmpty()) {
			//������� �� ������� ������
			Cell curr = q.peek();
			q.remove();
			//��� ������ ������ ���� ����� ����� ��������� �� ���� ������
			for(int i = -1; i <= 1; i++) {
				for(int j = -1; j <= 1; j++) {
					//���� � ��� ������ ����� ����� ��
					if(isValidCell(curr, i, j)) {
						//��������� ���������
						gameBoard[curr.getX() + i][curr.getY() + j].setDistance(curr.getDistance() + 1);
						//��������� � ������� ������ �� ������������ 
						//� ��������� ����������� �����
						q.add(gameBoard[curr.getX() + i][curr.getY() + j]);
					}
				}
			}
		}
	}
	//������� ����������
	private void clearDistances() {
		for(int i = 0; i < BOARD_SIZE; i++)
			for(int j = 0; j < BOARD_SIZE; j++)
				gameBoard[i][j].clearDistance();
	}
	//�������� �� ���������� ������������ ������
	//��� ����� ��� �������� ������
	private boolean isValidCell(Cell curr, int dx, int dy) {
		//��������:
		// 1) ������ � �������� ���� 
		// 2) �� ��� ����� ������ (� ��� ����� � �� ������ ������, �.�. ����� ����� "������" �� ����
		// 3) ��������� �� �����������
		boolean isValid = (curr.getX() + dx > 0 && curr.getX() + dx < BOARD_SIZE-1
				&& curr.getY() + dy > 0 && curr.getY() + dy < BOARD_SIZE-1
				&& gameBoard[curr.getX() + dx][curr.getY() + dy].isMoveable()
				&& gameBoard[curr.getX() + dx][curr.getY() + dy].getDistance() == -1);
		return isValid;
	}
	//���������� ��� ������
	public void display() {
		for(int i = 0; i < BOARD_SIZE; i++) {
			for(int j = 0; j < BOARD_SIZE; j++) {
				gameBoard[i][j].display(cellSize);
			}
		}
	}
}
