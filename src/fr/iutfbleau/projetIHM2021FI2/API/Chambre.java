package fr.iutfbleau.projetIHM2021FI2.API;
/**
 * Une chambre
 */

public interface Chambre extends MonPrint {

    /**
     * permet de récupérer le numéro de la chambre.
     * @return le numéro.
     */
    public int getNumero();

    /**
     * permet de savoir si la chambre a un seul lit qui est simple
     * @return vrai si c'est le cas.
     * @deprecated  replaced by {@link #getType()}
     */
    @Deprecated
    public boolean unLitSimple();

    /**
     * permet de savoir si la chambre a deux lits simples
     * @return vrai si c'est le cas.
     * @deprecated  replaced by {@link #getType()}
     */
    @Deprecated
    public boolean deuxLitsSimples();

    /**
     * permet de savoir si la chambre a un lit double
     * @return vrai si c'est le cas.
     * @deprecated  replaced by {@link #getType()}
     */
    @Deprecated
    public boolean unLitDouble();

    /**
     * @return le type de chambre (un type énuméré de l'API)
     *
     * NB. Les trois méthodes ci-dessus sont assez moches.
     * De toute façon Chambre ou Prérerservation exposent le type énuméré TypeChambre à la vue.
     * Il est donc plus simple d'ajouter des types de chambre à ce type énuméré plutôt que d'ajouter des tests ici.
     * Je laisse les méthodes obsolètes pour illustrer l'annotation <pre>@deprecated</pre> de la javadoc.
     */
    public TypeChambre getType();
    
    /**
     * @see MonPrint
     * NB. On n'utilise le mécanisme des méthodes par défaut pour donner du code dans une interface. C'est un petit peu laid et à contre-emploi mais pratique ici.
     */ 
    public default String monPrint() {
        return String.format("Chambre " + getNumero() + " ("+ getType() +")");
    }

}
