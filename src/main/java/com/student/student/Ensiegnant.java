package com.student.student;

import java.sql.ResultSet;
import java.sql.SQLException;


@SuppressWarnings("all")
public class Ensiegnant {
    private String id;
    private String name;
    private String last_name;
    private String dateNais;
    private String gender;
    private String email;
    private String module;
    private String cls;
    private String phnNum;
    private String niv;
    private DbUtils db = new DbUtils();
    
    public Ensiegnant (String id) {
        try {
            ResultSet rs = db.runQuery("SELECT * FROM EnsiegnantsList,ens_class WHERE ens_id = " + id + " and id = " + id);
            if (rs.next()) {
                this.id = String.valueOf(rs.getInt("id"));
                name = rs.getString("name");
                last_name = rs.getString("last_name");
                dateNais = String.valueOf(rs.getDate("date_nais"));
                gender = rs.getString("gender");
                niv = String.valueOf(rs.getInt("niv_scho"));
                cls = String.valueOf(rs.getInt("class"));
                email = rs.getString("email");
                phnNum = rs.getString("num_tel");
                module = rs.getString("module");
            }
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public Ensiegnant (int niv, int clss, String mdl) {
        try {
            ResultSet rs = db.runQuery("SELECT * FROM EnsiegnantsList,ens_class WHERE class = " + clss + " AND niv_scho = " + niv + " AND module = '" + mdl + "'");
            if (rs.next()) {
                this.id = String.valueOf(rs.getInt("id"));
                name = rs.getString("name");
                last_name = rs.getString("last_name");
                dateNais = String.valueOf(rs.getDate("date_nais"));
                gender = rs.getString("gender");
                this.niv = String.valueOf(rs.getInt("niv_scho"));
                cls = String.valueOf(rs.getInt("class"));
                email = rs.getString("email");
                phnNum = rs.getString("num_tel");
                module = rs.getString("module");
            }
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public Ensiegnant (String id, String niv, String cls) {
        try {
            ResultSet rs = db.runQuery("SELECT * FROM EnsiegnantsList,ens_class WHERE ens_id = " + id + " and id = " + id + " and niv_scho = " + niv + " and class = " + cls);
            if (rs.next()) {
                this.id = String.valueOf(rs.getInt("id"));
                name = rs.getString("name");
                last_name = rs.getString("last_name");
                dateNais = String.valueOf(rs.getDate("date_nais"));
                gender = rs.getString("gender");
                niv = String.valueOf(rs.getInt("niv_scho"));
                cls = String.valueOf(rs.getInt("class"));
                email = rs.getString("email");
                phnNum = rs.getString("num_tel");
                module = rs.getString("module");
            }
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public Ensiegnant (String name, String last_name, String dateNais, String gender, String email, String module, String cls, String phnNum, String niv) {
        this.name = name;
        this.last_name = last_name;
        this.dateNais = dateNais;
        this.gender = gender;
        this.email = email;
        this.module = module;
        this.cls = cls;
        this.phnNum = phnNum;
        this.niv = niv;
    }
    
    public String getNiv () {
        return niv;
    }
    
    public void setNiv (String niv) {
        this.niv = niv;
    }
    
    public String getId () {
        return id;
    }
    
    public void setId (String id) {
        this.id = id;
    }
    
    public String getName () {
        return name;
    }
    
    public void setName (String name) {
        this.name = name;
    }
    
    public String getLast_name () {
        return last_name;
    }
    
    public void setLast_name (String last_name) {
        this.last_name = last_name;
    }
    
    public String getDateNais () {
        return dateNais;
    }
    
    public void setDateNais (String dateNais) {
        this.dateNais = dateNais;
    }
    
    public String getGender () {
        return gender;
    }
    
    public void setGender (String gender) {
        this.gender = gender;
    }
    
    public String getEmail () {
        return email;
    }
    
    public void setEmail (String email) {
        this.email = email;
    }
    
    public String getModule () {
        return module;
    }
    
    public void setModule (String module) {
        this.module = module;
    }
    
    public String getCls () {
        return cls;
    }
    
    public void setCls (String cls) {
        this.cls = cls;
    }
    
    public String getPhnNum () {
        return phnNum;
    }
    
    public void setPhnNum (String phnNum) {
        this.phnNum = phnNum;
    }
}
