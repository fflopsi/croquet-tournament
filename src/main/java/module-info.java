module me.frauenfelderflorian.crocket {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;

    opens me.frauenfelderflorian.crocket to javafx.fxml;
    exports me.frauenfelderflorian.crocket;
    exports me.frauenfelderflorian.crocket.data;
    opens me.frauenfelderflorian.crocket.data to javafx.fxml;
}