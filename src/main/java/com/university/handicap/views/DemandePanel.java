package com.university.handicap.views;

import com.university.handicap.controllers.DemandeController;
import com.university.handicap.models.Demande;
import com.university.handicap.models.HistoriqueDemande;
import com.university.handicap.models.PieceJustificative;
import com.university.handicap.models.User;
import com.university.handicap.services.DemandeWorkflowService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.Calendar;
import java.util.List;

public class DemandePanel extends JPanel {

    private final boolean adminMode;
    private final User currentUser;
    private final DemandeController demandeController;
    private final DemandeWorkflowService workflowService;

    private JComboBox<String> createTypeBox;
    private JTextArea createDescriptionArea;
    private File selectedPieceFile;
    private JLabel selectedPieceLabel;

    private JComboBox<String> filterTypeBox;
    private JComboBox<String> filterStatusBox;
    private JComboBox<String> monthBox;
    private JTextField yearField;

    private JComboBox<String> statutBox;

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextArea historiqueArea;

    public DemandePanel(boolean adminMode, User currentUser) {
        this.adminMode = adminMode;
        this.currentUser = currentUser;
        this.demandeController = new DemandeController();
        this.workflowService = new DemandeWorkflowService();

        setLayout(new BorderLayout(12, 12));
        setBackground(AppTheme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        if (!adminMode) {
            add(buildCreateBlock(), BorderLayout.NORTH);
        }

        add(buildListBlock(), BorderLayout.CENTER);

        loadDemandes();
    }

    private JPanel buildCreateBlock() {
        JPanel block = AppTheme.cardPanel();
        block.setLayout(new BorderLayout(10, 10));

        JLabel title = AppTheme.subtitle("Nouvelle demande");
        block.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        createTypeBox = new JComboBox<>(new String[]{
                "AMENAGEMENT_EXAMEN",
                "ACCESSIBILITE",
                "ACCOMPAGNEMENT",
                "AUTRE"
        });

        createDescriptionArea = new JTextArea(3, 40);
        createDescriptionArea.setLineWrap(true);
        createDescriptionArea.setWrapStyleWord(true);

        selectedPieceLabel = new JLabel("Aucun fichier sélectionné");
        selectedPieceLabel.setForeground(AppTheme.MUTED);

        JButton choosePieceButton = AppTheme.secondaryButton("Choisir une pièce");
        choosePieceButton.addActionListener(e -> choosePieceFile());

        JButton sendButton = AppTheme.primaryButton("Envoyer la demande");

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        form.add(new JLabel("Type de demande :"), c);

        c.gridx = 1;
        c.weightx = 1;
        form.add(createTypeBox, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        form.add(new JLabel("Description :"), c);

        c.gridx = 1;
        c.weightx = 1;
        form.add(new JScrollPane(createDescriptionArea), c);

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0;
        form.add(new JLabel("Pièce justificative :"), c);

        JPanel piecePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        piecePanel.setOpaque(false);
        piecePanel.add(choosePieceButton);
        piecePanel.add(selectedPieceLabel);

        c.gridx = 1;
        c.weightx = 1;
        form.add(piecePanel, c);

        c.gridx = 1;
        c.gridy = 3;
        c.anchor = GridBagConstraints.EAST;
        form.add(sendButton, c);

        sendButton.addActionListener(e -> createDemande());

        block.add(form, BorderLayout.CENTER);
        return block;
    }

    private JPanel buildListBlock() {
        JPanel block = AppTheme.cardPanel();
        block.setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setOpaque(false);

        JLabel title = AppTheme.subtitle(adminMode ? "Gestion des demandes" : "Mes demandes");
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        top.add(title);
        top.add(Box.createVerticalStrut(10));
        top.add(buildFilterRow());

        block.add(top, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Type", "Description", "Statut", "Date", "Pièce"}, 0) {
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

        block.add(buildRightActions(), BorderLayout.EAST);

        block.add(buildHistoriqueScreen(), BorderLayout.SOUTH);

        return block;
    }

    private JPanel buildFilterRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row.setOpaque(false);

        filterTypeBox = new JComboBox<>(new String[]{
                "TOUS",
                "AMENAGEMENT_EXAMEN",
                "ACCESSIBILITE",
                "ACCOMPAGNEMENT",
                "AUTRE"
        });

        filterStatusBox = new JComboBox<>(new String[]{
                "TOUS",
                "EN_COURS",
                "ACCEPTEE",
                "REFUSEE"
        });

        monthBox = new JComboBox<>(new String[]{
                "TOUS",
                "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
        });

        yearField = new JTextField(7);

        JButton filterButton = AppTheme.primaryButton("Filtrer");
        JButton refreshButton = AppTheme.secondaryButton("Actualiser");

        row.add(new JLabel("Type :"));
        row.add(filterTypeBox);
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
            filterTypeBox.setSelectedItem("TOUS");
            filterStatusBox.setSelectedItem("TOUS");
            monthBox.setSelectedItem("TOUS");
            yearField.setText("");
            historiqueArea.setText("Sélectionnez une ligne pour voir son historique.");
            loadDemandes();
        });

        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        return row;
    }

