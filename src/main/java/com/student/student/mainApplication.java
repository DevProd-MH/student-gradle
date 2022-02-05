package com.student.student;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;


public class mainApplication extends Application {
    public static void main (String[] args) {
        launch();
    }
    
    @Override
    public void start (Stage stage) throws IOException {
        String filePath = System.getProperty("user.home") + File.separatorChar + "Desktop" + File.separatorChar;
        FileWriter myWriter;
        File f = new File(filePath + ".setup.cfg");
        try {
            if (!f.exists()) {
                f.createNewFile();
                myWriter = new FileWriter(f.getAbsolutePath());
                myWriter.write("false");
                myWriter.flush();
                myWriter.close();
            }
            FileReader fileReader = new FileReader(f.getAbsolutePath());
            BufferedReader reader = new BufferedReader(fileReader);
            if (!reader.readLine().contains(System.getProperty("user.name"))) {
                myWriter = new FileWriter(f.getAbsolutePath());
                myWriter.write("false");
                myWriter.flush();
                myWriter.close();
            }
            reader.close();
            reader = new BufferedReader(new FileReader(f.getAbsolutePath()));
            boolean st = reader.readLine().contains("false");
            reader.close();
            if (st) {
                TextInputDialog td = new TextInputDialog("root");
                td.setHeaderText("enter database username");
                TextInputDialog td2 = new TextInputDialog("");
                td2.setHeaderText("enter database password");
                String line = td.showAndWait().get() + "," + td2.showAndWait().get();
                myWriter = new FileWriter(f.getAbsolutePath());
                myWriter.write(line);
                myWriter.flush();
                myWriter.close();
                DbUtils db = new DbUtils();
                File fsql = new File(filePath + ".sqlScript");
                try {
                    if (!fsql.exists()) {
                        fsql.createNewFile();
                        myWriter = new FileWriter(fsql);
                        myWriter.write(sqlScript.script);
                        myWriter.flush();
                        myWriter.close();
                    }
                    boolean executed = db.excuteFile(fsql.getAbsolutePath());
                    if (executed) {
                        myWriter = new FileWriter(f.getAbsolutePath());
                        myWriter.write(line + ",true," + System.getProperty("user.name"));
                        myWriter.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
            }
            
        } catch (IOException e) {
            System.out.println("error creating file");
            e.printStackTrace();
        }
        
        FXMLLoader fxmlLoader = new FXMLLoader(mainApplication.class.getResource("Logview.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/student/student/css/ui.css")).toExternalForm());
        stage.setTitle("Students Management");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}