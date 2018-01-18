/*
 * Eviatar Natan
 * 307851808
 */
package gameapp;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
/**
 * represents a cell in the board.
 * <p>
 * the cell contains info about it's position, the player who occupies the cell,
 * a rectangle and panes.
 */
public class Cell extends Circle {
    private int row;
    private int column;
    private char player;
    private GridPane grid;
    private StackPane stackPane;
    private Rectangle rectangle;
    /**
     * constructs the board's cell.
     * @param grid - the gridpane.
     * @param row - the cell's row position.
     * @param column - the cell's column position.
     */
    Cell(GridPane grid, int row, int column) {
        this.grid = grid;
        // initialize the peice location.
        this.row = row;
        this.column = column;
        // initialize the piece color.
        this.setFill(Paint.valueOf("#f4f4f4"));
        // initialize the piece type.
        this.player = 'n';
        this.stackPane = new StackPane();
        this.rectangle = new Rectangle();
        // set the background
        this.rectangle.setStroke(Color.BLACK);
        this.rectangle.setFill(Color.BISQUE);
        this.setFill(Color.BISQUE);
    }
    /**
     * draws the cell.
     * @param cellWidth - the cell's width.
     * @param cellHeight - the cell's height.
     */
    public void draw(int cellWidth, int cellHeight) {
        int minValue = Math.min(cellHeight, cellWidth);
        // setting the radius of the tile.
        this.setRadius(minValue / 2 - 2);
        this.rectangle.setWidth(cellWidth - 1);
        this.rectangle.setHeight(cellHeight - 1);

        if (this.getPlayerSymbol() != 'n') {
            this.setStroke(Color.BLACK);
        }
        this.stackPane.getChildren().removeAll(this.rectangle, this);
        this.stackPane.getChildren().addAll(this.rectangle, this);
        this.grid.getChildren().remove(this.stackPane);
        this.grid.add(this.stackPane, column, row);
    }
    /**
     * returns the cell's row.
     * @return the cell's row.
     */
    public int getRow() {
        return this.row;
    }
    /**
     * returns the cell's column.
     * @return the cell's column.
     */
    public int getColumn() {
        return this.column;
    }
    /**
     * returns the player's symbol.
     * @return the player's symbol.
     */
    public char getPlayerSymbol() {
        return this.player;
    }
    /**
     * sets the player's symbol.
     * @param playerSymbol - the player's symbol.
     */
    public void setPlayerSymbol(char playerSymbol) {
        this.player = playerSymbol;
    }
}
