package fr.iutfbleau.projetIHM2021FI2.MNP;
import fr.iutfbleau.projetIHM2021FI2.API.*;
import java.time.LocalDate;
import java.util.*;
/**
 * Une préréservation non persitante toute bête
 * 
 * e.g. utilisé par le système de réservation externe à l'hôtel.
 * 
 */

public class PrereservationNP implements Prereservation {

    private String reference;
    private LocalDate dateDebut;
    private int jours;
    private TypeChambre type;
    private Client client;

    /**
     * Constructeur
     *
     * NB. on force jours>0 et les autres attributs à ne pas être null
     */
    public PrereservationNP(String reference, LocalDate dateDebut, int jours, TypeChambre t, Client client){
        Objects.requireNonNull(reference,"On ne peut pas créer une préréservation avec un reference à null.");
        Objects.requireNonNull(dateDebut,"On ne peut pas créer une préréservation avec un date de début à null.");
        if (jours<1) {
            throw new IllegalArgumentException("On ne peut pas créer une réservation avec un nombre de jours négatif ou nul.");
        }
        Objects.requireNonNull(t,"On ne peut pas créer une préréservation avec un type de chambre à null.");
        Objects.requireNonNull(client,"On ne peut pas créer une préréservation avec un client à null.");
        this.reference=reference;
        this.dateDebut=dateDebut;

        this.jours=jours;
        this.type=t;
        this.client=client;
    }

    
    /**
     * permet de récupérer 
     * @return la référence.
     */
    public String getReference(){
        return this.reference;
    }

    /**
     * permet de récupérer 
     * @return la date de début
     *
     * A priori seule la date est importante, le reste est sans importance.
     */
    public LocalDate getDateDebut(){
        return this.dateDebut;
    }

    /**
     * permet de récupérer 
     * @return la durée en jours (mais comme un entier)
     */
    public int getJours(){
        return this.jours;
    }

    /**
     * permet de récupérer 
     * @return le type de chambre
     */
    public TypeChambre getTypeChambre(){
        return this.type;
    }

    /**
     * permet de récupérer 
     * @return le client
     */
    public Client getClient(){
        return this.client;
    }

    // @Override
    // public String toString() {
    //     return String.format("Préréservation " + this.reference + " au nom de " + this.client + " le " + + " pour " + this.jours + " nuit(s) à partir du " + this.dateDebut);
    // }
}
