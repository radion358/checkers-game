import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private final static List<Pawn> greyPawnList = new ArrayList<>();
    private final static List<Pawn> redPawnList = new ArrayList<>();
    private final  List<Pawn> pawnToRemove = new ArrayList<>();
    private final List<Tile> availableMoves = new ArrayList<>();
    private static String whoseTurn;
    List<Pawn> occupiedField = new ArrayList<>();
    private GridPane gameBoard = new GridPane();


    public static List<Pawn> getGreyPawnList() {
        return greyPawnList;
    }

    public static List<Pawn> getRedPawnList() {
        return redPawnList;
    }

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
    public void move (GridPane gameBoard, Pawn pawn, int newPosX, int newPosY) {

        if(pawn instanceof GreyPawn) {
            greyPawnList.remove(pawn);
            gameBoard.getChildren().remove(pawn);
            pawn.setPosX(newPosX);
            pawn.setPosY(newPosY);
            greyPawnList.add(pawn);
            gameBoard.add(pawn, pawn.getPosX(), pawn.getPosY());
        }else {
            redPawnList.remove(pawn);
            gameBoard.getChildren().remove(pawn);
            pawn.setPosX(newPosX);
            pawn.setPosY(newPosY);
            redPawnList.add(pawn);
            gameBoard.add(pawn, pawn.getPosX(), pawn.getPosY());
        }
        for (Tile tile: availableMoves) {
            gameBoard.getChildren().remove(tile);
        }
        availableMoves.clear();

        if(pawn instanceof GreyPawn){
            if(pawn.getPosY() == 0){
                pawn.setKing(true);
            }
        }else {
            if(pawn.getPosY() == 7) {
                pawn.setKing(true);
            }
        }
        toggleTurn();
    }

    public boolean isMoveAllowed (Pawn pawn, int newPosX, int newPosY) {
        if(whoseTurn.equals(pawn.getWhoIs())){
            if (newPosX != pawn.getPosX() && newPosY != pawn.getPosY()){
//                if ((newPosX + newPosY) % 2 != 0) {
                    if (isFieldEmpty(newPosX, newPosY)) {
                        return true;
//                        if (pawn.isKing()) {
//                            int diagonalProportion = pawn.getPosX() - newPosX;
//                            return (pawn.getPosY() - newPosY == diagonalProportion) || ((pawn.getPosY() - newPosY) == -diagonalProportion);
//                        }else return (((pawn.getPosX() - newPosX) == 1) || ((pawn.getPosX() - newPosX) == -1))
//                                && (((pawn.getPosY() - newPosY) == 1) || ((pawn.getPosY() - newPosY) == -1));
                    }else return false;
//                }else return false;
            }else return false;
        }else return false;
    }

    private boolean isFieldEmpty(int newPosX, int newPosY) {
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
        if (occupiedField.size() == 0) {
            return true;
        }
        return false;
    }


    private void showAvailableMove(Pawn pawn, GridPane gameBoard){
        for (Tile tile: availableMoves) {
            gameBoard.getChildren().remove(tile);
        }
        availableMoves.clear();
        for (Move move: pawn.getAvailableMoves(this)){
            addAvailableMoveTile(pawn, move.getPosX(), move.getPosY());
        }
    }
    private void toggleTurn() {
        if ("player".equals(whoseTurn)) {
            whoseTurn = "computer";
        }else {
            whoseTurn = "player";
        }
    }
    private void addPawnToRemove(int x, int y) {
        pawnToRemove.clear();
        pawnToRemove.add(getPawn(x, y));
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

    public void addAvailableMoveTile(Pawn pawn, int x, int y) {
        Tile tile = new Tile(x, y, 110, 110);
        availableMoves.add(tile);
        gameBoard.add(tile, x, y);
        tile.setOnMouseClicked(e -> {
            move(gameBoard, pawn, tile.getPosX(), tile.getPosY());
        });
    }

}
