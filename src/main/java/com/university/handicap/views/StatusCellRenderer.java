package com.university.handicap.views;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class StatusCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {

        JLabel label = (JLabel) super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column
        );

        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        label.setFont(AppTheme.NORMAL_FONT);

        if (isSelected) {
            label.setBackground(table.getSelectionBackground());
            label.setForeground(AppTheme.TEXT);
            return label;
        }

        String status = value == null ? "" : value.toString();

        switch (status) {
            case "EN_COURS":
                label.setBackground(new Color(254, 243, 199));
                label.setForeground(new Color(146, 64, 14));
                break;
            case "ACCEPTEE":
            case "TRAITEE":
                label.setBackground(new Color(220, 252, 231));
                label.setForeground(new Color(22, 101, 52));
                break;
            case "REFUSEE":
            case "REJETEE":
                label.setBackground(new Color(254, 226, 226));
                label.setForeground(new Color(153, 27, 27));
                break;
            default:
                label.setBackground(Color.WHITE);
                label.setForeground(AppTheme.TEXT);
        }

        return label;
    }
}
