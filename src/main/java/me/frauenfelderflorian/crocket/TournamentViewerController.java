package me.frauenfelderflorian.crocket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import me.frauenfelderflorian.crocket.data.Game;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TournamentViewerController implements Initializable {
    @FXML
    private Label tournamentLabel;
    @FXML
    private GridPane grid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set text for title label
        tournamentLabel.setText("Turnier " + App.tournament.getSemester());
        //set up labels for ranking
        int row = 0;
        int rowOffset = grid.getRowCount();
        String last = null;
        int equal = 0;
        for (String player : App.tournament.getOrder().keySet()) {
            if (App.tournament.getOrder().get(player).equals(App.tournament.getOrder().get(last))) {
                grid.add(new Label((row - equal) + ". Platz"), 0, row + rowOffset);
                equal++;
            } else {
                grid.add(new Label((row + 1) + ". Platz"), 0, row + rowOffset);
                equal = 0;
            }
            grid.add(new Label(player), 1, row + rowOffset);
            grid.add(new Label(String.valueOf(App.tournament.getOrder().get(player))), 2, row + rowOffset);
            row++;
            last = player;
        }
    }

    @FXML
    private void viewAllGames() throws IOException {
        Stage gameViewer = new Stage();
        gameViewer.setScene(new Scene(App.loadFXML("GameViewer")));
        gameViewer.setTitle("Spielübersicht");
        gameViewer.show();
        gameViewer.setMinHeight(gameViewer.getHeight());
        gameViewer.setMinWidth(gameViewer.getWidth());
    }
}
