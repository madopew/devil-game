package kz.engine;

import kz.graphics.Graphics;
import kz.graphics.ImageResource;

//игровой объект с текстурой и обработкой нажатия
public class GameObject {
	//переменные для хранения координат левого верхнего угла объекта
	private int x;
	private int y;
	//переменные для хранения ширины и высоты объекта
	private int w;
	private int h;
	//текстура объекта
	private ImageResource img;
	//инициализация объекта
	public GameObject(int x, int y, int w, int h, ImageResource img) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.img = img;
	}
	//сеттер на текстуку
	public void setTexture(ImageResource img) {
		this.img = img;
	}
	//возвращает true если указатель мыши нажал на этот объект
	public boolean isPressed(int _x, int _y) {
		//если координата указателя входит в прямоугольник 
		//где левый верхний угол - координата объекта
		//правый нижний - координата + высота/ширина
		//вернуть true
		boolean inRange = (_x >= x && _x <= x+w && _y >= y && _y <= y+h);
		return inRange;
	}
	//отрисовывает объект
	public void display() {
		//отрисовать текстуру img в координатах x, y с размерами w h
		Graphics.drawImage(img, x, y, w, h);
	}
}
