/*
 * Eviatar Natan
 * 307851808
 */
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
 * the controller.
 */
public class BoardGameController implements Initializable {
    //buttons and labels
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
    //classes
    @FXML
    private Board reversiBoard;
    private Player firstP;
    private Player secondP;
    private Player current;
    private Player standby;
    private GameLogic logic;

    // load colors
    private Color firstColor;
    private Color secondColor;

    private final int sizeChangePref = 120;

    /**
     *  initializing the controller
     * @param location - the url.
     * @param resources - the resources.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> info = GameSettings.loadSettings();
        // initializes the first player and second player color.
        this.firstColor = Color.valueOf(info.get(1));
        this.secondColor = Color.valueOf(info.get(2));
        // initializes the starter player.
        if(info.get(0).compareTo("BlackPlayer") == 0) {
            this.firstP = new HumanPlayer('b', this.firstColor);
            this.secondP = new HumanPlayer('w', this.secondColor);
        } else {
            this.firstP = new HumanPlayer('w', this.secondColor);
            this.secondP = new HumanPlayer('b', this.firstColor);
        }
        this.current = firstP;
        this.standby = secondP;
        this.logic = new ReversiLogic();
        // initializing the board.
        this.reversiBoard = new Board(Integer.valueOf(info.get(3)), this.firstColor, this.secondColor);
        this.reversiBoard.onMouseClickedProperty().setValue(e -> {
            int firstPlayerscore = this.firstP.getDisksNum();
            this.blackScoreLabel.setText(Integer.toString(firstPlayerscore));
            int secondPlayerscore = this.secondP.getDisksNum();
            this.whiteScoreLabel.setText(Integer.toString(secondPlayerscore));
            if(this.logic.checkIfGameIsWon(this.reversiBoard)) {
                this.endGame();
                return;
            }
        });
        Cell[][] tempBoard = this.reversiBoard.getBoardTable();
        // go over each board cell
        for(int i = 0; i < tempBoard.length; i++) {
            for (int j = 0; j < tempBoard[i].length; j++) {
                // sets mouse event if a cell on the board was clicked on
                this.reversiBoard.getBoardTable()[i][j].onMouseClickedProperty().setValue(e -> {
                    Cell temp = (Cell) e.getSource();
                    Vector<Point> possibleMoves = this.logic.availableMoves(
                            this.reversiBoard, this.current.getSymbol(), this.current.getOppositeSymbol());
                    if(possibleMoves.size() != 0) {
                        if (this.logic.validOption(this.reversiBoard, temp.getRow() + 1,
                                temp.getColumn() + 1, possibleMoves)) {
                            Point tempPoint = new Point(temp.getRow() +1,temp.getColumn() +1);
                            int counter = this.logic.flipDiscs(this.reversiBoard,tempPoint, this.current);
                            this.current.setDisksNum(counter+1);
                            this.standby.setDisksNum(-counter);
                            if (this.current.getSymbol() == 'b') {
                                ((Circle) e.getSource()).setFill(decidePlayerColor());
                            } else if (this.current.getSymbol() == 'w') {
                                ((Circle) e.getSource()).setFill(decidePlayerColor());
                            }
                            ((Circle) e.getSource()).setStroke(Color.BLACK);
                            if (temp.getPlayerSymbol() == 'n') {
                                temp.setPlayerSymbol(this.current.getSymbol());
                            }
                            // change the current player
                            switchTurn();
                        }
                    }
                    // check if the other player has no moves
                    checkForNoMoves();
                    // show the new available moves
                    showAvailableMovesOnBoard();
                });
            }
        }
        resizeWindowEvents();
        // make board disabled untill start button is pressed
        this.reversiBoard.setDisable(true);
    }

    /**
     * swaps between current and standby players.
     */
    private void switchTurn() {
        if (this.playingPlayer.getText().compareTo("First player") == 0) {
            this.playingPlayer.setText("Second player");
            this.current = this.secondP;
            this.standby = this.firstP;
        } else if (playingPlayer.getText().compareTo("Second player") == 0) {
            this.playingPlayer.setText("First player");
            this.current = this.firstP;
            this.standby = this.secondP;
        }
    }

