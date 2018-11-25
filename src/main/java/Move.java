class Move {
    private int posX;
    private int posY;
    private Pawn pawn;
    private int pawnToRemovePosX;
    private int pawnToRemovePosY;
    private boolean isJumpingOverOpponent;

    Move(int posX, int posY, Pawn pawn) {
        this.posX = posX;
        this.posY = posY;
        this.pawn = pawn;
        this.isJumpingOverOpponent = false;
    }

    Move(int posX, int posY, Pawn pawn, int pawnToRemovePosX, int pawnToRemovePosY) {
        this.posX = posX;
        this.posY = posY;
        this.pawn = pawn;
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

    Pawn getPawn() {
        return pawn;
    }

    public boolean isJumpingOverOpponent() {
        return isJumpingOverOpponent;
    }
}
