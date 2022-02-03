package com.student.student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Student {
    private DbUtils db = new DbUtils();
    private String id;
    private String name;
    private String last_name;
    private String dateNais;
    private String gender;
    private String cls;
    private String niv;
    private ArrayList<Double> notes = new ArrayList<>();
    private ArrayList<String> obsrv = new ArrayList<>();
    
    Student (String id, String module) {
        try {
            stuCalcul stc = new stuCalcul();
            stc.calcGenMoy(id);
            ResultSet rs = db.runQuery("SELECT id, name, last_name, date_nais, gender, niv_scho, class FROM StudentsList,stu_class WHERE id=stu_id and id = " + id);
            if (rs.next()) {
                this.id = String.valueOf(rs.getInt("id"));
                name = rs.getString("name");
                last_name = rs.getString("last_name");
                dateNais = String.valueOf(rs.getDate("date_nais"));
                gender = rs.getString("gender");
                niv = String.valueOf(rs.getInt("niv_scho"));
                cls = String.valueOf(rs.getInt("class"));
            }
            db.close();
            String query;
            boolean priciple = module.equals("math") || module.equals("arabic") || module.equals("english") || module.equals("french");
            if (priciple)
                query =
                 "SELECT " + module + "_cc, " + module + "_dv, " + module + "_dv2," + module + "_ex, " + module + "_moy, " + module + "_rem FROM notes_cc,notes_dv,notes_dv2,notes_exmn,notes_moy,remark WHERE notes_cc.stu_id = " + id + " " + "AND notes_dv.stu_id = " + id + " AND notes_dv2.stu_id = " + id + " AND notes_exmn.stu_id = " + id + " AND notes_moy.stu_id = " + id + " AND remark.stu_id = " + id;
            else
                query = "SELECT " + module + "_cc, " + module + "_dv, " + module + "_ex, " + module + "_moy, " + module + "_rem FROM notes_cc,notes_dv,notes_exmn,notes_moy,remark WHERE notes_cc.stu_id = " + id + " " + "AND" + " " +
                 "notes_dv.stu_id = " + id + "  AND notes_exmn.stu_id = " + id + " AND notes_moy.stu_id = " + id + " AND remark.stu_id = " + id;
            rs = db.runQuery(query);
            if (rs.next()) {
                notes.add(round(rs.getDouble(module + "_cc")));
                notes.add(round(rs.getDouble(module + "_dv")));
                if (priciple) notes.add(round(rs.getDouble(module + "_dv2")));
                else notes.add(round(0));
                notes.add(round(rs.getDouble(module + "_ex")));
                notes.add(round(rs.getDouble(module + "_moy")));
                obsrv.add(rs.getString(module + "_rem"));
            }
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    private double round (double a) {
        return Math.round(a * 100) / 100;
    }
    
    public ArrayList<Double> getNotes () {
        return notes;
    }
    
    public void setNotes (ArrayList<Double> notes) {
        this.notes = notes;
    }
    
    public ArrayList<String> getObsrv () {
        return obsrv;
    }
    
    public void setObsrv (ArrayList<String> obsrv) {
        this.obsrv = obsrv;
    }
    
    public String getId () {
        return id;
    }
    
    public String getName () {
        return name;
    }
    
    public String getLast_name () {
        return last_name;
    }
    
    public String getDateNais () {
        return dateNais;
    }
    
    public String getGender () {
        return gender;
    }
    
    public String getCls () {
        return cls;
    }
    
    public String getNiv () {
        return niv;
    }
}
