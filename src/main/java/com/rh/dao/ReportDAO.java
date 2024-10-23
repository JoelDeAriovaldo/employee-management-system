package main.java.com.rh.dao;

import main.java.com.rh.config.DatabaseConfig;
import main.java.com.rh.model.Report;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

    public boolean createReport(Report report) {
        String sql = "INSERT INTO reports (report_type, start_date, end_date, generated_by, file_path) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, report.getReportType());
            stmt.setDate(2, report.getStartDate());
            stmt.setDate(3, report.getEndDate());
            stmt.setInt(4, report.getGeneratedBy());
            stmt.setString(5, report.getFilePath());

            // Adicione logs para debug
            System.out.println("Executando SQL: " + sql);
            System.out.println("Parâmetros: " + report.getReportType() + ", " + report.getStartDate() + ", " +
                    report.getEndDate() + ", " + report.getGeneratedBy() + ", " + report.getFilePath());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro SQL: " + e.getMessage());
            return false;
        }
    }

    public boolean updateReport(Report report) {
        String sql = "UPDATE reports SET report_type = ?, start_date = ?, end_date = ?, generated_by = ?, file_path = ? WHERE report_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, report.getReportType());
            stmt.setDate(2, report.getStartDate());
            stmt.setDate(3, report.getEndDate());
            stmt.setInt(4, report.getGeneratedBy());
            stmt.setString(5, report.getFilePath());
            stmt.setLong(6, report.getReportId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteReport(Long reportId) {
        String sql = "DELETE FROM reports WHERE report_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, reportId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Report> searchReports(String searchTerm) {
        List<Report> reports = new ArrayList<>();
        String sql = "SELECT * FROM reports WHERE report_type LIKE ? OR file_path LIKE ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + searchTerm + "%");
            stmt.setString(2, "%" + searchTerm + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reports.add(mapResultSetToReport(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    public List<Report> getAllReports() {
        List<Report> reports = new ArrayList<>();
        String sql = "SELECT * FROM reports";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                reports.add(mapResultSetToReport(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    private Report mapResultSetToReport(ResultSet rs) throws SQLException {
        Report report = new Report();
        report.setReportId(rs.getLong("report_id"));
        report.setReportType(rs.getString("report_type"));
        report.setStartDate(rs.getDate("start_date"));
        report.setEndDate(rs.getDate("end_date"));
        report.setGeneratedBy(rs.getInt("generated_by"));
        report.setFilePath(rs.getString("file_path"));
        return report;
    }
}