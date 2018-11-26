import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;

class ScoreBoard {
    private static String player1Name = "Grey";
    private static String player2Name = "Red";
    static Label player1NameLabel = new Label(player1Name + ": ");
    static Label player2NameLabel = new Label(player2Name + ": ");
    static Label player1Score = new Label();
    static Label player2Score = new Label();



    static void setPlayer1Name(String playerName) {
        player1Name = playerName;
        player1NameLabel.setText(player1Name + ": ");
    }

    static void setPlayer2Name(String playerName) {
        player2Name = playerName;
        player2NameLabel.setText(player2Name + ": ");
    }

    static void setPlayersScore() {
        player1Score.textProperty().bind(new SimpleIntegerProperty(GameBoard.greyPawnList.size()).asString());
        player2Score.textProperty().bind(new SimpleIntegerProperty(GameBoard.redPawnList.size()).asString());
    }

    static String getPlayer1Name() {
        return player1Name;
    }

    static String getPlayer2Name() {
        return player2Name;
    }
}
