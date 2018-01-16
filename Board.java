package gameapp;

public class Board {
	private int row;
	private int column;
	private char board[][];
	Board(int row_size,int column_size) {
		  row = row_size;
		  column= column_size;
		  board  = new char[row_size][column_size];
		  for (int i = 0; i < row; ++i) {
		    for (int j = 0;j < column; ++j) {
		      board[i][j] = ' ';
		    }
		  }
		  /*board[row/2-1][column/2-1] = 'O';
		  board[row/2-1][column/2] = 'X';
		  board[row/2][column/2-1] = 'X';
		  board[row/2][column/2] = 'O';
		}

		void print() {
			System.out.println("Current board:\n\n");
		  for (int i = 1; i < column + 1; i++){
			  System.out.print(" | " +i);
		  }
		  System.out.println(" |");
		  System.out.print("--");
		  for (int i = 0; i < column; i++){
			  System.out.print("----");
		  }
		  System.out.println();
		  for (int i = 0; i < row; ++i) {
		    System.out.print(i+1 +"| ");
		    for (int j = 0; j < column; ++j) {
		    	System.out.print(board[i][j]+ " | ");
		    }
		    System.out.println();
		    System.out.print("--");
		    for (int k = 0; k < column; k++){
		    	System.out.print("----");
		    }
		    System.out.println();
		  }
		}

		char[][] getBoardTable() {
		  return board;
		}
		void setBoardTable(char[][] table) {
		  board = table;
		}
		int getRowSize() {
		  return row;
		}
		int getColumnSize() {
		  return column;
		}*/
	}
}