    private JPanel buildRightActions() {
        JPanel actions = new JPanel();
        actions.setOpaque(false);
        actions.setLayout(new BoxLayout(actions, BoxLayout.Y_AXIS));
        actions.setPreferredSize(new Dimension(190, 0));

        if (!adminMode) {
            return actions;
        }

        JLabel label = new JLabel("Nouveau statut :");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        statutBox = new JComboBox<>(new String[]{"EN_COURS", "ACCEPTEE", "REFUSEE"});
        statutBox.setMaximumSize(new Dimension(170, 35));

        JButton statusButton = AppTheme.primaryButton("Modifier statut");
        statusButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusButton.addActionListener(e -> updateSelectedStatus());

        JButton viewPiecesButton = AppTheme.secondaryButton("Voir pièces");
        viewPiecesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewPiecesButton.addActionListener(e -> showSelectedPieces());

        actions.add(label);
        actions.add(Box.createVerticalStrut(8));
        actions.add(statutBox);
        actions.add(Box.createVerticalStrut(12));
        actions.add(statusButton);
        actions.add(Box.createVerticalStrut(12));
        actions.add(viewPiecesButton);

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

    private void createDemande() {
        String type = createTypeBox.getSelectedItem().toString();
        String description = createDescriptionArea.getText().trim();

        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir une description.");
            return;
        }

        boolean ok = demandeController.createDemande(currentUser.getId(), type, description);

        if (!ok) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'envoi de la demande.");
            return;
        }

        if (selectedPieceFile != null) {
            Demande createdDemande = findLatestCreatedDemande(type, description);

            if (createdDemande != null) {
                boolean pieceOk = workflowService.addPieceJustificative(createdDemande.getId(), selectedPieceFile);

                if (!pieceOk) {
                    JOptionPane.showMessageDialog(this,
                            "Demande envoyée, mais erreur lors de l'ajout de la pièce justificative.");
                }
            }
        }

