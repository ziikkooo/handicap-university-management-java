package com.university.handicap.views;

import com.university.handicap.controllers.DashboardController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DashboardPanel extends JPanel {

    public interface DashboardNavigation {
        void showDemandesByStatut(String statut);
        void showDemandesByType(String type);
        void showReclamationsByStatut(String statut);
    }

    private final DashboardController dashboardController;
    private final DashboardNavigation navigation;
    private JPanel statsPanel;

    public DashboardPanel(DashboardNavigation navigation) {
        this.dashboardController = new DashboardController();
        this.navigation = navigation;

        setLayout(new BorderLayout(15, 15));
        setBackground(AppTheme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        createTopPanel();
        createStatsPanel();
        loadStats();
    }

    private void createTopPanel() {
        JPanel topPanel = AppTheme.cardPanel();
        topPanel.setLayout(new GridLayout(2, 1));

        topPanel.add(AppTheme.subtitle("Tableau de bord"));
        topPanel.add(AppTheme.muted("Cliquez sur une carte pour ouvrir la liste filtrée."));

        add(topPanel, BorderLayout.NORTH);
    }

    private void createStatsPanel() {
        statsPanel = new JPanel(new GridLayout(0, 3, 15, 15));
        statsPanel.setBackground(AppTheme.BACKGROUND);

        JScrollPane scrollPane = new JScrollPane(statsPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(AppTheme.BACKGROUND);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadStats() {
        statsPanel.removeAll();

        addClickableCard("Demandes en cours",
                dashboardController.countDemandesByStatut("EN_COURS"),
                () -> navigation.showDemandesByStatut("EN_COURS"));

        addClickableCard("Demandes acceptées",
                dashboardController.countDemandesByStatut("ACCEPTEE"),
                () -> navigation.showDemandesByStatut("ACCEPTEE"));

        addClickableCard("Demandes refusées",
                dashboardController.countDemandesByStatut("REFUSEE"),
                () -> navigation.showDemandesByStatut("REFUSEE"));

        addClickableCard("Aménagement examen",
                dashboardController.countDemandesByType("AMENAGEMENT_EXAMEN"),
                () -> navigation.showDemandesByType("AMENAGEMENT_EXAMEN"));

        addClickableCard("Accessibilité",
                dashboardController.countDemandesByType("ACCESSIBILITE"),
                () -> navigation.showDemandesByType("ACCESSIBILITE"));

        addClickableCard("Accompagnement",
                dashboardController.countDemandesByType("ACCOMPAGNEMENT"),
                () -> navigation.showDemandesByType("ACCOMPAGNEMENT"));

        addClickableCard("Autre",
                dashboardController.countDemandesByType("AUTRE"),
                () -> navigation.showDemandesByType("AUTRE"));

        addClickableCard("Réclamations en cours",
                dashboardController.countReclamationsByStatut("EN_COURS"),
                () -> navigation.showReclamationsByStatut("EN_COURS"));

        addClickableCard("Réclamations traitées",
                dashboardController.countReclamationsByStatut("TRAITEE"),
                () -> navigation.showReclamationsByStatut("TRAITEE"));

        addClickableCard("Réclamations rejetées",
                dashboardController.countReclamationsByStatut("REJETEE"),
                () -> navigation.showReclamationsByStatut("REJETEE"));

        statsPanel.revalidate();
        statsPanel.repaint();
    }

    private void addClickableCard(String title, int value, Runnable action) {
        JPanel card = AppTheme.statCard(title, value);
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setToolTipText("Cliquer pour afficher les détails");

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.run();
            }
        });

        statsPanel.add(card);
    }
}
