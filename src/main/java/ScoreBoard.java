import javafx.scene.control.Label;

public class ScoreBoard {
    private final Label scoreBoard = new Label();
    private int playerScore;
    private int computerScore;
    private  String playerName;
    
    public ScoreBoard() {
        this.playerName = "Player";
        this.playerScore = 0;
        this.computerScore = 0;
    }
    
    public ScoreBoard(String playerName) {
        this.playerName = playerName;
        this.playerScore = 0;
        this.computerScore = 0;
    }
    
    public ScoreBoard(String playerName, int playerScore, int computerScore) {
        this.playerName = playerName;
        this.playerScore = playerScore;
        this.computerScore = computerScore;
    }
    
    public void resetScoreBoard() {
        playerScore = 0;
        computerScore = 0;
    }
    public Label generateScoreBoard (){
        scoreBoard.setText(playerName + ": " + playerScore + "  Computer: " + computerScore);
        return new Label(scoreBoard.getText());
    }
    public Label addScore(boolean playerWin){
        if(playerWin){
            playerScore++;
        }else {
            computerScore++;
        }
        return generateScoreBoard();
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    public String generateScoreBoardText (){
        scoreBoard.setText(playerName + ": " + playerScore + "  Computer: " + computerScore);
        return scoreBoard.getText();
    }
}
