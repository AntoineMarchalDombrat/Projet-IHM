package fr.iutfbleau.projetIHM2021FI2.API;
import java.time.LocalDate;
/**
 * Une Réservation
 * 
 * e.g. utilisé par le système de réservation interne à l'hôtel.
 * 
 */

public interface Reservation {

    /**
     * permet de récupérer 
     * @return la référence.
     */
    public String getReference();

    /**
     * permet de récupérer 
     * @return la date de début
     *
     * A priori seule la date est importante, le reste est sans importance.
     */
    public LocalDate getDateDebut();

    /**
     * permet de récupérer 
     * @return la durée en jours (mais comme un entier)
     */
    public int getJours();

    /**
     * permet de récupérer 
     * @return la chambre
     */
    public Chambre getChambre();

    /**
     * permet de récupérer 
     * @return le client
     */
    public Client getClient();

    /**
     * @see MonPrint
     * NB. On n'utilise le mécanisme des méthodes par défaut pour donner du code dans une interface. C'est un petit peu laid et à contre-emploi mais pratique ici.
     */ 
    public default String monPrint() {
        return String.format("Réservation " + getReference() + ": " + getClient().monPrint() + " le " +  getDateDebut().toString() + " pour " + getJours() + " nuit(s) " + getChambre().monPrint());
    }

}



