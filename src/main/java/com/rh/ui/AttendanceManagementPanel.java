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
import main.java.com.rh.service.AttendanceService;
import main.java.com.rh.service.EmployeeService;
import main.java.com.rh.model.Attendance;
import main.java.com.rh.model.Employee;

import java.sql.Date;
import java.sql.Time;
import java.util.List; // Ensure this import is correct

public class AttendanceManagementPanel extends JPanel {
    private JTextField employeeIdField, dateField, timeInField, timeOutField, searchField;
    private JComboBox<String> statusComboBox;
    private JComboBox<String> employeeComboBox; // Declare employeeComboBox here
    private JButton recordButton, updateButton, deleteButton, clearButton, searchButton;
    private AttendanceService attendanceService;
    private EmployeeService employeeService; // Declare employeeService here
    private DefaultTableModel tableModel;
    private JTable attendanceTable;

    // Modern color palette
    private final Color PRIMARY_COLOR = new Color(79, 70, 229); // Indigo-600
    private final Color SECONDARY_COLOR = new Color(99, 102, 241); // Indigo-500
    private final Color DANGER_COLOR = new Color(220, 38, 38); // Red-600
    private final Color WARNING_COLOR = new Color(234, 179, 8); // Yellow-500
    private final Color BACKGROUND_COLOR = new Color(249, 250, 251); // Gray-50
    private final Color TEXT_COLOR = new Color(17, 24, 39); // Gray-900
    private final Color BORDER_COLOR = new Color(229, 231, 235); // Gray-200

    public AttendanceManagementPanel() {
        attendanceService = new AttendanceService();
        employeeService = new EmployeeService(); // Initialize employeeService here
        initComponents();
        styleComponents();
        loadAttendanceData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(BACKGROUND_COLOR);

        // Initialize fields
        employeeIdField = new JPlaceholderTextField("Employee ID");
        dateField = new JPlaceholderTextField("Date");
        timeInField = new JPlaceholderTextField("Time In");
        timeOutField = new JPlaceholderTextField("Time Out");
        searchField = new JPlaceholderTextField("Search");

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

        // Style components
        styleComponents();
    }

