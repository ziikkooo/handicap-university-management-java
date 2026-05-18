package com.university.handicap.views;

import com.university.handicap.controllers.UserController;
import com.university.handicap.models.PersonneHandicap;
import com.university.handicap.models.Role;
import com.university.handicap.models.User;

import javax.swing.*;
import java.awt.*;

public class ProfilePanel extends JPanel {

    private final User currentUser;
    private final UserController userController;

    public ProfilePanel(User currentUser) {
        this.currentUser = currentUser;
        this.userController = new UserController();

        setLayout(new BorderLayout());
        setBackground(AppTheme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        add(buildPage(), BorderLayout.NORTH);
    }

    private JPanel buildPage() {
        JPanel page = new JPanel();
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));
        page.setOpaque(false);

        page.add(buildHeaderCard());
        page.add(Box.createVerticalStrut(18));
        page.add(buildAccountCard());

        if (currentUser.getRole() == Role.PERSONNE_HANDICAP) {
            page.add(Box.createVerticalStrut(18));
            page.add(buildStudentCard());
        }

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrapper.setOpaque(false);
        wrapper.add(page);

        return wrapper;
    }

    private JPanel buildHeaderCard() {
        JPanel card = AppTheme.cardPanel();
        card.setLayout(new BorderLayout(18, 10));
        card.setPreferredSize(new Dimension(760, 120));
        card.setMaximumSize(new Dimension(760, 120));

        JLabel avatar = new JLabel(getInitials(), SwingConstants.CENTER);
        avatar.setPreferredSize(new Dimension(78, 78));
        avatar.setOpaque(true);
        avatar.setBackground(AppTheme.PRIMARY);
        avatar.setForeground(Color.WHITE);
        avatar.setFont(new Font("SansSerif", Font.BOLD, 26));

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        JLabel name = new JLabel(currentUser.getNom() + " " + currentUser.getPrenom());
        name.setFont(new Font("SansSerif", Font.BOLD, 24));
        name.setForeground(AppTheme.TEXT);

        JLabel email = new JLabel(currentUser.getEmail());
        email.setFont(AppTheme.NORMAL_FONT);
        email.setForeground(AppTheme.MUTED);

        JLabel badge = new JLabel("  " + currentUser.getRole().toString() + "  ");
        badge.setOpaque(true);
        badge.setBackground(new Color(219, 234, 254));
        badge.setForeground(AppTheme.PRIMARY_DARK);
        badge.setFont(new Font("SansSerif", Font.BOLD, 12));
        badge.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));

        name.setAlignmentX(Component.LEFT_ALIGNMENT);
        email.setAlignmentX(Component.LEFT_ALIGNMENT);
        badge.setAlignmentX(Component.LEFT_ALIGNMENT);

        info.add(name);
        info.add(Box.createVerticalStrut(5));
        info.add(email);
        info.add(Box.createVerticalStrut(10));
        info.add(badge);

        card.add(avatar, BorderLayout.WEST);
        card.add(info, BorderLayout.CENTER);

        return card;
    }

    private JPanel buildAccountCard() {
        JPanel card = AppTheme.cardPanel();
        card.setLayout(new BorderLayout(10, 12));
        card.setPreferredSize(new Dimension(760, 230));
        card.setMaximumSize(new Dimension(760, 230));

        JLabel title = AppTheme.subtitle("Informations du compte");
        card.add(title, BorderLayout.NORTH);

        JPanel rows = new JPanel();
        rows.setLayout(new BoxLayout(rows, BoxLayout.Y_AXIS));
        rows.setOpaque(false);

        rows.add(infoRow("Nom", currentUser.getNom()));
        rows.add(infoRow("Prénom", currentUser.getPrenom()));
        rows.add(infoRow("Email", currentUser.getEmail()));
        rows.add(infoRow("Rôle", currentUser.getRole().toString()));

        card.add(rows, BorderLayout.CENTER);

        return card;
    }

    private JPanel buildStudentCard() {
        JPanel card = AppTheme.cardPanel();
        card.setLayout(new BorderLayout(10, 12));
        card.setPreferredSize(new Dimension(760, 230));
        card.setMaximumSize(new Dimension(760, 230));

        JLabel title = AppTheme.subtitle("Informations étudiant");
        card.add(title, BorderLayout.NORTH);

        JPanel rows = new JPanel();
        rows.setLayout(new BoxLayout(rows, BoxLayout.Y_AXIS));
        rows.setOpaque(false);

        PersonneHandicap profile = userController.getPersonneByUserId(currentUser.getId());

        if (profile == null) {
            rows.add(infoRow("Profil détaillé", "Aucun profil trouvé"));
        } else {
            rows.add(infoRow("Téléphone", profile.getTelephone()));
            rows.add(infoRow("Type handicap", profile.getTypeHandicap()));
            rows.add(infoRow("Filière", profile.getFiliere()));
            rows.add(infoRow("Niveau", profile.getNiveau()));
        }

        card.add(rows, BorderLayout.CENTER);

        return card;
    }

    private JPanel infoRow(String label, String value) {
        JPanel row = new JPanel(new BorderLayout(15, 0));
        row.setBackground(new Color(249, 250, 251));
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, AppTheme.BORDER),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));
        row.setMaximumSize(new Dimension(720, 48));

        JLabel key = new JLabel(label + " :");
        key.setPreferredSize(new Dimension(140, 25));
        key.setFont(new Font("SansSerif", Font.BOLD, 13));
        key.setForeground(AppTheme.MUTED);

        JLabel val = new JLabel(value == null || value.isBlank() ? "-" : value);
        val.setFont(new Font("SansSerif", Font.BOLD, 14));
        val.setForeground(AppTheme.TEXT);

        row.add(key, BorderLayout.WEST);
        row.add(val, BorderLayout.CENTER);

        return row;
    }

    private String getInitials() {
        String nom = currentUser.getNom() == null ? "" : currentUser.getNom().trim();
        String prenom = currentUser.getPrenom() == null ? "" : currentUser.getPrenom().trim();

        String first = nom.isEmpty() ? "" : nom.substring(0, 1).toUpperCase();
        String second = prenom.isEmpty() ? "" : prenom.substring(0, 1).toUpperCase();

        String initials = first + second;
        return initials.isEmpty() ? "U" : initials;
    }
}
