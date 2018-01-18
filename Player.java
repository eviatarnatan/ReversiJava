/*
 * Eviatar Natan
 * 307851808
 */
package gameapp;

import javafx.scene.paint.Color;
/**
 * the player's interface.
 */
public interface Player {
    /**
     * returns the player's disks number.
     * @return the player's disks number.
     */
     int getDisksNum();
     /**
      * adds new amount of discs to the amount the player has.
      * @param disksNum - the amount of disks to add/substract.
      */
      void setDisksNum(int disksNum);
      /**
       * returns the player's turn status.
       * @return true/false - true if it's current player turn, false otherwise.
       */
      boolean getMyTurn();
      /**
       * sets the player's turn status.
       * @param status - the status to set.
       */
      void setMyTurn(boolean status);
      /**
       * returns the symbol of the player.
       * @return the player's symbol.
       */
      char getSymbol();
      /**
       * sets the symbol of the player.
       * @param symbol - sets the symbol of the player.
       */
      void setSymbol(char symbol);
      /**
       * returns the symbol of the opposite player.
       * @return the opposite player symbol.
       */
      char getOppositeSymbol();
      /**
       * sets the symbol of the opposite number.
       * @param otherSymbol - sets the other player's symbol
       */
      void setOppositeSymbol(char otherSymbol);
      /**
       * the player's play his turn.
       * @param logic - the game's logic.
       * @param board - the game's board.
       * @param other - the other player.
       */
      void turn(GameLogic logic, Board board, Player other);
      /**
       * returns the player's color.
       * @return the player's color.
       */
      Color getColor();
}
