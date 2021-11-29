package fr.iutfbleau.projetIHM2021FI2.API;
public enum TypeChambre {
    UNLS ("Un lit simple"), DEUXLS ("Deux lits simples"), UNLD ("Un lit double");

    private final String name;       

    private TypeChambre(String s) {
        name = s;
    }
}
