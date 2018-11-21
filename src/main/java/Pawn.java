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
                x = getPosX() - 1;
                y = getPosY() - 1;
                if (gameBoard.isMoveAllowed(this, x, y)) {
                    addAvailableMoveTile(gameBoard, this, x, y);
                } else if (isJumpingOverOpponent(gameBoard, x, y, x - 1, y - 1)) {
                    if (isBoardContainsField(x - 1, y - 1)) {
                        if (gameBoard.isFieldEmpty(x - 1, y - 1)) {
                            addAvailableMoveTile(gameBoard, this, x - 1, y - 1, x, y);
                        }
                    }
                }
                x = getPosX() - 1;
                y = getPosY() + 1;
                if (gameBoard.isMoveAllowed(this, x, y)) {
                    addAvailableMoveTile(gameBoard, this, x, y);
                } else if (isJumpingOverOpponent(gameBoard, x, y, x - 1, y + 1)) {
                    if (isBoardContainsField(x - 1, y + 1)) {
                        if (gameBoard.isFieldEmpty(x - 1, y + 1)) {
                            addAvailableMoveTile(gameBoard, this, x - 1, y + 1, x, y);
                        }
                    }
                }
                x = getPosX() + 1;
                y = getPosY() - 1;
                if (gameBoard.isMoveAllowed(this, x, y)) {
                    addAvailableMoveTile(gameBoard, this, x, y);
                } else if (isJumpingOverOpponent(gameBoard, x, y, x + 1, y - 1)) {
                    if (isBoardContainsField(x + 1, y - 1)) {
                        if (gameBoard.isFieldEmpty(x + 1, y - 1)) {
                            addAvailableMoveTile(gameBoard, this, x + 1, y - 1, x, y);
                        }
                    }
                }

                x = getPosX() + 1;
                y = getPosY() + 1;
                if (gameBoard.isMoveAllowed(this, x, y)) {
                    addAvailableMoveTile(gameBoard, this, x, y);
                } else if (isJumpingOverOpponent(gameBoard, x, y, x + 1, y + 1)) {
                    if (isBoardContainsField(x + 1, y + 1)) {
                        if (gameBoard.isFieldEmpty(x + 1, y + 1)) {
                            addAvailableMoveTile(gameBoard, this, x + 1, y + 1, x, y);
                        }
                    }
                }
            }
        }
    }

    private boolean isJumpingOverOpponent(GameBoard gameBoard, int x, int y, int nextFieldX, int nextFieldY) {
        try {
            if (gameBoard.isFieldEmpty(nextFieldX, nextFieldY)) {
                if (gameBoard.getPawn(x, y).getWhoIs().equals(this.getWhoIs())) {
                    return false;
                }
                return true;
            }
        }catch (Exception e) {
            return false;
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
        tile.setOnMouseClicked(e -> {
            board.move(pawn, tile.getPosX(), tile.getPosY());
        });
    }
    private boolean isBoardContainsField (int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }
    public abstract void changeToKing();
}