    /**
     * checks if current player has no moves.
     * <p>
     * if not, the turn will swap to the other player, if the other player has
     * no moves as well the game will end. otherwise it will continue.
     */
    private void checkForNoMoves() {
        // if the current player has no moves
        Vector<Point> availableMoves = this.logic.availableMoves(this.reversiBoard, this.current.getSymbol(),this.current.getOppositeSymbol());
        if(availableMoves.size() == 0) {
            // change the current player
            switchTurn();
            // check if the next player has no moves left
            availableMoves = this.logic.availableMoves(this.reversiBoard, this.current.getSymbol(),this.current.getOppositeSymbol());
            if(availableMoves.size() == 0) {
                // if true then end the game
                endGame();
                return;
            }
            // show noMoves message
            noMovesAlert();
        }
    }

    /**
     * returns the current player's color.
     * @return current player's color.
     */
    private Color decidePlayerColor() {
        return this.current.getColor();
    }

    /**
     * starts the game.
     */
    @FXML
    public void startGame() {
        this.start.setVisible(false);
        this.reversiBoard.setDisable(false);
        showAvailableMovesOnBoard();
    }
    /**
     * ends the game and shows the winner/tie message.
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
            this.winnerLabel2.setText("Won!");
        } else if(Integer.valueOf(this.whiteScoreLabel.getText()) < Integer.valueOf(this.blackScoreLabel.getText())) {
            this.winnerLabel.setText("First player");
            this.winnerLabel2.setText("Won!");
        } else {
            this.winnerLabel.setText("Its a draw");
            this.winnerLabel2.setVisible(false);
        }
    }

    /**
     * closes the game's menu.
     * @param mouseEvent - the mouse event.
     */
    public void exitGameWindow(MouseEvent mouseEvent) {
        Main.returnToMainMenu();
    }

    /**
     * check that resizing is correct.
     */
    private void resizeWindowEvents() {
        this.root.getChildren().add(0, this.reversiBoard);
        this.root.widthProperty().addListener((observable, oldVal, newVal) -> {
            double boardNewWidth = newVal.doubleValue() - sizeChangePref;
            this.reversiBoard.setPrefWidth(boardNewWidth);
            this.reversiBoard.draw();
        });
        this.root.heightProperty().addListener((observable, oldValue, newValue) -> {
            this.reversiBoard.setPrefHeight(newValue.doubleValue());
            this.reversiBoard.draw();
        });
    }

    /**
     * removes last player's available moves.
     */
    private void removeAvailableMovesDisplay() {
        for (int i = 0; i < this.reversiBoard.getBoardTable().length; i++) {
            for (int j = 0; j < this.reversiBoard.getBoardTable()[i].length; j++) {
                if (this.reversiBoard.getBoardTable()[i][j].getPlayerSymbol() != 'n') {
                    reversiBoard.getBoardTable()[i][j].setStroke(Color.BLACK);
                } else {
                    reversiBoard.getBoardTable()[i][j].setStroke(Color.BISQUE);
                }
            }
        }
    }

    /**
     * checks current player available moves and draws them on the board.
     */
    private void showAvailableMovesOnBoard() {
        removeAvailableMovesDisplay();
        // getting a list of the possible moves.
        Vector<Point> availableMoves = this.logic.availableMoves(this.reversiBoard, current.getSymbol(),current.getOppositeSymbol());
        for(int i = 0; i < this.reversiBoard.getBoardTable().length; i++) {
            for (int j = 0; j < this.reversiBoard.getBoardTable()[i].length; j++) {
                for (int k = 0; k < availableMoves.size(); k++) {
                    // shows the possible move as a yellow circle.
                    if (availableMoves.get(k).getPointX()-1 == i && availableMoves.get(k).getPointY()-1 == j) {
                        this.reversiBoard.getBoardTable()[i][j].setStroke(Color.RED);
                    }
                }
            }
        }
    }

    /**
     * shows the no moves alert.
     */
    private void noMovesAlert() {
        Stage noMovesWindow = new Stage();
        noMovesWindow.setTitle("No moves");
        noMovesWindow.centerOnScreen();
        // set the label message.
        Label noMovesLabel = new Label("you have no moves...\n turn passes to other player.");
        noMovesLabel.setId("noMove");
        VBox box = new VBox();
        box.getChildren().add(noMovesLabel);
        //alert size
        Scene scene = new Scene(box, 220, 50);
        root.setDisable(true);
        noMovesWindow.setScene(scene);
        noMovesWindow.showAndWait();
        root.setDisable(false);
    }
}
