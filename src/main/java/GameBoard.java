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
    private String whoseTurn;
    List<Pawn> occupiedField = new ArrayList<>();


    public static List<Pawn> getGreyPawnList() {
        return greyPawnList;
    }

    public static List<Pawn> getRedPawnList() {
        return redPawnList;
    }

    public GridPane deal() {
        GridPane gameBoard = drawBoard();
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
        GridPane gameBoard = new GridPane();
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
        int oldPosX = pawn.getPosX();
        int oldPosY = pawn.getPosY();
        List<Pawn> opponentPawnList;
        if(pawn instanceof GreyPawn) {
            greyPawnList.remove(pawn);
            gameBoard.getChildren().remove(pawn);
            pawn.setPosX(newPosX);
            pawn.setPosY(newPosY);
            greyPawnList.add(pawn);
            gameBoard.add(pawn, pawn.getPosX(), pawn.getPosY());
            for(Pawn jumpedOverPawn: pawnToRemove) {
                gameBoard.getChildren().remove(jumpedOverPawn);
                redPawnList.remove(jumpedOverPawn);
            }
        }else {
            redPawnList.remove(pawn);
            gameBoard.getChildren().remove(pawn);
            pawn.setPosX(newPosX);
            pawn.setPosY(newPosY);
            redPawnList.add(pawn);
            gameBoard.add(pawn, pawn.getPosX(), pawn.getPosY());
            for(Pawn jumpedOverPawn: pawnToRemove) {
                gameBoard.getChildren().remove(jumpedOverPawn);
                greyPawnList.remove(jumpedOverPawn);
            }
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

    private boolean isMoveAllowed (Pawn pawn, int newPosX, int newPosY) {
        if(whoseTurn.equals(pawn.getWhoIs())){
            if (newPosX != pawn.getPosX() && newPosY != pawn.getPosY()){
                if ((newPosX + newPosY) % 2 != 0) {
                    if (isFieldEmpty(newPosX, newPosY)) {
                        if (pawn.isKing()) {
                            return true;
                        }else return (((pawn.getPosX() - newPosX) == 1) || ((pawn.getPosX() - newPosX) == -1))
                                && (((pawn.getPosY() - newPosY) == 1) || ((pawn.getPosY() - newPosY) == -1));
                    }else return false;
                }else return false;
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
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++){
                if((x + y) % 2 != 0){
                    if(isMoveAllowed (pawn, x, y)){
                        Tile tile = new Tile(x, y, 110, 110);
                        availableMoves.add(tile);
                        gameBoard.add(tile, x, y);
                        tile.setOnMouseClicked(e -> move(gameBoard, pawn, tile.getPosX(), tile.getPosY()));
                    }else if( ! isFieldEmpty(x, y)){
                        if(((pawn.getPosX() - x) == 1)) {
                            if(((pawn.getPosY() - y) == 1)) {
                                if(isFieldEmpty(x - 1, y - 1) && jumpedPawnBelongsToOpponent(pawn)) {
                                    Tile tile = new Tile(x - 1, y - 1, 110, 110);
                                    availableMoves.add(tile);
                                    gameBoard.add(tile, x - 1, y - 1);
                                    tile.setOnMouseClicked(e -> {
                                        addPawnToRemove(pawn.getPosX() - 1, pawn.getPosY() - 1);
                                        move(gameBoard, pawn, tile.getPosX(), tile.getPosY());
                                    });
                                }
                            }else if(((pawn.getPosY() - y) == -1)) {
                                if(isFieldEmpty(x - 1, y + 1) && jumpedPawnBelongsToOpponent(pawn)) {
                                    Tile tile = new Tile(x - 1, y + 1, 110, 110);
                                    availableMoves.add(tile);
                                    gameBoard.add(tile, x - 1, y + 1);
                                    tile.setOnMouseClicked(e -> {
                                        addPawnToRemove(pawn.getPosX() - 1, pawn.getPosY() + 1);
                                        move(gameBoard, pawn, tile.getPosX(), tile.getPosY());
                                    });
                                }
                            }
                        }else if((pawn.getPosX() - x) == -1) {
                            if(((pawn.getPosY() - y) == 1)) {
                                if(isFieldEmpty(x + 1, y - 1) && jumpedPawnBelongsToOpponent(pawn)) {
                                    Tile tile = new Tile(x + 1, y - 1, 110, 110);
                                    availableMoves.add(tile);
                                    gameBoard.add(tile, x + 1, y - 1);
                                    tile.setOnMouseClicked(e -> {
                                        addPawnToRemove(pawn.getPosX() +1, pawn.getPosY() - 1);
                                        move(gameBoard, pawn, tile.getPosX(), tile.getPosY());
                                    });
                                }
                            }else if(((pawn.getPosY() - y) == -1)) {
                                if (isFieldEmpty(x + 1, y + 1) && jumpedPawnBelongsToOpponent(pawn)) {
                                    Tile tile = new Tile(x + 1, y + 1, 110, 110);
                                    availableMoves.add(tile);
                                    gameBoard.add(tile, x + 1, y + 1);
                                    tile.setOnMouseClicked(e -> {
                                        addPawnToRemove(pawn.getPosX() +1, pawn.getPosY() + 1);
                                        move(gameBoard, pawn, tile.getPosX(), tile.getPosY());

                                    });
                                }
                            }
                        }
                    }
                }
            }
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
        List<Pawn> pawnToRemoveFromPawnToRemove = new ArrayList<>();
        for (Pawn pawn: greyPawnList) {
            if (pawn.getPosX() == x) {
                pawnToRemove.add(pawn);
            }
        }
        for (Pawn pawn: redPawnList) {
            if (pawn.getPosX() == x) {
                pawnToRemove.add(pawn);
            }
        }
        for (Pawn pawn: pawnToRemove) {
            if (pawn.getPosY() != y) {
                pawnToRemoveFromPawnToRemove.add(pawn);
            }
        }
        for(Pawn pawn: pawnToRemoveFromPawnToRemove) {
            pawnToRemove.remove(pawn);
        }
    }
    private boolean jumpedPawnBelongsToOpponent(Pawn pawn) {
        if(whoseTurn.equals(pawn.getWhoIs())){
            return false;
        }else return true;
    }
}
