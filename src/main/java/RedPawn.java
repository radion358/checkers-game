import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RedPawn extends Pawn {

    private Image redMan = new Image("file:resources/redMan.png");
    private Image redKing = new Image("file:resources/redKing.png");




    public RedPawn(int posX, int posY) {
        super(posX, posY);
        ImageView view = new ImageView(redMan);
        getChildren().addAll(new Rectangle(110, 110, Color.BLUE), view);
    }


}
