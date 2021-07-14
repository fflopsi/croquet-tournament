package me.frauenfelderflorian.croquet;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import me.frauenfelderflorian.croquet.data.Game;

import java.net.URL;
import java.util.ResourceBundle;

public class GameViewerController implements Initializable {
    @FXML
    private Label tournamentLabel;
    @FXML
    private GridPane grid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set text for title label
        tournamentLabel.setText(CroquetApp.getString("tournament") + " " + CroquetApp.tournament.getSemester());
        //set up labels for players
        int row = 1;
        for (String player : CroquetApp.tournament.getOrder().keySet()) {
            grid.add(new Label(player), 0, row);
            row++;
        }
        //set up labels for games
        int gameCol = 1;
        for (Game game : CroquetApp.tournament.getGames()) {
            grid.add(new Label(game.getDate().toString()), gameCol, 0);
            //set up labels for points
            int playerRow = 1;
            for (String player : CroquetApp.tournament.getOrder().keySet()) {
                grid.add(new Label(String.valueOf(CroquetApp.tournament.getGame(game.getDate()).getPointsPlayer(player))),
                        gameCol, playerRow);
                playerRow++;
            }
            gameCol++;
        }
    }
}
