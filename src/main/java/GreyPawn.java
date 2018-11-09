import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GreyPawn extends Pawn {
    private Image greyMan = new Image("file:resources/greyMan.png");
    private Image greyKing = new Image("file:resources/greyKing.png");

    public GreyPawn(int posX, int posY) {
        super(posX, posY);
        ImageView view = new ImageView(greyMan);
        getChildren().addAll(new Rectangle(110, 110, Color.BLUE), view);
    }


}

