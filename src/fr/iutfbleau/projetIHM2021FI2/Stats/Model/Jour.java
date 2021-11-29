package fr.iutfbleau.projetIHM2021FI2.Stats.Model;
import fr.iutfbleau.projetIHM2021FI2.Stats.Controller.ListenOccupD;
import javax.swing.*;
import java.awt.*;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.time.*;
import java.sql.*;
import java.util.Arrays;
import java.text.*;
import java.time.format.DateTimeFormatter;

/**
*La classe <code>Jour</code> est utilisée pour obtenir toutes les informations nécessaires
*depuis la base de données à propos d'une date.
*/


public class Jour {
    /**
     *La date analysée.
     */
    private LocalDate date;

    /**
     *Constructeur dédié à la création de la variable date.
     *
     *@param date la date analysée (au format yyyy/MM/dd).
     */
    public Jour(LocalDate date) {

        this.date = date;
    }

    /**
     *Renvoie le pourcentage de chambre occupée à cette date.
     *
     *@return le pourcentage de chambre occupée à cette date (compris entre 0 et 100).
     */
    public int ratio() {

        int nbChambreOccupe = 0;
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            try {
                Connection cnx = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/cunsolo", "cunsolo", "projetphp");

                PreparedStatement pst = cnx.prepareStatement("Select COUNT(*) AS total from Reservation WHERE ? Between debut AND debut+nuits ");

                
                pst.setDate(1, Date.valueOf(this.date));
                ResultSet rs = pst.executeQuery();

                rs.next();
                nbChambreOccupe = rs.getInt("total");

                pst.close();
                cnx.close();
            } catch (SQLException e) {
                System.out.println("Non connecté ! ");
            }
        }catch(ClassNotFoundException e){
            System.err.println("Erreur chargement bd");
        }
        
        return nbChambreOccupe;

    }

    /**
     *Renvoie les données à partir de la date pendant nb_jours jours.
     *
     *@param nb_jours le nombre de jours.
     *
     *@return les données à partir de la date pendant nb_jours jours.
     */
    public int[] getData(int nb_jours) {
        LocalDate jour_analyse = this.date;
        int data[] = new int[nb_jours];
        for (int num_jour = 0; num_jour < nb_jours; num_jour++) {
            data[num_jour] = new Jour(jour_analyse).ratio();
            jour_analyse = jour_analyse.plusDays(1);

        }
        return data;
    }


    /**
     *Renvoie la moyenne des 3 dernières années précédant la date.
     *
     *@return Renvoie la moyenne des 3 dernières années précédant la date.
     */
    public int getMoyenne3ans() {

        LocalDate jour_analyse = this.date;
        int somme = 0;
        int nb_jour = 1095;
        int num_jour;

        for (num_jour = nb_jour; num_jour > 0; num_jour--) {
            somme += new Jour(jour_analyse).ratio();
            jour_analyse = jour_analyse.plusDays(-1);

        }
	
        return (somme/nb_jour);

    }

}
