/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.rh.dao;

import java.math.BigDecimal;

/**
 *
 * @author JoelDeAriovaldo
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.java.com.rh.config.DatabaseConfig;
import main.java.com.rh.model.Attendance;

public class AttendanceDAO {

    public boolean recordAttendance(Attendance attendance) {
        String sql = "INSERT INTO attendance (employee_id, date, time_in, time_out, hours_worked, overtime_hours, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, attendance.getEmployeeId());
            stmt.setDate(2, attendance.getDate());
            stmt.setTime(3, attendance.getTimeIn());
            stmt.setTime(4, attendance.getTimeOut());
            stmt.setBigDecimal(5, attendance.getHoursWorked());
            stmt.setBigDecimal(6, attendance.getOvertimeHours());
            stmt.setString(7, attendance.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAttendance(Attendance attendance) {
        String sql = "UPDATE attendance SET employee_id = ?, date = ?, time_in = ?, time_out = ?, hours_worked = ?, overtime_hours = ?, status = ? WHERE attendance_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, attendance.getEmployeeId());
            stmt.setDate(2, attendance.getDate());
            stmt.setTime(3, attendance.getTimeIn());
            stmt.setTime(4, attendance.getTimeOut());
            stmt.setBigDecimal(5, attendance.getHoursWorked());
            stmt.setBigDecimal(6, attendance.getOvertimeHours());
            stmt.setString(7, attendance.getStatus());
            stmt.setInt(8, attendance.getAttendanceId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAttendance(int attendanceId) {
        String sql = "DELETE FROM attendance WHERE attendance_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, attendanceId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Attendance> searchAttendances(String searchTerm) {
        List<Attendance> attendances = new ArrayList<>();
        String sql = "SELECT * FROM attendance WHERE employee_id LIKE ? OR status LIKE ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + searchTerm + "%");
            stmt.setString(2, "%" + searchTerm + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Attendance attendance = new Attendance(
                        rs.getInt("attendance_id"),
                        rs.getInt("employee_id"),
                        rs.getDate("date"),
                        rs.getTime("time_in"),
                        rs.getTime("time_out"),
                        rs.getBigDecimal("hours_worked"),
                        rs.getBigDecimal("overtime_hours"),
                        rs.getString("status"));
                attendances.add(attendance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendances;
    }

    private Attendance mapResultSetToAttendance(ResultSet rs) throws SQLException {
        BigDecimal hoursWorked = rs.getBigDecimal("hours_worked");
        BigDecimal overtimeHours = rs.getBigDecimal("overtime_hours");

        return new Attendance(
                rs.getInt("attendance_id"),
                rs.getInt("employee_id"),
                rs.getDate("date"),
                rs.getTime("time_in"),
                rs.getTime("time_out"),
                hoursWorked != null ? hoursWorked : BigDecimal.ZERO,
                overtimeHours != null ? overtimeHours : BigDecimal.ZERO,
                rs.getString("status"));
    }

    public List<Attendance> getAttendanceByEmployeeId(int employeeId) {
        List<Attendance> attendances = new ArrayList<>();
        String sql = "SELECT * FROM attendance WHERE employee_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                attendances.add(mapResultSetToAttendance(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendances;
    }

    public List<Attendance> getAllAttendances() {
        List<Attendance> attendances = new ArrayList<>();
        String sql = "SELECT * FROM attendance";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                attendances.add(mapResultSetToAttendance(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendances;
    }

    public Attendance getAttendanceById(int attendanceId) {
        String sql = "SELECT * FROM attendance WHERE attendance_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, attendanceId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToAttendance(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
