import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RedPawn extends Pawn {

    private Image redMan = new Image("redMan.png");
    private Image redKing = new Image("redKing.png");




    public RedPawn(int posX, int posY) {
        super(posX, posY, "computer");
        ImageView view = new ImageView(redMan);
        view.setFitWidth(100);
        view.setFitHeight(100);
        getChildren().add(view);
    }


}
