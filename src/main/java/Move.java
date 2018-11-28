class Move {
    private int posX;
    private int posY;
    private int pawnToRemovePosX;
    private int pawnToRemovePosY;
    private boolean isJumpingOverOpponent;

    Move(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.isJumpingOverOpponent = false;
    }

    Move(int posX, int posY, int pawnToRemovePosX, int pawnToRemovePosY) {
        this.posX = posX;
        this.posY = posY;
        this.pawnToRemovePosX = pawnToRemovePosX;
        this.pawnToRemovePosY = pawnToRemovePosY;
        this.isJumpingOverOpponent = true;
    }

    int getPosX() {
        return posX;
    }

    int getPosY() {
        return posY;
    }

    public int getPawnToRemovePosX() {
        return pawnToRemovePosX;
    }

    public int getPawnToRemovePosY() {
        return pawnToRemovePosY;
    }

    public boolean isJumpingOverOpponent() {
        return isJumpingOverOpponent;
    }
}
