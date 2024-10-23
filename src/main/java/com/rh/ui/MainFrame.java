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
import java.awt.geom.RoundRectangle2D;

public class MainFrame extends JFrame {
    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private JPanel headerPanel;
    private DashboardPanel dashboardPanel;
    private EmployeeRegistrationPanel employeeRegistrationPanel;
    private AttendanceManagementPanel attendanceManagementPanel;
    private ReportGenerationPanel reportGenerationPanel;

    // Paleta de cores moderna
    private final Color PRIMARY_COLOR = new Color(66, 99, 235); // Azul moderno
    private final Color SECONDARY_COLOR = new Color(94, 129, 244); // Azul claro
    private final Color SIDEBAR_BG = new Color(31, 41, 55); // Slate escuro
    private final Color CONTENT_BG = new Color(243, 244, 246); // Cinza claro
    private final Color TEXT_COLOR = new Color(17, 24, 39); // Texto escuro
    private final Color BORDER_COLOR = new Color(229, 231, 235); // Borda suave
    private final Color HOVER_COLOR = new Color(55, 65, 81); // Hover escuro
    private final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private final Font MENU_FONT = new Font("Segoe UI", Font.BOLD, 14);

    private String activeMenu = "Dashboard";
    private String username;

    public MainFrame(String username) {
        this.username = username;
        initComponents();
        styleComponents();
    }

    private void initComponents() {
        setTitle("Sistema de Gestão de RH");
        setSize(1280, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1080, 720));

        setLayout(new BorderLayout(0, 0));

        createHeaderPanel();
        createSidebarPanel();
        createContentPanel();

        // Inicializar painéis de conteúdo
        dashboardPanel = new DashboardPanel();
        employeeRegistrationPanel = new EmployeeRegistrationPanel();
        attendanceManagementPanel = new AttendanceManagementPanel();
        reportGenerationPanel = new ReportGenerationPanel();

