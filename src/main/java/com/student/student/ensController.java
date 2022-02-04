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
import java.util.HashMap;
import java.util.ResourceBundle;


public class ensController implements Initializable {
    private final HashMap<Object, String> lst = new HashMap<>();
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
    private boolean editing = false;
    @FXML
    private TextField nm, ln, em, pn, cls, mdl, ser;
    
    @FXML
    private void checkValidity (KeyEvent ae) {
        TextField src = (TextField) ae.getSource();
        String str = src.getText();
        lst.putIfAbsent(src.getId(), "fail");
        validate(src, str.isEmpty() ? "fail" : "pass");
        switch (src.getAccessibleText()) {
            case "name" -> validate(src, str.matches("(?<=\\s|^)[a-zA-Z]*(?=[.,;:]?\\s|$)") ? "pass" : "fail");
            case "class" -> validate(src, str.matches("\\d+$") ? "pass" : "fail");
            case "email" -> {
                String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                //validate(src, str.matches(regex) ? "pass" : "fail");
                if (str.matches(regex)) validate(src, "fail");
                else validate(src, "pass");
            }
            case "phone" -> validate(src, (str.matches("\\d+$") && str.length() == 10) ? "pass" : "fail");
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
        System.out.println(dt.getValue());
        switch (table) {
            case "EnsiegnantsList":
                if (editing) {
                    setSucceed(db.run("UPDATE " + table + " set name = '" + nm.getText() + "' , last_name = '" + ln.getText() + "', date_nais = '" + getDate() + "' , email = '" + em.getText() + "', num_tel = " + pn.getText() + ", " +
                     "gender = " + getGender() + ", module = '" + mdl.getText() + "' where id = " + sr.getValue().split(" ")[0]));
                    a = Integer.parseInt(sr.getValue().split(" ")[0]);
                } else {
                    rs = db.runQuery("SELECT id FROM EnsiegnantsList WHERE name = '" + nm.getText() + "' and last_name = '" + ln.getText() + "' and date_nais = '" + getDate() + "'");
                    if (rs.next()) {
                        showAlert("Ensiegnant already inserted");
                        return;
                    }
                    db.close();
                    setSucceed(db.run("INSERT INTO " + table + " (name,last_name,date_nais,email,num_tel,gender,module) VALUES ('" + nm.getText() + "','" + ln.getText() + "','" + getDate() + "','" + em.getText() + "','" + pn.getText() +
                     "'," + getGender() + ",'" + mdl.getText() + "')"));
                    rs = db.runQuery("SELECT id FROM EnsiegnantsList WHERE name = '" + nm.getText() + "' and last_name = '" + ln.getText() + "' and date_nais = '" + getDate() + "'");
                    a = (rs.next() ? rs.getInt(1) : 0);
                }
                db.close();
                rs = db.runQuery("SELECT * FROM ens_class where ens_id = " + a + " and niv_scho = " + lv.getValue() + " and class = " + cls.getText());
                if (rs.next()) {
                    showAlert("Change class or Level");
                    db.close();
                    return;
                } else {
                    db.close();
                    db.run("INSERT INTO ens_class (ens_id,niv_scho,class) values (" + a + "," + lv.getValue() + "," + cls.getText() + ")");
                }
                break;
            case "StudentsList":
                if (editing) {
                    a = Integer.parseInt(sr.getValue().split(" ")[0]);
                    setSucceed(db.run("UPDATE StudentsList set name = '" + nm.getText() + "' , last_name = '" + ln.getText() + "', date_nais = '" + getDate() + "' where id = " + a));
                } else {
                    try {
                        rs = db.runQuery("SELECT * FROM StudentsList where name = '" + nm.getText() + "' and last_name = '" + ln.getText() + "' and date_nais = '" + getDate() + "'");
                        if (rs.next()) {
                            showAlert("Student already inserted");
                            db.close();
                            return;
                        } else setSucceed(db.run("INSERT INTO StudentsList (name,last_name,date_nais,gender) VALUES ('" + nm.getText() + "','" + ln.getText() + "','" + getDate() + "'," + getGender() + ")"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                db.close();
                String query = "SELECT * FROM StudentsList where name = '" + nm.getText() + "' and last_name = '" + ln.getText() + "' and date_nais = '" + getDate() + "'";
                rs = db.runQuery(query);
                a = rs.next() ? rs.getInt("id") : 0;
                db.close();
                query = "SELECT * FROM stu_class where stu_id = " + a + " and niv_scho = " + lv.getValue() + " and class = " + cls.getText();
                rs = db.runQuery(query);
                if (rs.next()) {
                    db.run("UPDATE stu_class set niv_scho = " + lv.getValue() + " and class = " + cls.getText() + " where stu_id = " + a);
                    return;
                } else db.run("INSERT INTO stu_class (stu_id,niv_scho,class) values (" + a + "," + lv.getValue() + "," + cls.getText() + ")");
                db.close();
                break;
        }
        editing = false;
    }
    
    private void showAlert (String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    
    private void setSucceed (String str) throws SQLException {
        lb.setText(str);
        lb.setStyle("-fx-text-fill: " + (str.equals("Execution Successful") ? "green" : "red"));
        db.populateData(tv, "SELECT * FROM " + (tb.isSelected() ? "EnsiegnantsList" : "StudentsList"));
    }
    
    private String getDate () {
        return dateFormat(dt.getEditor().getText());
    }
    
    private String dateFormat (String dateValue) {
        if (dateValue.contains("-") && !dateValue.contains("/")) {
            return dateValue.split("-")[2] + "/" + dateValue.split("-")[1] + "/" + dateValue.split("-")[0];
        } else return dateValue.split("/")[2] + "/" + dateValue.split("/")[1] + "/" + dateValue.split("/")[0];
    }
    
    @FXML
    private String switchTable () throws SQLException {
        boolean selected = tb.isSelected();
        pn.setDisable(!selected);
        em.setDisable(!selected);
        mdl.setDisable(!selected);
        tb.setText(selected ? "Ensiegnant" : "Student");
        db.populateData(tv, "SELECT * FROM " + (selected ? "EnsiegnantsList" : "StudentsList"));
        check();
        return tb.getText().concat("sList");
    }
    
    @FXML
    private void search () {
        try {
            sr.getItems().clear();
            db.populateData(tv, "SELECT * FROM " + (tb.isSelected() ? "Ensiegnants" : "Students") + "List where name LIKE '%" + ser.getText().concat("%'") + " OR last_name LIKE '%" + ser.getText().concat("%'"));
            ResultSet rs = db.runQuery("SELECT * FROM " + (tb.isSelected() ? "Ensiegnants" : "Students") + "List where name LIKE '%" + ser.getText().concat("%'") + " OR last_name LIKE '%" + ser.getText().concat("%'"));
            while (rs.next()) sr.getItems().add(rs.getInt("id") + " " + rs.getString("name") + " " + rs.getString("last_name"));
            edt.setDisable(sr.getItems().isEmpty());
            dlt.setDisable(sr.getItems().isEmpty());
            db.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void dele () {
        try {
            String[] id = sr.getValue().split(" ");
            if (!tb.isSelected()) {
                String[] tables = {"notes_cc", "notes_dv", "notes_dv2", "notes_exmn", "notes_moy", "remark"};
                for (String table : tables) {
                    db.run(String.format("DELETE from %s where stu_id = %s", table, id[0]));
                }
            }
            db.run("DELETE FROM " + (tb.isSelected() ? "ens_class where ens_id = " : "stu_class where stu_id = ") + id[0]);
            db.run("DELETE FROM " + (tb.isSelected() ? "Ensiegnants" : "Students") + "List where id = " + id[0]);
            switchTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void edit () {
        editing = true;
        try {
            String id = sr.getValue().split(" ")[0];
            String query = "SELECT * from " + (tb.isSelected() ? "EnsiegnantsList ,ens_class" : "StudentsList ,stu_class") + " where id = " + id + " and " + (tb.isSelected() ? "ens_id = " : "stu_id = ") + id;
            ResultSet rs = db.runQuery(query);
            if (rs.next()) {
                nm.setText(rs.getString("name"));
                ln.setText(rs.getString("last_name"));
                cb.setValue((rs.getString("gender").equals("M") ? "Male" : "Female"));
                dt.getEditor().setText(dateFormat(rs.getString("date_nais")));
                lv.setValue(rs.getInt("niv_scho") + "");
                cls.setText(rs.getInt("class") + "");
                mdl.setText((tb.isSelected() ? rs.getString("module") : ""));
                pn.setText(tb.isSelected() ? rs.getInt("num_tel") + "" : "");
                em.setText(tb.isSelected() ? rs.getString("email") : "");
            }
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
