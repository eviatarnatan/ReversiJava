package gameapp;

import javafx.scene.paint.Color;

public interface Player {
	 int getDisksNum();
	  /*
	   * adds new amount of discs to the amount the player has.
	   */
	  void setDisksNum(int disks_num);
	  /*
	   * gets the player's turn status.
	   */
	  boolean getMyTurn();
	  /*
	   * sets the player's turn status.
	   */
	  void setMyTurn(boolean status);
	  /*
	   * returns the symbol (O/X) of the player.
	   */
	  char getSymbol();
	  /*
	   * sets the symbol (O/X) of the player.
	   */
	  void setSymbol(char symbol);
	  /*
	   * returns the symbol (O/X) of the opposite player.
	   */
	  char getOppositeSymbol();
	  /*
	   * sets the symbol (O/X) of the opposite player.
	   */
	  void setOppositeSymbol(char other_symbol);
	  /*
	   * the player's turn. on each turn, the player will see the available moves
	   * that he can make, and choose his desired move. the board will be updated
	   * after his choice, and the number of discs he and the opposite player have
	   * will be updated. in case there are no available moves, the turn will pass
	   * to the other player.
	   */
	  void turn(GameLogic logic, Board board, Player other);
	  public Color getColor();
}
