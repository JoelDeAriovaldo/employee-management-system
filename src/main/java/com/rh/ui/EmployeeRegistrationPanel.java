/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.rh.ui;

/**
 *
 * @author JoelDeAriovaldo
 */

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import main.java.com.rh.service.EmployeeService;
import main.java.com.rh.model.Employee;
import java.sql.Date;
import java.math.BigDecimal;

public class EmployeeRegistrationPanel extends JPanel {
    private JTextField fullNameField, dateOfBirthField, idPassportField, addressField;
    private JTextField phoneField, emailField, hireDateField, departmentField;
    private JTextField positionField, salaryField;
    private JComboBox<String> contractTypeComboBox;
    private JButton registerButton, updateButton, deleteButton, clearButton, searchButton;
    private EmployeeService employeeService;
    private DefaultTableModel tableModel;
    private JTable employeeTable;

    // Modern color palette
    private final Color PRIMARY_COLOR = new Color(79, 70, 229); // Indigo-600
    private final Color SECONDARY_COLOR = new Color(99, 102, 241); // Indigo-500
    private final Color DANGER_COLOR = new Color(220, 38, 38); // Red-600
    private final Color WARNING_COLOR = new Color(234, 179, 8); // Yellow-500
    private final Color BACKGROUND_COLOR = new Color(249, 250, 251); // Gray-50
    private final Color TEXT_COLOR = new Color(17, 24, 39); // Gray-900
    private final Color BORDER_COLOR = new Color(229, 231, 235); // Gray-200

