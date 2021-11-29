package fr.iutfbleau.projetIHM2021FI2.MNP;
import fr.iutfbleau.projetIHM2021FI2.API.*;
import java.util.*;
/**
 * Une chambre non persistante toute bête
 */

public class ChambreNP implements Chambre{

    private int numero;
    private TypeChambre type;

    /**
     * Constructeur
     */
    public ChambreNP(int numero, TypeChambre t){
        Objects.requireNonNull(t,"On ne peut pas créer une chambre avec un type de chambre à null.");
        this.numero=numero;
        this.type=t;
    }

    /**
     * permet de récupérer le numéro de la chambre.
     * @return le numéro.
     */
    public int getNumero(){
        return this.numero;
    }
    

    /**
     * permet de savoir si la chambre a un seul lit qui est simple
     * @return vrai si c'est le cas.
     */
    public boolean unLitSimple(){
        //https://stackoverflow.com/questions/1750435/comparing-java-enum-members-or-equals
        return (this.type == TypeChambre.UNLS);
    }

    /**
     * permet de savoir si la chambre a deux lits simples
     * @return vrai si c'est le cas.
     */
    public boolean deuxLitsSimples(){
        return (this.type == TypeChambre.DEUXLS);
    }

    /**
     * permet de savoir si la chambre a un lit double
     * @return vrai si c'est le cas.
     */
    public boolean unLitDouble(){
        return (this.type == TypeChambre.UNLD);
    }


    /**
     * @return le type de chambre (un type énuméré de l'API)
     *
     * NB. Les trois méthodes ci-dessus sont assez moches.
     * De toute façon Chambre ou Prérerservation exposent le type énuméré TypeChambre à la vue.
     * Il est donc plus simple d'ajouter des types de chambre à ce type énuméré plutôt que d'ajouter des tests ici.
     * Je laisse les méthodes obsolètes pour illustrer l'annotation <pre>@deprecated</pre> de la javadoc.
     */
    public TypeChambre getType(){
        return this.type;
    }

    
    // voir MonPrint dans l'interface Chambre
    // @Override
    // public String toString() {
    //     return String.format("Chambre " + this.numero + " ("+ this.type +")");
    // }
    
}
