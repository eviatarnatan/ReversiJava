package gameapp;
import java.util.Vector;

public interface GameLogic {
	  /*
	   * checks the available moves on the board for the current player.
	   * receives the current player's and other player symbols (X/O),
	   * and references to a vector of points (that will be filled) in
	   * case there are possible moves, and the current state of the board.
	   */
	  Vector<Point> availableMoves(Board board,
	      char symbol,char other_symbol);
	  /*
	   * flips discs of the opposite player.
	   * receives the current player's and other player symbols (X/O)
	   * references to a point (the chosen move)
	   * and the current state of the board.
	   * returns a counter which represents the amount of discs
	   * that has been flipped.
	   */
	  int flipDiscs(Board board,Point ref_point,
	      char symbol,char other_symbol);
	  /*
	   * receive a reference to a vector of points and sorts it,
	   * first by X values, and second by Y values.
	   */
	  void sortPoints(Vector<Point>ref);
	  /*
	   * receives a reference to a vector of points and removes
	   * duplicates.
	   */
	  void removeDuplicatePoints(Vector<Point>ref);
	  boolean isGameWon(Board board);
}
