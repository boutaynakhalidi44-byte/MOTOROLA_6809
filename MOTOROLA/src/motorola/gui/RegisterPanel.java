package motorola.gui;

import java.awt.*;
import javax.swing.*;
import motorola.cpu.CPU;

public class RegisterPanel extends JPanel {

    private final CPU cpu;
    private JLabel aLabel, bLabel, dLabel, xLabel, yLabel, uLabel, sLabel, pcLabel, ccLabel;
    private JTextField dpField;

    public RegisterPanel(CPU cpu) {
        this.cpu = cpu;

        setLayout(new GridLayout(10, 2, 5, 5));
        setBorder(Theme.createTitledBorder("Registres"));
        setBackground(Theme.PANEL_LIGHT);

        // 8-bit registers
        aLabel = addRegisterDisplay("A (8-bit)");
        bLabel = addRegisterDisplay("B (8-bit)");
        
        // 16-bit combined
        dLabel = addRegisterDisplay("D (A:B)");
        
        // 16-bit index registers
        xLabel = addRegisterDisplay("X (16-bit)");
        yLabel = addRegisterDisplay("Y (16-bit)");
        uLabel = addRegisterDisplay("U (16-bit)");
        sLabel = addRegisterDisplay("S (16-bit)");
        
        // Control registers
        pcLabel = addRegisterDisplay("PC");
        addDPDisplay();
        ccLabel = addRegisterDisplay("CC");

        refresh();
    }

    private void addDPDisplay() {
        JLabel nameLabel = new JLabel("DP");
        nameLabel.setFont(new Font("Monospaced", Font.BOLD, 11));
        nameLabel.setForeground(Color.BLACK);

        dpField = new JTextField("00", 2);
        dpField.setFont(new Font("Monospaced", Font.BOLD, 12));
        dpField.setForeground(Theme.ACCENT_BRIGHT);
        dpField.setBackground(new Color(30, 50, 60));
        dpField.setBorder(BorderFactory.createLineBorder(Theme.BORDER));
        
        // Ajouter ActionListener pour Entrée
        dpField.addActionListener(e -> setDP());

        add(nameLabel);
        add(dpField);
        
        // Initialiser avec la valeur du CPU au démarrage
        dpField.setText(String.format("%02X", cpu.getRegDP()));
    }

    private void setDP() {
        try {
            String text = dpField.getText().trim();
            if (text.isEmpty()) return;
            
            // Enlever 0x si présent
            if (text.toUpperCase().startsWith("0X")) {
                text = text.substring(2);
            }
            
            // Parser directement en base 16
            int value = Integer.parseInt(text, 16);
            
            // Garder que 8 bits
            value = value & 0xFF;
            
            cpu.setRegDP(value);
            dpField.setText(String.format("%02X", value));
            
        } catch (Exception ex) {
            System.out.println("Erreur DP = " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private JLabel addRegisterDisplay(String name) {
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Monospaced", Font.BOLD, 11));
        nameLabel.setForeground(Color.BLACK);

        JLabel valueLabel = new JLabel("00000000");
        valueLabel.setFont(new Font("Monospaced", Font.BOLD, 12));
        valueLabel.setForeground(Theme.ACCENT_BRIGHT);
        valueLabel.setOpaque(true);
        valueLabel.setBackground(new Color(30, 50, 60));
        valueLabel.setBorder(BorderFactory.createLineBorder(Theme.BORDER));

        add(nameLabel);
        add(valueLabel);

        return valueLabel;
    }

    public void refresh() {
        aLabel.setText(String.format("0x%02X", cpu.getAccA()));
        bLabel.setText(String.format("0x%02X", cpu.getAccB()));
        dLabel.setText(String.format("0x%04X", cpu.getAccD()));
        xLabel.setText(String.format("0x%04X", cpu.getRegX()));
        yLabel.setText(String.format("0x%04X", cpu.getRegY()));
        uLabel.setText(String.format("0x%04X", cpu.getRegU()));
        sLabel.setText(String.format("0x%04X", cpu.getRegS()));
        pcLabel.setText(String.format("0x%04X", cpu.getRegPC()));
        
        // NE PAS mettre à jour DP depuis le CPU - laisser l'utilisateur le contrôler via le GUI
        // DP est géré indépendamment par l'utilisateur qui tape dans le champ
        
        ccLabel.setText(String.format("0x%02X", cpu.getRegCC()));
    }

    public void resetDP() {
        cpu.setRegDP(0);  // Réinitialiser le CPU aussi
        dpField.setText("00");
    }

    /**
     * Restaurer l'affichage du DP depuis le CPU (après compilation)
     * Utile quand le DP a été modifié avant la compilation
     */
    public void updateDPDisplay() {
        int currentDP = cpu.getRegDP();
        //System.out.println("DEBUG RegisterPanel.updateDPDisplay(): CPU DP = 0x" + String.format("%02X", currentDP));
        dpField.setText(String.format("%02X", currentDP));
        //System.out.println("DEBUG RegisterPanel.updateDPDisplay(): GUI dpField = " + dpField.getText());
    }
}
