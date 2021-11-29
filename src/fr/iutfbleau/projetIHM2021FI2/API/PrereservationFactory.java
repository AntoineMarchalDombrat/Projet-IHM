package fr.iutfbleau.projetIHM2021FI2.API;
import java.util.*;
/**
 * Cette interface est une usine abstraite.
 * 
 *  Elle permet de rechercher les préréservations à partir du numéro d'une préréservation, ou bien du nom et prenom d'un Client.
 *
 */
public interface PrereservationFactory{
    
    /**
     * Recherche une préréservation par reference
     * @param  r la référence du système de préréservation 
     * @return la préréservation.
     * @throws NullPointerException si un argument est null
     * @throws IllegalStateException si la Préréservation avec cette référence n'existe pas.
     *
     * Ne devrait pas retourner un objet null.
     */
    public Prereservation getPrereservation(String r);

    /**
     * Recherche une préréservation par nom et prenom
     * @param  n le nom
     * @param  p le prenom
     * @return un ensemble de préréservations.
     * @throws NullPointerException si un argument est null
     * @throws IllegalStateException si aucune préréservation n'existe avec ce nom
     *
     * Ne devrait pas retourner un objet null ou un ensemble vide.
     */
    public Set<Prereservation> getPrereservations(String n, String p);

}
