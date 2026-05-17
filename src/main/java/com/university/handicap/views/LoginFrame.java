package com.university.handicap.views;

import com.university.handicap.controllers.AuthController;
import com.university.handicap.models.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final AuthController authController;
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame() {
        this.authController = new AuthController();

        setTitle("Connexion");
        setSize(900, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(850, 520));

        JPanel root = new JPanel(new GridLayout(1, 2));
        root.setBackground(AppTheme.BACKGROUND);

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(AppTheme.PRIMARY_DARK);

        JLabel appTitle = new JLabel("<html><center>Gestion<br>Université Handicap</center></html>");
        appTitle.setFont(new Font("SansSerif", Font.BOLD, 34));
        appTitle.setForeground(Color.WHITE);
        appTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel appSubtitle = new JLabel("<html><center>Demandes, réclamations, profils et statistiques</center></html>");
        appSubtitle.setFont(AppTheme.NORMAL_FONT);
        appSubtitle.setForeground(new Color(219, 234, 254));
        appSubtitle.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel brandBox = new JPanel(new GridLayout(2, 1, 0, 20));
        brandBox.setOpaque(false);
        brandBox.add(appTitle);
        brandBox.add(appSubtitle);
        leftPanel.add(brandBox);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(AppTheme.BACKGROUND);

        JPanel loginCard = AppTheme.cardPanel();
        loginCard.setLayout(new GridBagLayout());
        loginCard.setPreferredSize(new Dimension(360, 330));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;

        JLabel title = AppTheme.title("Connexion");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        loginCard.add(title, c);

        c.gridy++;
        JLabel instruction = new JLabel("Veuillez saisir vos identifiants");
        instruction.setForeground(AppTheme.MUTED);
        instruction.setHorizontalAlignment(SwingConstants.CENTER);
        loginCard.add(instruction, c);

        c.gridwidth = 1;
        c.gridy++;
        c.gridx = 0;
        loginCard.add(new JLabel("Email :"), c);

        c.gridx = 1;
        emailField = new JTextField();
        loginCard.add(emailField, c);

        c.gridy++;
        c.gridx = 0;
        loginCard.add(new JLabel("Mot de passe :"), c);

        c.gridx = 1;
        passwordField = new JPasswordField();
        loginCard.add(passwordField, c);

        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 2;
        JButton loginButton = AppTheme.primaryButton("Se connecter");
        loginCard.add(loginButton, c);

        c.gridy++;
        JLabel hint = new JLabel("Utilisez les comptes de test fournis dans la base de données.");
        hint.setFont(AppTheme.SMALL_FONT);
        hint.setForeground(AppTheme.MUTED);
        hint.setHorizontalAlignment(SwingConstants.CENTER);
        loginCard.add(hint, c);

        rightPanel.add(loginCard);

        root.add(leftPanel);
        root.add(rightPanel);

        add(root);

        loginButton.addActionListener(e -> login());
        getRootPane().setDefaultButton(loginButton);
    }

    private void login() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir l'email et le mot de passe.");
            return;
        }

        try {
            User user = authController.login(email, password);
            new MainFrame(user, authController).setVisible(true);
            dispose();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Email ou mot de passe incorrect.");
        }
    }
}
