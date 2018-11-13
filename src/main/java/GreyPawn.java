import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GreyPawn extends Pawn {
    private Image greyMan = new Image("greyMan.png");
    private Image greyKing = new Image("greyKing.png");

    public GreyPawn(int posX, int posY) {
        super(posX, posY, "player");
        ImageView view = new ImageView(greyMan);
        view.setFitWidth(100);
        view.setFitHeight(100);
        getChildren().add(view);
    }


}

