package main.java.com.rh.ui;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author JoelDeAriovaldo
 */

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import main.java.com.rh.service.ReportService;
import main.java.com.rh.model.Report; // Certifique-se de importar a classe Report
import java.sql.Date;

public class ReportGenerationPanel extends JPanel {
    private JTextField startDateField, endDateField, searchField;
    private JComboBox<String> reportTypeComboBox;
    private JButton generateButton, updateButton, deleteButton, clearButton, searchButton;
    private ReportService reportService;
    private DefaultTableModel tableModel;
    private JTable reportTable;

    // Modern color palette
    private final Color PRIMARY_COLOR = new Color(79, 70, 229); // Indigo-600
    private final Color SECONDARY_COLOR = new Color(99, 102, 241); // Indigo-500
    private final Color DANGER_COLOR = new Color(220, 38, 38); // Red-600
    private final Color WARNING_COLOR = new Color(234, 179, 8); // Yellow-500
    private final Color BACKGROUND_COLOR = new Color(249, 250, 251); // Gray-50
    private final Color TEXT_COLOR = new Color(17, 24, 39); // Gray-900
    private final Color BORDER_COLOR = new Color(229, 231, 235); // Gray-200

    public ReportGenerationPanel() {
        reportService = new ReportService();
        initComponents();
        styleComponents();
        loadReportData();
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
                        "Geração de Relatórios",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Segoe UI", Font.BOLD, 14))));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Initialize form fields
        startDateField = new JPlaceholderTextField("AAAA-MM-DD");
        endDateField = new JPlaceholderTextField("AAAA-MM-DD");
        reportTypeComboBox = new JComboBox<>(new String[] { "Frequência", "Desempenho", "Horas Extras" });

        // Add fields to form
        addFieldToForm(formPanel, "Data de Início (AAAA-MM-DD):", startDateField, gbc, 0);
        addFieldToForm(formPanel, "Data de Fim (AAAA-MM-DD):", endDateField, gbc, 1);
        addFieldToForm(formPanel, "Tipo de Relatório:", reportTypeComboBox, gbc, 2);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        generateButton = createStyledButton("Gerar Relatório", PRIMARY_COLOR);
        updateButton = createStyledButton("Atualizar", WARNING_COLOR);
        deleteButton = createStyledButton("Excluir", DANGER_COLOR);
        clearButton = createStyledButton("Limpar Campos", SECONDARY_COLOR);

        buttonPanel.add(generateButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        gbc.gridy = 3;
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
        searchField = new JPlaceholderTextField("Buscar relatório...");
        searchButton = createStyledButton("Buscar", PRIMARY_COLOR);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Create table
        tableModel = new DefaultTableModel(
                new Object[] { "ID Relatório", "Data de Início", "Data de Fim", "Tipo de Relatório" },
                0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        reportTable = new JTable(tableModel);
        reportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        reportTable.getTableHeader().setBackground(PRIMARY_COLOR);
        reportTable.getTableHeader().setForeground(Color.WHITE);
        reportTable.setRowHeight(25);
        reportTable.setGridColor(BORDER_COLOR);

        JScrollPane tableScrollPane = new JScrollPane(reportTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private void setupActionListeners() {
        generateButton.addActionListener(this::generateReportActionPerformed);
        updateButton.addActionListener(this::updateReportActionPerformed);
        deleteButton.addActionListener(this::deleteReportActionPerformed);
        clearButton.addActionListener(e -> clearFields());
        searchButton.addActionListener(this::searchReportActionPerformed);

        reportTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && reportTable.getSelectedRow() != -1) {
                loadReportToForm(reportTable.getSelectedRow());
            }
        });
    }

    private void generateReportActionPerformed(ActionEvent e) {
        try {
            Date startDate = Date.valueOf(startDateField.getText());
            Date endDate = Date.valueOf(endDateField.getText());
            String reportType = (String) reportTypeComboBox.getSelectedItem();

            // Validate the length of reportType
            if (reportType.length() > 50) {
                JOptionPane.showMessageDialog(this, "O tipo de relatório não pode exceder 50 caracteres.");
                return;
            }

            Report report = new Report();
            report.setStartDate(startDate);
            report.setEndDate(endDate);
            report.setReportType(reportType);
            // Adicionar campos obrigatórios
            report.setGeneratedBy(1); // ID do usuário atual ou valor padrão
            report.setFilePath("reports/" + reportType.toLowerCase() + "_" + System.currentTimeMillis() + ".pdf"); // Caminho
                                                                                                                   // do
                                                                                                                   // arquivo

            if (reportService.createReport(report)) {
                JOptionPane.showMessageDialog(this, "Relatório gerado com sucesso!");
                loadReportData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao gerar relatório.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar relatório: " + ex.getMessage());
            ex.printStackTrace(); // Adicione isso para debug
        }
    }

    private void updateReportActionPerformed(ActionEvent e) {
        try {
            int selectedRow = reportTable.getSelectedRow();
            if (selectedRow != -1) {
                Date startDate = Date.valueOf(startDateField.getText());
                Date endDate = Date.valueOf(endDateField.getText());
                String reportType = (String) reportTypeComboBox.getSelectedItem();
                Long reportId = (Long) tableModel.getValueAt(selectedRow, 0);

                Report report = new Report();
                report.setReportId(reportId);
                report.setStartDate(startDate);
                report.setEndDate(endDate);
                report.setReportType(reportType);
                report.setGeneratedBy(1); // ID do usuário atual ou valor padrão
                report.setFilePath("reports/" + reportType.toLowerCase() + "_" + System.currentTimeMillis() + ".pdf");

                if (reportService.updateReport(report.getReportId(), report.getStartDate(), report.getEndDate(),
                        report.getReportType())) {
                    JOptionPane.showMessageDialog(this, "Relatório atualizado com sucesso!");
                    loadReportData();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Falha ao atualizar relatório.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um relatório para atualizar.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + ex.getMessage());
            ex.printStackTrace(); // Adicione isso para debug
        }
    }

    private void deleteReportActionPerformed(ActionEvent e) {
        int selectedRow = reportTable.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente excluir este relatório?",
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                Long reportId = (Long) tableModel.getValueAt(selectedRow, 0);
                if (reportService.deleteReport(reportId)) {
                    JOptionPane.showMessageDialog(this, "Relatório excluído com sucesso!");
                    loadReportData();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Falha ao excluir relatório.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um relatório para excluir.");
        }
    }

    private void searchReportActionPerformed(ActionEvent e) {
        String searchTerm = searchField.getText();
        tableModel.setRowCount(0);
        for (Report report : reportService.searchReports(searchTerm)) {
            addReportToTable(report);
        }
    }

    private void loadReportToForm(int row) {
        startDateField.setText(tableModel.getValueAt(row, 1).toString());
        endDateField.setText(tableModel.getValueAt(row, 2).toString());
        reportTypeComboBox.setSelectedItem(tableModel.getValueAt(row, 3).toString());
    }

    private void loadReportData() {
        tableModel.setRowCount(0);
        for (Report report : reportService.getAllReports()) {
            addReportToTable(report);
        }
    }

    private void addReportToTable(Report report) {
        tableModel.addRow(new Object[] {
                report.getReportId(),
                report.getStartDate(),
                report.getEndDate(),
                report.getReportType()
        });
    }

    private void clearFields() {
        startDateField.setText("");
        endDateField.setText("");
        reportTypeComboBox.setSelectedIndex(0);
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
        styleTextField(startDateField);
        styleTextField(endDateField);
    }

    private void styleTextField(JComponent field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
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