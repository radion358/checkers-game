import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RedPawn extends Pawn {

    private Image redMan = new Image("redMan.png");
    private Image redKing = new Image("redKing.png");
    private ImageView view = new ImageView();




    public RedPawn(int posX, int posY) {
        super(posX, posY, "computer");
        view.setImage(redMan);
        view.setFitWidth(100);
        view.setFitHeight(100);
        getChildren().add(view);
    }

    public void changeToKing() {
        view.setImage(redKing);
    }


}