        showDashboard();
    }

    private void createHeaderPanel() {
        headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        headerPanel.setPreferredSize(new Dimension(0, 64));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR),
                BorderFactory.createEmptyBorder(0, 20, 0, 20)));

        // Logo e título
        JPanel brandPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        brandPanel.setOpaque(false);

        JLabel logoLabel = createRoundLabel("RH", PRIMARY_COLOR, Color.WHITE, 36);
        JLabel titleLabel = new JLabel("Sistema RH");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);

        brandPanel.add(logoLabel);
        brandPanel.add(titleLabel);

        // Painel do usuário
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        userPanel.setOpaque(false);

        JButton notificationButton = createIconButton("🔔");
        JLabel userLabel = new JLabel(username);
        userLabel.setFont(MENU_FONT);
        JButton logoutButton = createStyledButton("Sair");

        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        userPanel.add(notificationButton);
        userPanel.add(userLabel);
        userPanel.add(logoutButton);

        headerPanel.add(brandPanel, BorderLayout.WEST);
        headerPanel.add(userPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);
    }

    private void createSidebarPanel() {
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(SIDEBAR_BG);
        sidebarPanel.setPreferredSize(new Dimension(260, 0));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        addMenuButton("Dashboard", e -> showDashboard());
        addMenuButton("Funcionários", e -> showEmployeeRegistration());
        addMenuButton("Frequência", e -> showAttendanceManagement());
        addMenuButton("Relatórios", e -> showReportGeneration());

        sidebarPanel.add(Box.createVerticalGlue());

        // Adicionar footer no sidebar
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footerPanel.setOpaque(false);
        footerPanel.setMaximumSize(new Dimension(260, 40));

        JLabel versionLabel = new JLabel("v1.0.0");
        versionLabel.setForeground(new Color(156, 163, 175));
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        footerPanel.add(versionLabel);
        sidebarPanel.add(footerPanel);

        add(sidebarPanel, BorderLayout.WEST);
    }

    private void createContentPanel() {
        contentPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(CONTENT_BG);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        add(contentPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2d.setColor(PRIMARY_COLOR.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(PRIMARY_COLOR.brighter());
                } else {
                    g2d.setColor(PRIMARY_COLOR);
                }

                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));

                g2d.setColor(Color.WHITE);
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2d.drawString(text, x, y);

                g2d.dispose();
            }
        };

        button.setFont(MENU_FONT);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(100, 36));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private JButton createIconButton(String icon) {
        JButton button = new JButton(icon);
        button.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void addMenuButton(String text, ActionListener listener) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (text.startsWith(activeMenu)) {
                    g2d.setColor(PRIMARY_COLOR);
                } else if (getModel().isRollover()) {
                    g2d.setColor(HOVER_COLOR);
                } else {
                    g2d.setColor(SIDEBAR_BG);
                }

                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));

                g2d.setColor(Color.WHITE);
                FontMetrics fm = g2d.getFontMetrics();
                int x = 15;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2d.drawString(text, x, y);

                g2d.dispose();
            }
        };

        button.setFont(MENU_FONT);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(230, 45));
        button.setMaximumSize(new Dimension(230, 45));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);

        button.addActionListener(listener);
        sidebarPanel.add(button);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    private JLabel createRoundLabel(String text, Color bgColor, Color textColor, int size) {
        JLabel label = new JLabel(text, SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(bgColor);
                g2d.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

                g2d.setColor(textColor);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2d.drawString(text, x, y);

                g2d.dispose();
            }
        };

        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setPreferredSize(new Dimension(size, size));
        label.setOpaque(false);

        return label;
    }

    private void showDashboard() {
        activeMenu = "📊 Dashboard";
        switchPanel(dashboardPanel, "Dashboard");
    }

    private void showEmployeeRegistration() {
        activeMenu = "👥 Funcionários";
        switchPanel(employeeRegistrationPanel, "Cadastro de Funcionários");
    }

    private void showAttendanceManagement() {
        activeMenu = "📅 Frequência";
        switchPanel(attendanceManagementPanel, "Gestão de Frequência");
    }

    private void showReportGeneration() {
        activeMenu = "📈 Relatórios";
        switchPanel(reportGenerationPanel, "Geração de Relatórios");
    }

    private void switchPanel(JPanel panel, String title) {
        contentPanel.removeAll();

        // Painel de título com breadcrumb
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);

        JLabel breadcrumbLabel = new JLabel("Home > " + title);
        breadcrumbLabel.setForeground(new Color(107, 114, 128));
        breadcrumbLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);

        titlePanel.add(breadcrumbLabel, BorderLayout.NORTH);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Painel de conteúdo com sombra
        JPanel contentWrapper = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Desenhar sombra
                int shadowSize = 4;
                for (int i = 0; i < shadowSize; i++) {
                    g2d.setColor(new Color(0, 0, 0, 10));
                    g2d.fillRoundRect(shadowSize - i, shadowSize - i,
                            getWidth() - ((shadowSize - i) * 2),
                            getHeight() - ((shadowSize - i) * 2), 10, 10);
                }

                // Desenhar fundo
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth() - shadowSize,
                        getHeight() - shadowSize, 10, 10);

                g2d.dispose();
            }
        };
        contentWrapper.setOpaque(false);
        contentWrapper.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contentWrapper.add(panel, BorderLayout.CENTER);

        // Layout final
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setOpaque(false);
        wrapperPanel.add(titlePanel, BorderLayout.NORTH);
        wrapperPanel.add(contentWrapper, BorderLayout.CENTER);

        contentPanel.add(wrapperPanel);
        contentPanel.revalidate();
        contentPanel.repaint();

        // Atualizar sidebar
        sidebarPanel.repaint();
    }

    private void styleComponents() {
        // Configurar look and feel moderno
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Customizar componentes padrão
            UIManager.put("Button.arc", 8);
            UIManager.put("Component.arc", 8);
            UIManager.put("ProgressBar.arc", 8);
            UIManager.put("TextComponent.arc", 8);

            // Fontes
            UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));
            UIManager.put("Button.font", new Font("Segoe UI", Font.PLAIN, 14));
            UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 14));
            UIManager.put("TextArea.font", new Font("Segoe UI", Font.PLAIN, 14));
            UIManager.put("ComboBox.font", new Font("Segoe UI", Font.PLAIN, 14));
            UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 14));

            // Cores
            UIManager.put("Panel.background", Color.WHITE);
            UIManager.put("TextField.background", new Color(249, 250, 251));
            UIManager.put("TextField.border", BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)));

            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Habilitar aceleração de hardware
        System.setProperty("sun.java2d.opengl", "true");

        SwingUtilities.invokeLater(() -> {
            try {
                // Definir escala para monitores HiDPI
                System.setProperty("sun.java2d.uiScale", "1.0");

                MainFrame frame = new MainFrame("Admin");
                frame.setVisible(true);

                // Adicionar animação de fade-in
                frame.setOpacity(0.0f);
                Timer timer = new Timer(10, new ActionListener() {
                    float opacity = 0.0f;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        opacity += 0.05f;
                        if (opacity > 1.0f) {
                            opacity = 1.0f;
                            ((Timer) e.getSource()).stop();
                        }
                        frame.setOpacity(opacity);
                    }
                });
                timer.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
