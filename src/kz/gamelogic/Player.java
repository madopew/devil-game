package kz.gamelogic;
//���������� ��� ������ � ������
public class Player{
	//����� ������ � �������
	private int row;
	private int col;
	//������� � �������
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
	//�����������
	public Player(int i, int j) {
		setRow(i);
		setCol(j);
	}
}
