package fr.iutfbleau.projetIHM2021FI2.API;

/**
 * Un client
 */

public interface Client extends MonPrint{

    /**
     * permet de récupérer l'identifiant du client (qu'on suppose être le même pour les différents systèmes, internes et externes à l'hôtel).
     * @return l'identifiant.
     */
    public int getId();

    /**
     * permet de récupérer 
     * @return le nom du client.
     */
    public String getNom();

    /**
     * permet de récupérer
     * @return le prénom du client
     */
    public String getPrenom();

    /**
     * @see MonPrint
     * NB. On n'utilise le mécanisme des méthodes par défaut pour donner du code dans une interface. C'est un petit peu laid et à contre-emploi mais pratique ici.
     */ 
    public default String monPrint() {
        return String.format("Nom " + getNom() + " Prenom " + getPrenom() + " (id="+getId()+")");
    }
}
