import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RedPawn extends Pawn {

    private Image redMan = new Image("redMan.png");
    private Image redKing = new Image("redKing.png");




    public RedPawn(int posX, int posY) {
        super(posX, posY);
        ImageView view = new ImageView(redMan);
        view.setFitWidth(100);
        view.setFitHeight(100);
        getChildren().addAll(new Rectangle(110, 110, Color.BLUE), view);
    }


}
