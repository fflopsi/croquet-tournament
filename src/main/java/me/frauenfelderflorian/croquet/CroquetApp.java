package me.frauenfelderflorian.croquet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import me.frauenfelderflorian.croquet.data.Tournament;

import java.io.File;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * JavaFX App
 */
public class CroquetApp extends Application {

    public static Tournament tournament;
    private static Preferences preferences;

    @Override
    public void start(Stage stage) throws IOException {
        //configure preferences
        preferences = Preferences.userNodeForPackage(CroquetApp.class);
        //configure data directory path preference
        putPreference(preferenceKey.DATA_DIR_PATH, getPreference(preferenceKey.DATA_DIR_PATH));
        File dataDir = new File(getPreference(preferenceKey.DATA_DIR_PATH));
        if (!dataDir.exists() && dataDir.mkdir()) System.out.println("app data directory created");
        //configure current tournament preference
        putPreference(preferenceKey.CURRENT_TOURNAMENT, getPreference(preferenceKey.CURRENT_TOURNAMENT));
        //configure players preference
        putPreference(preferenceKey.PLAYERS, getPreference(preferenceKey.PLAYERS));
        //configure points preference
        putPreference(preferenceKey.POINTS, getPreference(preferenceKey.POINTS));
        //load tournament
        if (loadTournament(getPreference(preferenceKey.CURRENT_TOURNAMENT))) {
            //show main window only if the tournament could be loaded correctly
            stage.setScene(new Scene(loadFXML("Selection")));
            stage.setTitle(getString("appTitle"));
            stage.show();
            stage.setMinHeight(stage.getHeight());
            stage.setMinWidth(stage.getWidth());
        }
    }

    public static boolean loadTournament(String tournamentSemester) {
        try {
            //try to load the given tournament
            tournament = Tournament.load(tournamentSemester);
            putPreference(preferenceKey.CURRENT_TOURNAMENT, tournamentSemester);
            return true;
        } catch (ClassNotFoundException | IOException e) {
            //dialog with two options: cancel and close program or create new tournament
            System.out.println("tournament could not be loaded");
            ButtonType editInput = new ButtonType(getString("otherTournament"), ButtonBar.ButtonData.NEXT_FORWARD);
            ButtonType newInput = new ButtonType(getString("newTournament"), ButtonBar.ButtonData.BACK_PREVIOUS);
            Alert alert = new Alert(Alert.AlertType.WARNING, getString("tournamentLoadError"),
                    ButtonType.CANCEL, editInput, newInput);
            //alert.setTitle(getString("warning"));
            alert.setHeaderText(getString("loadingError"));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == editInput) {
                //show dialog box to input another tournament name
                alert.close();
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle(getString("enterTournament"));
                dialog.setHeaderText(getString("loadOtherTournament"));
                dialog.setContentText(getString("tournament"));
                Optional<String> otherTournament = dialog.showAndWait();
                //try to load the new given tournament
                if (otherTournament.isPresent()) {
                    loadTournament(otherTournament.get());
                    putPreference(preferenceKey.CURRENT_TOURNAMENT, otherTournament.get());
                    return true;
                }
            } else if (result.isPresent() && result.get() == newInput) {
                //show dialog box to input a new tournament name
                alert.close();
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle(getString("enterTournament"));
                dialog.setHeaderText(getString("createNewTournament"));
                dialog.setContentText(getString("tournament"));
                Optional<String> otherTournament = dialog.showAndWait();
                //try to create a new tournament
                if (otherTournament.isPresent()) {
                    tournament = new Tournament(otherTournament.get());
                    putPreference(preferenceKey.CURRENT_TOURNAMENT, otherTournament.get());
                    return true;
                }
            }
            //returns false only if cancelled or closed
            return false;
        }
    }

    @Override
    public void stop() {
        try {
            tournament.save();
        } catch (IOException e) {
            System.out.println("tournament could not be saved");
            Alert alert = new Alert(Alert.AlertType.ERROR, getString("tournamentSaveError"));
            alert.showAndWait();
        } catch (NullPointerException e) {
            System.out.println("tournament is null, aborting");
        }
    }

    public static void putPreference(preferenceKey key, String value) {
        preferences.put(key.key, value);
    }

    public static String getPreference(preferenceKey key) {
        return preferences.get(key.key, key.defaultValue);
    }

    public static Parent loadFXML(String fxml) throws IOException {
        return new FXMLLoader(CroquetApp.class.getResource(fxml + ".fxml"),
                ResourceBundle.getBundle("me.frauenfelderflorian.croquet.Strings")).load();
    }

    public static String getString(String key) {
        if (!ResourceBundle.getBundle("me.frauenfelderflorian.croquet.Strings").containsKey(key))
            throw new MissingResourceException("key " + key + " not found", CroquetApp.class.getName(), key);
        return ResourceBundle.getBundle("me.frauenfelderflorian.croquet.Strings").getString(key);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public enum preferenceKey {
        DATA_DIR_PATH("datadirpath", (System.getProperty("os.name")).toUpperCase().contains("WIN")
                ? System.getenv("AppData") + File.separator + "CroquetTournament"
                : System.getProperty("user.home") + File.separator + "CroquetTournament"),
        CURRENT_TOURNAMENT("tournament", "2021.1"),
        PLAYERS("players",
                "Ale,Alissa,Andrin,Berzan,Christian,Elias,Evelin,Fabian,Florian,Gian,Jan,Michael,Michi,Robin,Silvan,Tim"),
        POINTS("points", "20,17,15,13,12,11,10,9,8,7,6,5,4,3,2,1");

        private final String key;
        private final String defaultValue;

        preferenceKey(String key, String defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }
    }
}