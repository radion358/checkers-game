import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameBoard {
    private final static List<Pawn> greyPawnList = new ArrayList<>();
    private final static List<Pawn> redPawnList = new ArrayList<>();
    private final  List<Pawn> pawnToRemove = new ArrayList<>();
    private final List<Tile> availableMoves = new ArrayList<>();
    private String whoseTurn;


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
        if(isMoveAllowed(pawn, newPosX, newPosY)) {
            if(pawn instanceof GreyPawn) {
                greyPawnList.remove(pawn);
                pawn.setPosX(newPosX);
                pawn.setPosY(newPosY);
                greyPawnList.add(pawn);
                opponentPawnList = getRedPawnList();
            }else {
                redPawnList.remove(pawn);
                pawn.setPosX(newPosX);
                pawn.setPosY(newPosY);
                redPawnList.add(pawn);
                opponentPawnList = getGreyPawnList();
            }

            if(jumpOverOpponent(oldPosX, oldPosY, newPosX, newPosY,opponentPawnList)) {
                pawnToRemove.forEach(gameBoard.getChildren()::remove);
            }else {
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
        }

    }
    private boolean isMoveAllowed (Pawn pawn, int newPosX, int newPosY) {
        if(whoseTurn.equals(pawn.getWhoIs())){
            if (newPosX != pawn.getPosX() && newPosY != pawn.getPosY()){
                if ((newPosX + newPosY) % 2 == 0) {
                    if (isFieldEmpty(newPosX, newPosY)) {
                        if (pawn.isKing()) {
                            return true;
                        }else return (pawn.getPosX() - newPosX == 1) || (pawn.getPosX() - newPosX == -1);
                    }else return false;
                }else return false;
            }else return false;
        }else return false;
    }

    private boolean isFieldEmpty(int newPosX, int newPosY) {
        List<Integer> occupiedX = getGreyPawnList().stream()
                .map(Pawn::getPosX)
                .collect(Collectors.toList());
        GameBoard.getRedPawnList().stream()
                .map(Pawn::getPosX)
                .forEach(occupiedX::add);

        List<Integer> occupiedY = getGreyPawnList().stream()
                .map(Pawn::getPosY)
                .collect(Collectors.toList());
        getRedPawnList().stream()
                .map(Pawn::getPosY)
                .forEach(occupiedY::add);

        return !occupiedX.contains(newPosX) || !occupiedY.contains(newPosY);
    }
    private boolean jumpOverOpponent(int oldPosX, int oldPosY, int newPosX, int newPosY, List<Pawn> opponentPawnList) {
        if((oldPosX - newPosX)== 1 || (oldPosX - newPosX)== -1){
            return false;
        }else {
            if(newPosX > oldPosX ){
                for(int x = oldPosX + 1; x < newPosX; x++) {
                    removeOpponentPawn(oldPosY, newPosY, opponentPawnList, x);
                }
            }else {
                for(int x = oldPosX - 1; x < newPosX; x--) {
                    removeOpponentPawn(oldPosY, newPosY, opponentPawnList, x);
                }
            }
        }
        return true;
    }

    private void removeOpponentPawn(int oldPosY, int newPosY, List<Pawn> opponentPawnList, int x) {
        if(newPosY > oldPosY) {
            for(int y = oldPosY + 1; y < newPosY; y++) {
                for(Pawn element: opponentPawnList){
                    if(element.getPosX() == x){
                        if(element.getPosY() == y){
                            opponentPawnList.remove(element);
                            pawnToRemove.remove(element);
                        }
                    }
                }
            }
        }else {
            for(int y = oldPosY - 1; y > newPosY; y--) {
                for(Pawn element: opponentPawnList){
                    if(element.getPosX() == x){
                        if(element.getPosY() == y){
                            opponentPawnList.remove(element);
                            pawnToRemove.remove(element);
                        }
                    }
                }
            }
        }
    }

    private void showAvailableMove(Pawn pawn, GridPane gameBoard){
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++){
                if((x + y) % 2 == 0){
                    System.out.println(x + " " + y);
                    if(isMoveAllowed (pawn, x, y)){
                        Tile tile = new Tile(x, y, 110, 110);
                        availableMoves.add(tile);
                        gameBoard.add(tile, x, y);
                        tile.setOnMouseClicked(e -> move(gameBoard, pawn, tile.getPosX(), tile.getPosY()));
                    }
                }
            }
        }
    }
    private void toggleTurn() {
        if (whoseTurn.equals("player")) {
            whoseTurn = "computer";
        }else {
            whoseTurn = "player";
        }
    }
}
