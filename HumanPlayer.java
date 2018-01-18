package gameapp;

import java.util.Scanner;
import java.util.Vector;

import javafx.scene.paint.Color;
public class HumanPlayer implements Player{
	private int disks_num_;
	private boolean my_turn_;
	private char symbol_;
	private char other_symbol_;
	private Color playerColor;
	HumanPlayer(char symbol,Color color) {
	  // TODO Auto-generated constructor stub
	  disks_num_ = 2;
	  my_turn_ = false;
	  symbol_ = symbol;
	  if (symbol_ == 'b') {
		  other_symbol_ = 'w';
	  }
	  else {
		  other_symbol_ = 'b';
	  }
	  this.playerColor = color;
	  //other_symbol_ = other_symbol;
	}
	public int getDisksNum() {
	  return disks_num_;
	}
	public void setDisksNum(int disks_num) {
	  disks_num_ = disks_num_ + disks_num;
	}
	public boolean getMyTurn() {
	  return my_turn_;
	}
	public void setMyTurn(boolean status) {
	  my_turn_ = status;
	}
	public char getSymbol() {
	  return symbol_;
	}
	public void setSymbol(char symbol) {
	  symbol_ = symbol;
	}
	public char getOppositeSymbol() {
	  return other_symbol_;
	}
	public void setOppositeSymbol(char other_symbol) {
	  other_symbol_ = other_symbol;
	}
	public Color getColor() {
		return this.playerColor;
	}
	public void turn(GameLogic logic, Board board, Player other) {
	 /*int row, column;
	 Vector<Point> possible_moves = new Vector<Point>();
	 System.out.println(symbol_ + ": It's your move." + "\n");
	 logic.availableMoves(possible_moves, board, symbol_, other_symbol_);
	 if (possible_moves.isEmpty() == false) {
	   logic.sortPoints(possible_moves);
	   logic.removeDuplicatePoints(possible_moves);
	   System.out.println("Your possible moves: ");
	   //print available moves
	   for (int i = 0; i < possible_moves.size(); i++) {
	     if (i > 0) {
	       System.out.print(",");
	     }
	     System.out.print("(" + possible_moves.elementAt(i).getPointX() + "," +
	         possible_moves.elementAt(i).getPointY() + ")");
	   }
	   System.out.println("\n");
	 }
	 //in case there are no avaiable moves.
	 else{
	   System.out.println("no possible moves. play passes back to the other player.\n");
	   setMyTurn(false);
	   return;
	 }
	 boolean legal = false;
	 System.out.println("Please enter your move row col:");
	 while (true){
		 Scanner s=new Scanner(System.in);
		 row = s.nextInt();
		 column = s.nextInt();
	   for (int i = 0; i < possible_moves.size(); i++) {
	     if (possible_moves.get(i).getPointX() == row) {
	       if (possible_moves.get(i).getPointY() == column ) {
	         legal = true;
	       }
	     }
	   }
	   if (legal == true)
	   {
		   //s.close();
	       break;
	   }
	   System.out.println("illegal move. choose another:");
	 }
	 Point point = new Point(row,column);
	 int counter = 0;
	 //set counter with the current amount of flips occured this turn.
	 counter = logic.flipDiscs(board,point,symbol_,other_symbol_);
	 System.out.println("number of flips: " + counter);
	 setDisksNum(1+counter);
	 other.setDisksNum(-counter);
	 //board.print();
	 System.out.println(symbol_ + " played (" + row + "," + column + ")" + "\n\n");*/
	}
}