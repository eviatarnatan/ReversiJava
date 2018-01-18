package gameapp;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
public class Board extends GridPane{
	private int size;
	private Cell[][] board;
	
	Board(int boardSize ,Color firstPlayer, Color secondPlayer) {
		this.setGridLinesVisible(true);
        this.setId("reversiBoard");
        this.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
		  this.board[size / 2][size/ 2 - 1].setFill(firstPlayer);
		  this.board[size / 2][size / 2].setFill(secondPlayer);
		  this.board[size / 2 - 1][size / 2 - 1].setPlayer('w');
		  this.board[size / 2 - 1][size / 2].setPlayer('b');
		  this.board[size / 2][size/ 2 - 1].setPlayer('b');
		  this.board[size / 2][size / 2].setPlayer('w');
		}
		public void draw() {
			 this.getChildren().clear();
		        int height = (int)this.getPrefHeight();
		        int width = (int)this.getPrefWidth();
		        this.setStyle("-fx-grid-lines-visible: true");
		        int cellHeight = height / this.board.length;
		        int cellWidth = width / this.board.length;

		        for(int i = 0; i < this.board.length; i++) {
		            for(int j = 0; j < this.board[i].length; j++) {
		                this.board[i][j].draw(cellWidth, cellHeight);
		            }
		        }
		}
		public Cell[][] getBoardTable() {
		  return this.board;
		}
		public void setBoardTable(Cell[][] table) {
		  board = table;
		}
		public int getBoardSize() {
		  return this.size;
		}
		public char checkCell(final int x, final int y) {
	        //if the cell is out of the boards bounds.
	        if (x < 0 || x >= this.size || y < 0 || y >= this.size) {
	            return ' ';
	        }
	        // return the cells value.
	        if (this.board[x][y].getPlayer() == 'x') {
	            return 'x';
	        } else if (this.board[x][y].getPlayer() == 'w') {
	            return 'o';
	        }
	        return ' ';
	}
}
