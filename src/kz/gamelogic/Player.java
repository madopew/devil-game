package kz.gamelogic;
//суперкласс для игрока и демона
public class Player{
	//номер строки и столбца
	private int row;
	private int col;
	//геттеры и сеттеры
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	//конструктор
	public Player(int i, int j) {
		setRow(i);
		setCol(j);
	}
}