    private Attendance getAttendanceFromForm() throws Exception {
        Attendance attendance = new Attendance();

        String selectedEmployeeName = (String) employeeComboBox.getSelectedItem();
        Employee selectedEmployee = employeeService.getEmployeeByName(selectedEmployeeName);
        if (selectedEmployee == null) {
            throw new Exception("Employee not found.");
        }

        attendance.setEmployeeId(selectedEmployee.getEmployeeId().intValue());
        attendance.setDate(Date.valueOf(dateField.getText()));
        attendance.setTimeIn(Time.valueOf(timeInField.getText()));
        attendance.setTimeOut(timeOutField.getText().isEmpty() ? null : Time.valueOf(timeOutField.getText()));

        String status = (String) statusComboBox.getSelectedItem();
        if (status.length() > 50) { // Adjust the size check based on your database schema
            throw new Exception("Status value is too long.");
        }
        attendance.setStatus(status);

        return attendance;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BACKGROUND_COLOR);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20),
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR),
                        "Registro de Frequência",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Segoe UI", Font.BOLD, 14))));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Initialize form fields
        employeeComboBox = new JComboBox<>();
        loadEmployeeNames();
        dateField = new JPlaceholderTextField("AAAA-MM-DD");
        timeInField = new JPlaceholderTextField("HH:MM:SS");
        timeOutField = new JPlaceholderTextField("HH:MM:SS");
        statusComboBox = new JComboBox<>(new String[] { "Presente", "Ausente", "Atrasado", "Licença" });

        // Add fields to form
        addFieldToForm(formPanel, "Nome do Funcionário:", employeeComboBox, gbc, 0);
        addFieldToForm(formPanel, "Data (AAAA-MM-DD):", dateField, gbc, 1);
        addFieldToForm(formPanel, "Hora de Entrada (HH:MM:SS):", timeInField, gbc, 2);
        addFieldToForm(formPanel, "Hora de Saída (HH:MM:SS):", timeOutField, gbc, 3);
        addFieldToForm(formPanel, "Status:", statusComboBox, gbc, 4);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        recordButton = createStyledButton("Registrar Frequência", PRIMARY_COLOR);
        updateButton = createStyledButton("Atualizar", WARNING_COLOR);
        deleteButton = createStyledButton("Excluir", DANGER_COLOR);
        clearButton = createStyledButton("Limpar Campos", SECONDARY_COLOR);

        buttonPanel.add(recordButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        return formPanel;
    }

    private void loadEmployeeNames() {
        List<Employee> employees = employeeService.getAllEmployees();
        for (Employee employee : employees) {
            employeeComboBox.addItem(employee.getFullName());
        }
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
        searchField = new JPlaceholderTextField("Buscar frequência...");
        searchButton = createStyledButton("Buscar", PRIMARY_COLOR);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Create table
        tableModel = new DefaultTableModel(
                new Object[] { "ID", "Nome", "Data", "Hora de Entrada", "Hora de Saída", "Status" },
                0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        attendanceTable = new JTable(tableModel);
        attendanceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        attendanceTable.getTableHeader().setBackground(PRIMARY_COLOR);
        attendanceTable.getTableHeader().setForeground(Color.WHITE);
        attendanceTable.setRowHeight(25);
        attendanceTable.setGridColor(BORDER_COLOR);

        JScrollPane tableScrollPane = new JScrollPane(attendanceTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Load attendance data into the table
        loadAttendanceData();

        return tablePanel;
    }

    private void setupActionListeners() {
        recordButton.addActionListener(this::recordAttendanceActionPerformed);
        updateButton.addActionListener(this::updateAttendanceActionPerformed);
        deleteButton.addActionListener(this::deleteAttendanceActionPerformed);
        clearButton.addActionListener(e -> clearFields());
        searchButton.addActionListener(this::searchAttendanceActionPerformed);

        attendanceTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && attendanceTable.getSelectedRow() != -1) {
                loadAttendanceToForm(attendanceTable.getSelectedRow());
            }
        });

        employeeComboBox.addActionListener(e -> {
            String selectedEmployeeName = (String) employeeComboBox.getSelectedItem();
            Employee selectedEmployee = employeeService.getEmployeeByName(selectedEmployeeName);
            if (selectedEmployee != null) {
                employeeIdField.setText(String.valueOf(selectedEmployee.getEmployeeId()));
            }
        });
    }

    private void recordAttendanceActionPerformed(ActionEvent e) {
        try {
            Attendance attendance = getAttendanceFromForm();
            if (attendanceService.recordAttendance(attendance)) {
                JOptionPane.showMessageDialog(this, "Frequência registrada com sucesso!");
                addAttendanceToTable(attendance);
                clearFields();
                loadAttendanceData();
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao registrar frequência.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao registrar: " + ex.getMessage());
        }
    }

    private void updateAttendanceActionPerformed(ActionEvent e) {
        try {
            int selectedRow = attendanceTable.getSelectedRow();
            if (selectedRow != -1) {
                Attendance attendance = getAttendanceFromForm();
                attendance.setAttendanceId((Integer) tableModel.getValueAt(selectedRow, 0)); // Alterado para Integer
                if (attendanceService.updateAttendance(attendance)) {
                    JOptionPane.showMessageDialog(this, "Frequência atualizada com sucesso!");
                    loadAttendanceData();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Falha ao atualizar frequência.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma frequência para atualizar.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + ex.getMessage());
        }
    }

    private void deleteAttendanceActionPerformed(ActionEvent e) {
        int selectedRow = attendanceTable.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente excluir esta frequência?",
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                int attendanceId = (Integer) tableModel.getValueAt(selectedRow, 0); // Alterado para Integer
                if (attendanceService.deleteAttendance(attendanceId)) {
                    JOptionPane.showMessageDialog(this, "Frequência excluída com sucesso!");
                    loadAttendanceData();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Falha ao excluir frequência.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma frequência para excluir.");
        }
    }

    private void searchAttendanceActionPerformed(ActionEvent e) {
        String searchTerm = searchField.getText();
        tableModel.setRowCount(0);
        for (Attendance attendance : attendanceService.searchAttendances(searchTerm)) {
            addAttendanceToTable(attendance);
        }
    }

    private void loadAttendanceToForm(int row) {
        try {
            // Obter o ID da frequência da tabela
            Integer attendanceId = (Integer) tableModel.getValueAt(row, 0);

            // Buscar a frequência completa do banco de dados
            Attendance attendance = attendanceService.getAttendanceById(attendanceId);

            if (attendance != null) {
                // Carregar o nome do funcionário no ComboBox
                Employee employee = employeeService.getEmployeeById(attendance.getEmployeeId());
                if (employee != null) {
                    employeeComboBox.setSelectedItem(employee.getFullName());
                }

                // Formatar e carregar a data
                if (attendance.getDate() != null) {
                    dateField.setText(attendance.getDate().toString());
                    dateField.setForeground(Color.BLACK);
                }

                // Formatar e carregar hora de entrada
                if (attendance.getTimeIn() != null) {
                    timeInField.setText(attendance.getTimeIn().toString());
                    timeInField.setForeground(Color.BLACK);
                }

                // Formatar e carregar hora de saída
                if (attendance.getTimeOut() != null) {
                    timeOutField.setText(attendance.getTimeOut().toString());
                    timeOutField.setForeground(Color.BLACK);
                } else {
                    timeOutField.setText("");
                }

                // Carregar status
                if (attendance.getStatus() != null) {
                    statusComboBox.setSelectedItem(attendance.getStatus());
                }

                // Habilitar botões de edição e exclusão
                updateButton.setEnabled(true);
                deleteButton.setEnabled(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar dados: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            clearFields();
        }
    }

    // Método auxiliar para formatar Time para String
    private String formatTime(Time time) {
        if (time == null)
            return "";
        return String.format("%02d:%02d:%02d",
                time.getHours(),
                time.getMinutes(),
                time.getSeconds());
    }

    private void loadAttendanceData() {
        tableModel.setRowCount(0); // Limpa os dados existentes na tabela
        for (Attendance attendance : attendanceService.getAllAttendances()) {
            addAttendanceToTable(attendance);
        }
    }

    private void addAttendanceToTable(Attendance attendance) {
        String employeeName = employeeService.getEmployeeById(attendance.getEmployeeId()).getFullName();
        tableModel.addRow(new Object[] {
                attendance.getAttendanceId(),
                employeeName,
                attendance.getDate(),
                attendance.getTimeIn(),
                attendance.getTimeOut(),
                attendance.getStatus()
        });
    }

    private void clearFields() {
        employeeComboBox.setSelectedIndex(0);
        dateField.setText("");
        timeInField.setText("");
        timeOutField.setText("");
        statusComboBox.setSelectedIndex(0);
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
        styleTextField(employeeIdField);
        styleTextField(dateField);
        styleTextField(timeInField);
        styleTextField(timeOutField);
        styleTextField(searchField);
    }

    private void styleTextField(JComponent field) {
        if (field != null) {
            field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            field.setForeground(TEXT_COLOR);
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        }
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