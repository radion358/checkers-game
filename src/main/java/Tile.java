
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Tile extends Rectangle {
    private final int posX;
    private final int posY;
    
    public Tile(int posX, int posY, double width, double height) {
        super(width, height, Color.GREEN);
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
    
    
}
