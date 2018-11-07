import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameBoard {
    private Image greyMan = new Image("file:resources/greyMan.png");
    private Image redMan = new Image("file:resources/redMan.png");
    private Image greyKing = new Image("file:resources/greyKing.png");
    private Image redKing = new Image("file:resources/redKing.png");
    
    public Pane Deale(Pane board) {
        //ImageView pawn = new ImageView();
        return board;
    }
    
    public GridPane drawBoard() {
        GridPane gameBoard = new GridPane();
        gameBoard.setStyle("-fx-border-color: silver;\n"
                + "-fx-border-width: 10;\n");

       
        for(int x = 0; x < 7; x++) {
            for(int y = 0; y < 7; y++){
                if((x + y) % 2 == 0){
                    StackPane lightTile = new StackPane();
                    lightTile.getChildren().add(new Rectangle(110, 110, Color.YELLOW));
                    gameBoard.add(lightTile, x, y);
                }else {
                    StackPane darkTile = new StackPane();
                    darkTile.getChildren().add(new Rectangle(110, 110, Color.BLUE));
                    gameBoard.add(darkTile, x, y);
                    
                }
                
            }
        }
        return gameBoard;
    }
    
}
