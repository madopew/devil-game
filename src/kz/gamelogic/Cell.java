package kz.gamelogic;

import kz.engine.Menu;
import kz.graphics.Graphics;
import kz.graphics.ImageResource;
import kz.graphics.Renderer;
//������� ������
public class Cell {
	//���������� ���������� � ���� ��� ������
	private int type;
	//��������� ����� ������
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
	//������������ ��������� �� ������
	public static final int MAX_DIST = 99;
	//����� ������ (�� ����������)
	private int x;
	private int y;
	//��������� �� ������
	private int dist;
	//��������
	private ImageResource image;
	//���� �������� ��������
	private float rotation = 0;
	//������� ��� ������ ��������
	private static final int DARK_INDEX = 1;
	//���������� ��� �������� ����� ��������
	private boolean isAnimated = false;
	private float deltaSize = 0;
	private float dDeltaSize = 0;
	public static final int S_TO_MS = 1000;
	//������ ��� ����
	public int getType() {
		return type;
	}
	//����� ���� 
	public void setType(int type) {
		this.type = type;
		//���� ��� ����������, ����� ������ ��������
		if(type != -1)
			updateTexture();
	}
	//����� ���� � ��������� 
	public void animateType(int type, float time) {
		//������ ����������
		this.type = type;
		//���������� ������ ��� �������� �������� (�.�
		//�������� ������� �� ���� ������)
		int animFrames = (int)Math.ceil(time * Renderer.FPS / (2 * S_TO_MS));
		//�� ������� �������� �� ���� ���� ����� ����� (�������� ��� ���������) ������
		dDeltaSize = Renderer.HEIGHT / Board.BOARD_SIZE / animFrames;
		//�������� ��������
		isAnimated = true;
	}
	//����� ��������
	private void updateTexture() {
		//������� ������� ������ ���
		//��������� ������� �� ��������
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
		//������ ��� ����� ���� ��������
		switch(type) {
		case REGULAR:
			//������ ������ ���������� ������ ��� �������
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
	//������ ��� ���������
	public int getDistance() {
		return dist;
	}
	//������ ��� ���������
	public void setDistance(int dist) {
		this.dist = dist;
	}
	//������ ���������
	public void clearDistance() {
		int dist;
		//���� �� ������ ������ �������� ����������� ������������ ��������
		//����� ������� (0 ������ ��� ��� 0 ��� �����)
		if(isBorder() || isShield())
			dist = MAX_DIST;
		else
			dist = -1;
		setDistance(dist);
	}
	//������ ��� ������
	public int getX() {
		return x;
	}
	//������ ��� �������
	public int getY() {
		return y;
	}
	//�����������
	Cell(int type, int _x, int _y) {
		x = _x;
		y = _y;
		setType(type);
		setDistance(-1);
	}
	//���� ��� �� ����� �� ��������� -1 
	Cell(int _x, int _y) {
		setType(-1);
		setDistance(-1);
		x = _x;
		y = _y;
	}
	//�������� �� �������������������� ������
	public boolean isInitialized() {
		boolean isInit = (type != 1);
		return isInit;
	}
	//�������� �� ��� ������...
	
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
	//����� �� ������������� �� ��� ������?
	public boolean isMoveable() {
		//OCCUPIED_DEVIL, �.�. ������ ����� �������� �� ���� ������
		boolean isMov = (type == REGULAR || type == OCCUPIED_DEVIL || type == OCCUPIED_HUMAN);
		return isMov;
	}
	//������������� �������������� ����������� ��� ������� _i _j
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
	//�������� �� ��� (�������� �� ��������)
	public boolean isDirectional() {
		boolean isDir = (type >= DIR_UL && type <= DIR_DR);
		return isDir;
	}
	//����������� � ���������
	public void display(float cellSize) {	
		//���� �������� ��������
		if(isAnimated) {
			//������ ���������� �������
			deltaSize += dDeltaSize;
			//��� ������� ���������� �� 90 ��������
			//������ ���������� �������
			if(rotation == 90 || rotation == -90)
				Graphics.drawImageRot(image, y*cellSize, x*cellSize + deltaSize / 2, cellSize, cellSize- deltaSize, rotation, 0, 0, 1);
			else
				Graphics.drawImageRot(image, y*cellSize + deltaSize / 2, x*cellSize, cellSize - deltaSize, cellSize, rotation, 0, 0, 1);
			//���� ������ ������ ������� ������
			//������ �������� � �������������� ������ �
			//����������������� �����������
			if(deltaSize >= cellSize) {
				dDeltaSize = -dDeltaSize;
				if(type != -1) {
					updateTexture();
				}
			}
			//��� ������ ������ ���������� ������ ���� (�.�. �������� �����������)
			//������������� �� � ���� � ���������� ��������
			if(deltaSize <= 0) {
				deltaSize = 0;
				isAnimated = false;
			}
			
			/*
			 * �� ����� ��� ����� ��������� ���
			 * 1) ������ ����������� �� ������ 
			 * �� ��� ��� ���� ������ �� ������ ����� 0
			 * 2) �������� �������� ������
			 * 3) ������ ����������� ������� �� �������������� ��������
			 * 
			 * ��� ������������ ��� �������� ���
			 * ����� ������ ������������ �� ��� �
			 * */
		}
		else
			//��� �������� ������ ������
			Graphics.drawImageRot(image, y*cellSize, x*cellSize, cellSize, cellSize, rotation, 0, 0, 1);
	}
}
