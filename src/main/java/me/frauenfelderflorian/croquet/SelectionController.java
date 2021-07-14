package me.frauenfelderflorian.croquet;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SelectionController {
    @FXML
    public GridPane grid;

    @FXML
    private void createNewGame() throws IOException {
        grid.getScene().getWindow().hide();
        Stage newGame = new Stage();
        newGame.setScene(new Scene(App.loadFXML("GameEditor")));
        newGame.setTitle(App.getString("newGame"));
        newGame.show();
        newGame.setMinHeight(newGame.getHeight());
        newGame.setMinWidth(newGame.getWidth());
        newGame.setOnHidden(windowEvent -> ((Stage) grid.getScene().getWindow()).show());
    }

    @FXML
    private void viewTournament() throws IOException {
        Stage tournamentViewer = new Stage();
        tournamentViewer.setScene(new Scene(App.loadFXML("TournamentViewer")));
        tournamentViewer.setTitle(App.getString("tournamentOverview"));
        tournamentViewer.show();
        tournamentViewer.setMinHeight(tournamentViewer.getHeight());
        tournamentViewer.setMinWidth(tournamentViewer.getWidth());
    }

    @FXML
    private void editGame() throws IOException {
        //open new window
        //let user choose which game to edit
        grid.getScene().getWindow().hide();
        Stage editGame = new Stage();
        editGame.setScene(new Scene(App.loadFXML("GameEditor")));
        editGame.setTitle(App.getString("editGame"));
        //fill in game info
        editGame.show();
        editGame.setMinHeight(editGame.getHeight());
        editGame.setMinWidth(editGame.getWidth());
        editGame.setOnHidden(windowEvent -> ((Stage) grid.getScene().getWindow()).show());
    }

    @FXML
    private void editSettings() throws IOException {
        grid.getScene().getWindow().hide();
        Stage settingsEditor = new Stage();
        settingsEditor.setScene(new Scene((App.loadFXML("SettingsEditor"))));
        settingsEditor.setTitle(App.getString("settings"));
        settingsEditor.show();
        settingsEditor.setMinHeight(settingsEditor.getHeight());
        settingsEditor.setMinWidth(settingsEditor.getWidth());
        settingsEditor.setOnHidden(windowEvent -> ((Stage) grid.getScene().getWindow()).show());
    }
}
