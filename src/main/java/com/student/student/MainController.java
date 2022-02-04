package com.student.student;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    @FXML
    Label lb;
    @FXML
    TextField usr;
    @FXML
    PasswordField pwd;
    @FXML
    AnchorPane ap;
    @FXML
    ImageView img;
    @FXML
    Button exit, submit;
    DbUtils db = new DbUtils();
    
    @FXML
    public void exit () {
        Platform.exit();
    }
    
    @FXML
    public void submit () {
        ResultSet rs = db.runQuery("SELECT * FROM `users` WHERE `usr` = '" + usr.getText() + "' AND `pwd` = '" + pwd.getText() + "'");
        try {
            if (rs.next()) {
                lb.setText("Connection success");
                lb.setStyle("-fx-text-fill: green");
                Thread.sleep(2000);
                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("adminPanel.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/student/student/css/ui.css")).toExternalForm());
                Stage stage = new Stage();
                stage.setTitle("Management");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                ap.getScene().getWindow().hide();
            } else {
                lb.setText("Credentials not valid!");
                lb.setStyle("-fx-text-fill : red");
            }
        } catch (SQLException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void check () {
        submit.setDisable((usr.getText().isEmpty() || pwd.getText().isEmpty()) || (usr.getText().replace("\\s+", "").isEmpty() || pwd.getText().replace("\\s+", "").isEmpty()));
    }
    
    @Override
    public void initialize (URL location, ResourceBundle resources) {
        img.fitWidthProperty().bind(ap.widthProperty());
        img.fitHeightProperty().bind(ap.heightProperty());
    }
}




