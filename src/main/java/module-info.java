module com.student.student {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mariadb.jdbc;
    requires java.sql;
    requires com.github.librepdf.openpdf;
    requires ibatis.core;
    requires java.desktop;
    
    opens com.student.student to javafx.fxml;
    exports com.student.student;
}