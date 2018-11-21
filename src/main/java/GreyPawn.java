import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GreyPawn extends Pawn {
    private Image greyMan = new Image("greyMan.png");
    private Image greyKing = new Image("greyKing.png");
    private ImageView view = new ImageView();

    public GreyPawn(int posX, int posY) {
        super(posX, posY, "player");
        view.setImage(greyMan);
        view.setFitWidth(100);
        view.setFitHeight(100);
        getChildren().add(view);
    }

    public void changeToKing() {
        view.setImage(greyKing);
    }


}

