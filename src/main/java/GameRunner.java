import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.io.IOUtils;

import java.util.Objects;
import java.util.regex.Pattern;

public class GameRunner extends Application {
    private GameBoard gameBoard;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        gameBoard = new GameBoard(this);
        gameBoard.drawBoard();
        GridPane board = gameBoard.board;

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
        Menu game = new Menu("Game");
        MenuItem newGame = new MenuItem("_New game...");
        MenuItem changePlayerName = new MenuItem("_Change player name...");
        MenuItem gameRules = new MenuItem("_Game rules...");
        game.getItems().addAll(newGame, changePlayerName,gameRules);
        Menu changeTheme = new Menu("_Change theme");

        ToggleGroup themeToggle = new ToggleGroup();
        RadioMenuItem theme1 = new RadioMenuItem("some theme");
        RadioMenuItem theme2 = new RadioMenuItem("some other theme");
        RadioMenuItem theme3 = new RadioMenuItem("something else");

        theme1.setToggleGroup(themeToggle);
        theme2.setToggleGroup(themeToggle);
        theme3.setToggleGroup(themeToggle);
        changeTheme.getItems().addAll(theme1, theme2, theme3);

//       menu eventHandler
        newGame.setOnAction(e -> startGame());
        gameRules.setOnAction(event -> showRules());
        changePlayerName.setOnAction(event -> changePlayerName());

        MenuBar gameMenuBar = new MenuBar();
        gameMenuBar.getMenus().addAll(game, changeTheme);

        background.getChildren().addAll(scoreTable, board);
        root.getChildren().addAll(gameMenuBar, background);

        Scene scene = new Scene(root, 890, 973);
//        primaryStage.setResizable(false);
        primaryStage.setTitle("Checkers game");
        primaryStage.setScene(scene);
        primaryStage.show();

        startGame();





    }
    private static boolean nameValidator(String input) {
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
    void showWinner(String whoWin){
        String message;
        if (whoWin.equals("player1")){
            message = ScoreBoard.getPlayer1Name() + " Win";
        }else {
            message = ScoreBoard.getPlayer2Name() + " Win";
        }
        String title = "congratulation!!!";
        showAlertBox(title, message);
    }

    private void startGame() {
        Button startGame = new Button("Start game");
        TextField player1NameInput = new TextField("Grey");
        Label player1NameInputLabel = new Label("Enter player name:");
        TextField player2NameInput = new TextField("Red");
        Label player2NameInputLabel = new Label("Enter player name:");

        Stage newGameStage = new Stage();
        newGameStage.initModality(Modality.APPLICATION_MODAL);
        startGame.setOnAction(event -> {
            if(nameValidator(player1NameInput.getText()) && nameValidator(player2NameInput.getText())) {
                ScoreBoard.setPlayer1Name(player1NameInput.getText());
                ScoreBoard.setPlayer2Name(player2NameInput.getText());
                newGameStage.close();
                gameBoard.deal();

            }
        });
        HBox controlBox = new HBox();
        controlBox.getChildren().addAll(player1NameInputLabel, player1NameInput, player2NameInputLabel, player2NameInput, startGame);
        VBox mainBox = new VBox();
        controlBox.setAlignment(Pos.CENTER);
        controlBox.setSpacing(20);
        Label rules = new Label();
        rules.setText(readRules());
        rules.setPadding(new Insets(10));
        mainBox.getChildren().addAll(controlBox, rules);
        newGameStage.setTitle("New Game");
        Scene newGameScene = new Scene(mainBox, 800, 600);
        newGameStage.setScene(newGameScene);
        newGameStage.show();

    }

    private void showRules() {
        Label rules = new Label();
        rules.setText(readRules());
        rules.setPadding(new Insets(10));
        Scene rulesScene = new Scene(rules);
        Stage rulesStage = new Stage();
        rulesStage.setTitle("Game rules");
        rulesStage.setScene(rulesScene);
        rulesStage.show();
    }

    void showDraw() {
        String title = "Tie";
        String message = "There is a tie\nTry again";
        showAlertBox(title, message);
    }

    private void showAlertBox(String title, String message) {
        Label messageToShow = new Label(message);

        messageToShow.setFont(new Font(40));
        Button newGame = new Button("new game");


        VBox alertBox = new VBox();
        alertBox.getChildren().addAll(messageToShow, newGame);
        alertBox.setAlignment(Pos.CENTER);

        Scene winnerScene = new Scene(alertBox, 400, 300);

        Stage alertBoxStage = new Stage();
        alertBoxStage.initModality(Modality.APPLICATION_MODAL);
        alertBoxStage.setTitle(title);
        alertBoxStage.setScene(winnerScene);
        alertBoxStage.setAlwaysOnTop(true);
        alertBoxStage.setResizable(false);
        alertBoxStage.show();
        newGame.setOnAction(event -> {
            startGame();
            alertBoxStage.close();
        });
    }

    private void changePlayerName() {
        TextField player1Name = new TextField(ScoreBoard.getPlayer1Name());
        TextField player2Name = new TextField(ScoreBoard.getPlayer2Name());
        Button changeNames = new Button("change names");

        VBox sceneBox = new VBox();
        sceneBox.getChildren().addAll(player1Name, player2Name, changeNames);
        sceneBox.setFillWidth(false);
        sceneBox.setSpacing(30);
        sceneBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(sceneBox, 300, 200);
        Stage changePlayerNameStage = new Stage();
        changePlayerNameStage.setScene(scene);
        changePlayerNameStage.setTitle("Change name");
        changePlayerNameStage.show();


        changeNames.setOnAction(event -> {
            if (nameValidator(player1Name.getText()) && nameValidator(player2Name.getText())) {
                ScoreBoard.setPlayer1Name(player1Name.getText());
                ScoreBoard.setPlayer2Name(player2Name.getText());
                changePlayerNameStage.close();
            }
        });
    }
}
