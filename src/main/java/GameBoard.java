import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private final static List<Pawn> greyPawnList = new ArrayList<>();
    private final static List<Pawn> redPawnList = new ArrayList<>();
    private final  List<Pawn> pawnsToRemove = new ArrayList<>();
    public final List<Tile> availableMoves = new ArrayList<>();
    public String whoseTurn;
    List<Pawn> occupiedField = new ArrayList<>();
    public GridPane gameBoard = new GridPane();

    public GridPane deal() {
        gameBoard = drawBoard();
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 3; y++){
                if((x + y) % 2 != 0){
                    RedPawn computerPawn = new RedPawn(x, y);
                    redPawnList.add(computerPawn);
                    gameBoard.add(computerPawn, x, y);
                    computerPawn.setOnMouseClicked((MouseEvent e) -> showAvailableMove(computerPawn, gameBoard));
                }
            }
        }
        for(int x = 0; x < 8; x++) {
            for(int y = 5; y < 8; y++){
                if((x + y) % 2 != 0){
                    GreyPawn playerPawn = new GreyPawn(x, y);
                    greyPawnList.add(playerPawn);
                    gameBoard.add(playerPawn, x, y);
                    playerPawn.setOnMouseClicked((MouseEvent e) -> showAvailableMove(playerPawn, gameBoard));
                }
            }
        }
        whoseTurn = "player";
        return gameBoard;
    }

    public GridPane drawBoard() {
        gameBoard.setStyle("-fx-border-color: silver;\n"
                + "-fx-border-width: 10;\n");


        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++){
                if((x + y) % 2 == 0){
                    Rectangle lightTile = new Rectangle(110, 110, Color.YELLOW);
                    gameBoard.add(lightTile, x, y);
                }else {
                    Rectangle darkTile = new Rectangle(110, 110, Color.BLUE);
                    gameBoard.add(darkTile, x, y);
                }
            }
        }
        return gameBoard;
    }
    public void move (Pawn pawn, int newPosX, int newPosY) {

        if(pawn instanceof GreyPawn) {
            greyPawnList.remove(pawn);
            Pawn newPawn = changePawnPosition(pawn, newPosX, newPosY);
            greyPawnList.add(newPawn);
        }else {
            redPawnList.remove(pawn);
            Pawn newPawn = changePawnPosition(pawn, newPosX, newPosY);
            redPawnList.add(newPawn);
        }
        for (Tile tile: availableMoves) {
            gameBoard.getChildren().remove(tile);
        }
        availableMoves.clear();
        for (Pawn pawnToRemove: pawnsToRemove) {
            gameBoard.getChildren().remove(pawnToRemove);
            redPawnList.remove(pawnToRemove);
            greyPawnList.remove(pawnToRemove);
        }

        if(pawn instanceof GreyPawn){
            if(pawn.getPosY() == 0){
                pawn.setKing(true);
                pawn.changeToKing();
            }
        }else {
            if(pawn.getPosY() == 7) {
                pawn.setKing(true);
                pawn.changeToKing();
            }
        }
        toggleTurn();
    }

    public boolean isMoveAllowed (Pawn pawn, int newPosX, int newPosY) {
        if (newPosX != pawn.getPosX() && newPosY != pawn.getPosY()){
            return isFieldEmpty(newPosX, newPosY);
        }else return false;
    }

    public boolean isFieldEmpty(int newPosX, int newPosY) {
        if(newPosX < 0 || newPosX > 7 || newPosY < 0 || newPosY > 7) {
            return false;
        }
        List<Pawn> pawnToRemoveFromOccupiedField = new ArrayList<>();
        occupiedField.clear();
        for (Pawn pawn: greyPawnList) {
            if (pawn.getPosX() == newPosX) {
                occupiedField.add(pawn);
            }
        }
        for (Pawn pawn: redPawnList) {
            if (pawn.getPosX() == newPosX) {
                occupiedField.add(pawn);
            }
        }
        for (Pawn pawn: occupiedField) {
            if (pawn.getPosY() != newPosY) {
                pawnToRemoveFromOccupiedField.add(pawn);
            }
        }
        for(Pawn pawn: pawnToRemoveFromOccupiedField) {
            occupiedField.remove(pawn);
        }
        return occupiedField.size() == 0;
    }


    private void showAvailableMove(Pawn pawn, GridPane gameBoard){
        for (Tile tile: availableMoves) {
            gameBoard.getChildren().remove(tile);
        }
        availableMoves.clear();
        pawn.getAvailableMoves(this);
    }
    private void toggleTurn() {
        if ("player".equals(whoseTurn)) {
            whoseTurn = "computer";
        }else {
            whoseTurn = "player";
        }
    }
    public void addPawnToRemove(int x, int y) {
        pawnsToRemove.clear();

        pawnsToRemove.add(getPawn(x, y));
    }

    public Pawn getPawn(int x, int y) {
        List<Pawn> pawnList = new ArrayList<>();
        List<Pawn> pawnToRemoveFromPawnList = new ArrayList<>();

        for (Pawn pawn: greyPawnList) {
            if (pawn.getPosX() == x) {
                pawnList.add(pawn);
            }
        }
        for (Pawn pawn: redPawnList) {
            if (pawn.getPosX() == x) {
                pawnList.add(pawn);
            }
        }
        for (Pawn pawn: pawnList) {
            if (pawn.getPosY() != y) {
                pawnToRemoveFromPawnList.add(pawn);
            }
        }
        for(Pawn pawn: pawnToRemoveFromPawnList) {
            pawnList.remove(pawn);
        }
        if(pawnList.size() != 0) return pawnList.get(0);
        return null;
    }
    private Pawn changePawnPosition(Pawn pawn, int newPosX, int newPosY) {
        gameBoard.getChildren().remove(pawn);
        pawn.setPosX(newPosX);
        pawn.setPosY(newPosY);
        gameBoard.add(pawn, pawn.getPosX(), pawn.getPosY());
        return pawn;
    }
}
