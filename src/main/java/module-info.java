module org.example.projetjardinage {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;

    exports org.example.projetjardinage;
    opens org.example.projetjardinage to javafx.fxml;
    opens org.example.projetjardinage.controller to javafx.fxml;
    opens org.example.projetjardinage.controller.mainBody to javafx.fxml;
    opens org.example.projetjardinage.controller.utils to javafx.fxml;
    opens org.example.projetjardinage.controller.utils.journal to javafx.fxml;
    opens org.example.projetjardinage.controller.utils.journal.ajoutTypeMesure to javafx.fxml;
}