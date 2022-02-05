package com.student.student;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class adminPanelController {
    @FXML
    private void manage (ActionEvent ae) throws IOException {
        Button src = (Button) ae.getSource();
        switch (src.getText()) {
            case "Etudiant", "Enseignant" -> {
                String fileName = src.getText().concat(".fxml").toLowerCase();
                FXMLLoader fxmlLoader = new FXMLLoader(mainApplication.class.getResource(fileName));
                Scene scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/student/student/css/ui.css")).toExternalForm());
                Stage stage = new Stage();
                stage.setTitle(src.getText() + " Management");
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.show();
            }
            case "exit" -> Platform.exit();
        }
    }
}
