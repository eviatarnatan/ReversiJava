/*
 * Eviatar Natan
 * 307851808
 */
package gameapp;

import javafx.scene.paint.Color;
/**
 * the human player class.
 */
public class HumanPlayer implements Player {
    private int disksNum;
    private boolean myTurn;
    private char symbol;
    private char otherSymbol;
    private Color playerColor;
    /**
     * creates a new human player.
     * @param playerSymbol - the symbol of the player
     * @param color - the color of the player
     */
    HumanPlayer(char playerSymbol, Color color) {
        disksNum = 2;
        myTurn = false;
        symbol = playerSymbol;
        if (symbol == 'b') {
            otherSymbol = 'w';
        } else {
            otherSymbol = 'b';
        }
        this.playerColor = color;
    }
    /**
     * returns the player's disks number.
     * @return the player's disks number.
     */
    public int getDisksNum() {
        return disksNum;
    }
    /**
     * adds new amount of discs to the amount the player has.
     * @param changeDisksNum - the amount of disks to add/substract.
     */
    public void setDisksNum(int changeDisksNum) {
        disksNum = disksNum + changeDisksNum;
    }
    /**
     * returns the player's turn status.
     * @return true/false - true if it's current player turn, false otherwise.
     */
    public boolean getMyTurn() {
        return myTurn;
    }
    /**
     * sets the player's turn status.
     * @param status - the status to set.
     */
    public void setMyTurn(boolean status) {
        myTurn = status;
    }
    /**
     * returns the symbol of the player.
     * @return the player's symbol.
     */
    public char getSymbol() {
        return symbol;
    }
    /**
     * sets the symbol of the player.
     * @param playerSymbol - sets the symbol of the player.
     */
    public void setSymbol(char playerSymbol) {
        symbol = playerSymbol;
    }
    /**
     * returns the symbol of the opposite player.
     * @return the opposite player symbol.
     */
    public char getOppositeSymbol() {
        return otherSymbol;
    }
    /**
     * sets the symbol of the opposite number.
     * @param otherPlayerSymbol - sets the other player's symbol
     */
    public void setOppositeSymbol(char otherPlayerSymbol) {
        otherSymbol = otherPlayerSymbol;
    }
    /**
     * returns the player's color.
     * @return the player's color.
     */
    public Color getColor() {
        return this.playerColor;
    }
    /**
     * the player's play his turn.
     * @param logic - the game's logic.
     * @param board - the game's board.
     * @param other - the other player.
     */
    public void turn(GameLogic logic, Board board, Player other) {
    }
}