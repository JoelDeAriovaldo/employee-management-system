package main.java.com.rh.dao;

import main.java.com.rh.config.DatabaseConfig;
import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardDAO {
    private static final Logger LOGGER = Logger.getLogger(DashboardDAO.class.getName());

    public int getActiveEmployees() {
        String sql = "SELECT COUNT(*) FROM employees WHERE status = 'ACTIVE'";
        return executeCountQuery(sql);
    }

    public int getHoursWorked() {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        String sql = "SELECT COALESCE(SUM(hours_worked), 0) FROM attendance WHERE date >= ?";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(startOfMonth));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting hours worked", e);
        }
        return 0;
    }

    public int getAbsences() {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        String sql = "SELECT COUNT(*) FROM attendance WHERE status = 'ABSENT' AND date >= ?";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(startOfMonth));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting absences", e);
        }
        return 0;
    }

    public int getOvertime() {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        String sql = "SELECT COALESCE(SUM(overtime_hours), 0) FROM attendance WHERE date >= ?";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(startOfMonth));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting overtime", e);
        }
        return 0;
    }

    private int executeCountQuery(String sql) {
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error executing count query: " + sql, e);
        }
        return 0;
    }
}