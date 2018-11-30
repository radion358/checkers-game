class Move {
    private int posX;
    private int posY;
    private int pawnToRemovePosX;
    private int pawnToRemovePosY;
    private boolean isJumpingOverOpponent;
    private Pawn pawn;

    Move(Pawn pawn, int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.isJumpingOverOpponent = false;
        this.pawn = pawn;
    }

    Move(Pawn pawn, int posX, int posY, int pawnToRemovePosX, int pawnToRemovePosY) {
        this.posX = posX;
        this.posY = posY;
        this.pawnToRemovePosX = pawnToRemovePosX;
        this.pawnToRemovePosY = pawnToRemovePosY;
        this.isJumpingOverOpponent = true;
        this.pawn = pawn;
    }

    int getPosX() {
        return posX;
    }

    int getPosY() {
        return posY;
    }

    int getPawnToRemovePosX() {
        return pawnToRemovePosX;
    }

    int getPawnToRemovePosY() {
        return pawnToRemovePosY;
    }

    boolean isJumpingOverOpponent() {
        return isJumpingOverOpponent;
    }

    Pawn getPawn() {
        return this.pawn;
    }
}
