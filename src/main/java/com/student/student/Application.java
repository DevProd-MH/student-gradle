package com.student.student;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;


public class Application extends javafx.application.Application {
    public static void main (String[] args) {
        launch();
    }
    
    @Override
    public void start (Stage stage) throws IOException {
        String filePath = "src/main/resources/com/student/student/setup/setup.dev";
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        FileWriter myWriter;
        if (!reader.readLine().contains(System.getProperty("user.name"))) {
            myWriter = new FileWriter(filePath);
            myWriter.write("false");
            myWriter.flush();
            myWriter.close();
        }
        reader.close();
        reader = new BufferedReader(new FileReader(filePath));
        boolean st = reader.readLine().contains("false");
        reader.close();
        if (st) {
            TextInputDialog td = new TextInputDialog("root");
            td.setHeaderText("enter database username");
            TextInputDialog td2 = new TextInputDialog("");
            td2.setHeaderText("enter database password");
            String line = td.showAndWait().get() + "," + td2.showAndWait().get();
            myWriter = new FileWriter(filePath);
            myWriter.write(line);
            myWriter.flush();
            myWriter.close();
            DbUtils db = new DbUtils();
            boolean executed = db.excuteFile("src/main/resources/com/student/student/script/sql_script.sql");
            if (executed) {
                myWriter = new FileWriter(filePath);
                myWriter.write(line + ",true," + System.getProperty("user.name"));
                myWriter.close();
            }
        }
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Logview.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/student/student/css/ui.css")).toExternalForm());
        stage.setTitle("Students Management");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}