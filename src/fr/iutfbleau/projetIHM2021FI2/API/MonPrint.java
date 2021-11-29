package fr.iutfbleau.projetIHM2021FI2.API;
/** 
 * Toutes nos interfaces vont étendre cette interface.
 */
public interface MonPrint {

    /**
     * Fonctionne comme ToPrint() sauf car une interface n'a pas le droit de faire un override sur ToPrint().
     * Toutes nos interfaces vont étendre cette interface.
     */
    public String monPrint();
}
