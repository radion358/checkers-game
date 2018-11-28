import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;


public abstract class Pawn extends StackPane {
    private int posX;
    private int posY;
    private boolean isKing;
    private final String whoIs;

    Pawn(int posX, int posY, String whoIs) {
        this.posX = posX;
        this.posY = posY;
        this.isKing = false;
        this.whoIs = whoIs;
    }

    int getPosX() {
        return posX;
    }

    int getPosY() {
        return posY;
    }

    String getWhoIs() {
        return whoIs;
    }

    void setPosX(int posX) {
        this.posX = posX;
    }

    void setPosY(int posY) {
        this.posY = posY;
    }

    void setKing() {
        isKing = true;
    }

    List<Move> getAvailableMoves(GameBoard gameBoard) {
        int x;
        int y;
        List<Move> availableMoves = new ArrayList<>();
        if (isKing) {
            for (x = getPosX() - 1, y = getPosY() - 1; isBoardContainsField(x, y); x--, y--) {
                if (gameBoard.isMoveAllowed(this, x, y)) {
                    availableMoves.add(new Move(x, y));
                }else if (isJumpingOverOpponent(gameBoard, x, y, x - 1, y - 1)) {
                    availableMoves.add(new Move(x - 1, y - 1, x, y));
                    break;
                }else break;
            }
            for (x = getPosX() - 1, y = getPosY() + 1; isBoardContainsField(x, y); x--, y++) {
                if (gameBoard.isMoveAllowed(this, x, y)) {
                    availableMoves.add(new Move(x, y));
                }else if (isJumpingOverOpponent(gameBoard, x, y, x - 1, y + 1)) {
                    availableMoves.add(new Move(x - 1, y + 1, x, y));
                    break;
                }else break;
            }
            for (x = getPosX() + 1, y = getPosY() - 1; isBoardContainsField(x, y); x++, y--) {
                if (gameBoard.isMoveAllowed(this, x, y)) {
                    availableMoves.add(new Move(x, y));
                }else if (isJumpingOverOpponent(gameBoard, x, y, x + 1, y - 1)) {
                    availableMoves.add(new Move(x + 1, y - 1, x, y));
                    break;
                }else break;
            }
            for (x = getPosX() + 1, y = getPosY() + 1; isBoardContainsField(x, y); x++, y++) {
                if (gameBoard.isMoveAllowed(this, x, y)) {
                    availableMoves.add(new Move(x, y));
                }else if (isJumpingOverOpponent(gameBoard, x, y, x + 1, y + 1)) {
                    availableMoves.add(new Move(x + 1, y + 1, x, y));
                    break;
                }else break;
            }
        } else {
            if (this instanceof GreyPawn) {
                x = getPosX() - 1;
                y = getPosY() - 1;
                if (gameBoard.isMoveAllowed(this, x, y)) {
                    availableMoves.add(new Move(x, y));
                } else {
                    checkMoveAvailability(gameBoard, x, y, "up left", availableMoves);
                }

                x = getPosX() + 1;
                y = getPosY() - 1;
                if (gameBoard.isMoveAllowed(this, x, y)) {
                    availableMoves.add(new Move(x, y));
                } else checkMoveAvailability(gameBoard, x, y, "up right", availableMoves);

                x = getPosX() - 1;
                y = getPosY() + 1;
                checkMoveAvailability(gameBoard, x, y, "down left", availableMoves);

                x = getPosX() + 1;
                y = getPosY() + 1;
                checkMoveAvailability(gameBoard, x, y, "down right", availableMoves);
            }else {
                x = getPosX() - 1;
                y = getPosY() - 1;
                checkMoveAvailability(gameBoard, x, y, "up left", availableMoves);

                x = getPosX() - 1;
                y = getPosY() + 1;
                if (gameBoard.isMoveAllowed(this, x, y)) {
                    availableMoves.add(new Move(x, y));
                } else {
                    checkMoveAvailability(gameBoard, x, y, "down left", availableMoves);
                }

                x = getPosX() + 1;
                y = getPosY() - 1;
                checkMoveAvailability(gameBoard, x, y, "up right", availableMoves);

                x = getPosX() + 1;
                y = getPosY() + 1;
                if (gameBoard.isMoveAllowed(this, x, y)) {
                    availableMoves.add(new Move(x, y));
                } else checkMoveAvailability(gameBoard, x, y, "down right", availableMoves);
            }
        }
        return availableMoves;
    }

    private void checkMoveAvailability(GameBoard gameBoard, int x, int y, String direction, List<Move> availableMoves) {
        int nextPosX, nextPosY;
        switch (direction) {
            case "up right":
                nextPosX = x + 1;
                nextPosY = y - 1;
                break;
            case "up left":
                nextPosX = x - 1;
                nextPosY = y - 1;
                break;
            case "down right":
                nextPosX = x + 1;
                nextPosY = y + 1;
                break;
            case "down left":
                nextPosX = x - 1;
                nextPosY = y + 1;
                break;
                default:
                    nextPosX = x;
                    nextPosY = y;

        }

        if (isJumpingOverOpponent(gameBoard, x, y, nextPosX, nextPosY)) {
            if (isBoardContainsField(nextPosX, nextPosY)) {
                if (gameBoard.isFieldEmpty(nextPosX, nextPosY)) {
                    availableMoves.add(new Move(nextPosX, nextPosY, x, y));
                }
            }
        }
    }


    private boolean isJumpingOverOpponent(GameBoard gameBoard, int x, int y, int nextFieldX, int nextFieldY) {
        if (gameBoard.isFieldEmpty(nextFieldX, nextFieldY)) {
            if (gameBoard.getPawn(x, y) != null) {
                return !gameBoard.getPawn(x, y).getWhoIs().equals(this.getWhoIs());
            }
        }
        return false;
    }

    private boolean isBoardContainsField(int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }
    public abstract void changeToKing();
}
