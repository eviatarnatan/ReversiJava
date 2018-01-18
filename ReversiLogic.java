package gameapp;
import java.util.Vector;
public class ReversiLogic implements GameLogic{
	public Vector<Point> availableMoves(Board board,
		     char symbol,char other_symbol) {
		  Vector<Point> availableMoves = new Vector<Point>();
		  verticalMoves(board, availableMoves, symbol, other_symbol);
		  horizontalMoves(board, availableMoves, symbol, other_symbol);
		  topDiagonalMoves(board, availableMoves, symbol, other_symbol);
		  bottomDiagonalMoves(board, availableMoves, symbol, other_symbol);
		  return availableMoves;

		}
		public int flipDiscs(Board board,Point ref_point,
		    char symbol,char other_symbol) {
		  int counter = 0;
		  int counter_ref = 0;
		  int add=0;
		  add = verticalFlip(board, counter_ref, ref_point, symbol, other_symbol);
		  counter = counter + add;
		  add = horizontalFlip(board, counter_ref, ref_point, symbol, other_symbol);
		  counter = counter + add;
		  add = topDiagonalFlip(board, counter_ref, ref_point, symbol, other_symbol);
		  counter = counter + add;
		  add = bottomDiagonalFlip(board, counter_ref, ref_point, symbol, other_symbol);
		  counter = counter + add;
		  return counter;
		}
		public void sortPoints(Vector<Point>moves) {
		  //sort by x value
		  for (int i = 0; i < moves.size() - 1; i++) {
		    for (int j = i + 1; j < moves.size(); j++) {
		      if (moves.get(i).getPointX() > moves.get(j).getPointX()) {
		        Point p= new Point(moves.get(i).getPointX(), moves.get(i).getPointY());
		        moves.get(i).setPointX(moves.get(j).getPointX());
		        moves.get(i).setPointY(moves.get(j).getPointY());
		        moves.get(j).setPointX(p.getPointX());
		        moves.get(j).setPointY(p.getPointY());
		      }
		    }
		  }
		  //sort by y value
		  for (int i = 0; i < moves.size() - 1; i++) {
		    for (int j = i + 1; j < moves.size(); j++) {
		      if (moves.get(i).getPointY() > moves.get(j).getPointY()
		          &&moves.get(i).getPointX() == moves.get(j).getPointX()) {
		        Point p= new Point(moves.get(i).getPointX(), moves.get(i).getPointY());
		        moves.get(i).setPointX(moves.get(j).getPointX());
		        moves.get(i).setPointY(moves.get(j).getPointY());
		        moves.get(j).setPointX(p.getPointX());
		        moves.get(j).setPointY(p.getPointY());
		      }
		    }
		  }
		}
		public void removeDuplicatePoints(Vector<Point>moves) {
		  for (int i = 0; i < moves.size() - 1; i++) {
		    for (int j = i + 1; j < moves.size(); j++){
		      if (moves.get(i).getPointY() == moves.get(j).getPointY()
		          && moves.get(i).getPointX() == moves.get(j).getPointX()) {
		        moves.remove(j);
		        j--;
		      }
		    }
		  }
		}
		void verticalMoves(Board board, Vector<Point> moves,
		    char current, char standby){
		  //up
		  int row_size = board.getBoardSize();
		  int column_size = board.getBoardSize();
		  Cell[][] board_array = board.getBoardTable();
		  for (int i = 0; i < row_size; i++) {
		    for (int j = 0; j < column_size; j++) {
		      if (board_array[i][j].getPlayer() == current) {
		        int l = i - 1, m = j;
		        if (l > 0) {
		          if (board_array[l][m].getPlayer() == standby && l > 0) {
		            while (board_array[l][m].getPlayer() == standby && l > 0)
		              l--;
		            if (board_array[l][m].getPlayer() == ' ') {
		              Point point= new Point(l + 1, m + 1);
		              moves.add(point);
		            }
		          }
		        }
		      }
		    }
		  }
		  //down
		  for (int i = 0; i < row_size; i++) {
		    for (int j = 0; j < column_size; j++) {
		      if (board_array[i][j].getPlayer() == current) {
		        int l = i + 1, m = j;
		        if (l < row_size) {
		          if (board_array[l][m].getPlayer() == standby && l < row_size) {
		            while (board_array[l][m].getPlayer() == standby && l < row_size) {
		              l++;
		              if (l >= row_size) {
		                break;
		              }
		            }
		            if (l < row_size){
		              if (board_array[l][m].getPlayer() == ' ') {
		                Point point = new Point(l + 1, m + 1);
		                moves.add(point);
		              }
		            }
		          }
		        }
		      }
		    }
		  }
		}
		void horizontalMoves(Board board, Vector<Point> moves,
		    char current, char standby){
		  //right
		  int row_size = board.getBoardSize();
		  int column_size = board.getBoardSize();
		  Cell[][] board_array = board.getBoardTable();
		  for (int i = 0; i < row_size; i++) {
		    for (int j = 0; j < column_size; j++) {
		      if (board_array[i][j].getPlayer() == current) {
		        int l = i,m = j + 1;
		        if (m < column_size-1) {
		          if (board_array[l][m].getPlayer() == standby && m < column_size - 1) {
		            while (board_array[l][m].getPlayer() == standby && m < column_size - 1) {
		              m++;
		            }
		            if (m < column_size){
		              if (board_array[l][m].getPlayer() == ' '){
		                Point point= new Point(l + 1, m + 1);
		                moves.add(point);
		              }
		            }
		          }
		        }
		      }
		    }
		  }
		  //left
		  for (int i = 0; i < row_size; i++) {
		    for (int j = 0; j < column_size; j++) {
		      if (board_array[i][j].getPlayer() == current) {
		        int l = i, m = j - 1;
		        if (m > 0) {
		          if (board_array[l][m].getPlayer() == standby && m > 0) {
		            while (board_array[l][m].getPlayer() == standby && m > 0)
		              m--;
		            if (board_array[l][m].getPlayer() == ' ') {
		              Point point = new Point(l + 1, m + 1);
		              moves.add(point);
		            }
		          }
		        }
		      }
		    }
		  }
		}
		void topDiagonalMoves(Board board,Vector<Point>  moves,
		    char current,char standby) {
		  //top left
		  int row_size = board.getBoardSize();
		  int column_size = board.getBoardSize();
		  Cell[][] board_array = board.getBoardTable();
		  for (int i = 0; i < row_size; i++) {
		    for (int j = 0; j < column_size; j++) {
		      if (board_array[i][j].getPlayer() == current) {
		        int l = i - 1, m = j - 1;
		        if (l > 0 && m > 0) {
		          if (board_array[l][m].getPlayer() == standby && l > 0 && m > 0) {
		            while (board_array[l][m].getPlayer() == standby && l > 0 && m > 0){
		              l--;
		              m--;
		            }
		            if (m < column_size) {
		              if (board_array[l][m].getPlayer() == ' ') {
		                Point point= new Point(l + 1, m + 1);
		                moves.add(point);
		              }
		            }
		          }
		        }
		      }
		    }
		  }
		  //top right
		  for (int i = 0; i < row_size; i++) {
		    for (int j = 0; j < column_size; j++){
		      if (board_array[i][j].getPlayer() == current) {
		        int l = i - 1,m = j + 1;
		        if (l > 0 && m < column_size) {
		          if (board_array[l][m].getPlayer() == standby && l > 0 && m < column_size - 1) {
		            while (board_array[l][m].getPlayer() == standby && l > 0
		                && m < column_size - 1) {
		              l--;
		              m++;
		            }
		            if (board_array[l][m].getPlayer() == ' ') {
		              Point point = new Point(l + 1, m + 1);
		              moves.add(point);
		            }
		          }
		        }
		      }
		    }
		  }
		}
		void bottomDiagonalMoves(Board board,Vector<Point> moves,
		    char current,char standby){
		  //bottom left
		  final int row_size = board.getBoardSize();
		  final int column_size = board.getBoardSize();
		  Cell[][] board_array = board.getBoardTable();
		  for (int i = 0; i < row_size; i++) {
		    for (int j = 0; j < column_size; j++) {
		      if (board_array[i][j].getPlayer() == current) {
		        int l = i + 1, m = j - 1;
		        if (l < row_size && m >= 0) {
		          if (board_array[l][m].getPlayer() == standby && l < row_size && m >= 0) {
		            while (board_array[l][m].getPlayer() == standby && l < row_size && m >= 0) {
		              l++;
		              m--;
		              if (l >= row_size || m < 0) {
		                break;
		              }
		            }
		            if (l < row_size && m >= 0) {
		              if (board_array[l][m].getPlayer() == ' ') {
		                Point point = new Point(l + 1, m + 1);
		                moves.add(point);
		              }
		            }
		          }
		        }
		      }
		    }
		  }
		  //bottom right
		  for (int i = 0; i < row_size; i++) {
		    for (int j = 0; j < column_size; j++) {
		      if (board_array[i][j].getPlayer() == current) {
		        int l = i + 1, m = j + 1;
		        if (l < row_size && m < column_size) {
		          if (board_array[l][m].getPlayer() == standby && l < row_size
		              && m < column_size) {
		            while (board_array[l][m].getPlayer() == standby && l < row_size
		                && m < column_size) {
		              l++;
		              m++;
		              if (l >= row_size || m >= column_size) {
		                break;
		              }
		            }
		            if (l < row_size && m < column_size) {
		              if (board_array[l][m].getPlayer() == ' ') {
		                Point point = new Point(l + 1, m + 1);
		                moves.add(point);
		              }
		            }
		          }
		        }
		      }
		    }
		  }
		}
		int verticalFlip(Board board, int counter, Point ref_point,
		    char current, char standby) {
		  //top
		  int i = ref_point.getPointX() - 2;
		  int j = ref_point.getPointY() - 1;
		  final int row_size = board.getBoardSize();
		  Cell[][] board_array = board.getBoardTable();
		  board_array[i + 1][j] = current;
		  if (i >= 0) {
		    if (board_array[i][j] == standby && i > 0){
		      while (board_array[i][j] == standby && i > 0) {
		        i--;
		      }
		      if (board_array[i][j] == current) {
		        for (int l = ref_point.getPointX() - 2; l > i; l--) {
		          counter++;
		          board_array[l][j] = current;
		        }
		      }
		    }
		  }
		  //down
		  i = ref_point.getPointX();
		  j = ref_point.getPointY() - 1;
		  if (i < row_size) {
		    if (board_array[i][j] == standby && i < row_size) {
		      while (board_array[i][j] == standby && i < row_size) {
		        i++;
		        if (i >= row_size) {
		          return counter;
		        }
		      }
		      if (board_array[i][j] == current) {
		        for (int l = ref_point.getPointX(); l < i; l++) {
		          counter++;
		          board_array[l][j] = current;
		        }
		      }
		    }
		  }
		  System.out.println("flips after vertical" + counter);
		  return counter;
		}
		int horizontalFlip(Board board,int counter,Point ref_point,
		    char current, char standby) {
		  //left
		  int i = ref_point.getPointX() - 1;
		  int j = ref_point.getPointY() - 2;
		  final int column_size = board.getBoardSize();
		  Cell[][] board_array = board.getBoardTable();
		  if (j >= 0) {
		    if (board_array[i][j] == standby && j > 0) {
		      while (board_array[i][j] == standby && j > 0) {
		        j--;
		      }
		      if (board_array[i][j] == current) {
		        for (int m = ref_point.getPointY() - 2; m > j; m--) {
		          counter++;
		          board_array[i][m] = current;
		        }
		      }
		    }
		  }
		  //right
		  i = ref_point.getPointX() - 1;
		  j = ref_point.getPointY();
		  if (j < column_size - 1) {
		    if (board_array[i][j] == standby && j < column_size - 1) {
		      while (board_array[i][j] == standby && j < column_size - 1) {
		        j++;
		      }
		      if (j < column_size) {
		        if (board_array[i][j] == current) {
		          for (int m = ref_point.getPointY();m < j; m++) {
		            counter++;
		            board_array[i][m] = current;
		          }
		        }
		      }
		    }
		  }
		  System.out.println("flips after horizontal" + counter);
		  return counter;
		}
		int topDiagonalFlip(Board board, int counter,
		    Point ref_point, char current, char standby) {
		  //top left
		  int i = ref_point.getPointX() - 2;
		  int j = ref_point.getPointY() - 2;
		  final int column_size = board.getBoardSize();
		  Cell[][] board_array=board.getBoardTable();
		  if (j >= 0 && i >= 0) {
		    if (board_array[i][j] == standby && j > 0 && i > 0) {
		      while (board_array[i][j] == standby && i > 0 && j > 0) {
		        i--;
		        j--;
		      }
		      if (board_array[i][j] == current) {
		        int l = ref_point.getPointX() - 2;
		        int m = ref_point.getPointY() - 2;
		        while (l > i && m > j) {
		          board_array[l][m] = current;
		          l--;
		          m--;
		          counter++;
		        }
		      }
		    }
		  }
		  //top right
		  i = ref_point.getPointX() - 2;
		  j = ref_point.getPointY();
		  if (i >= 0 && j < column_size) {
		    if (board_array[i][j] == standby && j < column_size - 1 && i > 0) {
		      while (board_array[i][j] == standby && i > 0 && j < column_size - 1) {
		        i--;
		        j++;
		      }
		      if (i >= 0 && j < column_size) {
		        if (board_array[i][j] == current) {
		          int l = ref_point.getPointX() - 2;
		          int m = ref_point.getPointY();
		          while (l > i && m < j) {
		            board_array[l][m] = current;
		            l--;
		            m++;
		            counter++;
		          }
		        }
		      }
		    }
		  }
		  System.out.println("flips after topdiagonal" + counter);
		  return counter;
		}
		int bottomDiagonalFlip(Board board, int counter,
		    Point ref_point, char  current, char standby) {
		  //bottom left
		  int i = ref_point.getPointX();
		  int j = ref_point.getPointY() - 2;
		  final int row_size = board.getBoardSize();
		  final int column_size = board.getBoardSize();
		  Cell[][] board_array = board.getBoardTable();
		  if (j >= 0 && i < row_size) {
		    if (board_array[i][j] == standby && j > 0 && i < row_size) {
		      while (board_array[i][j] == standby && i < row_size && j > 0) {
		        i++;
		        j--;
		        if (i >= row_size || j <= 0) {
		          break;
		        }
		      }
		      if (i < row_size && j >= 0) {
		        if (board_array[i][j] == current) {
		          int l = ref_point.getPointX();
		          int m = ref_point.getPointY() - 2;
		          while (l < i && m > j) {
		            board_array[l][m] = current;
		            l++;
		            m--;
		            counter++;
		          }
		        }
		      }
		    }
		  }
		  //bottom right
		  i = ref_point.getPointX();
		  j = ref_point.getPointY();
		  if (j < column_size && i < row_size) {
		    if (board_array[i][j] == standby && j < column_size && i < row_size) {
		      while (board_array[i][j] == standby && i < row_size && j < column_size) {
		        i++;
		        j++;
		        if (i >= row_size || j >= column_size) {
		          break;
		        }
		      }
		      if (i < row_size && j < column_size) {
		        if (board_array[i][j] == current) {
		          int l = ref_point.getPointX();
		          int m = ref_point.getPointY();
		          while (l < i && m < j) {
		            counter++;
		            board_array[l][m] = current;
		            l++;
		            m++;
		          }
		        }
		      }
		    }
		  }
		  System.out.println("flips after bottomdiagonal" + counter);
		  return counter;
		}
		 public boolean isGameWon(Board board) {
		        //check if board is full.
		        for (int i = 0; i < board.getBoardSize(); i++) {
		            for (int j = 0; j < board.getBoardSize(); j++) {
		                if (board.checkCell(i, j) == ' ') {
		                    return false;
		                }
		            }
		        }
		        return true;
		    }
		 public boolean validOption(Board board, int x, int y, Vector<Point> options) {
		        if(isGameWon(board)) {
		            return true;
		        }
		        if (x > 0 && y > 0 && x <= board.getBoardSize() && y <= board.getBoardSize()) {
		            //go over all of the possible moves.
		            for (int i = 0; i < options.size(); i++) {
		                //if the move is a possible move.
		                if (x == options.get(i).getPointX() && y == options.get(i).getPointY()) {
		                    return true;
		                }
		            }
		        }
		        return false;
		}
}
