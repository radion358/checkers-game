import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private Image greyMan = new Image("file:resources/greyMan.png");
    private Image redMan = new Image("file:resources/redMan.png");
    private Image greyKing = new Image("file:resources/greyKing.png");
    private Image redKing = new Image("file:resources/redKing.png");
    private final static List<GreyPawn> greyPawnList = new ArrayList<>();
    private final static List<RedPawn> redPawnList = new ArrayList<>();


    public static List<GreyPawn> getGreyPawnList() {
        return new ArrayList<>(greyPawnList);
    }

    public static List<RedPawn> getRedPawnList() {
        return new ArrayList<>(redPawnList);
    }

    public GridPane deal() {
        GridPane gameBoard = drawBoard();
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 3; y++){
                if((x + y) % 2 != 0){
                    RedPawn computerPawn = new RedPawn(x, y);
                    redPawnList.add(computerPawn);
                    gameBoard.add(computerPawn, x, y);
                }
            }
        }
        for(int x = 0; x < 8; x++) {
            for(int y = 5; y < 8; y++){
                if((x + y) % 2 != 0){
                    GreyPawn playerPawn = new GreyPawn(x, y);
                    greyPawnList.add(playerPawn);
                    gameBoard.add(playerPawn, x, y);
                }
            }
        }


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
    
}
