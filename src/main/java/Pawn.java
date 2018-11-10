import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Pawn extends StackPane {
    private int posX;
    private int posY;
    private boolean isKing;

    public Pawn(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.isKing = false;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void move (int newPosX, int newPosY) {

    }
    private boolean isMoveAlowed (int newPosX, int newPosY) {
        if (newPosX != posX && newPosY != posY){
            if ((newPosX + newPosY) % 2 == 0) {
                if (isFieldEmpty(newPosX, newPosY)) {
                    if (isKing) {
                        return true;
                    }else return (posX - newPosX == 1) || (posX - newPosX == -1);
                }else return false;
            }else return false;
        }else return false;
    }

    private boolean isFieldEmpty(int newPosX, int newPosY) {
        List<Integer> occupiedX = new ArrayList<>(GameBoard.getGreyPawnList().stream()
                .map(GreyPawn::getPosX)
                .collect(Collectors.toList()));
        GameBoard.getRedPawnList().stream()
                .map(Pawn::getPosX)
                .forEach(occupiedX::add);

        List<Integer> occupiedY = new ArrayList<>(GameBoard.getGreyPawnList().stream()
                .map(GreyPawn::getPosY)
                .collect(Collectors.toList()));
        GameBoard.getRedPawnList().stream()
                .map(Pawn::getPosY)
                .forEach(occupiedY::add);

        return !occupiedX.contains(newPosX) || !occupiedY.contains(newPosY);
    }
}
