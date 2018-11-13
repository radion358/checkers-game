import javafx.scene.layout.StackPane;


public abstract class Pawn extends StackPane {
    private int posX;
    private int posY;
    private boolean isKing;
    private final String whoIs;

    public Pawn(int posX, int posY, String whoIs) {
        this.posX = posX;
        this.posY = posY;
        this.isKing = false;
        this.whoIs = whoIs;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public boolean isKing() {
        return isKing;
    }

    public String getWhoIs() {
        return whoIs;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setKing(boolean king) {
        isKing = king;
    }
}
