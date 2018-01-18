/*
 * Eviatar Natan
 * 307851808
 */
package gameapp;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
/**
 * the game's board.
 */
public class Board extends GridPane {
    private int size;
    private Cell[][] board;
    /**
     * creates a new board based on received size and player's colors.
     * @param boardSize - the board's size
     * @param firstPlayer - the first player's color
     * @param secondPlayer - the second player's color
     */
    Board(int boardSize , Color firstPlayer, Color secondPlayer) {
        this.setGridLinesVisible(true);
        this.setId("reversiBoard");
        this.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        this.size = boardSize;
        this.board  = new Cell[this.size][this.size];
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ReversiGame.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
          for (int i = 0; i < this.size; ++i) {
            for (int j = 0; j < this.size; ++j) {
              this.board[i][j] = new Cell(this, i, j);
            }
          }
          this.board[size / 2 - 1][size / 2 - 1].setFill(secondPlayer);
          this.board[size / 2 - 1][size / 2].setFill(firstPlayer);
          this.board[size / 2][size / 2 - 1].setFill(firstPlayer);
          this.board[size / 2][size / 2].setFill(secondPlayer);
          this.board[size / 2 - 1][size / 2 - 1].setPlayerSymbol('w');
          this.board[size / 2 - 1][size / 2].setPlayerSymbol('b');
          this.board[size / 2][size / 2 - 1].setPlayerSymbol('b');
          this.board[size / 2][size / 2].setPlayerSymbol('w');
        }
    /**
     * draws the board.
     */
    public void draw() {
        this.getChildren().clear();
        int height = (int) this.getPrefHeight();
        int width = (int) this.getPrefWidth();
        this.setStyle("-fx-grid-lines-visible: true");
        int cellHeight = height / this.board.length;
        int cellWidth = width / this.board.length;
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                this.board[i][j].draw(cellWidth, cellHeight);
            }
        }
    }
    /**
     * returns the board.
     * @return the board.
     */
    public Cell[][] getBoardTable() {
        return this.board;
    }
    /**
     * sets the board.
     * @param table - the board's table to set.
     */
    public void setBoardTable(Cell[][] table) {
        board = table;
    }
    /**
     * returns the size of the board.
     * @return the board's size
     */
    public int getBoardSize() {
        return this.size;
    }
    /**
     * checks if the cell is out of bounds or not.
     * @param x - the checked cell x value
     * @param y - the checked cell y value
     * @return ' ' if out of bounds, otherwise returns 'x' or 'o'
     */
    public char outOfBoundsCheck(final int x, final int y) {
        //if illegal
        if (x < 0 || x >= this.size || y < 0 || y >= this.size) {
            return ' ';
        }
        if (this.board[x][y].getPlayerSymbol() == 'b') {
            return 'x';
        } else if (this.board[x][y].getPlayerSymbol() == 'w') {
            return 'o';
        }
        return ' ';
    }
}