        JOptionPane.showMessageDialog(this, "Demande envoyée avec succès.");
        createDescriptionArea.setText("");
        selectedPieceFile = null;
        selectedPieceLabel.setText("Aucun fichier sélectionné");
        loadDemandes();
    }

    private void choosePieceFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choisir une pièce justificative");

        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedPieceFile = chooser.getSelectedFile();
            selectedPieceLabel.setText(selectedPieceFile.getName());
        }
    }

    private Demande findLatestCreatedDemande(String type, String description) {
        List<Demande> demandes = demandeController.getDemandesByUser(currentUser.getId());

        for (Demande d : demandes) {
            if (d.getType().name().equals(type)
                    && d.getDescription() != null
                    && d.getDescription().equals(description)) {
                return d;
            }
        }

        return demandes.isEmpty() ? null : demandes.get(0);
    }

    private void loadDemandes() {
        List<Demande> demandes = adminMode
                ? demandeController.getAllDemandes()
                : demandeController.getDemandesByUser(currentUser.getId());

        fillTable(demandes);
    }

    private void applyFilters() {
        String selectedType = filterTypeBox.getSelectedItem().toString();
        String selectedStatut = filterStatusBox.getSelectedItem().toString();
        String selectedMonth = monthBox.getSelectedItem().toString();
        String yearText = yearField.getText().trim();

        List<Demande> demandes = adminMode
                ? demandeController.getAllDemandes()
                : demandeController.getDemandesByUser(currentUser.getId());

        if (!"TOUS".equals(selectedType)) {
            demandes.removeIf(d -> !d.getType().name().equals(selectedType));
        }

        if (!"TOUS".equals(selectedStatut)) {
            demandes.removeIf(d -> !d.getStatut().name().equals(selectedStatut));
        }

        if (!"TOUS".equals(selectedMonth) || !yearText.isEmpty()) {
            if (yearText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez saisir l'année.");
                return;
            }

            try {
                int selectedYear = Integer.parseInt(yearText);
                int selectedMonthNumber = monthBox.getSelectedIndex();

                demandes.removeIf(d -> {
                    if (d.getDateCreation() == null) {
                        return true;
                    }

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(d.getDateCreation());

                    int recordMonth = cal.get(Calendar.MONTH) + 1;
                    int recordYear = cal.get(Calendar.YEAR);

                    if ("TOUS".equals(selectedMonth)) {
                        return recordYear != selectedYear;
                    }

                    return recordMonth != selectedMonthNumber || recordYear != selectedYear;
                });

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Veuillez saisir une année valide.");
                return;
            }
        }

        historiqueArea.setText("Sélectionnez une ligne pour voir son historique.");
        fillTable(demandes);
    }

    private void fillTable(List<Demande> demandes) {
        tableModel.setRowCount(0);

        for (Demande d : demandes) {
            String pieceSigne = workflowService.getPiecesByDemande(d.getId()).isEmpty()
                    ? "—"
                    : "📎 Oui";

            tableModel.addRow(new Object[]{
                    d.getId(),
                    d.getType(),
                    d.getDescription(),
                    d.getStatut(),
                    d.getDateCreation(),
                    pieceSigne
            });
        }
    }

    private void onRowSelected(ListSelectionEvent event) {
        if (!event.getValueIsAdjusting()) {
            int id = getSelectedDemandeId();
            if (id > 0) {
                loadHistorique(id);
            }
        }
    }

    private int getSelectedDemandeId() {
        int row = table.getSelectedRow();

        if (row < 0) {
            return -1;
        }

        return (int) table.getValueAt(row, 0);
    }

    private void updateSelectedStatus() {
        int id = getSelectedDemandeId();

        if (id <= 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une demande.");
            return;
        }

        String statut = statutBox.getSelectedItem().toString();
        boolean ok = workflowService.changeStatut(id, statut, currentUser.getId());

        if (ok) {
            JOptionPane.showMessageDialog(this, "Statut modifié avec succès.");
            loadDemandes();
            loadHistorique(id);
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification du statut.");
        }
    }


    public void showByType(String type) {
        if (filterTypeBox != null) {
            filterTypeBox.setSelectedItem(type);
        }

        List<Demande> demandes = adminMode
                ? demandeController.getAllDemandes()
                : demandeController.getDemandesByUser(currentUser.getId());

        demandes.removeIf(d -> !d.getType().name().equals(type));
        historiqueArea.setText("Filtre appliqué depuis le dashboard : type = " + type);
        fillTable(demandes);
    }

    public void showByStatut(String statut) {
        if (filterStatusBox != null) {
            filterStatusBox.setSelectedItem(statut);
        }

        List<Demande> demandes = adminMode
                ? demandeController.getAllDemandes()
                : demandeController.getDemandesByUser(currentUser.getId());

        demandes.removeIf(d -> !d.getStatut().name().equals(statut));
        historiqueArea.setText("Filtre appliqué depuis le dashboard : statut = " + statut);
        fillTable(demandes);
    }


    private void addPieceToSelectedDemande() {
        int id = getSelectedDemandeId();

        if (id <= 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une demande.");
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choisir une pièce justificative");

        int result = chooser.showOpenDialog(this);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File selectedFile = chooser.getSelectedFile();

        boolean ok = workflowService.addPieceJustificative(id, selectedFile);

        if (ok) {
            JOptionPane.showMessageDialog(this, "Pièce justificative ajoutée avec succès.");
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la pièce justificative.");
        }
    }

    private void showSelectedPieces() {
        int id = getSelectedDemandeId();

        if (id <= 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une demande.");
            return;
        }

        List<PieceJustificative> pieces = workflowService.getPiecesByDemande(id);

        if (pieces.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucune pièce justificative trouvée pour cette demande.");
            return;
        }

        PieceJustificative piece = pieces.get(0);
        showPiecePreview(piece);
    }

    private void showPiecePreview(PieceJustificative piece) {
        File file = new File(piece.getFilePath());

        if (!file.exists()) {
            file = new File(System.getProperty("user.dir"), piece.getFilePath());
        }

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.setText(
                "Fichier : " + piece.getFileName() + "\n" +
                "Chemin  : " + piece.getFilePath() + "\n" +
                "Date    : " + piece.getUploadedAt()
        );
        panel.add(info, BorderLayout.NORTH);

        if (isImageFile(file.getName()) && file.exists()) {
            ImageIcon icon = new ImageIcon(file.getAbsolutePath());
            Image image = icon.getImage();

            int maxWidth = 600;
            int maxHeight = 350;

            int width = icon.getIconWidth();
            int height = icon.getIconHeight();

            double ratio = Math.min((double) maxWidth / width, (double) maxHeight / height);
            int newWidth = Math.max(1, (int) (width * ratio));
            int newHeight = Math.max(1, (int) (height * ratio));

            Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

            panel.add(new JScrollPane(imageLabel), BorderLayout.CENTER);
        } else {
            JTextArea message = new JTextArea("Aperçu non disponible. Le fichier existe, mais ce n'est pas une image.");
            message.setEditable(false);
            panel.add(message, BorderLayout.CENTER);
        }

        panel.setPreferredSize(new Dimension(700, 500));

        JOptionPane.showMessageDialog(this, panel, "Pièce justificative", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean isImageFile(String fileName) {
        String lower = fileName.toLowerCase();
        return lower.endsWith(".png")
                || lower.endsWith(".jpg")
                || lower.endsWith(".jpeg")
                || lower.endsWith(".gif")
                || lower.endsWith(".bmp");
    }

    private void loadHistorique(int demandeId) {
        List<HistoriqueDemande> historique = workflowService.getHistoriqueDemande(demandeId);

        if (historique.isEmpty()) {
            historiqueArea.setText("Aucun historique trouvé pour cette demande.");
            return;
        }

        StringBuilder text = new StringBuilder();

        for (HistoriqueDemande h : historique) {
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
