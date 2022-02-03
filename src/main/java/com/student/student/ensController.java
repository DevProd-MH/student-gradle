package com.student.student;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;


public class ensController implements Initializable {
    private final HashMap<Object, String> lst = new HashMap<>();
    private final List<String> columnNames = new ArrayList<>();
    private final DbUtils db = new DbUtils();
    @FXML
    ComboBox<String> lv, sr;
    @FXML
    DatePicker dt;
    @FXML
    ToggleButton tb;
    @FXML
    Label lb;
    @FXML
    ChoiceBox<String> cb;
    @FXML
    Button ins, edt, dlt;
    @FXML
    TableView<ObservableList<String>> tv;
    @FXML
    AnchorPane ap;
    @FXML
    ImageView img;
    @FXML
    private TextField nm, ln, em, pn, cls, mdl, ser;
    
    @FXML
    private void checkValidity (KeyEvent ae) {
        TextField src = (TextField) ae.getSource();
        String str = src.getText();
        lst.putIfAbsent(src.getId(), "fail");
        validate(src, str.isEmpty() ? "fail" : "pass");
        switch (src.getAccessibleText()) {
            case "name":
                validate(src, str.matches("(?<=\\s|^)[a-zA-Z]*(?=[.,;:]?\\s|$)") ? "pass" : "fail");
                break;
            case "class":
                validate(src, str.matches("\\d+$") ? "pass" : "fail");
                break;
            case "email":
                String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                //validate(src, str.matches(regex) ? "pass" : "fail");
                if (str.matches(regex)) validate(src, "fail");
                else validate(src, "pass");
                break;
            case "phone":
                validate(src, (str.matches("\\d+$") && str.length() == 10) ? "pass" : "fail");
                break;
        }
        check();
    }
    
    private void check () {
      /*  try {
            if (!tb.isSelected()) ins.setDisable((isPassed(nm) && isPassed(ln) && isPassed(cls)));
            else ins.setDisable((isPassed(nm) && isPassed(ln) && isPassed(cls) && isPassed(em) && isPassed(pn)));
            ins.setDisable(cb.getValue().isEmpty() || lv.getValue().isEmpty() || dt.getValue().toString().isEmpty());
        } catch (NullPointerException e) {
            ins.setDisable(true);
        }*/
    }
    
    private boolean isPassed (Object obj) {
        return lst.get(obj).equals("pass");
    }
    
    private void validate (TextField tf, String str) {
        tf.setStyle("-fx-border-color: " + (str.equals("pass") ? "green" : "red"));
        lst.replace(tf.getId(), lst.get(tf.getId()), str);
    }
    
    @FXML
    private void insert () throws SQLException {
        String table = switchTable();
        ResultSet rs;
        int a;
        switch (table) {
            case "EnsiegnantsList":
                //  Ensiegnant ensiegnant = new Ensiegnant(nm.getText(), ln.getText(), getDate(), getGender(), em.getText(), mdl.getText(), cls.getText(), pn.getText(), lv.getValue());
                setSucceed(db.run("INSERT INTO " + table + " (name,last_name,date_nais,email,num_tel,gender,module) VALUES ('" + nm.getText() + "','" + ln.getText() + "','" + getDate() + "','" + em.getText() + "','" + pn.getText() + "'," + getGender() + ",'" + mdl.getText() + "')"));
                rs = db.runQuery("SELECT id FROM EnsiegnantsList WHERE name = '" + nm.getText() + "' and last_name = '" + ln.getText() + "' and date_nais = '" + getDate() + "'");
                a = (rs.next() ? rs.getInt(1) : null);
                db.close();
                db.run("INSERT INTO ens_class (ens_id,niv_scho,class) values (" + a + "," + lv.getValue() + "," + cls.getText() + ")");
                break;
            case "StudentsList":
                setSucceed(db.run("INSERT INTO " + table + " (name,last_name,date_nais,gender) VALUES ('" + nm.getText() + "','" + ln.getText() + "','" + getDate() + "'," + getGender() + ")"));
                rs = db.runQuery("SELECT id FROM StudentsList WHERE name = '" + nm.getText() + "' and last_name = '" + ln.getText() + "' and date_nais = '" + getDate() + "'");
                a = (rs.next() ? rs.getInt(1) : null);
                db.close();
                db.run("INSERT INTO stu_class (stu_id,niv_scho,class) values (" + a + "," + lv.getValue() + "," + cls.getText() + ")");
                break;
        }
    }
    
    private void setSucceed (String str) throws SQLException {
        lb.setText(str);
        lb.setStyle("-fx-text-fill: " + (str.equals("Execution Successful") ? "green" : "red"));
        db.populateData(tv, "SELECT * FROM " + (tb.isSelected() ? "EnsiegnantsList" : "StudentsList"));
    }
    
    private String getDate () {
        try {
            return dt.getValue().toString();
        } catch (NullPointerException npe) {
            //TODO date regex
            return dt.getEditor().getText();
        }
    }
    
    @FXML
    private String switchTable () throws SQLException {
        boolean selected = tb.isSelected();
        pn.setDisable(!selected);
        em.setDisable(!selected);
        tb.setText(selected ? "Ensiegnant" : "Student");
        db.populateData(tv, "SELECT * FROM " + (selected ? "EnsiegnantsList" : "StudentsList"));
        check();
        return tb.getText().concat("sList");
    }
    
    @FXML
    private void search () {
        ResultSet rs = db.runQuery("SELECT * FROM " + (tb.isSelected() ? "Ensiegnant" : "Student") + "List ");
    }
    
    private String getGender () {
        return "'" + cb.getValue().charAt(0) + "'";
    }
    
    
    @Override
    public void initialize (URL location, ResourceBundle resources) {
        cb.getItems().addAll("Male", "Female");
        lv.getItems().addAll("1", "2", "3", "4");
        img.fitWidthProperty().bind(ap.widthProperty());
        img.fitHeightProperty().bind(ap.heightProperty());
        try {
            db.populateData(tv, "SELECT * FROM StudentsList");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
