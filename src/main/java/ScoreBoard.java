import javafx.scene.control.Label;

public class ScoreBoard {
    private final Label scoreBoard = new Label();
    private final int[] score = new int[2];
    private final String playerName;
    
    public ScoreBoard() {
        this.playerName = "Player";
        this.score[0] = 0;
        this.score[1] = 0;
    }
    
    public ScoreBoard(String playerName) {
        this.playerName = playerName;
        this.score[0] = 0;
        this.score[1] = 0;
    }
    
    public ScoreBoard(String playerName, int[] score) {
        this.playerName = playerName;
        this.score[0] = score[0];
        this.score[1] = score[1];
    }
    
    public void resetScoreBoard() {
        score[0] = 0;
        score[1] = 0;
    }
    public Label generateScoreBoard (){
        scoreBoard.setText(playerName + ": " + score[0] + "  Computer: " + score[1]);
        return new Label(scoreBoard.getText());
    }
    public Label addScore(boolean playerWin){
        if(playerWin){
            score[0]++;
        }else {
            score[1]++;
        }
        return generateScoreBoard();
    }
}
