package main.java.com.rh.service;

import main.java.com.rh.dao.DashboardDAO;

public class DashboardService {
    private DashboardDAO dashboardDAO;

    public DashboardService() {
        this.dashboardDAO = new DashboardDAO();
    }

    public int getActiveEmployees() {
        return dashboardDAO.getActiveEmployees();
    }

    public int getHoursWorked() {
        return dashboardDAO.getHoursWorked();
    }

    public int getAbsences() {
        return dashboardDAO.getAbsences();
    }

    public int getOvertime() {
        return dashboardDAO.getOvertime();
    }
}