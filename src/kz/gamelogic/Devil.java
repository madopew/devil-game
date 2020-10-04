package kz.gamelogic;
//������ ������
public class Devil extends Player{
	//����������� �������� ����������� � Player
	public Devil(int i, int j) {
		super(i, j);
	}
	//����� ����������� ���
	public int step(Cell[][] gameBoard, Human human) {
		//��� ������� ���������� ����� ���������� 
		//����������� �� ������
		//� ��� ��������� ����� ������
		//� ����� ����������� ���������� �� ������
		int minRow = getRow();
		int minCol = getCol();
		//����������� ��������� ��� ������������� ������� ������������
		int min = Cell.MAX_DIST;
		for(int i = -1; i <= 1; i++)
			for(int j = -1; j <= 1; j++) {
				//������� ������ ��� ������������ (���� �� 8 ��������)
				Cell current = gameBoard[getRow() + i][getCol() + j];
				//���� ��������� � ��� ������ ������, ��� ����������� ��
				if(current.getDistance() < min) {
					//������������ �����������
					//� ��������� ����� ��� ������
					min = current.getDistance();
					minRow = getRow() + i;
					minCol = getCol() + j;
					//���� ��������� � ��������������� ������ ����� �����������
					//� ��� ���� ����������� ���������� �� ����� �������������
					//(�� ���� ������ ������ ��� ���� �����������)
					//������������� ���� ��������� ������ � ���������� ����������� �� ������
					//����� ������������� ������ ������� ����������� ����� � ������
					//��� ���� ����� ����� ����� ���� ������ �����
				} else if(current.getDistance() == min && min != Cell.MAX_DIST) {
					int distNew = Math.abs(getRow()+i - human.getRow()) + Math.abs(getCol()+j - human.getCol());
					int distOld = Math.abs(minRow - human.getRow()) + Math.abs(minCol - human.getCol());
					//���� ����������� ��������� ������ ��� � �����������
					//�������������
					if(distNew < distOld) {
						minRow = getRow() + i;
						minCol = getCol() + j; 
					}
				}
			}
		//����� ���������� ������ ������������ ������
		setRow(minRow);
		setCol(minCol);
		//� ���������� ���������� �� ������
		//��� ����������� ��������� ��� ������
		return min;
	}
}
