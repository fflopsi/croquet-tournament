package me.frauenfelderflorian.croquet;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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
        tournamentLabel.setText(CroquetApp.getString("tournament") + " " + CroquetApp.tournament.getSemester());
        //set up labels for ranking
        int row = 0;
        int rowOffset = grid.getRowCount();
        String last = null;
        int equal = 0;
        for (String player : CroquetApp.tournament.getOrder().keySet()) {
            if (CroquetApp.tournament.getOrder().get(player).equals(CroquetApp.tournament.getOrder().get(last))) {
                grid.add(new Label((row - equal) + ". " + CroquetApp.getString("place")), 0, row + rowOffset);
                equal++;
            } else {
                grid.add(new Label((row + 1) + ". " + CroquetApp.getString("place")), 0, row + rowOffset);
                equal = 0;
            }
            grid.add(new Label(player), 1, row + rowOffset);
            grid.add(new Label(String.valueOf(CroquetApp.tournament.getOrder().get(player))), 2, row + rowOffset);
            row++;
            last = player;
        }
    }

    @FXML
    private void viewAllGames() throws IOException {
        Stage gameViewer = new Stage();
        gameViewer.setScene(new Scene(CroquetApp.loadFXML("GameViewer")));
        gameViewer.setTitle(CroquetApp.getString("gamesOverview"));
        gameViewer.show();
        gameViewer.setMinHeight(gameViewer.getHeight());
        gameViewer.setMinWidth(gameViewer.getWidth());
    }
}
