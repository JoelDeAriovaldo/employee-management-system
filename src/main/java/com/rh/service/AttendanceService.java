/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.rh.service;

/**
 *
 * @author JoelDeAriovaldo
 */

import java.util.List;
import main.java.com.rh.dao.AttendanceDAO;
import main.java.com.rh.model.Attendance;

public class AttendanceService {
    private AttendanceDAO attendanceDAO;

    public AttendanceService() {
        this.attendanceDAO = new AttendanceDAO();
    }

    public boolean recordAttendance(Attendance attendance) {
        return attendanceDAO.recordAttendance(attendance);
    }

    public boolean updateAttendance(Attendance attendance) {
        return attendanceDAO.updateAttendance(attendance);
    }

    public boolean deleteAttendance(int attendanceId) {
        return attendanceDAO.deleteAttendance(attendanceId);
    }

    public Attendance getAttendanceById(int attendanceId) {
        return attendanceDAO.getAttendanceById(attendanceId);
    }

    public List<Attendance> getAttendanceByEmployeeId(int employeeId) {
        return attendanceDAO.getAttendanceByEmployeeId(employeeId);
    }

    public List<Attendance> getAllAttendances() {
        return attendanceDAO.getAllAttendances();
    }

    // Adicionar o método searchAttendances
    public List<Attendance> searchAttendances(String searchTerm) {
        return attendanceDAO.searchAttendances(searchTerm);
    }
}