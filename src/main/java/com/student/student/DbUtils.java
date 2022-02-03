package com.student.student;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("all")
public class DbUtils {
    private final List<String> columnNames = new ArrayList<>();
    private Connection con;
    private String usr, pwd;
    
    DbUtils () {
        try {
            String file = "src/main/resources/com/student/student/setup/setup.dev";
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String[] setup = reader.readLine().split(",");
            usr = setup[0];
            pwd = setup[1];
            reader.close();
            connect();
            con.setAutoCommit(true);
            run("SET SESSION foreign_key_checks=OFF");
            close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean excuteFile (String filePath) {
        try {
            connect();
            ScriptRunner sr = new ScriptRunner(con);
            Reader reader = new BufferedReader(new FileReader(filePath));
            sr.runScript(reader);
            reader.close();
            return true;
        } catch (Exception err) {
            err.printStackTrace();
            return false;
        }
    }
    
    private String connect () {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/students", usr, pwd);
            return "Connection successful";
        } catch (Exception e) {
            System.out.println("Connection exception starts here : \n" + e + "\nconnection exception ends here.");
            return "Connection failed";
        }
    }
    
    public ResultSet runQuery (String query) {
        if (connect().equals("Connection successful")) {
            try {
                return con.createStatement().executeQuery(query);
            } catch (SQLException e) {
                System.out.println("runQuery() exception starts here : " + e);
                return null;
            }
        } else return null;
    }
    
    public String run (String query) {
        if (connect().equals("Connection successful")) {
            try {
                con.createStatement().execute(query);
                close();
            } catch (SQLException e) {
                System.out.println("run() exception starts here : ");
                e.printStackTrace();
                return "Execution failed";
            }
        }
        return "Execution Successful";
    }
    
    void populateData (TableView tv, String query) throws SQLException {
        if (!tv.getColumns().isEmpty()) tv.getColumns().removeAll(tv.getColumns());
        ResultSet resultSet = runQuery(query);
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        
        for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
            
            final int j = i;
            TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));
            col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> {
                if (param.getValue().get(j) != null) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                } else {
                    return null;
                }
            });
            
            tv.getColumns().addAll(col);
            this.columnNames.add(col.getText());
        }
        
        while (resultSet.next()) {
            //Iterate Row
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                //Iterate Column
                row.add(resultSet.getString(i));
            }
            data.add(row);
            
        }
        //FINALLY ADDED TO TableView
        tv.setItems(data);
    }
    
    public void close () {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
