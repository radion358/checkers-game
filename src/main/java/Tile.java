
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


class Tile extends Rectangle {
    private final int posX;
    private final int posY;
    
    Tile(int posX, int posY, double width, double height) {
        super(width, height, Color.GREEN);
        this.posX = posX;
        this.posY = posY;
    }

    int getPosX() {
        return posX;
    }

    int getPosY() {
        return posY;
    }
    
    
}
