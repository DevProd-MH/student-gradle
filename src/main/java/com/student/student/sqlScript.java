package com.student.student;

public class sqlScript {
    public static String script = """
     drop database if exists students;
     create database if not exists students;
     use students;
     create table EnsiegnantsList
     (
         `id`      int auto_increment
             primary key,
         name      varchar(30) not null,
         last_name varchar(30) not null,
         date_nais date        not null,
         email     varchar(50) not null,
         num_tel   varchar(10) not null,
         gender    varchar(1)  not null,
         module    varchar(15) not null
     );
     
     create table StudentsList
     (
         `id`      int auto_increment
             primary key,
         name      varchar(35) not null,
         last_name varchar(35) not null,
         date_nais date        not null,
         gender    varchar(1)  not null
     );
     
     create table coef
     (
         modul varchar(12) not null,
         niv   int         not null,
         coef  int         not null
     );
     
     create table ens_class
     (
         ens_id   int    not null,
         niv_scho int(1) not null,
         class    int(2) not null,
         constraint ens_class_ibfk_1
             foreign key (ens_id) references EnsiegnantsList (id)
     );
     
     create index ens_id
         on ens_class (ens_id);
      
     create table notes_cc
     (
         stu_id          int    not null,
         math_cc         double not null,
         arabic_cc       double not null,
         french_cc       double not null,
         english_cc      double not null,
         islamic_cc      double not null,
         civil_cc        double not null,
         geo_histo_cc    double not null,
         sport_cc        double not null,
         physics_cc      double not null,
         science_cc      double not null,
         informatique_cc double not null,
         music_cc        double not null,
         design_cc       double not null,
         constraint notes_cc_ibfk_1
             foreign key (stu_id) references StudentsList (id)
     );
     
     create index stu_id
         on notes_cc (stu_id);
      
     create table notes_dv
     (
         stu_id          int    not null,
         math_dv         double not null,
         arabic_dv       double not null,
         french_dv       double not null,
         english_dv      double not null,
         islamic_dv      double not null,
         music_dv        double not null,
         geo_histo_dv    double not null,
         sport_dv        double not null,
         physics_dv      double not null,
         science_dv      double not null,
         informatique_dv double not null,
         civil_dv        double not null,
         design_dv       double not null,
         constraint notes_dv_ibfk_1
             foreign key (stu_id) references StudentsList (id)
     );
     
     create index stu_id
         on notes_dv (stu_id);
      
     create table notes_dv2
     (
         stu_id      int    not null,
         math_dv2    double not null,
         arabic_dv2  double not null,
         french_dv2  double not null,
         english_dv2 double not null,
         constraint notes_dv2_ibfk_1
             foreign key (stu_id) references StudentsList (id)
     );
     
     create index stu_id
         on notes_dv2 (stu_id);
      
     create table notes_exmn
     (
         stu_id          int    not null,
         math_ex         double not null,
         arabic_ex       double not null,
         french_ex       double not null,
         english_ex      double not null,
         islamic_ex      double not null,
         music_ex        double not null,
         geo_histo_ex    double not null,
         sport_ex        double not null,
         physics_ex      double not null,
         science_ex      double not null,
         informatique_ex double not null,
         civil_ex        double not null,
         design_ex       double not null,
         constraint notes_exmn_ibfk_1
             foreign key (stu_id) references StudentsList (id)
     );
     
     create index stu_id
         on notes_exmn (stu_id);
      
     create table notes_moy
     (
         stu_id           int         not null,
         math_moy         double      not null,
         arabic_moy       double      not null,
         french_moy       double      not null,
         english_moy      double      not null,
         islamic_moy      double      not null,
         music_moy        double      not null,
         geo_histo_moy    double      not null,
         sport_moy        double      not null,
         physics_moy      double      not null,
         science_moy      double      not null,
         informatique_moy double      not null,
         civil_moy        double      not null,
         design_moy       varchar(13) not null,
         constraint notes_moy_ibfk_1
             foreign key (stu_id) references StudentsList (id)
     );
     
     create index stu_id
         on notes_moy (stu_id);
      
     create table remark
     (
         stu_id           int         not null,
         math_rem         varchar(13) not null,
         arabic_rem       varchar(13) not null,
         french_rem       varchar(13) not null,
         english_rem      varchar(13) not null,
         islamic_rem      varchar(13) not null,
         civil_rem        varchar(13) not null,
         geo_histo_rem    varchar(13) not null,
         sport_rem        varchar(13) not null,
         physics_rem      varchar(13) not null,
         science_rem      varchar(13) not null,
         informatique_rem varchar(13) not null,
         music_rem        varchar(13) not null,
         design_rem       varchar(13) not null,
         constraint remark_ibfk_1
             foreign key (stu_id) references StudentsList (id)
     );
     
     create index stu_id
         on remark (stu_id);
      
     create table stu_class
     (
         stu_id   int    not null,
         niv_scho int(1) not null,
         class    int(2) not null,
         constraint stu_class_ibfk_1
             foreign key (stu_id) references StudentsList (id)
     );
     
     create index stu_id
         on stu_class (stu_id);
      
     create table users
     (
         usr varchar(25) not null,
         pwd varchar(50) not null
     );
     insert into users
     values ('admin', 'admin');
     
     insert into coef
     values ('math', 1, 2);
     insert into coef
     values ('math', 2, 3);
     insert into coef
     values ('math', 3, 3);
     insert into coef
     values ('math', 4, 4);
     
     insert into coef
     values ('arabic', 1, 2);
     insert into coef
     values ('arabic', 2, 3);
     insert into coef
     values ('arabic', 3, 3);
     insert into coef
     values ('arabic', 4, 5);
     
     insert into coef
     values ('french', 1, 1);
     insert into coef
     values ('french', 2, 2);
     insert into coef
     values ('french', 3, 2);
     insert into coef
     values ('french', 4, 3);
     
     insert into coef
     values ('english', 1, 1);
     insert into coef
     values ('english', 2, 1);
     insert into coef
     values ('english', 3, 1);
     insert into coef
     values ('english', 4, 2);
     
     insert into coef
     values ('islamic', 1, 1);
     insert into coef
     values ('islamic', 2, 1);
     insert into coef
     values ('islamic', 3, 1);
     insert into coef
     values ('islamic', 4, 2);
     
     insert into coef
     values ('civil', 1, 1);
     insert into coef
     values ('civil', 2, 1);
     insert into coef
     values ('civil', 3, 1);
     insert into coef
     values ('civil', 4, 1);
     
     insert into coef
     values ('geo_histo', 1, 2);
     insert into coef
     values ('geo_histo', 2, 2);
     insert into coef
     values ('geo_histo', 3, 2);
     insert into coef
     values ('geo_histo', 4, 3);
     
     insert into coef
     values ('sport', 1, 1);
     insert into coef
     values ('sport', 2, 1);
     insert into coef
     values ('sport', 3, 1);
     insert into coef
     values ('sport', 4, 1);
     
     insert into coef
     values ('physics', 1, 1);
     insert into coef
     values ('physics', 2, 2);
     insert into coef
     values ('physics', 3, 2);
     insert into coef
     values ('physics', 4, 2);
     
     insert into coef
     values ('science', 1, 1);
     insert into coef
     values ('science', 2, 2);
     insert into coef
     values ('science', 3, 2);
     insert into coef
     values ('science', 4, 2);
     
     insert into coef
     values ('informatique', 1, 1);
     insert into coef
     values ('informatique', 2, 1);
     insert into coef
     values ('informatique', 3, 1);
     insert into coef
     values ('informatique', 4, 1);
     
     insert into coef
     values ('music', 1, 1);
     insert into coef
     values ('music', 2, 1);
     insert into coef
     values ('music', 3, 1);
     insert into coef
     values ('music', 4, 1);
     
     insert into coef
     values ('design', 1, 1);
     insert into coef
     values ('design', 2, 1);
     insert into coef
     values ('design', 3, 1);
     insert into coef
     values ('design', 4, 1);""";
}
