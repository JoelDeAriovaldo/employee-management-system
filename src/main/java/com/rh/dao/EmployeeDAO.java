/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.rh.dao;

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
import main.java.com.rh.model.Employee;

public class EmployeeDAO {

    public boolean addEmployee(Employee employee) {
        String sql = "INSERT INTO employees (full_name, date_of_birth, id_passport, address, phone, email, hire_date, department, position, salary, contract_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employee.getFullName());
            stmt.setDate(2, employee.getDateOfBirth());
            stmt.setString(3, employee.getIdPassport());
            stmt.setString(4, employee.getAddress());
            stmt.setString(5, employee.getPhone());
            stmt.setString(6, employee.getEmail());
            stmt.setDate(7, employee.getHireDate());
            stmt.setString(8, employee.getDepartment());
            stmt.setString(9, employee.getPosition());
            stmt.setBigDecimal(10, employee.getSalary());
            stmt.setString(11, employee.getContractType());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("employee_id"),
                        rs.getString("full_name"),
                        rs.getDate("date_of_birth"),
                        rs.getString("id_passport"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getDate("hire_date"),
                        rs.getString("department"),
                        rs.getString("position"),
                        rs.getBigDecimal("salary"),
                        rs.getString("contract_type"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public Employee getEmployeeById(int employeeId) {
        String sql = "SELECT * FROM employees WHERE employee_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Employee(
                        rs.getInt("employee_id"),
                        rs.getString("full_name"),
                        rs.getDate("date_of_birth"),
                        rs.getString("id_passport"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getDate("hire_date"),
                        rs.getString("department"),
                        rs.getString("position"),
                        rs.getBigDecimal("salary"),
                        rs.getString("contract_type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateEmployee(Employee employee) {
        String sql = "UPDATE employees SET full_name = ?, date_of_birth = ?, id_passport = ?, address = ?, phone = ?, email = ?, hire_date = ?, department = ?, position = ?, salary = ?, contract_type = ? WHERE employee_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employee.getFullName());
            stmt.setDate(2, employee.getDateOfBirth());
            stmt.setString(3, employee.getIdPassport());
            stmt.setString(4, employee.getAddress());
            stmt.setString(5, employee.getPhone());
            stmt.setString(6, employee.getEmail());
            stmt.setDate(7, employee.getHireDate());
            stmt.setString(8, employee.getDepartment());
            stmt.setString(9, employee.getPosition());
            stmt.setBigDecimal(10, employee.getSalary());
            stmt.setString(11, employee.getContractType());
            stmt.setLong(12, employee.getEmployeeId()); // Alterado para Long
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEmployee(Long employeeId) { // Alterado para Long
        String sql = "DELETE FROM employees WHERE employee_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, employeeId); // Alterado para Long
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Employee getEmployeeByName(String fullName) {
        String sql = "SELECT * FROM employees WHERE full_name = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fullName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Employee(
                        rs.getInt("employee_id"),
                        rs.getString("full_name"),
                        rs.getDate("date_of_birth"),
                        rs.getString("id_passport"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getDate("hire_date"),
                        rs.getString("department"),
                        rs.getString("position"),
                        rs.getBigDecimal("salary"),
                        rs.getString("contract_type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}