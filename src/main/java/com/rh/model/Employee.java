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

public class Employee {
    private Long employeeId;
    private String fullName;
    private Date dateOfBirth;
    private String idPassport;
    private String address;
    private String phone;
    private String email;
    private Date hireDate;
    private String department;
    private String position;
    private BigDecimal salary;
    private String contractType;

    // No-Argument Constructor
    public Employee() {
    }

    // Constructor with Arguments
    public Employee(int employeeId, String fullName, Date dateOfBirth, String idPassport, String address, String phone,
            String email, Date hireDate, String department, String position, BigDecimal salary, String contractType) {
        this.employeeId = (long) employeeId;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.idPassport = idPassport;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.hireDate = hireDate;
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.contractType = contractType;
    }

    // Getters and Setters
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long long1) {
        this.employeeId = long1;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIdPassport() {
        return idPassport;
    }

    public void setIdPassport(String idPassport) {
        this.idPassport = idPassport;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }
}