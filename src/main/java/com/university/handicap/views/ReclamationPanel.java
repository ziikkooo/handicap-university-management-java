package com.university.handicap.views;

import com.university.handicap.controllers.ReclamationController;
import com.university.handicap.models.HistoriqueReclamation;
import com.university.handicap.models.Reclamation;
import com.university.handicap.models.StatutReclamation;
import com.university.handicap.models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ReclamationPanel extends JPanel {

    private final boolean adminMode;
    private final User currentUser;
    private final ReclamationController reclamationController;

    private JTextField sujetField;
    private JTextArea descriptionArea;

    private JComboBox<String> filterStatusBox;
    private JComboBox<String> monthBox;
    private JTextField yearField;

    private JComboBox<StatutReclamation> statutBox;

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextArea historiqueArea;

    public ReclamationPanel(boolean adminMode, User currentUser) {
        this.adminMode = adminMode;
        this.currentUser = currentUser;
        this.reclamationController = new ReclamationController();

        setLayout(new BorderLayout(12, 12));
        setBackground(AppTheme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        if (!adminMode) {
            add(buildCreateBlock(), BorderLayout.NORTH);
        }

        add(buildListBlock(), BorderLayout.CENTER);

        loadReclamations();
    }

    private JPanel buildCreateBlock() {
        JPanel block = AppTheme.cardPanel();
        block.setLayout(new BorderLayout(10, 10));

        JLabel title = AppTheme.subtitle("Nouvelle réclamation");
        block.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        sujetField = new JTextField(35);
        descriptionArea = new JTextArea(3, 35);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JButton sendButton = AppTheme.primaryButton("Envoyer la réclamation");

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        form.add(new JLabel("Sujet :"), c);

        c.gridx = 1;
        c.weightx = 1;
        form.add(sujetField, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        form.add(new JLabel("Description :"), c);

        c.gridx = 1;
        c.weightx = 1;
        form.add(new JScrollPane(descriptionArea), c);

        c.gridx = 1;
        c.gridy = 2;
        c.anchor = GridBagConstraints.EAST;
        form.add(sendButton, c);

        sendButton.addActionListener(e -> createReclamation());

        block.add(form, BorderLayout.CENTER);
        return block;
    }

    private JPanel buildListBlock() {
        JPanel block = AppTheme.cardPanel();
        block.setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setOpaque(false);

        JLabel title = AppTheme.subtitle(adminMode ? "Gestion des réclamations" : "Mes réclamations");
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        top.add(title);
        top.add(Box.createVerticalStrut(10));
        top.add(buildFilterRow());

        block.add(top, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Sujet", "Description", "Statut", "Date"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        AppTheme.styleTable(table);
        table.getColumnModel().getColumn(3).setCellRenderer(new StatusCellRenderer());
        hideIdColumn();

        table.getSelectionModel().addListSelectionListener(this::onRowSelected);

        block.add(new JScrollPane(table), BorderLayout.CENTER);

        if (adminMode) {
            block.add(buildAdminActions(), BorderLayout.EAST);
        }

        block.add(buildHistoriqueScreen(), BorderLayout.SOUTH);

        return block;
    }

    private JPanel buildFilterRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row.setOpaque(false);

        filterStatusBox = new JComboBox<>(new String[]{"TOUS", "EN_COURS", "TRAITEE", "REJETEE"});

        monthBox = new JComboBox<>(new String[]{
                "TOUS",
                "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
        });

        yearField = new JTextField(7);

        JButton filterButton = AppTheme.primaryButton("Filtrer");
        JButton refreshButton = AppTheme.secondaryButton("Actualiser");

        row.add(new JLabel("Statut :"));
        row.add(filterStatusBox);
        row.add(new JLabel("Mois :"));
        row.add(monthBox);
        row.add(new JLabel("Année :"));
        row.add(yearField);
        row.add(filterButton);
        row.add(refreshButton);

        filterButton.addActionListener(e -> applyFilters());
        refreshButton.addActionListener(e -> {
            filterStatusBox.setSelectedItem("TOUS");
            monthBox.setSelectedItem("TOUS");
            yearField.setText("");
            historiqueArea.setText("Sélectionnez une ligne pour voir son historique.");
            loadReclamations();
        });

        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        return row;
    }

    private JPanel buildAdminActions() {
        JPanel actions = new JPanel();
        actions.setOpaque(false);
        actions.setLayout(new BoxLayout(actions, BoxLayout.Y_AXIS));
        actions.setPreferredSize(new Dimension(190, 0));

        JLabel label = new JLabel("Nouveau statut :");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        statutBox = new JComboBox<>(StatutReclamation.values());
        statutBox.setMaximumSize(new Dimension(170, 35));

        JButton statusButton = AppTheme.primaryButton("Modifier statut");
        statusButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusButton.addActionListener(e -> updateSelectedStatus());

        actions.add(label);
        actions.add(Box.createVerticalStrut(8));
        actions.add(statutBox);
        actions.add(Box.createVerticalStrut(12));
        actions.add(statusButton);

        return actions;
    }

    private JPanel buildHistoriqueScreen() {
        JPanel wrapper = new JPanel(new BorderLayout(5, 5));
        wrapper.setOpaque(false);
        wrapper.setPreferredSize(new Dimension(0, 110));

        JLabel title = AppTheme.subtitle("Historique");
        wrapper.add(title, BorderLayout.NORTH);

        historiqueArea = new JTextArea();
        historiqueArea.setEditable(false);
        historiqueArea.setFocusable(false);
        historiqueArea.setLineWrap(true);
        historiqueArea.setWrapStyleWord(true);
        historiqueArea.setBackground(new Color(17, 24, 39));
        historiqueArea.setForeground(new Color(229, 231, 235));
        historiqueArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        historiqueArea.setText("Sélectionnez une ligne pour voir son historique.");

        JScrollPane scrollPane = new JScrollPane(historiqueArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(31, 41, 55)));

        wrapper.add(scrollPane, BorderLayout.CENTER);

        return wrapper;
    }

    private void hideIdColumn() {
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);
    }

    private void createReclamation() {
        String sujet = sujetField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (sujet.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir le sujet et la description.");
            return;
        }

        Reclamation reclamation = new Reclamation();
        reclamation.setUserId(currentUser.getId());
        reclamation.setSujet(sujet);
        reclamation.setDescription(description);
        reclamation.setStatut(StatutReclamation.EN_COURS);

        boolean ok = reclamationController.createReclamation(reclamation);

        if (ok) {
            JOptionPane.showMessageDialog(this, "Réclamation envoyée avec succès.");
            sujetField.setText("");
            descriptionArea.setText("");
            loadReclamations();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'envoi de la réclamation.");
        }
    }

    private void loadReclamations() {
        List<Reclamation> reclamations = adminMode
                ? reclamationController.getAllReclamations()
                : reclamationController.getReclamationsByUser(currentUser.getId());

        fillTable(reclamations);
    }

    private void applyFilters() {
        String selectedStatus = filterStatusBox.getSelectedItem().toString();
        String selectedMonth = monthBox.getSelectedItem().toString();
        String yearText = yearField.getText().trim();

        List<Reclamation> reclamations = adminMode
                ? reclamationController.getAllReclamations()
                : reclamationController.getReclamationsByUser(currentUser.getId());

        if (!"TOUS".equals(selectedStatus)) {
            StatutReclamation statut = StatutReclamation.valueOf(selectedStatus);
            reclamations.removeIf(r -> r.getStatut() != statut);
        }

        if (!"TOUS".equals(selectedMonth) || !yearText.isEmpty()) {
            if (yearText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez saisir l'année.");
                return;
            }

            try {
                int selectedYear = Integer.parseInt(yearText);
                int selectedMonthNumber = monthBox.getSelectedIndex();

                reclamations.removeIf(r -> {
                    if (r.getCreatedAt() == null) {
                        return true;
                    }

                    LocalDate date = r.getCreatedAt().toLocalDate();

                    if ("TOUS".equals(selectedMonth)) {
                        return date.getYear() != selectedYear;
                    }

                    return date.getMonthValue() != selectedMonthNumber || date.getYear() != selectedYear;
                });

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Veuillez saisir une année valide.");
                return;
            }
        }

        historiqueArea.setText("Sélectionnez une ligne pour voir son historique.");
        fillTable(reclamations);
    }

    private void fillTable(List<Reclamation> reclamations) {
        tableModel.setRowCount(0);

        for (Reclamation r : reclamations) {
            tableModel.addRow(new Object[]{
                    r.getId(),
                    r.getSujet(),
                    r.getDescription(),
                    r.getStatut(),
                    r.getCreatedAt()
            });
        }
    }

    private void onRowSelected(ListSelectionEvent event) {
        if (!event.getValueIsAdjusting()) {
            int id = getSelectedReclamationId();
            if (id > 0) {
                loadHistorique(id);
            }
        }
    }

    private int getSelectedReclamationId() {
        int row = table.getSelectedRow();

        if (row < 0) {
            return -1;
        }

        return (int) table.getValueAt(row, 0);
    }

    private void updateSelectedStatus() {
        int id = getSelectedReclamationId();

        if (id <= 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une réclamation.");
            return;
        }

        StatutReclamation statut = (StatutReclamation) statutBox.getSelectedItem();
        boolean ok = reclamationController.changeStatut(id, statut, currentUser.getId());

        if (ok) {
            JOptionPane.showMessageDialog(this, "Statut modifié avec succès.");
            loadReclamations();
            loadHistorique(id);
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification du statut.");
        }
    }


    public void showByStatut(String statutText) {
        StatutReclamation statut = StatutReclamation.valueOf(statutText);

        if (filterStatusBox != null) {
            filterStatusBox.setSelectedItem(statutText);
        }

        List<Reclamation> reclamations = adminMode
                ? reclamationController.getAllReclamations()
                : reclamationController.getReclamationsByUser(currentUser.getId());

        reclamations.removeIf(r -> r.getStatut() != statut);
        historiqueArea.setText("Filtre appliqué depuis le dashboard : statut = " + statutText);
        fillTable(reclamations);
    }

    private void loadHistorique(int reclamationId) {
        List<HistoriqueReclamation> historique = reclamationController.getHistoriqueReclamation(reclamationId);

        if (historique.isEmpty()) {
            historiqueArea.setText("Aucun historique trouvé pour cette réclamation.");
            return;
        }

        StringBuilder text = new StringBuilder();

        for (HistoriqueReclamation h : historique) {
            text.append("Ancien statut : ").append(h.getAncienStatut()).append("\n");
            text.append("Nouveau statut : ").append(h.getNouveauStatut()).append("\n");
            text.append("Action        : ").append(h.getAction()).append("\n");
            text.append("Date          : ").append(h.getActionDate()).append("\n");
            text.append("--------------------------------------------------\n");
        }

        historiqueArea.setText(text.toString());
        historiqueArea.setCaretPosition(0);
    }
}
