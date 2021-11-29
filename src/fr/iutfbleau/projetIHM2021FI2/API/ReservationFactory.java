package fr.iutfbleau.projetIHM2021FI2.API;
import java.time.LocalDate;
import java.util.*;

/**
 * Cette interface est une usine abstraite.
 * 
 *  Elle permet de trouver une ou des chambres à partir d'une préréservation, de créer une réservation.
 *  Elle permet aussi de compter toutes les chambres d'un certain type (disponibles ou non).
 *
 *  Conseil : 
 *  si vous instantiez cette classe pour proposer un modèle persistent avec une base de données, attention  à ne pas faire des choses qu'on peut faire efficacement en BdD côté Java.
 *  En particulier, ne pas faire des jointures ou de l'aggrégat côté java alors que votre bdd fait ça beaucoup plus efficacement.
 *  Plus de détails ici : https://use-the-index-luke.com/sql/join/nested-loops-join-n1-problem
 *
 *  Autre conseil : 
 *  vous n'êtes probablement pas obligé de coder toutes les méthodes listées ici pour que votre projet soit terminé.
 *  Cette interface est volontairement assez complète pour permettre de faire varier le sujet du projet sans la changer.
 *
 *  Je ne l'ai pas ajouté individuellement pour la documentation de chaque méthode, mais si la classe réalisant cette interface ne réalise pas vraiment une méthode, vous pouvez lever 
 *  java.lang.UnsupportedOperationException
 *  avec un message "pas encore implanté" ou "aucune implantation prévue" selon les cas.
 * 
 *  La raison de cette absence dans la documentation ci-dessous c'est qu'il s'agit d'une Exception héritant de RuntimeException.
 *  La doc indique aussi :
 *  "RuntimeException and its subclasses are unchecked exceptions. Unchecked exceptions do not need to be declared in a method or constructor's throws clause if they can be thrown by the execution of the method or constructor and propagate outside the method or constructor boundary."
 *
 */
public interface ReservationFactory{
    
    /**
     * Recherche une chambre adéquate à partir de
     * @param  p une  préréservation 
     * @return la chambre
     * @throws NullPointerException si un argument est null
     * @throws IllegalStateException si une chambre correspondant à cette Préréservation n'existe pas.
     *
     * Ne devrait pas retourner un objet null.
     */
    public Chambre getChambre(Prereservation p);

    /**
     * Recherche toutes les chambres adéquates à partir de
     * @param  p une  préréservation 
     * @return les chambres (set de chambre)
     * @throws NullPointerException si un argument est null
     * @throws IllegalStateException si une chambre correspondant à cette Préréservation n'existe pas.
     *
     * Ne devrait pas retourner un objet null.
     */
    public Set<Chambre> getChambres(Prereservation p);

    /**
     * Fabrique (ajoute) une réservation
     * @param  p une  préréservation 
     * @param  c une  chambre (normalement libre et adaptée à la préréservation) 
     * @return la réservation
     * @throws NullPointerException si un argument est null
     * @throws IllegalArgumentException si la chambre ne correspondant pas au type de chambre de la préréservation.
     * @throws IllegalStateException si la chambre n'est pas disponible.
     *
     * Ne devrait pas retourner un objet null.
     */    
    public Reservation createReservation(Prereservation p, Chambre c);

    /**
     * Cherche les réservations
     * @param  d une date
     * @return la ou les réservation(s) à cette date sous forme d'un ensemble
     * @throws NullPointerException si un argument est null
     *
     * Ne devrait pas retourner un objet null, par contre peut être un ensemble qui est vide.
     */    
    public Set<Reservation> getReservation(LocalDate d);

    /**
     * Cherche le nombre de chambres disponibles pour une date (réservées ou non).
     * @param  d une date
     * @return un entier
     * @throws NullPointerException si un argument est null
     *
     * Ne devrait pas retourner un entier négatif.
     */    
    public int getDisponibles(LocalDate d);

    /**
     * Cherche les réservations
     * @param  d une date
     * @param  t un type de chambre
     * @return la ou les réservation(s) pour ce type de chambre à cette date sous forme d'un ensemble
     * @throws NullPointerException si un argument est null
     *
     * Ne devrait pas retourner un objet null, par contre peut être un ensemble qui est vide.
     */    
    public Set<Reservation> getReservation(LocalDate d, TypeChambre t);
    
    /**
     * Cherche le nombre de chambres disponibles d'un certain type pour une date (réservées ou non).
     * @param  d une date
     * @param  t un type de chambre
     * @return un entier
     * @throws NullPointerException si un argument est null
     *
     * Ne devrait pas retourner un entier négatif.
     */    
    public int getDisponibles(LocalDate d, TypeChambre t);

    /**
     * Cherche la proportion de chambres disponibles pour une date (réservées sur réservables).
     * @param  d une date
     * @return un entier entre 0 et 100
     * @throws NullPointerException si un argument est null
     */    
    public int getRatio(LocalDate d);

    /**
     * Cherche la proportion de chambres disponibles d'un certain type pour une date (réservées sur réservables).
     * @param  d une date
     * @param  t un type de chambre
     * @return un entier entre 0 et 100
     * @throws NullPointerException si un argument est null
     */    
    public int getRatio(LocalDate d, TypeChambre t);

    /**
     * Cherche le nombre moyen de chambres disponibles entre deux date (réservées ou non), arrondies à l'entier inférieur.
     * @param  d1 une date
     * @param  d2 une date
     * @return un entier
     * @throws NullPointerException si un argument est null
     * @throws IllegalArgumentException si l'ordre temporel d1 avant d2 n'est pas respecté.
     *
     * Ne devrait pas retourner un entier négatif.
     */    
    public int getDisponibles(LocalDate d1, LocalDate d2);

    /**
     * Cherche les réservations
     * @param  d1 une date
     * @param  d2 une date
     * @param  t un type de chambre
     * @return la ou les réservation(s) pour ce type de chambre entre les dates sous forme d'un ensemble
     * @throws NullPointerException si un argument est null
     * @throws IllegalArgumentException si l'ordre temporel d1 avant d2 n'est pas respecté.
     *
     * Ne devrait pas retourner un objet null, par contre peut être un ensemble qui est vide.
     */    
    public Set<Reservation> getReservation(LocalDate d1, LocalDate d2, TypeChambre t);
    
    /**
     * Cherche le <b>nombre moyen</b> de chambres disponibles d'un certain type entre deux date (réservées ou non), arrondies à l'entier inférieur.
     * @param  d1 une date
     * @param  d2 une date
     * @param  t un type de chambre
     * @return un entier
     * @throws NullPointerException si un argument est null
     * @throws IllegalArgumentException si l'ordre temporel d1 avant d2 n'est pas respecté.
     *
     * Ne devrait pas retourner un entier négatif.
     */    
    public int getDisponibles(LocalDate d1, LocalDate d2, TypeChambre t);

    /**
     * Cherche la <b>proportion moyenne</b> de chambres disponibles pour une date (réservées sur réservables).
     * @param  d1 une date
     * @param  d2 une date
     * @return un entier entre 0 et 100
     * @throws NullPointerException si un argument est null
     */    
    public int getRatio(LocalDate d1, LocalDate d2);

    /**
     * Cherche la <b>proportion moyenne</b> de chambres disponibles d'un certain type pour une date (réservées sur réservables).
     * @param  d1 une date
     * @param  d2 une date
     * @param  t un type de chambre
     * @return un entier entre 0 et 100
     * @throws NullPointerException si un argument est null
     */    
    public int getRatio(LocalDate d1, LocalDate d2, TypeChambre t);
    
}
