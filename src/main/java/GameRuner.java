import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameRuner extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        ScoreBoard scoreBoard = new ScoreBoard();
        GameBoard gameBoard = new GameBoard();

        GridPane board = gameBoard.drawBoard();

        board.setAlignment(Pos.CENTER);






        Label scoreBoardLabel = scoreBoard.generateScoreBoard();
        scoreBoardLabel.setFont(new Font(40));

        VBox root = new VBox();
        VBox background = new VBox();
        background.setAlignment(Pos.CENTER);
        background.setStyle("-fx-background-color: #336699;");




        //Menu bar
        Menu newGame = new Menu("_New game...");
        Menu changePlayerName = new Menu("_Change player name...");
        Menu gameRules = new Menu("_Game rules...");
        Menu changeTheme = new Menu("_Change theme");

        ToggleGroup themeToggle = new ToggleGroup();
        RadioMenuItem theme1 = new RadioMenuItem("burgundowy");
        RadioMenuItem theme2 = new RadioMenuItem("cos");
        RadioMenuItem theme3 = new RadioMenuItem("proszę zmień mnie");

        theme1.setToggleGroup(themeToggle);
        theme2.setToggleGroup(themeToggle);
        theme3.setToggleGroup(themeToggle);
        changeTheme.getItems().addAll(theme1, theme2, theme3);



        MenuBar gameMenuBar = new MenuBar();
        gameMenuBar.getMenus().add(newGame);
        gameMenuBar.getMenus().add(changePlayerName);
        gameMenuBar.getMenus().add(gameRules);
        gameMenuBar.getMenus().add(changeTheme);

        background.getChildren().addAll(scoreBoardLabel, board);
        root.getChildren().addAll(gameMenuBar, background);

        gameBoard.Deale(board);

        Scene scene = new Scene(root, 890, 973);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Checkers game");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
