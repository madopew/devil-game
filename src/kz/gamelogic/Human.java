package kz.gamelogic;
//логика игрока
public class Human extends Player {
	//переменная хранит в себе состояния стрелок вокруг игрока
	// если стрелки отображена то переменная равна true
	private boolean isDirected;
	//конструктор вызывает конструктор из Player
	public Human(int i, int j) {
		super(i, j);
		setDirected(false);
	}
	//геттер и сеттер для переменной
	public boolean isDirected() {
		return isDirected;
	}
	public void setDirected(boolean isDirected) {
		this.isDirected = isDirected;
	}
}
