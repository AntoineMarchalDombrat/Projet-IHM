package fr.iutfbleau.projetIHM2021FI2.MNP;
import fr.iutfbleau.projetIHM2021FI2.API.*;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.lang.*;

/**
 * Usine non persistante stockant les réservations dans une structure de données permettant de simuler un ensemble.
 * 
 * L'hôtel est magique.
 * Il y a trop de chambres (3 millions, 1 million de chaque sorte).
 * on va juste prendre la prochaine chambre.
 * on ne fera même peut-être jamais le ménage (beurk).
 *
 * Par paresse, la réservation possède la même référence que la préréservation correspondante.
 *
 */
public class ReservationFactoryNP implements ReservationFactory{

    // On dispose de beaucoup de chambres.
    // On stocke le prochain numéro libre dans un map particulier dont les clé viennent d'un type énuméré (ici celui des chambres).
    // La valeur étant forcément un objet, il faut stocker un Integer plutôt qu'un int pour le numéro.
    // Par contre les Integer ne sont pas mutables. Pour incrémenter il faut aller chercher le prochain Integer.
    // Pour éviter de créer inutilement ce prochain Integer si il existe déjà, on va utiliser Integer.ValueOf(int)
    // https://docs.oracle.com/javase/8/docs/api/java/lang/Integer.html#valueOf-int-
    private EnumMap<TypeChambre,Integer> nextFreeRoom;

    // similaire pour stocker la valeur max, même un hotel magique devrait faire le ménage.
    private EnumMap<TypeChambre,Integer> brokenMagic;

    // BUGFIX. ^ c'est le xor en java donc 10^6 ça ne fait pas un million mais autre chose
    // Du coup en dessous j'écris 1000000 à la place.
    // plus petite et plus grande valeur pour les chambres UNLS
    private static int MIN_UNLS   = 51;
    private static int MAX_UNLS   = 76 -1;
    // plus petite et plus grande valeur pour les chambres DEUXLS
    private static int MIN_DEUXLS = 76;
    private static int MAX_DEUXLS = 2*101 -1;
    // plus petite et plus grande valeur pour les chambres UNLD
    private static int MIN_UNLD  =  2*1;
    private static int MAX_UNLD  =  3*51 -1;

    // plus grand nombre de chambres à proposer si le client souhaite choisir (va automatiquement salir les chambres précédentes)
    private static int ADEQUATE = 50;

    
    // Plutôt que d'utiliser un ensemble, on utilise un HashMap indexé par les dates car on va devoir chercher
    // les réservations par date pour faire des statistiques.
    // En effet, sinon il faudrait tout parcourir.
    //
    // Si par exemple, on faisait comme les préréservations en stockant une réservation par référence.
    // <pre>
    //     private HashMap<String,Reservation> brain;
    // </pre>
    //
    // On pourrait alors chercher les réservations comme ceci (mais au bout de plusieurs années ça risque d'être looooooong).
    // <pre>{@code
    //     Collection<Reservation> c = this.brain.values();
    //     // on définit un prédicat sur les Réservations :
    //     // il faut enlever les réservations telles que date de fin (date de debut + nbre jours) termine avant d
    //     // il faut enlever les réservations telles que date de début resa est après d
    //     Predicate<Reservation> filtre = resa -> resa.getDateDebut().isAfter(d) || resa.getDateDebut().plusDays(resa.getJours()).isBefore(d) ;
    //     // on filtre la collection.
    //     c.removeIf(filtre);
    //     // on fabrique un ensemble.
    //     Set<Reservation> found = new HashSet<Reservation>(c);
    //     return found;
    // }</pre>
    //
    // On va donc utiliser un hasmap indexé par les dates pour trouver directement l'information sans tout parcourir.
    // Toutefois, le hashMap ne peut contenir qu'une valeur pour une clé donnée.
    // Puisqu'on a en général plusieurs réservations pour une date donnée, il faut que la valeur soit un ensemble de réservations.
    //
    // Pour rester efficace pendant la recherche, on va supposer que l'espace pris par quelques pointeurs n'est pas un soucis et
    // une reservation va apparaître pour tous les jours concernés.
    // Alternativement, si il y avait une limite sur la durée d'une réservation, on pourrait décider de se passer de ces pointeurs et chercher autour d'une date dans cette limite
    // pour trouver une réservation. 
    //
    // Pour une recherche pour un type de chambre donné, il faudra filtrer en ne conservant que les réservations du type qui nous intéresse.
    //
    // Pour plus d'efficacité dans la recherche, on aurait pu utiliser un second niveau d'indexation en changeant Set<Reservation> par un 
    // EnumMap<TypeChambre,Set<Reservation>>
    // Mais bon il vaut mieux faire tout ça avec une base de données.
    // C'est précisément ce que l'index sous-jacent à la clé primaire ou un index secondaire bien choisi fera.
    // 
    private HashMap<LocalDate,Set<Reservation>> brain;

