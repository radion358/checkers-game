import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;


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

    public List<Move> getAvailableMoves(GameBoard gameBoard) {
        List<Move> availableMoves = new ArrayList<>();
        int x;
        int y;
        if(isKing){
            for(x = getPosX(), y = getPosY(); x != 0 || y != 0; x--, y--) {
                if(gameBoard.isMoveAllowed(this, posX, posY)){
                    availableMoves.add(new Move(x, y));
                }else if (!(gameBoard.getPawn(x, y).equals(null))){
                    if(this.getWhoIs().equals(gameBoard.getPawn(x, y).getWhoIs())){
                        break;
                    }
                }
            }
            for(x = getPosX(), y = getPosY(); x != 0 || y != 0; x--, y++) {
                if(gameBoard.isMoveAllowed(this, posX, posY)){
                    availableMoves.add(new Move(x, y));
                }else if (!(gameBoard.getPawn(x, y).equals(null))){
                    if(this.getWhoIs().equals(gameBoard.getPawn(x, y).getWhoIs())){
                        break;
                    }
                }
            }
            for(x = getPosX(), y = getPosY(); x != 0 || y != 0; x++, y--) {
                if(gameBoard.isMoveAllowed(this, posX, posY)){
                    availableMoves.add(new Move(x, y));
                }else if (!(gameBoard.getPawn(x, y).equals(null))){
                    if(this.getWhoIs().equals(gameBoard.getPawn(x, y).getWhoIs())){
                        break;
                    }
                }
            }
            for(x = getPosX(), y = getPosY(); x != 0 || y != 0; x++, y++) {
                if(gameBoard.isMoveAllowed(this, posX, posY)){
                    availableMoves.add(new Move(x, y));
                }else if (!(gameBoard.getPawn(x, y).equals(null))){
                    if(this.getWhoIs().equals(gameBoard.getPawn(x, y).getWhoIs())){
                        break;
                    }
                }
            }

        }else {
            for(x = getPosX() - 1, y = getPosY() - 1; x != 0 || y != 0 || (x >= getPosX() - 2) || (y >= getPosY() - 2); x--, y--) {
                if(gameBoard.isMoveAllowed(this, posX, posY)){
                    availableMoves.add(new Move(x, y));
                }else if (!(gameBoard.getPawn(x, y).equals(null))){
                    if(this.getWhoIs().equals(gameBoard.getPawn(x, y).getWhoIs())){
                        break;
                    }
                }
            }
            for(x = getPosX() - 1, y = getPosY() + 1; x != 0 || y != 0 || (x >= getPosX() - 2) || (y <= getPosY() + 2); x--, y++) {
                if(gameBoard.isMoveAllowed(this, posX, posY)){
                    availableMoves.add(new Move(x, y));
                }else if (!(gameBoard.getPawn(x, y).equals(null))){
                    if(this.getWhoIs().equals(gameBoard.getPawn(x, y).getWhoIs())){
                        break;
                    }
                }
            }
            for(x = getPosX() + 1, y = getPosY() - 1; x != 0 || y != 0 || (x <= getPosX() + 2) || (y >= getPosY() - 2); x++, y--) {
                if(gameBoard.isMoveAllowed(this, posX, posY)){
                    availableMoves.add(new Move(x, y));
                }else if (!(gameBoard.getPawn(x, y).equals(null))){
                    if(this.getWhoIs().equals(gameBoard.getPawn(x, y).getWhoIs())){
                        break;
                    }
                }
            }
            for(x = getPosX() + 1, y = getPosY() + 1; x != 0 || y != 0 || (x <= getPosX() + 2) || (y <= getPosY() + 2); x++, y++) {
                if(gameBoard.isMoveAllowed(this, posX, posY)){
                    availableMoves.add(new Move(x, y));
                }else if (!(gameBoard.getPawn(x, y).equals(null))){
                    if(this.getWhoIs().equals(gameBoard.getPawn(x, y).getWhoIs())){
                        break;
                    }
                }
            }
        }
        return availableMoves;
    }

}
