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
import java.text.DecimalFormat;
import java.util.*;


@SuppressWarnings("all")
public class etudiantController implements Initializable {
    
    private final DbUtils db = new DbUtils();
    private final HashMap<String, String> lst = new HashMap<>();
    @FXML
    ImageView img;
    @FXML
    TableView<ObservableList<String>> tv;
    @FXML
    ComboBox<String> niv, cls, etu, nt, aff;
    @FXML
    Label id;
    @FXML
    TextField ar, mt, ph, is, fr, en, gh, mu, in, sp, sc, ci, ds;
    @FXML
    Button ins, edt, bult;
    @FXML
    AnchorPane ap;
    
    @FXML
    private void getClassCount () {
        Set<Integer> set = new LinkedHashSet<>();
        if (!cls.getItems().isEmpty()) cls.getItems().removeAll(cls.getItems());
        String query = "SELECT `class` FROM `stu_class` WHERE `niv_scho` = " + niv.getValue() + " ORDER BY class";
        ResultSet rs = db.runQuery(query);
        try {
            while (rs.next()) {
                set.add(rs.getInt(1));
            }
            for (int cl : set) {
                cls.getItems().add(cl + "");
            }
            db.populateData(tv, "SELECT name,last_name,class FROM StudentsList,stu_class WHERE id = stu_class.stu_id AND niv_scho = " + niv.getValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void getStudentsCount () {
        if (!etu.getItems().isEmpty()) etu.getItems().removeAll(etu.getItems());
        String query = "SELECT `id`,`name`,`last_name` FROM StudentsList,`stu_class` WHERE `id` = `stu_id` AND `niv_scho` = " + niv.getValue() + " AND `class` = " + cls.getValue();
        ResultSet rs = db.runQuery(query);
        try {
            while (rs.next()) etu.getItems().add(rs.getString("id").concat(" " + rs.getString("name").concat(" " + rs.getString("last_name"))));
            db.populateData(tv, "SELECT `id`,`name` AS `Nom`, `last_name` AS `Prenom` FROM StudentsList, stu_class WHERE niv_scho = " + niv.getValue() + " AND class = " + cls.getValue() + " AND id = stu_class.stu_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    @FXML
    private void showStudent () {
        try {
            String[] stu = etu.getValue().split(" ");
            id.setText(stu[0]);
            String query = "SELECT " + aff.getValue() + "_cc, " + aff.getValue() + "_dv, " + aff.getValue() + "_ex, " + aff.getValue() + "_moy FROM notes_cc,notes_dv,notes_exmn,notes_moy WHERE notes_cc.stu_id = " + id.getText() + " " +
             "AND " + "notes_dv.stu_id = " + id.getText() + " AND notes_exmn.stu_id = " + id.getText() + " AND notes_moy.stu_id = " + id.getText();
            db.populateData(tv, query);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException ignored) {
            
        }
    }
    
    @FXML
    private void showSpecModul () {
        String query = "SELECT " + aff.getValue() + "_cc, " + aff.getValue() + "_dv, " + aff.getValue() + "_ex, " + aff.getValue() + "_moy FROM notes_cc,notes_dv,notes_exmn,notes_moy WHERE notes_cc.stu_id = " + id.getText() + " " + "AND "
         + "notes_dv.stu_id = " + id.getText() + " AND notes_exmn.stu_id = " + id.getText() + " AND notes_moy.stu_id = " + id.getText();
        try {
            db.populateData(tv, query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void showSpecific () {
        if (aff.getValue().equals("math") || aff.getValue().equals("arabic") || aff.getValue().equals("french") || aff.getValue().equals("english")) {
        
        }
        String query = "SELECT * FROM " + switchNotesTable() + " WHERE stu_id = " + id.getText();
        try {
            db.populateData(tv, query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void clear () {
        clearText(ar, mt, ph, is, fr, en);
        clearText(gh, mu, in, sp, sc, ci);
        clearStyle(ar, mt, ph, is, fr, en);
        clearStyle(gh, mu, in, sp, sc, ci);
        lst.clear();
        ds.getStylesheets().removeAll(ds.getStyle());
        lst.replace(ds.getId(), "fail");
        ds.setText("");
        check();
    }
    
    private void clearText (TextField ar, TextField mt, TextField ph, TextField is, TextField fr, TextField en) {
        ar.setText("");
        mt.setText("");
        ph.setText("");
        is.setText("");
        fr.setText("");
        en.setText("");
    }
    
    private void clearStyle (TextField ar, TextField mt, TextField ph, TextField is, TextField fr, TextField en) {
        ar.getStylesheets().removeAll(ar.getStyle());
        mt.getStylesheets().removeAll(mt.getStyle());
        ph.getStylesheets().removeAll(ph.getStyle());
        is.getStylesheets().removeAll(is.getStyle());
        fr.getStylesheets().removeAll(fr.getStyle());
        en.getStylesheets().removeAll(en.getStyle());
    }
    
    private void failAll (TextField ar, TextField mt, TextField ph, TextField is, TextField fr, TextField en) {
        lst.replace(ar.getId(), "fail");
        lst.replace(mt.getId(), "fail");
        lst.replace(ph.getId(), "fail");
        lst.replace(is.getId(), "fail");
        lst.replace(fr.getId(), "fail");
        lst.replace(en.getId(), "fail");
    }
    
    @FXML
    private void acceptDigitOnly (KeyEvent ae) {
        TextField src = (TextField) ae.getSource();
        String str = src.getText();
        lst.putIfAbsent(src.getId(), "fail");
        double d;
        if (!str.isEmpty()) {
            if (str.matches("\\d+(\\.\\d+)?") && str.length() <= 5) {
                d = Double.parseDouble(str);
                if (d >= 00.00 && d <= 20.00) {
                    sub(src, "pass");
                } else {
                    sub(src, "fail");
                }
            } else {
                sub(src, "fail");
            }
        } else {
            sub(src, "fail");
        }
        check();
    }
    
    private void sub (TextField tf, String judg) {
        tf.setStyle("-fx-border-color: " + (judg.equals("pass") ? "green" : "red"));
        lst.replace(tf.getId(), lst.get(tf.getId()), judg);
    }
    
    private void check () {
        ins.setDisable(lst.containsValue("fail"));
        edt.setDisable(lst.containsValue("fail"));
    }
    
    @FXML
    private void prepareBultien () {
        bultienPDF bPDF = new bultienPDF();
        ArrayList<Student> StuList = new ArrayList<>();
        Ensiegnant ens = new Ensiegnant(Integer.valueOf(niv.getValue()), Integer.valueOf(cls.getValue()), aff.getValue());
        String module = aff.getValue();
        for (String stu_id : etu.getItems()) {
            String[] ids = stu_id.split(" ");
            StuList.add(new Student(ids[0], module));
        }
        System.out.println(StuList);
        for (Student s : StuList) System.out.println(s.getNotes().isEmpty());
        System.out.println(ens.getName());
        bPDF.createDocument(StuList, ens);
    }
    
    @FXML
    private void insert () {
        String query =
         "INSERT INTO " + switchNotesTable() + " VALUES (" + id.getText() + "," + getDouble(mt) + "," + getDouble(ar) + "," + getDouble(fr) + "," + getDouble(en) + "," + getDouble(is) + "," + getDouble(ci) + "," + getDouble(gh) + "," + getDouble(sp) + "," + getDouble(ph) + "," + getDouble(sc) + "," + getDouble(in) + "," + getDouble(mu) + "," + getDouble(ds) + ")";
        if (db.run(query).equals("Execution Successful")) {
            id.setStyle("-fx-text-fill: green");
            if (nt.getValue().equals("Devoir")) {
                /*ph.setDisable(true);
                sc.setDisable(true);
                sp.setDisable(true);
                gh.setDisable(true);
                ci.setDisable(true);
                is.setDisable(true);
                in.setDisable(true);
                mu.setDisable(true);
                ds.setDisable(true);*/
                db.run("INSERT INTO notes_dv2 values(" + id.getText() + "," + getDouble(mt) + "," + getDouble(ar) + "," + getDouble(fr) + "," + getDouble(en) + ")");
            }
        } else id.setStyle("-fx-text-fill: red");
        showSpecific();
        clear();
    }
    
    private double getDouble (TextField tf) {
        String str = tf.getText();
        return Double.parseDouble(new DecimalFormat("##.##").format(Double.parseDouble(str)));
    }
    
    private String switchNotesTable () {
        String table = "notes_moy";
        switch (nt.getValue()) {
            case "CC":
                table = "notes_cc";
                break;
            case "Examen":
                table = "notes_exmn";
                break;
            case "Devoir":
                table = "notes_dv";
                break;
        }
        return table;
    }
    
    @FXML
    private void edit () {
        // TODO
        // edit statement
    }
    
    @Override
    public void initialize (URL location, ResourceBundle resources) {
        try {
            db.populateData(tv, "SELECT * FROM StudentsList");
            niv.getItems().addAll("1", "2", "3", "4");
            nt.getItems().addAll("CC", "Devoir", "Examen");
            nt.setValue("CC");
            aff.getItems().addAll("math", "arabic", "french", "english", "islamic", "music", "geo_histo", "sport", "physics", "science", "informatique", "design");
            aff.setValue("math");
            img.fitWidthProperty().bind(ap.widthProperty());
            img.fitHeightProperty().bind(ap.heightProperty());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
