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

public class DashboardPanel extends JPanel {
    // Cores do tema
    private final Color PRIMARY_COLOR = new Color(66, 99, 235); // Azul moderno
    private final Color SECONDARY_COLOR = new Color(94, 129, 244); // Azul claro
    private final Color BACKGROUND_COLOR = new Color(249, 250, 251); // Gray-50
    private final Color CARD_BACKGROUND = Color.WHITE;
    private final Color TEXT_COLOR = new Color(17, 24, 39); // Gray-900
    private final Color BORDER_COLOR = new Color(229, 231, 235); // Gray-200
    private final Color SUCCESS_COLOR = new Color(34, 197, 94); // Green-500
    private final Color WARNING_COLOR = new Color(234, 179, 8); // Yellow-500
    private final Color DANGER_COLOR = new Color(239, 68, 68); // Red-500
    private final Color INFO_COLOR = new Color(59, 130, 246); // Blue-500

    private JPanel cardsPanel;
    private JPanel chartPanel;
    private JPanel actionsPanel;
    private JComboBox<String> periodSelector;

    public DashboardPanel() {
        initComponents();
        styleComponents();
        loadDummyData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(20, 20));
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Painel superior com filtros e ações
        createTopPanel();

        // Painel de cards informativos
        createCardsPanel();

        // Painel de gráficos
        createChartPanel();

        // Painel de ações rápidas
        createActionsPanel();
    }

    private void createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout(15, 0));
        topPanel.setBackground(BACKGROUND_COLOR);

        // Painel de filtros
        JPanel filtersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        filtersPanel.setBackground(BACKGROUND_COLOR);

        periodSelector = new JComboBox<>(new String[] {
                "Hoje", "Esta Semana", "Este Mês", "Este Ano"
        });

        filtersPanel.add(new JLabel("Período:"));
        filtersPanel.add(periodSelector);

        // Botão de atualização
        JButton refreshButton = createStyledButton("Atualizar Dashboard", PRIMARY_COLOR);

        topPanel.add(filtersPanel, BorderLayout.WEST);
        topPanel.add(refreshButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
    }

    private void createCardsPanel() {
        cardsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        cardsPanel.setBackground(BACKGROUND_COLOR);

        // Cards informativos
        cardsPanel.add(createInfoCard("Funcionários Ativos", "157", "↑ 12% vs. mês anterior", SUCCESS_COLOR));
        cardsPanel.add(createInfoCard("Horas Trabalhadas", "3.450", "Meta: 4.000 horas", INFO_COLOR));
        cardsPanel.add(createInfoCard("Faltas", "23", "↑ 5% vs. mês anterior", WARNING_COLOR));
        cardsPanel.add(createInfoCard("Horas Extras", "245", "↓ 8% vs. mês anterior", DANGER_COLOR));

        add(cardsPanel, BorderLayout.CENTER);
    }

    private void createChartPanel() {
        chartPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        chartPanel.setBackground(BACKGROUND_COLOR);

        // Painéis para gráficos
        JPanel attendanceChart = createChartCard("Presença por Departamento", "Gráfico de presença");
        JPanel overtimeChart = createChartCard("Horas Extras por Setor", "Gráfico de horas extras");

        chartPanel.add(attendanceChart);
        chartPanel.add(overtimeChart);

        add(chartPanel, BorderLayout.SOUTH);
    }

    private void createActionsPanel() {
        actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionsPanel.setBackground(BACKGROUND_COLOR);

        JButton exportButton = createStyledButton("Exportar Relatório", PRIMARY_COLOR);
        JButton printButton = createStyledButton("Imprimir Dashboard", SECONDARY_COLOR);

        actionsPanel.add(exportButton);
        actionsPanel.add(printButton);

        add(actionsPanel, BorderLayout.SOUTH);
    }

    private JPanel createInfoCard(String title, String value, String subtitle, Color accentColor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        // Título
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(TEXT_COLOR);

        // Valor principal
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(accentColor);

        // Subtítulo
        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(107, 114, 128)); // Gray-500

        // Alinhamento
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(valueLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(subtitleLabel);

        // Efeito hover
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(249, 250, 251));
            }

            public void mouseExited(MouseEvent e) {
                card.setBackground(CARD_BACKGROUND);
            }
        });

        return card;
    }

    private JPanel createChartCard(String title, String placeholder) {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(CARD_BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        // Título do gráfico
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(TEXT_COLOR);

        // Área do gráfico (placeholder)
        JPanel chartArea = new JPanel();
        chartArea.setBackground(new Color(243, 244, 246));
        chartArea.setPreferredSize(new Dimension(0, 200));
        JLabel placeholderLabel = new JLabel(placeholder, SwingConstants.CENTER);
        chartArea.add(placeholderLabel);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(chartArea, BorderLayout.CENTER);

        return card;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2d.setColor(color.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(color.brighter());
                } else {
                    g2d.setColor(color);
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

        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(200, 48)); // Aumenta o tamanho do botão
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void styleComponents() {
        // Estilizar componentes do seletor de período
        periodSelector.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        periodSelector.setBackground(Color.WHITE);
    }

    private void loadDummyData() {
        // Aqui você pode adicionar código para carregar dados reais do seu sistema
        // Por enquanto, os dados estão hardcoded nos componentes
    }

    public void updateDashboard(int hoursWorked, int activeEmployees, int absences, int overtime) {
        // Método para atualizar os dados do dashboard
        // Implementar lógica de atualização real aqui
    }
}