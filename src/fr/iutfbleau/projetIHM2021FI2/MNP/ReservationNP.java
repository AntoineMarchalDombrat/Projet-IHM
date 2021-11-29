package fr.iutfbleau.projetIHM2021FI2.MNP;
import fr.iutfbleau.projetIHM2021FI2.API.*;
import java.time.LocalDate;
import java.util.*;
/**
 * Une Réservation non presistante toute bête
 * 
 * e.g. utilisé par le système de réservation interne à l'hôtel.
 * 
 */

public class ReservationNP implements Reservation {

    private String reference;
    private LocalDate dateDebut;
    private int jours;
    private Chambre chambre;
    private Client client;

    /**
     * Constructeur
     *
     * NB. on force jours>0 et les autres attributs à ne pas être null
     */
    public ReservationNP(String reference, LocalDate dateDebut, int jours, Chambre chambre, Client client){
        Objects.requireNonNull(reference,"On ne peut pas créer une réservation avec un reference à null.");
        Objects.requireNonNull(dateDebut,"On ne peut pas créer une réservation avec un date de début à null.");
        if (jours<1) {
            throw new IllegalArgumentException("On ne peut pas créer une réservation avec un nombre de jours négatif ou nul.");
        }
        Objects.requireNonNull(chambre,"On ne peut pas créer une réservation avec une chambre à null.");
        Objects.requireNonNull(client,"On ne peut pas créer une réservation avec un client à null.");
        this.reference=reference;
        this.dateDebut=dateDebut;

        this.jours=jours;
        this.chambre=chambre;
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
     * @return la chambre
     */
    public Chambre getChambre(){
        return this.chambre;
    }

    /**
     * permet de récupérer 
     * @return le client
     */
    public Client getClient(){
        return this.client;
    }
}





