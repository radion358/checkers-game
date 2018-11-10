import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class GameRunner extends Application {
    private String playerName;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AtomicReference<ScoreBoard> scoreBoard = new AtomicReference<>(new ScoreBoard());
        GameBoard gameBoard = new GameBoard();

        GridPane board = gameBoard.deal();

        board.setAlignment(Pos.CENTER);






        Label scoreBoardLabel = scoreBoard.get().generateScoreBoard();
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

        Scene scene = new Scene(root, 890, 973);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Checkers game");
        primaryStage.setScene(scene);
        primaryStage.show();

        Button startGame = new Button("Start game");
        TextField playerNameInput = new TextField("Player");
        Label playerNameInputLabel = new Label("Enter player name:");

        Stage newGameStage = new Stage();
        startGame.setOnAction(event -> {
            if(nameValidator(playerNameInput.getText())) {
                scoreBoard.set(new ScoreBoard(playerNameInput.getText()));
                newGameStage.close();
            }else System.out.print(playerNameInput.getText());
        });
        HBox controlBox = new HBox();
        controlBox.getChildren().addAll(playerNameInputLabel, playerNameInput, startGame);
        VBox mainBox = new VBox();
        controlBox.setAlignment(Pos.CENTER);
        controlBox.setSpacing(20);
        mainBox.getChildren().add(controlBox);
        newGameStage.setTitle("New Game");
        Scene newGameScene = new Scene(mainBox, 600, 600);
        newGameStage.setScene(newGameScene);
        newGameStage.show();


    }
    private static boolean nameValidator(String input) {
        System.out.println(input);
        boolean b = Pattern.matches("[a-zA-Z]+", input);
        System.out.println(b);
        return b;
    }
}
