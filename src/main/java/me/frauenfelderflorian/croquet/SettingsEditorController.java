package me.frauenfelderflorian.croquet;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class SettingsEditorController implements Initializable {
    @FXML
    private GridPane grid;
    @FXML
    private TextField currentTournamentEdit;
    @FXML
    private TextField playersEdit;
    @FXML
    private TextField pointsEdit;
    @FXML
    private TextField dataDirEdit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentTournamentEdit.setText(CroquetApp.tournament.getSemester());
        playersEdit.setText(String.join(",", CroquetApp.tournament.getPlayers()));
        pointsEdit.setText(CroquetApp.getPreference(CroquetApp.preferenceKey.POINTS));
        dataDirEdit.setText(CroquetApp.getPreference(CroquetApp.preferenceKey.DATA_DIR_PATH));
    }

    @FXML
    private void selectDataDir() {
        DirectoryChooser dataDirChooser = new DirectoryChooser();
        dataDirChooser.setTitle(CroquetApp.getString("selectDirectory"));
        dataDirChooser.setInitialDirectory(new File(CroquetApp.getPreference(CroquetApp.preferenceKey.DATA_DIR_PATH)));
        dataDirEdit.setText(Objects.requireNonNullElse(dataDirChooser.showDialog(grid.getScene().getWindow()),
                CroquetApp.getPreference(CroquetApp.preferenceKey.DATA_DIR_PATH)).toString());
    }

    @FXML
    private void save() {
        //save current and load new tournament
        try {
            CroquetApp.tournament.save();
            CroquetApp.loadTournament(currentTournamentEdit.getText());
            CroquetApp.putPreference(CroquetApp.preferenceKey.CURRENT_TOURNAMENT, currentTournamentEdit.getText());
        } catch (IOException e) {
            System.out.println("tournament could not be saved");
            Alert alert = new Alert(Alert.AlertType.ERROR, CroquetApp.getString("tournamentSaveError"));
            alert.showAndWait();
        }
        //save players
        CroquetApp.tournament.setPlayers(Arrays.asList(playersEdit.getText().split(",")));
        //save points
        CroquetApp.putPreference(CroquetApp.preferenceKey.POINTS, pointsEdit.getText());
        //save data directory
        CroquetApp.putPreference(CroquetApp.preferenceKey.DATA_DIR_PATH, dataDirEdit.getText());
    }

    @FXML
    private void close() {
        grid.getScene().getWindow().hide();
    }

    @FXML
    private void exit() {
        save();
        close();
    }
}
