import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

class GameBoard {
    static final List<Pawn> greyPawnList = new ArrayList<>();
    static final List<Pawn> redPawnList = new ArrayList<>();
    private final  List<Pawn> pawnsToRemove = new ArrayList<>();
    final List<Tile> availableMoves = new ArrayList<>();
    String whoseTurn;
    GridPane board = new GridPane();

    GridPane deal() {
        board = drawBoard();
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 3; y++){
                if((x + y) % 2 != 0){
                    RedPawn player2Pawn = new RedPawn(x, y);
                    redPawnList.add(player2Pawn);
                    board.add(player2Pawn, x, y);
                    player2Pawn.setOnMouseClicked((MouseEvent e) -> {
                        if(whoseTurn.equals(player2Pawn.getWhoIs())) {
                            showAvailableMove(player2Pawn);
                        }
                    });
                }
            }
        }
        for(int x = 0; x < 8; x++) {
            for(int y = 5; y < 8; y++){
                if((x + y) % 2 != 0){
                    GreyPawn player1Pawn = new GreyPawn(x, y);
                    greyPawnList.add(player1Pawn);
                    board.add(player1Pawn, x, y);
                    player1Pawn.setOnMouseClicked((MouseEvent e) -> {
                        if (whoseTurn.equals(player1Pawn.getWhoIs())){
                            showAvailableMove(player1Pawn);
                        }
                    });
                }
            }
        }
        whoseTurn = "player1";
        ScoreBoard.setPlayersScore();
        return board;
    }

    private GridPane drawBoard() {
//        board.setStyle("-fx-border-color: silver;\n"
//                + "-fx-border-width: 10;\n");


        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++){
                if((x + y) % 2 == 0){
                    Rectangle lightTile = new Rectangle( 110, 110, Color.YELLOW);
                    board.add(lightTile, x, y);
                }else {
                    Rectangle darkTile = new Rectangle(110, 110, Color.BLUE);
                    board.add(darkTile, x, y);
                }
            }
        }
        return board;
    }
    void move (Pawn pawn, int newPosX, int newPosY) {

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
            board.getChildren().remove(tile);
        }
        availableMoves.clear();
        for (Pawn pawnToRemove: pawnsToRemove) {
            board.getChildren().remove(pawnToRemove);
            redPawnList.remove(pawnToRemove);
            greyPawnList.remove(pawnToRemove);
        }

        if(pawn instanceof GreyPawn){
            if(pawn.getPosY() == 0){
                pawn.setKing();
                pawn.changeToKing();
            }
        }else {
            if(pawn.getPosY() == 7) {
                pawn.setKing();
                pawn.changeToKing();
            }
        }
        ScoreBoard.setPlayersScore();

        if (canOpponentMove()) {
            toggleTurn();
        }else {
            GameRunner.showWinner(pawn.getWhoIs());
        }
    }

    boolean isMoveAllowed (Pawn pawn, int newPosX, int newPosY) {
        if (newPosX != pawn.getPosX() && newPosY != pawn.getPosY()){
            return isFieldEmpty(newPosX, newPosY);
        }
        return false;
    }

    boolean isFieldEmpty(int newPosX, int newPosY) {
        if(newPosX < 0 || newPosX > 7 || newPosY < 0 || newPosY > 7) {
            return false;
        }
        return getPawn(newPosX, newPosY) == null;
    }


    private void showAvailableMove(Pawn pawn){
        for (Tile tile: availableMoves) {
            board.getChildren().remove(tile);
        }
        availableMoves.clear();
        for (Move move: pawn.getAvailableMoves(this)) {
            if (move.isJumpingOverOpponent()) {
                addAvailableMoveTile(pawn, move.getPosX(), move.getPosY(), move.getPawnToRemovePosX(), move.getPawnToRemovePosY());
            }else addAvailableMoveTile(pawn, move.getPosX(), move.getPosY());
        }

    }
    private void toggleTurn() {
        if ("player1".equals(whoseTurn)) {
            whoseTurn = "player2";
        }else {
            whoseTurn = "player1";
        }
    }
    void addPawnToRemove(int x, int y) {
        pawnsToRemove.clear();

        pawnsToRemove.add(getPawn(x, y));
    }

    Pawn getPawn(int x, int y) {
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
        board.getChildren().remove(pawn);
        pawn.setPosX(newPosX);
        pawn.setPosY(newPosY);
        board.add(pawn, pawn.getPosX(), pawn.getPosY());
        return pawn;
    }
    private boolean canOpponentMove() {
        if (whoseTurn.equals("player1")) {
            for (Pawn pawn: redPawnList){
                if (pawn.getAvailableMoves(this).size() != 0) {
                    return true;
                }

            }
        }else {
            for (Pawn pawn: greyPawnList){
                if (pawn.getAvailableMoves(this).size() != 0) {
                    return true;
                }

            }
        }
        return false;
    }
    private void addAvailableMoveTile(Pawn pawn, int availableMoveX, int availableMoveY, int pawnToRemoveX, int pawnToRemoveY) {
        Tile tile = new Tile(availableMoveX, availableMoveY, 110, 110);
        board.add(tile, availableMoveX, availableMoveY);
        availableMoves.add(tile);
        tile.setOnMouseClicked(e -> {
            addPawnToRemove(pawnToRemoveX, pawnToRemoveY);
            move(pawn, tile.getPosX(), tile.getPosY());
        });
    }
    private void addAvailableMoveTile(Pawn pawn, int availableMoveX, int availableMoveY) {
        Tile tile = new Tile(availableMoveX, availableMoveY, 110, 110);
        board.add(tile, availableMoveX, availableMoveY);
        availableMoves.add(tile);
        tile.setOnMouseClicked(e -> move(pawn, tile.getPosX(), tile.getPosY()));
    }
}
