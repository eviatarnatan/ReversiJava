package gameapp;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
public class Cell extends Circle{
	private int row;
	private int column;
	private char player;
	private GridPane grid;
	private StackPane stackPane;
	private Rectangle rectangle;
	
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
        this.rectangle.setFill(Color.GREEN);
        this.setFill(Color.GREEN);
	}
	public void draw(int cellWidth, int cellHeight) {
		int minVal = Math.min(cellHeight, cellWidth);
        // setting the radius of the tile.
        this.setRadius(minVal / 2 - 2);
        this.rectangle.setWidth(cellWidth - 1);
        this.rectangle.setHeight(cellHeight - 1);

        if(this.getPlayer() != 'n') {
            this.setStroke(Color.BLACK);
        }
        this.stackPane.getChildren().removeAll(this.rectangle, this);
        this.stackPane.getChildren().addAll(this.rectangle, this);
        this.grid.getChildren().remove(this.stackPane);
        this.grid.add(this.stackPane, column, row);
	}
	public int getRow() {
		return this.row;
	}
	public int getColumn() {
		return this.column;
	}
	public char getPlayer() {
		return this.player;
	}
	public void setPlayer(char player) {
		this.player = player;
	}
}
