package com.university.handicap.views;

import com.university.handicap.controllers.AuthController;
import com.university.handicap.models.Role;
import com.university.handicap.models.User;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final User currentUser;
    private final AuthController authController;
    private final JPanel contentPanel;
    private final CardLayout cardLayout;
    private JLabel pageTitle;

    private DemandePanel demandePanel;
    private ReclamationPanel reclamationPanel;

    public MainFrame(User currentUser, AuthController authController) {
        this.currentUser = currentUser;
        this.authController = authController;

        setTitle("Application de gestion universitaire");
        setSize(1100, 680);
        setMinimumSize(new Dimension(950, 560));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(AppTheme.BACKGROUND);

        JPanel sidebar = createSidebar();
        JPanel header = createHeader();

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(AppTheme.BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        buildPages();

        root.add(sidebar, BorderLayout.WEST);
        root.add(header, BorderLayout.NORTH);
        root.add(contentPanel, BorderLayout.CENTER);

        add(root);
    }

    private void buildPages() {
        demandePanel = new DemandePanel(currentUser.getRole() == Role.ADMIN, currentUser);
        reclamationPanel = new ReclamationPanel(currentUser.getRole() == Role.ADMIN, currentUser);

        if (currentUser.getRole() == Role.ADMIN) {
            DashboardPanel dashboardPanel = new DashboardPanel(new DashboardPanel.DashboardNavigation() {
                @Override
                public void showDemandesByStatut(String statut) {
                    showPage("demandes", "Gestion des demandes");
                    demandePanel.showByStatut(statut);
                }

                @Override
                public void showDemandesByType(String type) {
                    showPage("demandes", "Gestion des demandes");
                    demandePanel.showByType(type);
                }

                @Override
                public void showReclamationsByStatut(String statut) {
                    showPage("reclamations", "Gestion des réclamations");
                    reclamationPanel.showByStatut(statut);
                }
            });

            contentPanel.add(dashboardPanel, "dashboard");
            contentPanel.add(demandePanel, "demandes");
            contentPanel.add(reclamationPanel, "reclamations");
            contentPanel.add(new ProfilePanel(currentUser), "profil");

            cardLayout.show(contentPanel, "dashboard");
        } else {
            contentPanel.add(demandePanel, "demandes");
            contentPanel.add(reclamationPanel, "reclamations");
            contentPanel.add(new ProfilePanel(currentUser), "profil");

            cardLayout.show(contentPanel, "demandes");
        }
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setBackground(AppTheme.PRIMARY_DARK);
        sidebar.setPreferredSize(new Dimension(230, 0));

        JLabel logo = new JLabel("<html><center>Gestion<br>Université</center></html>");
        logo.setFont(new Font("SansSerif", Font.BOLD, 24));
        logo.setForeground(Color.WHITE);
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));

        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setOpaque(false);
        menu.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        if (currentUser.getRole() == Role.ADMIN) {
            JButton dashboardButton = AppTheme.sidebarButton("Dashboard");
            JButton demandesButton = AppTheme.sidebarButton("Demandes");
            JButton reclamationsButton = AppTheme.sidebarButton("Réclamations");
            JButton profileButton = AppTheme.sidebarButton("Profil");

            dashboardButton.addActionListener(e -> showPage("dashboard", "Dashboard"));
            demandesButton.addActionListener(e -> showPage("demandes", "Gestion des demandes"));
            reclamationsButton.addActionListener(e -> showPage("reclamations", "Gestion des réclamations"));
            profileButton.addActionListener(e -> showPage("profil", "Profil"));

            addMenuButton(menu, dashboardButton);
            addMenuButton(menu, demandesButton);
            addMenuButton(menu, reclamationsButton);
            addMenuButton(menu, profileButton);
        } else {
            JButton demandesButton = AppTheme.sidebarButton("Mes demandes");
            JButton reclamationsButton = AppTheme.sidebarButton("Mes réclamations");
            JButton profileButton = AppTheme.sidebarButton("Mon profil");

            demandesButton.addActionListener(e -> showPage("demandes", "Mes demandes"));
            reclamationsButton.addActionListener(e -> showPage("reclamations", "Mes réclamations"));
            profileButton.addActionListener(e -> showPage("profil", "Mon profil"));

            addMenuButton(menu, demandesButton);
            addMenuButton(menu, reclamationsButton);
            addMenuButton(menu, profileButton);
        }

        JButton logoutButton = AppTheme.sidebarButton("Déconnexion");
        logoutButton.addActionListener(e -> logout());

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 15, 20, 15));
        bottom.add(logoutButton, BorderLayout.CENTER);

        sidebar.add(logo, BorderLayout.NORTH);
        sidebar.add(menu, BorderLayout.CENTER);
        sidebar.add(bottom, BorderLayout.SOUTH);

        return sidebar;
    }


    private void addMenuButton(JPanel menu, JButton button) {
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        menu.add(button);
        menu.add(Box.createVerticalStrut(10));
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, AppTheme.BORDER),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        String firstPage = currentUser.getRole() == Role.ADMIN ? "Dashboard" : "Mes demandes";
        pageTitle = AppTheme.title(firstPage);

        JLabel userInfo = new JLabel(currentUser.getNom() + " " + currentUser.getPrenom() + " | " + currentUser.getRole());
        userInfo.setForeground(AppTheme.MUTED);

        header.add(pageTitle, BorderLayout.WEST);
        header.add(userInfo, BorderLayout.EAST);

        return header;
    }

    private void showPage(String key, String title) {
        pageTitle.setText(title);
        cardLayout.show(contentPanel, key);
    }

    private void logout() {
        authController.logout();
        new LoginFrame().setVisible(true);
        dispose();
    }
}
