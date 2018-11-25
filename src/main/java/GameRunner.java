import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.commons.io.IOUtils;

import java.util.Objects;
import java.util.regex.Pattern;

public class GameRunner extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        GameBoard gameBoard = new GameBoard();

        GridPane board = gameBoard.deal();

        board.setAlignment(Pos.CENTER);

        HBox leftHBox = new HBox();
        leftHBox.getChildren().addAll(ScoreBoard.player1NameLabel, ScoreBoard.player1Score);
        ScoreBoard.player1NameLabel.setFont(new Font(40));
        ScoreBoard.player1Score.setFont(new Font(40));
        HBox rightHBox = new HBox();
        rightHBox.getChildren().addAll(ScoreBoard.player2NameLabel, ScoreBoard.player2Score);
        ScoreBoard.player2NameLabel.setFont(new Font(40));
        ScoreBoard.player2Score.setFont(new Font(40));

        BorderPane scoreTable = new BorderPane();
        scoreTable.setLeft(leftHBox);
        scoreTable.setRight(rightHBox);


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
        RadioMenuItem theme1 = new RadioMenuItem("some theme");
        RadioMenuItem theme2 = new RadioMenuItem("some other theme");
        RadioMenuItem theme3 = new RadioMenuItem("something else");

        theme1.setToggleGroup(themeToggle);
        theme2.setToggleGroup(themeToggle);
        theme3.setToggleGroup(themeToggle);
        changeTheme.getItems().addAll(theme1, theme2, theme3);



        MenuBar gameMenuBar = new MenuBar();
        gameMenuBar.getMenus().add(newGame);
        gameMenuBar.getMenus().add(changePlayerName);
        gameMenuBar.getMenus().add(gameRules);
        gameMenuBar.getMenus().add(changeTheme);

        background.getChildren().addAll(scoreTable, board);
        root.getChildren().addAll(gameMenuBar, background);

        Scene scene = new Scene(root, 890, 973);
//        primaryStage.setResizable(false);
        primaryStage.setTitle("Checkers game");
        primaryStage.setScene(scene);
        primaryStage.show();

        Button startGame = new Button("Start game");
        TextField player1NameInput = new TextField("Grey");
        Label player1NameInputLabel = new Label("Enter player name:");
        TextField player2NameInput = new TextField("Red");
        Label player2NameInputLabel = new Label("Enter player name:");

        Stage newGameStage = new Stage();
        startGame.setOnAction(event -> {
            if(nameValidator(player1NameInput.getText()) && nameValidator(player2NameInput.getText())) {
                ScoreBoard.setPlayer1Name(player1NameInput.getText());
                ScoreBoard.setPlayer2Name(player2NameInput.getText());
                newGameStage.close();
            }
        });
        HBox controlBox = new HBox();
        controlBox.getChildren().addAll(player1NameInputLabel, player1NameInput, player2NameInputLabel, player2NameInput, startGame);
        VBox mainBox = new VBox();
        controlBox.setAlignment(Pos.CENTER);
        controlBox.setSpacing(20);
        Label rules = new Label();
        rules.setText(readRules());
        mainBox.getChildren().addAll(controlBox, rules);
        newGameStage.setTitle("New Game");
        Scene newGameScene = new Scene(mainBox, 800, 600);
        newGameStage.setScene(newGameScene);
        newGameStage.show();



    }
    private static boolean nameValidator(String input) {
        System.out.println(input);
        return Pattern.matches("[a-zA-Z]+", input);
    }
    private String readRules() {
        String rules;
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            rules = IOUtils.toString(Objects.requireNonNull(classLoader.getResourceAsStream("rules.txt")));
        }catch (Exception e) {
            rules = e.toString() + "\nNo such file or directory";
        }
        return rules;
    }
    public static void showWinner(String whoWin){
        Label winner = new Label();
        if (whoWin.equals("player1")){
            winner.setText(ScoreBoard.getPlayer1Name() + " Win");
        }else {
            winner.setText(ScoreBoard.getPlayer2Name() + " Win");
        }
        winner.setFont(new Font(40));
        Button newGame = new Button("new game");

        VBox showWinner = new VBox();
        showWinner.getChildren().addAll(winner, newGame);
        showWinner.setAlignment(Pos.CENTER);

        Scene winnerScene = new Scene(showWinner, 400, 300);

        Stage winnerStage = new Stage();
        winnerStage.setTitle("Congratulation!!!");
        winnerStage.setScene(winnerScene);
        winnerStage.showAndWait();

    }
}
