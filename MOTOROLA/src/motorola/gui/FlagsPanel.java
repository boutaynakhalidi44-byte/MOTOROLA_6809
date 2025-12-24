package motorola.gui;

import java.awt.*;
import javax.swing.*;
import motorola.cpu.CPU;

public class FlagsPanel extends JPanel {

    private final CPU cpu;
    private JLabel[] flagLabels;
    private JLabel[] flagIndicators;
    private String[] flagNames = {"E", "F", "H", "I", "N", "Z", "V", "C"};
    private int[] flagMasks = {
        CPU.CC_E, CPU.CC_F, CPU.CC_H, CPU.CC_I,
        CPU.CC_N, CPU.CC_Z, CPU.CC_V, CPU.CC_C
    };

    public FlagsPanel(CPU cpu) {
        this.cpu = cpu;
        setBackground(Theme.PANEL_LIGHTER);
        setBorder(Theme.createTitledBorder("FLAGS CC"));
        setLayout(new GridLayout(2, 8, 5, 5));

        flagLabels = new JLabel[8];
        flagIndicators = new JLabel[8];

        // Créer les 8 flags avec labels et indicateurs
        for (int i = 0; i < 8; i++) {
            // Panel pour chaque flag
            JPanel flagPanel = new JPanel();
            flagPanel.setLayout(new BoxLayout(flagPanel, BoxLayout.Y_AXIS));
            flagPanel.setBackground(Theme.PANEL_LIGHTER);

            // Label du nom du flag
            flagLabels[i] = new JLabel(flagNames[i]);
            flagLabels[i].setFont(new Font("Monospaced", Font.BOLD, 12));
            flagLabels[i].setForeground(Color.BLACK);
            flagLabels[i].setAlignmentX(Component.CENTER_ALIGNMENT);

            // Indicateur coloré (point vide par défaut)
            flagIndicators[i] = new JLabel("○");
            flagIndicators[i].setFont(new Font("Monospaced", Font.BOLD, 16));
            flagIndicators[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            flagIndicators[i].setForeground(new Color(80, 80, 85));  // Gris sombre

            flagPanel.add(Box.createVerticalGlue());
            flagPanel.add(flagLabels[i]);
            flagPanel.add(flagIndicators[i]);
            flagPanel.add(Box.createVerticalGlue());

            add(flagPanel);
        }

        refresh();
    }

    public void refresh() {
        // Mettre à jour les indicateurs de couleur
        for (int i = 0; i < 8; i++) {
            if (cpu.isFlagSet(flagMasks[i])) {
                // Flag à 1 : point plein baby pink
                flagIndicators[i].setText("●");
                flagIndicators[i].setForeground(new Color(255, 182, 193));  // Baby pink
            } else {
                // Flag à 0 : point vide gris sombre
                flagIndicators[i].setText("○");
                flagIndicators[i].setForeground(new Color(120, 120, 125));
            }
        }
    }
}
