package com.university.handicap.views;

import com.university.handicap.controllers.AuthController;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private final AuthController authController;

    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField telephoneField;
    private JTextField typeHandicapField;
    private JTextField filiereField;
    private JTextField niveauField;

    public RegisterFrame() {
        this.authController = new AuthController();

        setTitle("Créer un compte");
        setSize(650, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(620, 560));

        JPanel root = new JPanel(new BorderLayout(15, 15));
        root.setBackground(AppTheme.BACKGROUND);
        root.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel card = AppTheme.cardPanel();
        card.setLayout(new BorderLayout(15, 15));

        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setOpaque(false);
        header.add(AppTheme.title("Créer un compte étudiant"));
        header.add(AppTheme.muted("Ce formulaire crée un compte PERSONNE_HANDICAP."));

        card.add(header, BorderLayout.NORTH);
        card.add(buildForm(), BorderLayout.CENTER);
        card.add(buildButtons(), BorderLayout.SOUTH);

        root.add(card, BorderLayout.CENTER);
        add(root);
    }

    private JPanel buildForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        nomField = new JTextField();
        prenomField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();
        telephoneField = new JTextField();
        typeHandicapField = new JTextField();
        filiereField = new JTextField();
        niveauField = new JTextField();

        int row = 0;
        addField(form, row++, "Nom :", nomField);
        addField(form, row++, "Prénom :", prenomField);
        addField(form, row++, "Email :", emailField);
        addField(form, row++, "Mot de passe :", passwordField);
        addField(form, row++, "Téléphone :", telephoneField);
        addField(form, row++, "Type handicap :", typeHandicapField);
        addField(form, row++, "Filière :", filiereField);
        addField(form, row++, "Niveau :", niveauField);

        return form;
    }

    private void addField(JPanel form, int row, String label, JComponent field) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(7, 7, 7, 7);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = row;
        c.weightx = 0;
        c.anchor = GridBagConstraints.WEST;
        JLabel labelComponent = new JLabel(label);
        labelComponent.setPreferredSize(new Dimension(130, 25));
        form.add(labelComponent, c);

        c.gridx = 1;
        c.weightx = 1;
        field.setPreferredSize(new Dimension(320, 32));
        form.add(field, c);
    }

    private JPanel buildButtons() {
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setOpaque(false);

        JButton backButton = AppTheme.secondaryButton("Retour");
        JButton createButton = AppTheme.primaryButton("Créer le compte");

        backButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        createButton.addActionListener(e -> createAccount());

        buttons.add(backButton);
        buttons.add(createButton);

        getRootPane().setDefaultButton(createButton);

        return buttons;
    }

    private void createAccount() {
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String telephone = telephoneField.getText().trim();
        String typeHandicap = typeHandicapField.getText().trim();
        String filiere = filiereField.getText().trim();
        String niveau = niveauField.getText().trim();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir au minimum le nom, prénom, email et mot de passe.");
            return;
        }

        boolean ok = authController.registerPersonne(
                nom, prenom, email, password,
                telephone, typeHandicap, filiere, niveau
        );

        if (ok) {
            JOptionPane.showMessageDialog(this, "Compte créé avec succès. Vous pouvez maintenant vous connecter.");
            new LoginFrame().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Impossible de créer le compte. Vérifiez si l'email existe déjà.");
        }
    }
}
