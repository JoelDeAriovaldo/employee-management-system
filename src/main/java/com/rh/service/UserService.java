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
import main.java.com.rh.dao.UserDAO;
import main.java.com.rh.model.User;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public boolean registerUser(User user) {
        return userDAO.registerUser(user);
    }

    public User authenticateUser(String email, String password) {
        return userDAO.authenticateUser(email, password);
    }

    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }

    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}