    public EmployeeRegistrationPanel() {
        employeeService = new EmployeeService();
        initComponents();
        styleComponents();
        loadEmployeeData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(BACKGROUND_COLOR);

        // Create main split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(400);
        splitPane.setBorder(null);

        // Left panel - Form
        JPanel formPanel = createFormPanel();

        // Right panel - Table and Operations
        JPanel tablePanel = createTablePanel();

        splitPane.setLeftComponent(new JScrollPane(formPanel));
        splitPane.setRightComponent(tablePanel);

        add(splitPane, BorderLayout.CENTER);

        // Add action listeners
        setupActionListeners();
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BACKGROUND_COLOR);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20),
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR),
                        "Cadastro de Funcionário",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Segoe UI", Font.BOLD, 14))));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Initialize form fields
        fullNameField = new JPlaceholderTextField("Digite o nome completo");
        dateOfBirthField = new JPlaceholderTextField("AAAA-MM-DD");
        idPassportField = new JPlaceholderTextField("Digite o ID/Passaporte");
        addressField = new JPlaceholderTextField("Digite o endereço");
        phoneField = new JPlaceholderTextField("Digite o telefone");
        emailField = new JPlaceholderTextField("Digite o e-mail");
        hireDateField = new JPlaceholderTextField("AAAA-MM-DD");
        departmentField = new JPlaceholderTextField("Digite o departamento");
        positionField = new JPlaceholderTextField("Digite o cargo");
        salaryField = new JPlaceholderTextField("Digite o salário");
        contractTypeComboBox = new JComboBox<>(new String[] { "Tempo Integral", "Meio Período" });

        // Add fields to form
        addFieldToForm(formPanel, "Nome Completo:", fullNameField, gbc, 0);
        addFieldToForm(formPanel, "Data de Nascimento:", dateOfBirthField, gbc, 1);
        addFieldToForm(formPanel, "ID/Passaporte:", idPassportField, gbc, 2);
        addFieldToForm(formPanel, "Endereço:", addressField, gbc, 3);
        addFieldToForm(formPanel, "Telefone:", phoneField, gbc, 4);
        addFieldToForm(formPanel, "E-mail:", emailField, gbc, 5);
        addFieldToForm(formPanel, "Data de Contratação:", hireDateField, gbc, 6);
        addFieldToForm(formPanel, "Departamento:", departmentField, gbc, 7);
        addFieldToForm(formPanel, "Cargo:", positionField, gbc, 8);
        addFieldToForm(formPanel, "Salário:", salaryField, gbc, 9);
        addFieldToForm(formPanel, "Tipo de Contrato:", contractTypeComboBox, gbc, 10);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        registerButton = createStyledButton("Registrar", PRIMARY_COLOR);
        updateButton = createStyledButton("Atualizar", WARNING_COLOR);
        deleteButton = createStyledButton("Excluir", DANGER_COLOR);
        clearButton = createStyledButton("Limpar", SECONDARY_COLOR);

        buttonPanel.add(registerButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        gbc.gridy = 11;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        return formPanel;
    }

    private void addFieldToForm(JPanel formPanel, String labelText, JComponent field, GridBagConstraints gbc, int y) {
        gbc.gridy = y;
        gbc.gridx = 0;
        gbc.gridwidth = 1;

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(TEXT_COLOR);
        formPanel.add(label, gbc);

        gbc.gridx = 1;
        formPanel.add(field, gbc);
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout(10, 10));
        tablePanel.setBackground(BACKGROUND_COLOR);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));

        // Create search panel
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(BACKGROUND_COLOR);
        JTextField searchField = new JPlaceholderTextField("Buscar funcionário...");
        searchButton = createStyledButton("Buscar", PRIMARY_COLOR);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Create table
        tableModel = new DefaultTableModel(
                new Object[] { "ID", "Nome", "Nascimento", "ID/Passaporte", "Endereço", "Telefone", "E-mail",
                        "Data de Contratação", "Departamento", "Cargo", "Salário", "Tipo de Contrato" },
                0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        employeeTable = new JTable(tableModel);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeTable.getTableHeader().setBackground(PRIMARY_COLOR);
        employeeTable.getTableHeader().setForeground(Color.WHITE);
        employeeTable.setRowHeight(25);
        employeeTable.setGridColor(BORDER_COLOR);

        JScrollPane tableScrollPane = new JScrollPane(employeeTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private void setupActionListeners() {
        registerButton.addActionListener(e -> registerEmployee());
        updateButton.addActionListener(e -> updateEmployee());
        deleteButton.addActionListener(e -> deleteEmployee());
        clearButton.addActionListener(e -> clearFields());

        employeeTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && employeeTable.getSelectedRow() != -1) {
                loadEmployeeToForm(employeeTable.getSelectedRow());
            }
        });
    }

    private void registerEmployee() {
        try {
            Employee employee = getEmployeeFromForm();
            if (employeeService.registerEmployee(employee)) {
                JOptionPane.showMessageDialog(this, "Funcionário registrado com sucesso!");
                addEmployeeToTable(employee);
                clearFields();
                loadEmployeeData();
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao registrar funcionário.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao registrar: " + ex.getMessage());
        }
    }

    private Employee getEmployeeFromForm() {
        Employee employee = new Employee();
        employee.setFullName(fullNameField.getText());
        employee.setDateOfBirth(Date.valueOf(dateOfBirthField.getText()));
        employee.setIdPassport(idPassportField.getText());
        employee.setAddress(addressField.getText());
        employee.setPhone(phoneField.getText());
        employee.setEmail(emailField.getText());
        employee.setHireDate(Date.valueOf(hireDateField.getText()));
        employee.setDepartment(departmentField.getText());
        employee.setPosition(positionField.getText());
        employee.setSalary(new BigDecimal(salaryField.getText()));
        employee.setContractType((String) contractTypeComboBox.getSelectedItem());
        return employee;
    }

    private void updateEmployee() {
        try {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow != -1) {
                Employee employee = getEmployeeFromForm();
                employee.setEmployeeId((Long) tableModel.getValueAt(selectedRow, 0));
                if (employeeService.updateEmployee(employee)) {
                    JOptionPane.showMessageDialog(this, "Funcionário atualizado com sucesso!");
                    loadEmployeeData(); // Recarrega os dados na tabela
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Falha ao atualizar funcionário.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um funcionário para atualizar.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + ex.getMessage());
        }
    }

    private void deleteEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente excluir este funcionário?",
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                Long employeeId = (Long) tableModel.getValueAt(selectedRow, 0); // Certifique-se de que o ID é Long
                if (employeeService.deleteEmployee(employeeId)) {
                    JOptionPane.showMessageDialog(this, "Funcionário excluído com sucesso!");
                    loadEmployeeData();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Falha ao excluir funcionário.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário para excluir.");
        }
    }

    private void loadEmployeeToForm(int row) {
        fullNameField.setText(tableModel.getValueAt(row, 1).toString());
        dateOfBirthField.setText(tableModel.getValueAt(row, 2).toString());
        idPassportField.setText(tableModel.getValueAt(row, 3).toString());
        addressField.setText(tableModel.getValueAt(row, 4).toString());
        phoneField.setText(tableModel.getValueAt(row, 5).toString());
        emailField.setText(tableModel.getValueAt(row, 6).toString());
        hireDateField.setText(tableModel.getValueAt(row, 7).toString());
        departmentField.setText(tableModel.getValueAt(row, 8).toString());
        positionField.setText(tableModel.getValueAt(row, 9).toString());
        salaryField.setText(tableModel.getValueAt(row, 10).toString());
        contractTypeComboBox.setSelectedItem(tableModel.getValueAt(row, 11).toString());
    }

    private void loadEmployeeData() {
        tableModel.setRowCount(0); // Limpa os dados existentes na tabela
        for (Employee employee : employeeService.getAllEmployees()) {
            addEmployeeToTable(employee);
        }
    }

    private JPanel createFieldPanel(String labelText, JComponent field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND_COLOR);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(TEXT_COLOR);

        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(field);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        return panel;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private void styleComponents() {
        styleTextField(fullNameField);
        styleTextField(dateOfBirthField);
        styleTextField(idPassportField);
        styleTextField(addressField);
        styleTextField(phoneField);
        styleTextField(emailField);
        styleTextField(hireDateField);
        styleTextField(departmentField);
        styleTextField(positionField);
        styleTextField(salaryField);
    }

    private void styleTextField(JComponent field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
    }

    private void registerEmployeeActionPerformed(ActionEvent e) {
        String fullName = fullNameField.getText();
        Date dateOfBirth = Date.valueOf(dateOfBirthField.getText());
        String idPassport = idPassportField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        Date hireDate = Date.valueOf(hireDateField.getText());
        String department = departmentField.getText();
        String position = positionField.getText();
        BigDecimal salary = new BigDecimal(salaryField.getText());
        String contractType = (String) contractTypeComboBox.getSelectedItem();

        Employee employee = new Employee();
        employee.setFullName(fullName);
        employee.setDateOfBirth(dateOfBirth);
        employee.setIdPassport(idPassport);
        employee.setAddress(address);
        employee.setPhone(phone);
        employee.setEmail(email);
        employee.setHireDate(hireDate);
        employee.setDepartment(department);
        employee.setPosition(position);
        employee.setSalary(salary);
        employee.setContractType(contractType);

        if (employeeService.registerEmployee(employee)) {
            JOptionPane.showMessageDialog(this, "Funcionário registrado com sucesso!");
            addEmployeeToTable(employee);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Falha ao registrar funcionário.");
        }
    }

    private void addEmployeeToTable(Employee employee) {
        tableModel.addRow(new Object[] {
                employee.getEmployeeId(),
                employee.getFullName(),
                employee.getDateOfBirth(),
                employee.getIdPassport(),
                employee.getAddress(),
                employee.getPhone(),
                employee.getEmail(),
                employee.getHireDate(),
                employee.getDepartment(),
                employee.getPosition(),
                employee.getSalary(),
                employee.getContractType()
        });
    }

    private void clearFields() {
        fullNameField.setText("");
        dateOfBirthField.setText("");
        idPassportField.setText("");
        addressField.setText("");
        phoneField.setText("");
        emailField.setText("");
        hireDateField.setText("");
        departmentField.setText("");
        positionField.setText("");
        salaryField.setText("");
        contractTypeComboBox.setSelectedIndex(0);
    }

    // Classe auxiliar para campo de texto com placeholder
    private static class JPlaceholderTextField extends JTextField {
        private String placeholder;

        public JPlaceholderTextField(String placeholder) {
            this.placeholder = placeholder;

            addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (getText().equals(placeholder)) {
                        setText("");
                        setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (getText().isEmpty()) {
                        setText(placeholder);
                        setForeground(Color.GRAY);
                    }
                }
            });

            setText(placeholder);
            setForeground(Color.GRAY);
        }
    }
}