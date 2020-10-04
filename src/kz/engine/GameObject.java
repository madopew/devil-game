package kz.engine;

import kz.graphics.Graphics;
import kz.graphics.ImageResource;

//������� ������ � ��������� � ���������� �������
public class GameObject {
	//���������� ��� �������� ��������� ������ �������� ���� �������
	private int x;
	private int y;
	//���������� ��� �������� ������ � ������ �������
	private int w;
	private int h;
	//�������� �������
	private ImageResource img;
	//������������� �������
	public GameObject(int x, int y, int w, int h, ImageResource img) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.img = img;
	}
	//������ �� ��������
	public void setTexture(ImageResource img) {
		this.img = img;
	}
	//���������� true ���� ��������� ���� ����� �� ���� ������
	public boolean isPressed(int _x, int _y) {
		//���� ���������� ��������� ������ � ������������� 
		//��� ����� ������� ���� - ���������� �������
		//������ ������ - ���������� + ������/������
		//������� true
		boolean inRange = (_x >= x && _x <= x+w && _y >= y && _y <= y+h);
		return inRange;
	}
	//������������ ������
	public void display() {
		//���������� �������� img � ����������� x, y � ��������� w h
		Graphics.drawImage(img, x, y, w, h);
	}
}