    // /**
    //  * Constructeur
    //  */
    public ReservationFactoryNP(){
        // voir https://docs.oracle.com/javase/8/docs/api/java/util/EnumMap.html#EnumMap-java.lang.Class-
        nextFreeRoom = new EnumMap<>(TypeChambre.class); 
        this.nextFreeRoom.put(TypeChambre.UNLS,Integer.valueOf(this.MIN_UNLS));
        this.nextFreeRoom.put(TypeChambre.DEUXLS,Integer.valueOf(this.MIN_DEUXLS));
        this.nextFreeRoom.put(TypeChambre.UNLD,Integer.valueOf(this.MIN_UNLD));
        
        brokenMagic = new EnumMap<>(TypeChambre.class); 
        this.brokenMagic.put(TypeChambre.UNLS,Integer.valueOf(this.MAX_UNLS));
        this.brokenMagic.put(TypeChambre.DEUXLS,Integer.valueOf(this.MAX_DEUXLS));
        this.brokenMagic.put(TypeChambre.UNLD,Integer.valueOf(this.MAX_UNLD));
        
        brain = new HashMap<LocalDate,Set<Reservation>>();
    }
    
    /**
     * Recherche une chambre adéquate à partir de
     * @param  p une  préréservation 
     * @return la chambre
     * @throws NullPointerException si un argument est null
     * @throws IllegalStateException si une chambre correspondant à cette Préréservation n'existe pas.
     *
     * Ne devrait pas retourner un objet null.
     */
    public Chambre getChambre(Prereservation p){
        Objects.requireNonNull(p,"La préréservation est null.");
        Integer numero = this.nextFreeRoom.get(p.getTypeChambre());
        if(numero.compareTo(this.brokenMagic.get(p.getTypeChambre())) >= 0){
            throw new IllegalStateException("Hotel Magique is sadly experiencing a lack of clean rooms of type "+ p.getTypeChambre());
        } 
        Chambre c = new ChambreNP(numero,p.getTypeChambre());
        return c;
    }

    /**
     * Recherche toutes les chambres adéquates à partir de
     * @param  p une  préréservation 
     * @return les chambres (set de chambre)
     * @throws NullPointerException si un argument est null
     * @throws IllegalStateException si une chambre correspondant à cette Préréservation n'existe pas.
     * Ne devrait pas retourner un objet null.
     *
     * NB. On considère que seulement les this.ADEQUATE prochaines chambres disponibles sont adéquates. 
     * 
     */
    public Set<Chambre> getChambres(Prereservation p){
        Objects.requireNonNull(p,"La préréservation est null.");
        Integer numero = this.nextFreeRoom.get(p.getTypeChambre());
        if(numero.compareTo(this.brokenMagic.get(p.getTypeChambre())) >= 0){
            throw new IllegalStateException("Hotel Magique is sadly experiencing a lack of clean rooms of type "+ p.getTypeChambre());
        }
        
        Set<Chambre> res = new HashSet<Chambre>();
        int howMany = Math.min(this.brokenMagic.get(p.getTypeChambre()) - this.nextFreeRoom.get(p.getTypeChambre()), this.ADEQUATE);            
        
        for (int i=0; i< howMany ;i++) {
            Chambre c = new ChambreNP(numero+i,p.getTypeChambre());
            res.add(c);
        }

        return res;
        
    }

    /*  
     * Prend en compte la réservation (ajout dans brain, mis à jour du prochain numéro)
     * @param r une réservation (qu'on suppose correcte)
     * 
     * NB. pas de vérification.
     * Utiliser les vérifications avant.
     * 
     */
    private void addReservationToBrain(Reservation r){
        // mise à jour de la prochaine chambre libre à 1 de plus que moi
        this.nextFreeRoom.put(r.getChambre().getType(),Integer.valueOf(r.getChambre().getNumero() + 1));        
        // Ajout pour toutes les dates de la réservation.
        LocalDate d = r.getDateDebut();
        for (int i=0; i< r.getJours() ;i++) {
            this.addReservationToBrain(r,d);
            d = d.plusDays(1); // Le jour suivant le matou revient.
        }
    }

    /*  
     * Ajout dans brain de la réservation à la date donnée.
     * @param r une réservation (qu'on suppose correcte)
     * 
     * NB. pas de vérification.
     * Utiliser les vérifications avant.
     * 
     */
    private void addReservationToBrain(Reservation r, LocalDate d){
        Set<Reservation> s = this.getReservation(d);
        s.add(r);
        this.brain.put(d,s);
    }

