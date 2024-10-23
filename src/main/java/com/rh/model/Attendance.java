/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.rh.model;

/**
 *
 * @author JoelDeAriovaldo
 */
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

public class Attendance {
    private int attendanceId;
    private int employeeId;
    private Date date;
    private Time timeIn;
    private Time timeOut;
    private BigDecimal hoursWorked;
    private BigDecimal overtimeHours;
    private String status;

    public Attendance(int attendanceId, int employeeId, Date date, Time timeIn, Time timeOut, BigDecimal hoursWorked,
            BigDecimal overtimeHours, String status) {
        this.attendanceId = attendanceId;
        this.employeeId = employeeId;
        this.date = date;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.hoursWorked = hoursWorked != null ? hoursWorked : BigDecimal.ZERO;
        this.overtimeHours = overtimeHours != null ? overtimeHours : BigDecimal.ZERO;
        this.status = status;
    }

    // Default constructor
    public Attendance() {
        this.hoursWorked = BigDecimal.ZERO;
        this.overtimeHours = BigDecimal.ZERO;
    }

    // Getters and Setters
    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Time timeIn) {
        this.timeIn = timeIn;
    }

    public Time getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Time timeOut) {
        this.timeOut = timeOut;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Update setter to prevent null values
    public void setHoursWorked(BigDecimal hoursWorked) {
        this.hoursWorked = hoursWorked != null ? hoursWorked : BigDecimal.ZERO;
    }

    public void setOvertimeHours(BigDecimal overtimeHours) {
        this.overtimeHours = overtimeHours != null ? overtimeHours : BigDecimal.ZERO;
    }

    // Safe getter methods that never return null
    public BigDecimal getHoursWorked() {
        return hoursWorked != null ? hoursWorked : BigDecimal.ZERO;
    }

    public BigDecimal getOvertimeHours() {
        return overtimeHours != null ? overtimeHours : BigDecimal.ZERO;
    }

    // Helper methods for string representation
    public String getHoursWorkedAsString() {
        return getHoursWorked().toString();
    }

    public String getOvertimeHoursAsString() {
        return getOvertimeHours().toString();
    }
}