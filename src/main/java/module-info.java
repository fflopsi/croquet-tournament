module me.frauenfelderflorian.croquet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;

    exports me.frauenfelderflorian.croquet;
    opens me.frauenfelderflorian.croquet to javafx.fxml;
    exports me.frauenfelderflorian.croquet.data;
    opens me.frauenfelderflorian.croquet.data to javafx.fxml;
}