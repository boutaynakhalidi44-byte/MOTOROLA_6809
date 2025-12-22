package motobatata.gui;

import java.awt.*;
import java.io.File;
import javax.swing.*;

public class UALPanel extends JPanel {
    private ImageIcon ualImage;

    public UALPanel() {
        setPreferredSize(new Dimension(250,300));
        setBorder(Theme.createTitledBorder("UAL"));
        setBackground(Theme.PANEL_LIGHT);
        loadImage();
    }

    private void loadImage() {
        try {
            // Chercher l'image dans le dossier resources avec différents chemins
            String[] possiblePaths = {
                "motobatata/resources/ual.jpg.jpeg",
                "motobatata/resources/ual.jpg",
                "resources/ual.jpg.jpeg",
                "resources/ual.jpg"
            };
            
            for (String imagePath : possiblePaths) {
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    System.out.println("Image trouvée: " + imagePath);
                    Image img = new ImageIcon(imagePath).getImage();
                    Image scaledImg = img.getScaledInstance(230, 430, Image.SCALE_SMOOTH);
                    ualImage = new ImageIcon(scaledImg);
                    return;
                }
            }
            System.err.println("Aucune image UAL trouvée dans les chemins: " + String.join(", ", possiblePaths));
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image UAL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (ualImage != null) {
            g.drawImage(ualImage.getImage(), 7, 15, this);
        }
    }
}