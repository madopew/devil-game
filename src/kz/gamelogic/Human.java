package kz.gamelogic;
//������ ������
public class Human extends Player {
	//���������� ������ � ���� ��������� ������� ������ ������
	// ���� ������� ���������� �� ���������� ����� true
	private boolean isDirected;
	//����������� �������� ����������� �� Player
	public Human(int i, int j) {
		super(i, j);
		setDirected(false);
	}
	//������ � ������ ��� ����������
	public boolean isDirected() {
		return isDirected;
	}
	public void setDirected(boolean isDirected) {
		this.isDirected = isDirected;
	}
}
