package fr.iutfbleau.projetIHM2021FI2.API;
import java.time.LocalDate;
/**
 * Une préréservation
 * 
 * e.g. utilisé par le système de réservation externe à l'hôtel.
 * 
 */

public interface Prereservation extends MonPrint{

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
     * @return le type de chambre
     */
    public TypeChambre getTypeChambre();

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
        return String.format("Préréservation " + getReference() + ": " + getClient().monPrint() + " le " +  getDateDebut().toString() + " pour " + getJours() + " nuit(s) ");
    }

    
}
