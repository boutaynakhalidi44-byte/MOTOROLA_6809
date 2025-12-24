package motorola.gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

public class Theme {

    // === PALETTE DE COULEURS GRIS TRÈS CLAIR + BABY PINK ===
    // Base gris très clair
    public static final Color BACKGROUND = new Color(130, 130, 135);        // Gris très clair
    
    // Dégradés gris très clair pour les panels
    public static final Color PANEL = new Color(150, 150, 155);             // Gris très clair de base
    public static final Color PANEL_LIGHT = new Color(170, 170, 175);       // Gris très très clair
    public static final Color PANEL_LIGHTER = new Color(190, 190, 195);     // Gris extrêmement clair
    public static final Color PANEL_ACCENT = new Color(210, 210, 215);      // Gris quasi blanc
    
    // Accents baby pink et texte
    public static final Color TITLE_COLOR = new Color(220, 100, 150);       // Baby pink foncé pour titres
    public static final Color TEXT = new Color(250, 250, 252);              // Blanc pur pour texte
    public static final Color TEXT_SECONDARY = new Color(240, 240, 245);    // Blanc très léger
    public static final Color ACCENT_BRIGHT = new Color(255, 200, 215);     // Baby pink clair pour accents
    
    public static final Color BORDER = new Color(220, 160, 180);            // Bordure baby pink-gris
    
    // Fonts
    public static final Font FONT_NORMAL = new Font("Monospaced", Font.PLAIN, 12);
    public static final Font FONT_TITLE  = new Font("Monospaced", Font.BOLD, 18);
    
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
