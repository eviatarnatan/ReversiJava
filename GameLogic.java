/*
 * Eviatar Natan
 * 307851808
 */
package gameapp;
import java.util.Vector;

/**
 * the game logic interface.
 */
public interface GameLogic {
    /**
     * checks the available moves of the current player.
     * <p>
     * it checks each direction for possible moves, and in the end
     * returns a vector with the available moves.
     * @param board - the game's board
     * @param symbol - the symbol of the current player
     * @param otherSymbol - the symbol of the opposite player.
     * @return a vector of the current player's available moves.
     */
      Vector<Point> availableMoves(Board board,
          char symbol, char otherSymbol);
      /**
       * flips the discs on the board and returns the counter of flips occurred.
       * @param board - the game's board.
       * @param refPoint - the current player move.
       * @param current - the current player.
       * @return the numbers of flips occurred during this turn.
       */
      int flipDiscs(Board board, Point refPoint,
          Player current);
      /**
       * sorts the points of the current player available moves.
       * @param ref - a vector of current player's available moves.
       */
      void sortPoints(Vector<Point> ref);
      /**
       * removes duplicate points from available moves vector.
       * @param ref - the vector of the current player's available moves.
       */
      void removeDuplicatePoints(Vector<Point> ref);
      /**
       * checks if the game was won in case the board is full.
       * @param board - the game's board.
       * @return true or false.
       */
      boolean checkIfGameIsWon(Board board);
      /**
       * checks if move exists in available moves vector and returns true or false.
       * @param board - the game's board.
       * @param x - the x value of the clicked point.
       * @param y - the y value of the clicked point.
       * @param options - a vector of current player's available moves.
       * @return true or false - true if it's a valid move, false otherwise.
       */
      boolean validOption(Board board, int x, int y, Vector<Point> options);
}
