package motobatata.gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

public class Theme {

    // === PALETTE DE COULEURS ESTHÉTIQUE ===
    // Base marron riche et chaleureux
    public static final Color BACKGROUND = new Color(45, 28, 14);           // Marron très foncé presque noir
    
    // Dégradés marron progressif pour les panels
    public static final Color PANEL = new Color(65, 42, 22);                // Marron foncé de base
    public static final Color PANEL_LIGHT = new Color(85, 58, 35);          // Marron moyen
    public static final Color PANEL_LIGHTER = new Color(110, 78, 50);       // Marron plus clair
    public static final Color PANEL_ACCENT = new Color(140, 100, 60);       // Bronze/cuivre dégradé
    
    // Accents et texte
    public static final Color TITLE_COLOR = new Color(255, 200, 87);        // Or/Bronze pétant pour titres
    public static final Color TEXT = new Color(240, 230, 220);              // Crème/Beige pour texte
    public static final Color TEXT_SECONDARY = new Color(200, 185, 170);    // Beige moyen
    public static final Color ACCENT_BRIGHT = new Color(0, 200, 255);       // Bleu turquoise pour contraste
    
    public static final Color BORDER = new Color(100, 70, 40);              // Bordure marron
    
    // Fonts
    public static final Font FONT_NORMAL = new Font("Monospaced", Font.PLAIN, 12);
    public static final Font FONT_TITLE  = new Font("Monospaced", Font.BOLD, 14);
    
    /**
     * Crée une bordure titré avec titre en or gras
     */
    public static TitledBorder createTitledBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(title);
        border.setTitleFont(FONT_TITLE);
        border.setTitleColor(TITLE_COLOR);
        return border;
    }
}