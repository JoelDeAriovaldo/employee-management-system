package main.java.com.rh.model;

import java.sql.Date;

public class Report {
    private Long reportId;
    private Date startDate;
    private Date endDate;
    private String reportType;
    private int generatedBy; // Add this field
    private String filePath; // Add this field

    // Getters and Setters
    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public int getGeneratedBy() { // Add this getter
        return generatedBy;
    }

    public void setGeneratedBy(int generatedBy) { // Add this setter
        this.generatedBy = generatedBy;
    }

    public String getFilePath() { // Add this getter
        return filePath;
    }

    public void setFilePath(String filePath) { // Add this setter
        this.filePath = filePath;
    }
}