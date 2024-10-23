/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.rh.util;

/**
 *
 * @author JoelDeAriovaldo
 */

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import main.java.com.rh.model.Attendance;
import main.java.com.rh.model.Employee;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class PDFExporter {
    private static final Font TITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font SUBTITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
    private static final Font BODY_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

    private String getDesktopPath() {
        return System.getProperty("user.home") + File.separator + "Desktop" + File.separator;
    }

    public void exportAttendanceReport(List<Attendance> attendances, Date startDate, Date endDate) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(getDesktopPath() + "Relatório_de_Presença.pdf"));
            document.open();
            addTitle(document, "Relatório de Presença");
            addSubtitle(document, "De " + startDate + " a " + endDate);
            addAttendanceTable(document, attendances);
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    public void exportPerformanceReport(List<Employee> employees, Date startDate, Date endDate) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(getDesktopPath() + "Relatório_de_Desempenho.pdf"));
            document.open();
            addTitle(document, "Relatório de Desempenho");
            addSubtitle(document, "De " + startDate + " a " + endDate);
            addEmployeeTable(document, employees);
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    public void exportOvertimeReport(List<Attendance> attendances, Date startDate, Date endDate) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(getDesktopPath() + "Relatório_de_Horas_Extras.pdf"));
            document.open();
            addTitle(document, "Relatório de Horas Extras");
            addSubtitle(document, "De " + startDate + " a " + endDate);
            addOvertimeTable(document, attendances);
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private void addTitle(Document document, String title) throws DocumentException {
        Paragraph paragraph = new Paragraph(title, TITLE_FONT);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
    }

    private void addSubtitle(Document document, String subtitle) throws DocumentException {
        Paragraph paragraph = new Paragraph(subtitle, SUBTITLE_FONT);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
    }

    private void addAttendanceTable(Document document, List<Attendance> attendances) throws DocumentException {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        addTableHeader(table, new String[] { "ID do Empregado", "Data", "Hora de Entrada", "Hora de Saída",
                "Horas Trabalhadas", "Estado" });
        for (Attendance attendance : attendances) {
            table.addCell(String.valueOf(attendance.getEmployeeId()));
            table.addCell(attendance.getDate().toString());
            table.addCell(attendance.getTimeIn().toString());
            table.addCell(attendance.getTimeOut() != null ? attendance.getTimeOut().toString() : "");
            table.addCell(attendance.getHoursWorked().toString());
            table.addCell(attendance.getStatus());
        }
        document.add(table);
    }

    private void addEmployeeTable(Document document, List<Employee> employees) throws DocumentException {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        addTableHeader(table, new String[] { "ID do Empregado", "Nome Completo", "Departamento", "Cargo", "Salário" });
        for (Employee employee : employees) {
            table.addCell(String.valueOf(employee.getEmployeeId()));
            table.addCell(employee.getFullName());
            table.addCell(employee.getDepartment());
            table.addCell(employee.getPosition());
            table.addCell(employee.getSalary().toString());
        }
        document.add(table);
    }

    private void addOvertimeTable(Document document, List<Attendance> attendances) throws DocumentException {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        addTableHeader(table,
                new String[] { "ID do Empregado", "Data", "Horas Trabalhadas", "Horas Extras", "Estado" });
        for (Attendance attendance : attendances) {
            table.addCell(String.valueOf(attendance.getEmployeeId()));
            table.addCell(attendance.getDate().toString());
            table.addCell(attendance.getHoursWorked().toString());
            table.addCell(attendance.getOvertimeHours().toString());
            table.addCell(attendance.getStatus());
        }
        document.add(table);
    }

    private void addTableHeader(PdfPTable table, String[] headers) {
        for (String header : headers) {
            PdfPCell cell = new PdfPCell();
            cell.setPhrase(new Phrase(header, BODY_FONT));
            table.addCell(cell);
        }
    }
}