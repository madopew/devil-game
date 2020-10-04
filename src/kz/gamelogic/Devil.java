package kz.gamelogic;
//логика демона
public class Devil extends Player{
	//конструктор вызывает конструктор в Player
	public Devil(int i, int j) {
		super(i, j);
	}
	//самый оптимальный шаг
	public int step(Cell[][] gameBoard, Human human) {
		//эта функция вызывается после заполнения 
		//дистанциями до игрока
		//в ней находится номер клетки
		//с самой оптимальной дистанцией до игрока
		int minRow = getRow();
		int minCol = getCol();
		//минимальное расстоние для инициализации возьмем максимальным
		int min = Cell.MAX_DIST;
		for(int i = -1; i <= 1; i++)
			for(int j = -1; j <= 1; j++) {
				//достаем клетку для рассмотрения (одну из 8 соседних)
				Cell current = gameBoard[getRow() + i][getCol() + j];
				//если дистанция в это клетке меньше, чем минимальная то
				if(current.getDistance() < min) {
					//переохраняем минимальную
					//и сохраняем номер это клетки
					min = current.getDistance();
					minRow = getRow() + i;
					minCol = getCol() + j;
					//если дистанция в рассматриваемой клетке равна минимальной
					//и при этом минимальное расстояние не равно максимальному
					//(то есть другие клетки уже были рассмотрены)
					//соотвественно есть несколько клеток с одинаковым расстоянием до игрока
					//тогда рассматриваем клетку которая ДИАГОНАЛЬНО ближе к игроку
					//для того чтобы точно знать куда пойдет демон
				} else if(current.getDistance() == min && min != Cell.MAX_DIST) {
					int distNew = Math.abs(getRow()+i - human.getRow()) + Math.abs(getCol()+j - human.getCol());
					int distOld = Math.abs(minRow - human.getRow()) + Math.abs(minCol - human.getCol());
					//если диагонально дистанция меньше чем в минимальном
					//пересохраняем
					if(distNew < distOld) {
						minRow = getRow() + i;
						minCol = getCol() + j; 
					}
				}
			}
		//после нахождения клетки переставляем демона
		setRow(minRow);
		setCol(minCol);
		//и возвращаем расстояние до игрока
		//для определения поражения или победы
		return min;
	}
}