    public String prochainesChambresLibres(){
        StringBuilder sb = new StringBuilder("prochaines chambres libres par type:\n");
        for (TypeChambre t : TypeChambre.values()){
            sb.append("\t _ " + t + " " +  this.nextFreeRoom.get(t) + "\n");
        }
        return sb.toString();
    }
    
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
    public Reservation createReservation(Prereservation p, Chambre c){
        Objects.requireNonNull(p,"La préréservation est null.");
        Objects.requireNonNull(p,"La chambre est null.");
        if (c.getType()!=p.getTypeChambre()) {
            throw new IllegalArgumentException("Erreur sur le type de la chambre: la préréservation indique " + p.getTypeChambre() + " mais la chambre est  " + c.getType());
        }
        else if (false) // on fait comme si ça n'arrive jamais dans l'hôtel magique (pour l'instant).
            {
                throw new IllegalArgumentException("La chambre " + c.monPrint() + " n'est pas disponible pour fabriquer une réservation à partir de la préréservation " + p.monPrint());
            }
        else {
            Reservation r = new ReservationNP(p.getReference(), p.getDateDebut(), p.getJours(), c, p.getClient());
            this.addReservationToBrain(r);
            return r;
            }
    }

    /**
     * Cherche les réservations
     * @param  d une date
     * @return la ou les réservation(s) à cette date sous forme d'un ensemble
     * @throws NullPointerException si un argument est null
     *
     * Ne devrait pas retourner un objet null, par contre peut être un ensemble qui est vide.
     */    
    public Set<Reservation> getReservation(LocalDate d){
        Objects.requireNonNull(d,"La date proposée est null.");
        
        Set<Reservation> s;
        if (this.brain.containsKey(d)){
            s = this.brain.get(d);
            }
        else{
            s = new HashSet<Reservation>();
        }        
        return s;
    }

    /**
     * Cherche le nombre de chambres disponibles pour une date (réservées ou non).
     * @param  d une date
     * @return un entier
     * @throws NullPointerException si un argument est null
     *
     * Ne devrait pas retourner un entier négatif.
     */    
    public int getDisponibles(LocalDate d){
        throw new UnsupportedOperationException("pas encore implanté");
    }

    /**
     * Cherche les réservations
     * @param  d une date
     * @param  t un type de chambre
     * @return la ou les réservation(s) pour ce type de chambre à cette date sous forme d'un ensemble
     * @throws NullPointerException si un argument est null
     *
     * Ne devrait pas retourner un objet null, par contre peut être un ensemble qui est vide.
     */    
    public Set<Reservation> getReservation(LocalDate d, TypeChambre t){
        throw new UnsupportedOperationException("pas encore implanté");
    }
    
    /**
     * Cherche le nombre de chambres disponibles d'un certain type pour une date (réservées ou non).
     * @param  d une date
     * @param  t un type de chambre
     * @return un entier
     * @throws NullPointerException si un argument est null
     *
     * Ne devrait pas retourner un entier négatif.
     */    
    public int getDisponibles(LocalDate d, TypeChambre t){
        throw new UnsupportedOperationException("pas encore implanté");
    }
    

    /**
     * Cherche la proportion de chambres disponibles pour une date (réservées sur réservables).
     * @param  d une date
     * @return un entier entre 0 et 100
     * @throws NullPointerException si un argument est null
     */    
    public int getRatio(LocalDate d){
        throw new UnsupportedOperationException("pas encore implanté");
    }
    

    /**
     * Cherche la proportion de chambres disponibles d'un certain type pour une date (réservées sur réservables).
     * @param  d une date
     * @param  t un type de chambre
     * @return un entier entre 0 et 100
     * @throws NullPointerException si un argument est null
     */    
    public int getRatio(LocalDate d, TypeChambre t){
        throw new UnsupportedOperationException("pas encore implanté");
    }
    

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
    public int getDisponibles(LocalDate d1, LocalDate d2){
        throw new UnsupportedOperationException("pas encore implanté");
    }
    

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
    public Set<Reservation> getReservation(LocalDate d1, LocalDate d2, TypeChambre t){
        throw new UnsupportedOperationException("pas encore implanté");
    }
    
    
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
    public int getDisponibles(LocalDate d1, LocalDate d2, TypeChambre t){
        throw new UnsupportedOperationException("pas encore implanté");
    }
    

    /**
     * Cherche la <b>proportion moyenne</b> de chambres disponibles pour une date (réservées sur réservables).
     * @param  d1 une date
     * @param  d2 une date
     * @return un entier entre 0 et 100
     * @throws NullPointerException si un argument est null
     */    
    public int getRatio(LocalDate d1, LocalDate d2){
        throw new UnsupportedOperationException("pas encore implanté");
    }
    

    /**
     * Cherche la <b>proportion moyenne</b> de chambres disponibles d'un certain type pour une date (réservées sur réservables).
     * @param  d1 une date
     * @param  d2 une date
     * @param  t un type de chambre
     * @return un entier entre 0 et 100
     * @throws NullPointerException si un argument est null
     */    
    public int getRatio(LocalDate d1, LocalDate d2, TypeChambre t){
        throw new UnsupportedOperationException("pas encore implanté");
    }
    
    
}
