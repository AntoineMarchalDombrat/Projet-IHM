package fr.iutfbleau.projetIHM2021FI2.Stats.View;

import javax.swing.*;
import java.awt.*;
/**
 *La classe <code>Diagramme</code> permet d'afficher un diagramme à partir de données.
 */
public class Diagramme extends JComponent {

    private int[] data;
    private int moyenne3ans;

    private int height;
    private int width;
    /**
     *Le constructeur permet de créer un nouveau diagramme.
     *
     *@param data le tableau de données.
     *@param moyenne3ans la moyenne d'occupation des 3 dernières années.
     */
    public Diagramme(int[] data, int moyenne3ans) {
        this.data = data;
        this.moyenne3ans = moyenne3ans;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = (int) screenSize.getWidth();
        this.height = (int) screenSize.getHeight();
        
    }

    @Override
    protected void paintComponent(Graphics pinceau) {

        Graphics secondPinceau = pinceau.create();

        if (this.isOpaque()) {
            secondPinceau.setColor(this.getBackground());
            secondPinceau.fillRect(0, 0, this.getWidth(), this.getHeight());
        }

        secondPinceau.setColor(new Color(0, 0, 0));

        int largeur_colonne;
        int espace_colonne;
        //titre
        secondPinceau.setFont(secondPinceau.getFont().deriveFont(18.0f));
        secondPinceau.drawString("Diagramme représentant le pourcentage d'occupation en fonction du temps", width / 5, height - 550);

        //axes
        secondPinceau.setFont(secondPinceau.getFont().deriveFont(12.0f));
        secondPinceau.fillRect(width / 5, height - 900, 2, 100 * 3);
        secondPinceau.fillRect(width / 5, height - 600, 800, 2);
        secondPinceau.drawString("Pourcentage d'occupation", width / 5 - 165, height - 900);
        secondPinceau.drawString("Temps", width / 5 + 800, height - 580);

        //legende
        secondPinceau.setFont(secondPinceau.getFont().deriveFont(18.0f));
        secondPinceau.drawString("Legende :", 100, height - 750);
        secondPinceau.setFont(secondPinceau.getFont().deriveFont(12.0f));
        secondPinceau.drawString("Pourcentage d'occupation", 100, height - 725);
        secondPinceau.fillRect(88, height - 735, 10, 10);
        secondPinceau.drawString("Moyenne", 100, height - 710);
        secondPinceau.setColor(new Color(0, 100, 100));
        secondPinceau.fillRect(88, height - 720, 10, 10);
        secondPinceau.setColor(new Color(0, 0, 0));

        //paramétrage pour les barres en fonction du bouton cliqué
        if (data.length < 9) {
            espace_colonne = 16;
            largeur_colonne = 90;
        } else if (data.length < 35) {
            espace_colonne = 4;
            largeur_colonne = 20;
            Font font = secondPinceau.getFont().deriveFont(12.0f);
            secondPinceau.setFont(font);
        } else {
            espace_colonne = 2;
            largeur_colonne = 6;
            Font font = secondPinceau.getFont().deriveFont(8.0f);
            secondPinceau.setFont(font);
        }

        //barres
        secondPinceau.setColor(new Color(0, 0, 0));
        int num_colonne = 0;
        for (num_colonne = 0; num_colonne < data.length; num_colonne++) {

            secondPinceau.fillRect(width / 5 + espace_colonne + (num_colonne) * (espace_colonne + largeur_colonne), height - data[num_colonne] * 3 - 600, largeur_colonne, data[num_colonne] * 3);
            secondPinceau.drawString(Integer.toString(data[num_colonne]), width / 5 + espace_colonne + (num_colonne) * (espace_colonne + largeur_colonne), height - data[num_colonne] * 3 - 600);
        }

        //moyenne 
        secondPinceau.setFont(secondPinceau.getFont().deriveFont(12.0f));
        secondPinceau.setColor(new Color(0, 100, 100));
        secondPinceau.fillRect(width / 5, height - 600 - (this.moyenne3ans * 3), 800, 2);
        secondPinceau.drawString(String.valueOf(this.moyenne3ans), width / 5 - 15, height - 600 - this.moyenne3ans*3);

    }

}
