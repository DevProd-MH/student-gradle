package com.student.student;


import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.alignment.VerticalAlignment;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class bultienPDF {
    
    public boolean createDocument (ArrayList<Student> StuList, Ensiegnant ensiegnant) {
        
        String FOLDER_PATH = System.getProperty("user.home") + File.separatorChar + "Desktop" + File.separatorChar + ensiegnant.getNiv().concat("M" + ensiegnant.getCls()) + File.separatorChar + ensiegnant.getModule() + File.separatorChar;
        String PDF_NAME = ensiegnant.getLast_name().toUpperCase().concat(ensiegnant.getName().concat(".pdf"));
        try {
            Document document = new Document();
            File folder = new File(FOLDER_PATH);
            try {
                folder.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String FILE_PATH_NAME = FOLDER_PATH + PDF_NAME;
            PdfWriter.getInstance(document, new FileOutputStream(FILE_PATH_NAME));
            Font font = new Font(Font.COURIER, 12, Font.BOLD);
            Table table = new Table(9);
            table.setPadding(3);
            table.setSpacing(0);
            table.setWidth(110);
            // Setting table headers
            Cell cell = new Cell("Ensiegnant : " + ensiegnant.getLast_name().concat(" " + ensiegnant.getName()));
            cell.setHeader(true);
            cell.setVerticalAlignment(VerticalAlignment.CENTER);
            cell.setHorizontalAlignment(HorizontalAlignment.LEFT);
            cell.setColspan(4);
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            table.addCell(cell);
            Cell cell1 = new Cell("Class : " + ensiegnant.getNiv().concat("M" + ensiegnant.getCls()));
            cell1.setHeader(true);
            cell1.setVerticalAlignment(VerticalAlignment.CENTER);
            cell1.setHorizontalAlignment(HorizontalAlignment.CENTER);
            cell1.setColspan(2);
            cell1.setBackgroundColor(Color.LIGHT_GRAY);
            table.addCell(cell1);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
            Date date = new Date();
            Cell cell2 = new Cell("Année : " + formatter.format(date));
            cell2.setHeader(true);
            cell2.setVerticalAlignment(VerticalAlignment.CENTER);
            cell2.setHorizontalAlignment(HorizontalAlignment.CENTER);
            cell2.setColspan(2);
            cell2.setBackgroundColor(Color.LIGHT_GRAY);
            table.addCell(cell2);
            table.addCell(new Phrase(ensiegnant.getModule()));
            Cell cell3 = new Cell("Nom Complete");
            cell3.setHeader(true);
            cell3.setVerticalAlignment(VerticalAlignment.CENTER);
            cell3.setHorizontalAlignment(HorizontalAlignment.CENTER);
            cell3.setColspan(2);
            cell3.setBackgroundColor(Color.LIGHT_GRAY);
            table.addCell(cell3);
            table.addCell(new Phrase("Control Continue", font));
            table.addCell(new Phrase("Devoire 1", font));
            table.addCell(new Phrase("Devoire 2", font));
            table.addCell(new Phrase("Examen", font));
            table.addCell(new Phrase("Moyenne Module", font));
            table.addCell(new Phrase("Observation", new Font(Font.COURIER, 9, Font.BOLD)));
            table.addCell(new Phrase("Classement", new Font(Font.COURIER, 10, Font.BOLD)));
            boolean principle = ensiegnant.getModule().equals("math") || ensiegnant.getModule().equals("arabic") || ensiegnant.getModule().equals("english") || ensiegnant.getModule().equals("french");
            for (Student student : StuList) {
                Cell cel = new Cell(student.getName() + " " + student.getLast_name());
                cel.setColspan(2);
                table.addCell(cel);
                table.addCell(student.getNotes().get(0) + "");
                table.addCell(student.getNotes().get(1) + "");
                if (principle) table.addCell(student.getNotes().get(2) + "");
                else table.addCell("/");
                table.addCell(student.getNotes().get(3) + "");
                table.addCell(student.getNotes().get(4) + "");
                table.addCell(student.getObsrv().get(0) + "");
                table.addCell("classement");
            }
            table.endHeaders();
            document.open();
            document.add(table);
            document.close();
            return true;
        } catch (DocumentException | FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
    
}
