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

    public void getAvailableMoves(GameBoard gameBoard) {
        int x;
        int y;
        if(gameBoard.whoseTurn.equals(getWhoIs())) {
            if (isKing) {
                for (x = getPosX() - 1, y = getPosY() - 1; isBoardContainsField(x, y); x--, y--) {
                    if (gameBoard.isMoveAllowed(this, x, y)) {
                        addAvailableMoveTile(gameBoard, this, x, y);
                    }else if (isJumpingOverOpponent(gameBoard, x, y, x - 1, y - 1)) {
                        addAvailableMoveTile(gameBoard, this, x - 1, y - 1, x, y);
                        break;
                    }else break;
                }
                for (x = getPosX() - 1, y = getPosY() + 1; isBoardContainsField(x, y); x--, y++) {
                    if (gameBoard.isMoveAllowed(this, x, y)) {
                        addAvailableMoveTile(gameBoard, this, x, y);
                    }else if (isJumpingOverOpponent(gameBoard, x, y, x - 1, y + 1)) {
                        addAvailableMoveTile(gameBoard, this, x - 1, y + 1, x, y);
                        break;
                    }else break;
                }
                for (x = getPosX() + 1, y = getPosY() - 1; isBoardContainsField(x, y); x++, y--) {
                    if (gameBoard.isMoveAllowed(this, x, y)) {
                        addAvailableMoveTile(gameBoard, this, x, y);
                    }else if (isJumpingOverOpponent(gameBoard, x, y, x + 1, y - 1)) {
                        addAvailableMoveTile(gameBoard, this, x + 1, y - 1, x, y);
                        break;
                    }else break;
                }
                for (x = getPosX() + 1, y = getPosY() + 1; isBoardContainsField(x, y); x++, y++) {
                    if (gameBoard.isMoveAllowed(this, x, y)) {
                        addAvailableMoveTile(gameBoard, this, x, y);
                    }else if (isJumpingOverOpponent(gameBoard, x, y, x + 1, y + 1)) {
                        addAvailableMoveTile(gameBoard, this, x + 1, y + 1, x, y);
                        break;
                    }else break;
                }
            } else {
                if (this instanceof GreyPawn) {
                    x = getPosX() - 1;
                    y = getPosY() - 1;
                    if (gameBoard.isMoveAllowed(this, x, y)) {
                        addAvailableMoveTile(gameBoard, this, x, y);
                    } else {
                        checkMoveAvailability(gameBoard, x, y, "up left");
                    }

                    x = getPosX() + 1;
                    y = getPosY() - 1;
                    if (gameBoard.isMoveAllowed(this, x, y)) {
                        addAvailableMoveTile(gameBoard, this, x, y);
                    } else checkMoveAvailability(gameBoard, x, y, "up right");

                    x = getPosX() - 1;
                    y = getPosY() + 1;
                    checkMoveAvailability(gameBoard, x, y, "down left");

                    x = getPosX() + 1;
                    y = getPosY() + 1;
                    checkMoveAvailability(gameBoard, x, y, "down right");
                }else {
                    x = getPosX() - 1;
                    y = getPosY() - 1;
                    checkMoveAvailability(gameBoard, x, y, "up left");

                    x = getPosX() - 1;
                    y = getPosY() + 1;
                    if (gameBoard.isMoveAllowed(this, x, y)) {
                        addAvailableMoveTile(gameBoard, this, x, y);
                    } else {
                        checkMoveAvailability(gameBoard, x, y, "down left");
                    }

                    x = getPosX() + 1;
                    y = getPosY() - 1;
                    checkMoveAvailability(gameBoard, x, y, "up right");

                    x = getPosX() + 1;
                    y = getPosY() + 1;
                    if (gameBoard.isMoveAllowed(this, x, y)) {
                        addAvailableMoveTile(gameBoard, this, x, y);
                    } else checkMoveAvailability(gameBoard, x, y, "down right");
                }

            }
        }
    }

    private void checkMoveAvailability(GameBoard gameBoard, int x, int y, String direction) {
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
                    addAvailableMoveTile(gameBoard, this, nextPosX, nextPosY, x, y);
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

    public void addAvailableMoveTile(GameBoard board, Pawn pawn, int availableMoveX, int availableMoveY, int pawnToRemoveX, int pawnToRemoveY) {
        Tile tile = new Tile(availableMoveX, availableMoveY, 110, 110);
        board.gameBoard.add(tile, availableMoveX, availableMoveY);
        board.availableMoves.add(tile);
        tile.setOnMouseClicked(e -> {
            board.addPawnToRemove(pawnToRemoveX, pawnToRemoveY);
            board.move(pawn, tile.getPosX(), tile.getPosY());
        });
    }
    public void addAvailableMoveTile(GameBoard board, Pawn pawn, int availableMoveX, int availableMoveY) {
        Tile tile = new Tile(availableMoveX, availableMoveY, 110, 110);
        board.gameBoard.add(tile, availableMoveX, availableMoveY);
        board.availableMoves.add(tile);
        tile.setOnMouseClicked(e -> board.move(pawn, tile.getPosX(), tile.getPosY()));
    }
    private boolean isBoardContainsField (int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }
    public abstract void changeToKing();
}
