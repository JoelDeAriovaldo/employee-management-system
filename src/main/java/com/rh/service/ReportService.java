/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.rh.service;

import java.math.BigDecimal;

/**
 *
 * @author JoelDeAriovaldo
 */

import main.java.com.rh.dao.ReportDAO;
import main.java.com.rh.model.Report;

import java.sql.Date;
import java.util.List;

public class ReportService {
    private ReportDAO reportDAO;

    public ReportService() {
        this.reportDAO = new ReportDAO();
    }

    public boolean createReport(Report report) {
        return reportDAO.createReport(report);
    }

    public boolean updateReport(Long reportId, Date startDate, Date endDate, String reportType) {
        Report report = new Report();
        report.setReportId(reportId);
        report.setStartDate(startDate);
        report.setEndDate(endDate);
        report.setReportType(reportType);
        report.setGeneratedBy(1); // ID do usuário atual ou valor padrão
        report.setFilePath("reports/" + reportType.toLowerCase() + "_" + System.currentTimeMillis() + ".pdf");
        return reportDAO.updateReport(report);
    }

    public boolean deleteReport(Long reportId) {
        return reportDAO.deleteReport(reportId);
    }

    public List<Report> searchReports(String searchTerm) {
        return reportDAO.searchReports(searchTerm);
    }

    public List<Report> getAllReports() {
        return reportDAO.getAllReports();
    }
}