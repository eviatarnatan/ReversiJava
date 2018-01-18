/*
 * Eviatar Natan
 * 307851808
 */
package gameapp;
import java.util.Vector;
import javafx.scene.paint.Color;
/**
 * the logic of the game.
 */
public class ReversiLogic implements GameLogic {
    /**
     * checks the available moves of the current player.
     * <p>
     * it checks each direction for possible moves, and in the end
     * returns a vector with the available moves.
     * @param board - the game's board
     * @param symbol - the symbol of the current player
     * @param otherSymbol - the symbol of the opposite player.
     * @return availableMoves - a vector of the current player's available moves.
     */
    public Vector<Point> availableMoves(Board board,
             char symbol, char otherSymbol) {
          Vector<Point> availableMoves = new Vector<Point>();
          verticalMoves(board, availableMoves, symbol, otherSymbol);
          horizontalMoves(board, availableMoves, symbol, otherSymbol);
          topDiagonalMoves(board, availableMoves, symbol, otherSymbol);
          bottomDiagonalMoves(board, availableMoves, symbol, otherSymbol);
          return availableMoves;

        }
    /**
     * flips the discs on the board and returns the counter of flips occurred.
     * @param board - the game's board.
     * @param refPoint - the current player move.
     * @param current - the current player.
     * @return counter - the numbers of flips occurred during this turn.
     */
    public int flipDiscs(Board board, Point refPoint,
        Player current) {
          int counter = 0;
          int specificFlipCounter = 0;
          int add = 0;
          add = verticalFlip(board, specificFlipCounter, refPoint, current);
          counter = counter + add;
          add = horizontalFlip(board, specificFlipCounter, refPoint, current);
          counter = counter + add;
          add = topDiagonalFlip(board, specificFlipCounter, refPoint, current);
          counter = counter + add;
          add = bottomDiagonalFlip(board, specificFlipCounter, refPoint, current);
          counter = counter + add;
          return counter;
        }
    /**
     * sorts the points of the current player available moves.
     * @param moves - a vector of current player's available moves.
     */
    public void sortPoints(Vector<Point> moves) {
      //sort by x value
      for (int i = 0; i < moves.size() - 1; i++) {
        for (int j = i + 1; j < moves.size(); j++) {
          if (moves.get(i).getPointX() > moves.get(j).getPointX()) {
            Point p = new Point(moves.get(i).getPointX(), moves.get(i).getPointY());
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
              && moves.get(i).getPointX() == moves.get(j).getPointX()) {
            Point p = new Point(moves.get(i).getPointX(), moves.get(i).getPointY());
            moves.get(i).setPointX(moves.get(j).getPointX());
            moves.get(i).setPointY(moves.get(j).getPointY());
            moves.get(j).setPointX(p.getPointX());
            moves.get(j).setPointY(p.getPointY());
          }
        }
      }
    }
    /**
     * removes duplicate points from available moves vector.
     * @param moves - the vector of the current player's available moves.
     */
    public void removeDuplicatePoints(Vector<Point> moves) {
      for (int i = 0; i < moves.size() - 1; i++) {
        for (int j = i + 1; j < moves.size(); j++) {
          if (moves.get(i).getPointY() == moves.get(j).getPointY()
              && moves.get(i).getPointX() == moves.get(j).getPointX()) {
            moves.remove(j);
            j--;
          }
        }
      }
    }
    /**
     * checks if vertical moves exist, and adds them if they are.
     * @param board - the game's board
     * @param moves - vector of available moves.
     * @param current - the current player's symbol
     * @param standby - the other player's symbol
     */
    void verticalMoves(Board board, Vector<Point> moves,
        char current, char standby) {
      //up
      int rowSize = board.getBoardSize();
      int columnSize = board.getBoardSize();
      Cell[][] boardArray = board.getBoardTable();
      for (int i = 0; i < rowSize; i++) {
        for (int j = 0; j < columnSize; j++) {
          if (boardArray[i][j].getPlayerSymbol() == current) {
            int l = i - 1, m = j;
            if (l > 0) {
              if (boardArray[l][m].getPlayerSymbol() == standby && l > 0) {
                while (boardArray[l][m].getPlayerSymbol() == standby && l > 0) {
                  l--;
                }
                if (boardArray[l][m].getPlayerSymbol() == 'n') {
                  Point point = new Point(l + 1, m + 1);
                  moves.add(point);
                }
              }
            }
          }
        }
      }
      //down
      for (int i = 0; i < rowSize; i++) {
        for (int j = 0; j < columnSize; j++) {
          if (boardArray[i][j].getPlayerSymbol() == current) {
            int l = i + 1, m = j;
            if (l < rowSize) {
              if (boardArray[l][m].getPlayerSymbol() == standby && l < rowSize) {
                while (boardArray[l][m].getPlayerSymbol() == standby && l < rowSize) {
                  l++;
                  if (l >= rowSize) {
                    break;
                  }
                }
                if (l < rowSize) {
                  if (boardArray[l][m].getPlayerSymbol() == 'n') {
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
    /**
     * checks if horizontal moves exist, and adds them if they are.
     * @param board - the game's board
     * @param moves - vector of available moves.
     * @param current - the current player's symbol
     * @param standby - the other player's symbol
     */
    void horizontalMoves(Board board, Vector<Point> moves,
        char current, char standby) {
      //right
      int rowSize = board.getBoardSize();
      int columnSize = board.getBoardSize();
      Cell[][] boardArray = board.getBoardTable();
      for (int i = 0; i < rowSize; i++) {
        for (int j = 0; j < columnSize; j++) {
          if (boardArray[i][j].getPlayerSymbol() == current) {
            int l = i, m = j + 1;
            if (m < columnSize - 1) {
              if (boardArray[l][m].getPlayerSymbol() == standby && m < columnSize - 1) {
                while (boardArray[l][m].getPlayerSymbol() == standby && m < columnSize - 1) {
                  m++;
                }
                if (m < columnSize) {
                  if (boardArray[l][m].getPlayerSymbol() == 'n') {
                    Point point = new Point(l + 1, m + 1);
                    moves.add(point);
                  }
                }
              }
            }
          }
        }
      }
      //left
      for (int i = 0; i < rowSize; i++) {
        for (int j = 0; j < columnSize; j++) {
          if (boardArray[i][j].getPlayerSymbol() == current) {
            int l = i, m = j - 1;
            if (m > 0) {
              if (boardArray[l][m].getPlayerSymbol() == standby && m > 0) {
                while (boardArray[l][m].getPlayerSymbol() == standby && m > 0) {
                  m--;
                }
                if (boardArray[l][m].getPlayerSymbol() == 'n') {
                  Point point = new Point(l + 1, m + 1);
                  moves.add(point);
                }
              }
            }
          }
        }
      }
    }
    /**
     * checks if top diagonal moves exist, and adds them if they are.
     * @param board - the game's board
     * @param moves - vector of available moves.
     * @param current - the current player's symbol
     * @param standby - the other player's symbol
     */
    void topDiagonalMoves(Board board, Vector<Point>  moves,
        char current, char standby) {
      //top left
      int rowSize = board.getBoardSize();
      int columnSize = board.getBoardSize();
      Cell[][] boardArray = board.getBoardTable();
      for (int i = 0; i < rowSize; i++) {
        for (int j = 0; j < columnSize; j++) {
          if (boardArray[i][j].getPlayerSymbol() == current) {
            int l = i - 1, m = j - 1;
            if (l > 0 && m > 0) {
              if (boardArray[l][m].getPlayerSymbol() == standby && l > 0 && m > 0) {
                while (boardArray[l][m].getPlayerSymbol() == standby && l > 0 && m > 0) {
                  l--;
                  m--;
                }
                if (m < columnSize) {
                  if (boardArray[l][m].getPlayerSymbol() == 'n') {
                    Point point = new Point(l + 1, m + 1);
                    moves.add(point);
                  }
                }
              }
            }
          }
        }
      }
      //top right
      for (int i = 0; i < rowSize; i++) {
        for (int j = 0; j < columnSize; j++) {
          if (boardArray[i][j].getPlayerSymbol() == current) {
            int l = i - 1, m = j + 1;
            if (l > 0 && m < columnSize) {
              if (boardArray[l][m].getPlayerSymbol() == standby && l > 0 && m < columnSize - 1) {
                while (boardArray[l][m].getPlayerSymbol() == standby && l > 0
                    && m < columnSize - 1) {
                  l--;
                  m++;
                }
                if (boardArray[l][m].getPlayerSymbol() == 'n') {
                  Point point = new Point(l + 1, m + 1);
                  moves.add(point);
                }
              }
            }
          }
        }
      }
    }
    /**
     * checks if bottom diagonal moves exist, and adds them if they are.
     * @param board - the game's board
     * @param moves - vector of available moves.
     * @param current - the current player's symbol
     * @param standby - the other player's symbol
     */
    void bottomDiagonalMoves(Board board, Vector<Point> moves,
        char current, char standby) {
      //bottom left
      final int rowSize = board.getBoardSize();
      final int columnSize = board.getBoardSize();
      Cell[][] boardArray = board.getBoardTable();
      for (int i = 0; i < rowSize; i++) {
        for (int j = 0; j < columnSize; j++) {
          if (boardArray[i][j].getPlayerSymbol() == current) {
            int l = i + 1, m = j - 1;
            if (l < rowSize && m >= 0) {
              if (boardArray[l][m].getPlayerSymbol() == standby && l < rowSize && m >= 0) {
                while (boardArray[l][m].getPlayerSymbol() == standby && l < rowSize && m >= 0) {
                  l++;
                  m--;
                  if (l >= rowSize || m < 0) {
                    break;
                  }
                }
                if (l < rowSize && m >= 0) {
                  if (boardArray[l][m].getPlayerSymbol() == 'n') {
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
      for (int i = 0; i < rowSize; i++) {
        for (int j = 0; j < columnSize; j++) {
          if (boardArray[i][j].getPlayerSymbol() == current) {
            int l = i + 1, m = j + 1;
            if (l < rowSize && m < columnSize) {
              if (boardArray[l][m].getPlayerSymbol() == standby && l < rowSize
                  && m < columnSize) {
                while (boardArray[l][m].getPlayerSymbol() == standby && l < rowSize
                    && m < columnSize) {
                  l++;
                  m++;
                  if (l >= rowSize || m >= columnSize) {
                    break;
                  }
                }
                if (l < rowSize && m < columnSize) {
                  if (boardArray[l][m].getPlayerSymbol() == 'n') {
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
    /**
     * flips discs vertical from current player's move.
     * @param board - the game's board.
     * @param counter - the flips counter for current function.
     * @param refPoint - current player's move.
     * @param current - the current player.
     * @return counter - the amount of vertical flips of current turn.
     */
    int verticalFlip(Board board, int counter, Point refPoint,
        Player current) {
      //top
      Color color = current.getColor();
      if (color == Color.BLACK) {
          System.out.println("is the black player");
      }
      int i = refPoint.getPointX() - 2;
      int j = refPoint.getPointY() - 1;
      final int rowSize = board.getBoardSize();
      Cell[][] boardArray = board.getBoardTable();
      boardArray[i + 1][j].setPlayerSymbol(current.getSymbol());
      boardArray[i + 1][j].setFill(current.getColor());
      if (i >= 0) {
        if (boardArray[i][j].getPlayerSymbol() == current.getOppositeSymbol() && i > 0) {
          while (boardArray[i][j].getPlayerSymbol() == current.getOppositeSymbol() && i > 0) {
            i--;
          }
          if (boardArray[i][j].getPlayerSymbol() == current.getSymbol()) {
            for (int l = refPoint.getPointX() - 2; l > i; l--) {
              counter++;
              boardArray[l][j].setPlayerSymbol(current.getSymbol());
              boardArray[l][j].setStroke(Color.BLACK);
              boardArray[l][j].setFill(current.getColor());
            }
          }
        }
      }
      //down
      i = refPoint.getPointX();
      j = refPoint.getPointY() - 1;
      if (i < rowSize) {
        if (boardArray[i][j].getPlayerSymbol() == current.getOppositeSymbol() && i < rowSize) {
          while (boardArray[i][j].getPlayerSymbol() == current.getOppositeSymbol() && i < rowSize) {
            i++;
            if (i >= rowSize) {
              return counter;
            }
          }
          if (boardArray[i][j].getPlayerSymbol() == current.getSymbol()) {
            for (int l = refPoint.getPointX(); l < i; l++) {
              counter++;
              boardArray[l][j].setPlayerSymbol(current.getSymbol());
              boardArray[l][j].setStroke(Color.BLACK);
              boardArray[l][j].setFill(current.getColor());
            }
          }
        }
      }
      return counter;
    }
    /**
     * flips discs horizontal from current player's move.
     * @param board - the game's board.
     * @param counter - the flips counter for current function.
     * @param refPoint - current player's move.
     * @param current - the current player.
     * @return counter - the amount of horizontal flips of current turn.
     */
    int horizontalFlip(Board board, int counter, Point refPoint,
        Player current) {
      //left
      int i = refPoint.getPointX() - 1;
      int j = refPoint.getPointY() - 2;
      final int columnSize = board.getBoardSize();
      Cell[][] boardArray = board.getBoardTable();
      if (j >= 0) {
        if (boardArray[i][j].getPlayerSymbol() == current.getOppositeSymbol() && j > 0) {
          while (boardArray[i][j].getPlayerSymbol() == current.getOppositeSymbol() && j > 0) {
            j--;
          }
          if (boardArray[i][j].getPlayerSymbol() == current.getSymbol()) {
            for (int m = refPoint.getPointY() - 2; m > j; m--) {
              counter++;
              boardArray[i][m].setPlayerSymbol(current.getSymbol());
              boardArray[i][m].setStroke(Color.BLACK);
              boardArray[i][m].setFill(current.getColor());
            }
          }
        }
      }
      //right
      i = refPoint.getPointX() - 1;
      j = refPoint.getPointY();
      if (j < columnSize - 1) {
        if (boardArray[i][j].getPlayerSymbol() == current.getOppositeSymbol() && j < columnSize - 1) {
          while (boardArray[i][j].getPlayerSymbol() == current.getOppositeSymbol() && j < columnSize - 1) {
            j++;
          }
          if (j < columnSize) {
            if (boardArray[i][j].getPlayerSymbol() == current.getSymbol()) {
              for (int m = refPoint.getPointY(); m < j; m++) {
                counter++;
                boardArray[i][m].setPlayerSymbol(current.getSymbol());
                boardArray[i][m].setStroke(Color.BLACK);
                boardArray[i][m].setFill(current.getColor());
              }
            }
          }
        }
      }
      return counter;
    }
    /**
     * flips discs top diagonal from current player's move.
     * @param board - the game's board.
     * @param counter - the flips counter for current function.
     * @param refPoint - current player's move.
     * @param current - the current player.
     * @return counter - the amount of top diagonal flips of current turn.
     */
    int topDiagonalFlip(Board board, int counter,
        Point refPoint, Player current) {
      //top left
      int i = refPoint.getPointX() - 2;
      int j = refPoint.getPointY() - 2;
      final int columnSize = board.getBoardSize();
      Cell[][] boardArray = board.getBoardTable();
      if (j >= 0 && i >= 0) {
        if (boardArray[i][j].getPlayerSymbol() == current.getOppositeSymbol() && j > 0 && i > 0) {
          while (boardArray[i][j].getPlayerSymbol() == current.getOppositeSymbol() && i > 0 && j > 0) {
            i--;
            j--;
          }
          if (boardArray[i][j].getPlayerSymbol() == current.getSymbol()) {
            int l = refPoint.getPointX() - 2;
            int m = refPoint.getPointY() - 2;
            while (l > i && m > j) {
              boardArray[l][m].setPlayerSymbol(current.getSymbol());
              boardArray[l][m].setStroke(Color.BLACK);
              boardArray[l][m].setFill(current.getColor());
              l--;
              m--;
              counter++;
            }
          }
        }
      }
      //top right
      i = refPoint.getPointX() - 2;
      j = refPoint.getPointY();
      if (i >= 0 && j < columnSize) {
        if (boardArray[i][j].getPlayerSymbol() == current.getOppositeSymbol() && j < columnSize - 1 && i > 0) {
          while (boardArray[i][j].getPlayerSymbol() == current.getOppositeSymbol() && i > 0 && j < columnSize - 1) {
            i--;
            j++;
          }
          if (i >= 0 && j < columnSize) {
            if (boardArray[i][j].getPlayerSymbol() == current.getSymbol()) {
              int l = refPoint.getPointX() - 2;
              int m = refPoint.getPointY();
              while (l > i && m < j) {
                boardArray[l][m].setPlayerSymbol(current.getSymbol());
                boardArray[l][m].setStroke(Color.BLACK);
                boardArray[l][m].setFill(current.getColor());
                l--;
                m++;
                counter++;
              }
            }
          }
        }
      }
      return counter;
    }
    /**
     * flips discs bottom diagonal from current player's move.
     * @param board - the game's board.
     * @param counter - the flips counter for current function.
     * @param refPoint - current player's move.
     * @param current - the current player.
     * @return counter - the amount of bottom diagonal flips of current turn.
     */
    int bottomDiagonalFlip(Board board, int counter,
        Point refPoint, Player current) {
      //bottom left
      int i = refPoint.getPointX();
      int j = refPoint.getPointY() - 2;
      final int rowSize = board.getBoardSize();
      final int columnSize = board.getBoardSize();
      Cell[][] boardArray = board.getBoardTable();
      if (j >= 0 && i < rowSize) {
        if (boardArray[i][j].getPlayerSymbol() == current.getOppositeSymbol() && j > 0 && i < rowSize) {
          while (boardArray[i][j].getPlayerSymbol() == current.getOppositeSymbol() && i < rowSize && j > 0) {
            i++;
            j--;
            if (i >= rowSize || j <= 0) {
              break;
            }
          }
          if (i < rowSize && j >= 0) {
            if (boardArray[i][j].getPlayerSymbol() == current.getSymbol()) {
              int l = refPoint.getPointX();
              int m = refPoint.getPointY() - 2;
              while (l < i && m > j) {
                boardArray[l][m].setPlayerSymbol(current.getSymbol());
                boardArray[l][m].setStroke(Color.BLACK);
                boardArray[l][m].setFill(current.getColor());
                l++;
                m--;
                counter++;
              }
            }
          }
        }
      }
      //bottom right
      i = refPoint.getPointX();
      j = refPoint.getPointY();
      if (j < columnSize && i < rowSize) {
        if (boardArray[i][j].getPlayerSymbol() == current.getOppositeSymbol()
                && j < columnSize && i < rowSize) {
          while (boardArray[i][j].getPlayerSymbol() == current.getOppositeSymbol()
                  && i < rowSize && j < columnSize) {
            i++;
            j++;
            if (i >= rowSize || j >= columnSize) {
              break;
            }
          }
          if (i < rowSize && j < columnSize) {
            if (boardArray[i][j].getPlayerSymbol() == current.getSymbol()) {
              int l = refPoint.getPointX();
              int m = refPoint.getPointY();
              while (l < i && m < j) {
                counter++;
                boardArray[l][m].setPlayerSymbol(current.getSymbol());
                boardArray[l][m].setStroke(Color.BLACK);
                boardArray[l][m].setFill(current.getColor());
                l++;
                m++;
              }
            }
          }
        }
      }
      return counter;
    }
    /**
     * checks if the game was won in case the board is full.
     * @param board - the game's board.
     * @return true or false.
     */
     public boolean checkIfGameIsWon(Board board) {
       //check if board is full.
       for (int i = 0; i < board.getBoardSize(); i++) {
         for (int j = 0; j < board.getBoardSize(); j++) {
           if (board.outOfBoundsCheck(i, j) == ' ') {
             return false;
            }
         }
       }
       return true;
    }
     /**
      * checks if move exists in available moves vector and returns true or false.
      * @param board - the game's board.
      * @param x - the x value of the clicked point.
      * @param y - the y value of the clicked point.
      * @param options - a vector of current player's available moves.
      * @return true or false - true if it's a valid move, false otherwise.
      */
     public boolean validOption(Board board, int x, int y, Vector<Point> options) {
        if (checkIfGameIsWon(board)) {
            return true;
        }
        if (x > 0 && y > 0 && x <= board.getBoardSize() && y <= board.getBoardSize()) {
            //go over all possible moves.
            for (int i = 0; i < options.size(); i++) {
                //if the move is valid
                if (x == options.get(i).getPointX() && y == options.get(i).getPointY()) {
                    return true;
                }
            }
        }
        return false;
    }
}
