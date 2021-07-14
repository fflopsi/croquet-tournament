package me.frauenfelderflorian.croquet;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import me.frauenfelderflorian.croquet.data.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameEditorController {
    @FXML
    private DatePicker date;
    @FXML
    private TextField difficulty;
    @FXML
    private GridPane grid;

    @FXML
    private void initialize() {
        //create ChoiceBoxes in List and set items
        List<ChoiceBox<String>> boxes = new ArrayList<>();
        for (int i = 0; i < CroquetApp.tournament.getPlayers().size(); i++)
            boxes.add(new ChoiceBox<>(FXCollections.observableList(CroquetApp.tournament.getPlayers())));
        //add ChoiceBoxes to view
        int row = 0;
        for (ChoiceBox<?> b : boxes) {
            grid.add(b, 1, row);
            row++;
            grid.add(new Label(row + ". " + CroquetApp.getString("place")), 0, row - 1);
            b.setMaxWidth(Double.MAX_VALUE);
        }
    }

    @FXML
    private void saveGame(ActionEvent actionEvent) {
        String[] playersByPoints = new String[CroquetApp.tournament.getPlayers().size()];
        int place = 0;
        for (Node node : grid.getChildren()) {
            if (node instanceof ChoiceBox<?>) {
                for (String player : playersByPoints) {
                    if (player != null && player.equals(((ChoiceBox<?>) node).getValue())) {
                        new Alert(Alert.AlertType.ERROR, CroquetApp.getString("playerMultipleTimes"),
                                ButtonType.CLOSE).showAndWait();
                        return;
                    }
                }
                playersByPoints[place] = (String) ((ChoiceBox<?>) node).getValue();
                place++;
            }
        }
        System.out.println(Arrays.toString(playersByPoints));
        for (String player : playersByPoints)
            if (player == null) System.out.println("a player was not selected, assumed absent");
        Game game = new Game(date.getValue(), difficulty.getText());
        String[] points = CroquetApp.getPreference(CroquetApp.preferenceKey.POINTS).split(",");
        for (int i = 0; i < playersByPoints.length; i++)
            if (playersByPoints[i] != null) game.addPoints(playersByPoints[i], Integer.parseInt(points[i]));
        int participants = game.getPoints().size();
        List<String> playersAbsent = new ArrayList<>(CroquetApp.tournament.getPlayers());
        for (String player : game.getPoints().keySet()) playersAbsent.remove(player);
        for (String player : playersAbsent) game.addPoints(player, Integer.parseInt(points[participants + 1]));
        CroquetApp.tournament.addGame(game);
        grid.getScene().getWindow().hide();
    }
}
