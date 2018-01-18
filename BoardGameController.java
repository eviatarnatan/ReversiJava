package gameapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * Organizes all the controllers in the board.
 */
public class BoardGameController implements Initializable {
    // all objects used in game window
    public Label whiteScoreLabel;
    public Label blackScoreLabel;
    public Button start;
    public Button end;
    public Label winnerLabel;
    public Label winnerLabel2;
    public Label playingPlayer;
    public Button closeButton;
    public Label firstStartLabel;
    public Label secondStartLabel;
    @FXML
    private HBox root;

    // the classes objects
    @FXML
    private Board reversiBoard;
    private Player first;
    private Player second;
    private Player current;
    private GameLogic logic;

    // load from file settings
    private Color firstColor;
    private Color secondColor;

    // defaults
    private final int PREFSIZECHANGE = 120;

    /**
     *  initializing the default values of the controller.
     * @param location the url.
     * @param resources the rescourses.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> info = SettingsWindow.loadSettings();
        // initializing the first player and second player color.
        this.firstColor = Color.valueOf(info.get(1));
        this.secondColor = Color.valueOf(info.get(2));
        // initializing the starter player.
        if(info.get(0).compareTo("BlackPlayer") == 0) {
            this.first = new HumanPlayer('b', this.firstColor);
            this.second = new HumanPlayer('w', this.secondColor);
        } else {
            this.first = new HumanPlayer('w', this.secondColor);
            this.second = new HumanPlayer('b', this.firstColor);
        }
        this.current = first;
        this.logic = new ReversiLogic();
        // initializing the board.
        this.reversiBoard = new Board(Integer.valueOf(info.get(3)), this.firstColor, this.secondColor);
        this.reversiBoard.onMouseClickedProperty().setValue(e -> {
            // initializing the counting of players' scores.
            //this.first.setScore(this.logic.playerGrade(this.reversiBoard, 'b'));
            //this.second.setScore(this.logic.playerGrade(this.reversiBoard, 'w'));
        	/*if (this.first.getDisksNum() != 2 && this.second.getDisksNum() == 2) {
        		
        	}*/
            int firstPlayerscore = this.first.getDisksNum();
            this.blackScoreLabel.setText(Integer.toString(firstPlayerscore));
            int secondPlayerscore = this.second.getDisksNum();
            this.whiteScoreLabel.setText(Integer.toString(secondPlayerscore));
            if(this.logic.isGameWon(this.reversiBoard)) {
                this.endGame();
                return;
            }
        });
        Cell[][] tempBoard = this.reversiBoard.getBoardTable();
        // go over the board cells
        for(int i = 0; i < tempBoard.length; i++) {
            for (int j = 0; j < tempBoard[i].length; j++) {
                // set event for when a cell on the board is clicked
                this.reversiBoard.getBoardTable()[i][j].onMouseClickedProperty().setValue(e -> {
                    Cell temp = (Cell) e.getSource();
                    Vector<Point> availableMovesInner = this.logic.availableMoves(
                            this.reversiBoard, this.current.getSymbol(), this.current.getOppositeSymbol());
                    if(availableMovesInner.size() != 0) {
                        if (this.logic.validOption(this.reversiBoard, temp.getRow() + 1,
                                temp.getColumn() + 1, availableMovesInner)) {
                            // changing the tiles after the click.
                        	Point tempPoint = new Point(temp.getRow(),temp.getColumn()); 
                            this.logic.flipDiscs(this.reversiBoard,tempPoint, this.current.getSymbol(),
                            		this.current.getOppositeSymbol());
                            if (this.current.getSymbol() == 'b') {
                                ((Circle) e.getSource()).setFill(decidePlayerColor());
                            } else if (this.current.getSymbol() == 'w') {
                                ((Circle) e.getSource()).setFill(decidePlayerColor());
                            }
                            ((Circle) e.getSource()).setStroke(Color.BLACK);
                            if (temp.getPlayer() == 'b') {
                                temp.setPlayer(this.current.getSymbol());
                            }
                            // change the current player
                            swapTurn();
                        }
                    }
                    // check if the other player has no moves
                    checkForNoMoves();
                    // show the new available moves
                    showAvailableMoves();
                });
            }
        }
        resizeWindowEvents();
        // make board disabled untill start button is pressed
        this.reversiBoard.setDisable(true);
    }

    /*
     * function name: swapTrun.
     * input: none.
     * output: none.
     * operation: switches the turns between the players.
     */
    private void swapTurn() {
        if (this.playingPlayer.getText().compareTo("First player") == 0) {
            this.playingPlayer.setText("Second player");
            this.current = this.second;
        } else if (playingPlayer.getText().compareTo("Second player") == 0) {
            this.playingPlayer.setText("First player");
            this.current = this.first;
        }
    }

    /*
     * function name: checkForNoMoves.
     * input: none.
     * output: none.
     * operation: checks if there are any available moves for the current player.
     * if there are no possible moves, a message is shown.
     * if both players have no possible moves, the game ends.
     */
    private void checkForNoMoves() {
        // if the current player has no moves
        Vector<Point> availableMoves = this.logic.availableMoves(this.reversiBoard, this.current.getSymbol(),this.current.getOppositeSymbol());
        if(availableMoves.size() == 0) {
            // change the current player
            swapTurn();
            // check if the next player has no moves left
            availableMoves = this.logic.availableMoves(this.reversiBoard, this.current.getSymbol(),this.current.getOppositeSymbol());
            if(availableMoves.size() == 0) {
                // if true then end the game
                endGame();
                return;
            }
            // show noMoves message
            noMovesPrompt();
        }
    }

    /*
     * function name: decidePlayerColor.
     * input: none.
     * output: the color of the current player.
     * operation: returns the color of the current player.
     */
    private Color decidePlayerColor() {
        return this.current.getColor();
    }

    /*
     * function name: startGame.
     * input: none.
     * output: none.
     * operation: starts the game operation.
     */
    @FXML
    public void startGame() {
        this.firstStartLabel.setVisible(false);
        this.secondStartLabel.setVisible(false);
        this.reversiBoard.setDisable(false);
        showAvailableMoves();
    }

    /*
     * function name: endGame.
     * input: none.
     * output: none.
     * operation: disables all moves, shows a winning message and shows a close button.
     */
    @FXML
    public void endGame() {
        this.start.setDisable(true);
        this.end.setDisable(true);
        this.closeButton.setVisible(true);
        this.reversiBoard.setDisable(true);
        this.winnerLabel.setVisible(true);
        this.winnerLabel2.setVisible(true);
        if(Integer.valueOf(this.whiteScoreLabel.getText()) > Integer.valueOf(this.blackScoreLabel.getText())) {
            this.winnerLabel.setText("Second player");
            this.winnerLabel2.setText("Wins!");
        } else if(Integer.valueOf(this.whiteScoreLabel.getText()) < Integer.valueOf(this.blackScoreLabel.getText())) {
            this.winnerLabel.setText("First player");
            this.winnerLabel2.setText("Wins!");
        } else {
            this.winnerLabel.setText("Its a tie!");
        }
    }

    /**
     * setting the action of close window to be switching back to menu.
     * @param mouseEvent clicking the mouse.
     */
    public void closeGameWindow(MouseEvent mouseEvent) {
        Main.switchBackToMain();
    }

    /*
     * function name: resizeWindowEvents.
     * input: none.
     * output: none.
     * operation: reassures that the window resize doesn't go over the limited borders of
     * min and max window size.
     */
    private void resizeWindowEvents() {
        this.root.getChildren().add(0, this.reversiBoard);
        this.root.widthProperty().addListener((observable, oldVal, newVal) -> {
            double boardNewWidth = newVal.doubleValue() - PREFSIZECHANGE;
            this.reversiBoard.setPrefWidth(boardNewWidth);
            this.reversiBoard.draw();
        });
        this.root.heightProperty().addListener((observable, oldValue, newValue) -> {
            this.reversiBoard.setPrefHeight(newValue.doubleValue());
            this.reversiBoard.draw();
        });
    }

    /*
     * function name: resetPiecesStroke.
     * input: none.
     * output: none.
     * operation: .
     */
    private void resetPiecesStroke() {
        for (int i = 0; i < this.reversiBoard.getBoardTable().length; i++) {
            for (int j = 0; j < this.reversiBoard.getBoardTable()[i].length; j++) {
                if (this.reversiBoard.getBoardTable()[i][j].getPlayer() != 'n') {
                    reversiBoard.getBoardTable()[i][j].setStroke(Color.BLACK);
                } else {
                    reversiBoard.getBoardTable()[i][j].setStroke(Color.GREEN);
                }
            }
        }
    }

    /*
     * function name: showAvailableMoves.
     * input: none.
     * output: none.
     * operation: show the possible moves of the current player. Represented as yellow circles.
     */
    private void showAvailableMoves() {
        resetPiecesStroke();
        // getting a list of the possible moves.
        Vector<Point> options = this.logic.availableMoves(this.reversiBoard, current.getSymbol(),current.getOppositeSymbol());
        for(int i = 0; i < this.reversiBoard.getBoardTable().length; i++) {
            for (int j = 0; j < this.reversiBoard.getBoardTable()[i].length; j++) {
                for (int k = 0; k < options.size(); k++) {
                    // shows the possible move as a yellow circle.
                    if (options.get(k).getPointX() - 1 == i && options.get(k).getPointY() - 1 == j) {
                        this.reversiBoard.getBoardTable()[i][j].setStroke(Color.YELLOW);
                    }
                }
            }
        }
    }

    /*
     * function name: noMovesPrompt.
     * input: none.
     * output: none.
     * operation: shows a window with no move message.
     */
    private void noMovesPrompt() {
        // setting a new window.
        Stage noMovesWindow = new Stage();
        noMovesWindow.setTitle("No moves!");
        noMovesWindow.centerOnScreen();
        // set the label message.
        Label noMovesLabel = new Label("No moves!\n turn goes to other player.");
        noMovesLabel.setId("noMove");
        VBox box = new VBox();
        box.getChildren().add(noMovesLabel);
        Scene scene = new Scene(box, 250, 40);
        root.setDisable(true);
        noMovesWindow.setScene(scene);
        noMovesWindow.showAndWait();
        root.setDisable(false);
    }
}
