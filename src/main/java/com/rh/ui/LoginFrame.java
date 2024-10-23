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
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import main.java.com.rh.service.UserService;
import main.java.com.rh.model.User;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private UserService userService;

    // Paleta de cores moderna
    private final Color PRIMARY_COLOR = new Color(79, 70, 229); // Indigo-600
    private final Color SECONDARY_COLOR = new Color(99, 102, 241); // Indigo-500
    private final Color BACKGROUND_COLOR = new Color(249, 250, 251); // Gray-50
    private final Color TEXT_COLOR = new Color(17, 24, 39); // Gray-900
    private final Color BORDER_COLOR = new Color(229, 231, 235); // Gray-200

    public LoginFrame() {
        userService = new UserService();
        initializeFrame();
        createComponents();
        setupLayout();
        addEventListeners();
        styleComponents();
    }

    private void initializeFrame() {
        setTitle("RH System");
        setSize(480, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BACKGROUND_COLOR);
    }

    private void createComponents() {
        // Campos de entrada com placeholder texts
        emailField = new JPlaceholderTextField("Digite seu e-mail");
        passwordField = new JPlaceholderPasswordField("Digite sua senha");
        loginButton = new JButton("Entrar");
        registerButton = new JButton("Criar nova conta");
    }

    private void setupLayout() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Logo or icon loading with proper error handling
        JLabel logoLabel = new JLabel();
        try {
            URL logoURL = getClass().getResource("/resources/logo.png");
            if (logoURL == null) {
                logoURL = getClass().getResource("/logo.png");
            }
            if (logoURL == null) {
                logoURL = getClass().getClassLoader().getResource("resources/logo.png");
            }

            if (logoURL != null) {
                ImageIcon logoIcon = new ImageIcon(logoURL);
                logoLabel.setIcon(logoIcon);
            } else {
                System.err.println("Logo file not found. Please ensure logo.png exists in the resources directory.");
                logoLabel.setText("RH System");
                logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            }
        } catch (Exception e) {
            System.err.println("Error loading logo: " + e.getMessage());
            logoLabel.setText("RH System");
            logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        }

        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Título e subtítulo
        JLabel titleLabel = new JLabel("Bem-vindo de volta");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitleLabel = new JLabel("Entre com suas credenciais para acessar");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(107, 114, 128)); // Gray-500
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Configuração dos campos
        Dimension fieldSize = new Dimension(380, 45);
        emailField.setPreferredSize(fieldSize);
        passwordField.setPreferredSize(fieldSize);

        // Configuração dos botões
        Dimension buttonSize = new Dimension(380, 48);
        loginButton.setPreferredSize(buttonSize);
        registerButton.setPreferredSize(buttonSize);

        // Adicionar componentes ao painel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(logoLabel, gbc);

        gbc.gridy++;
        mainPanel.add(titleLabel, gbc);

        gbc.gridy++;
        mainPanel.add(subtitleLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        mainPanel.add(createFieldPanel("E-mail", emailField), gbc);

        gbc.gridy++;
        mainPanel.add(createFieldPanel("Senha", passwordField), gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;
        mainPanel.add(loginButton, gbc);

        gbc.gridy++;
        mainPanel.add(registerButton, gbc);

        add(mainPanel);
    }

    private JPanel createFieldPanel(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(TEXT_COLOR);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    private void styleComponents() {
        // Estilo dos campos
        styleTextField(emailField);
        styleTextField(passwordField);

        // Estilo dos botões
        styleLoginButton(loginButton);
        styleRegisterButton(registerButton);
    }

    private void styleTextField(JComponent field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
    }

    private void styleLoginButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_COLOR);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(SECONDARY_COLOR);
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
    }

    private void styleRegisterButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setForeground(PRIMARY_COLOR);
        button.setBackground(BACKGROUND_COLOR);
        button.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1, true));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(238, 242, 255)); // Indigo-50
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(BACKGROUND_COLOR);
            }
        });
    }

    private void addEventListeners() {
        loginButton.addActionListener(this::loginActionPerformed);
        registerButton.addActionListener(this::registerActionPerformed);
        getRootPane().setDefaultButton(loginButton);
    }

    private void loginActionPerformed(ActionEvent e) {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        // Remove placeholders para validação
        if (email.equals("Digite seu e-mail"))
            email = "";
        if (password.equals("Digite sua senha"))
            password = "";

        if (email.isEmpty() || password.isEmpty()) {
            showError("Por favor, preencha todos os campos.");
            return;
        }

        User user = userService.authenticateUser(email, password);
        if (user != null) {
            showSuccess("Login realizado com sucesso!");
            new MainFrame(user.getUsername()).setVisible(true); // Pass the username to MainFrame
            this.dispose();
        } else {
            showError("E-mail ou senha inválidos.");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Erro",
                JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void registerActionPerformed(ActionEvent e) {
        new RegisterFrame().setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
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

    // Classe auxiliar para campo de senha com placeholder
    private static class JPlaceholderPasswordField extends JPasswordField {
        private String placeholder;

        public JPlaceholderPasswordField(String placeholder) {
            this.placeholder = placeholder;

            addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (String.valueOf(getPassword()).equals(placeholder)) {
                        setText("");
                        setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (String.valueOf(getPassword()).isEmpty()) {
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