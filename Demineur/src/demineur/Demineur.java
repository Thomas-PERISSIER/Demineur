package demineur;

import modele.*;
import vue.*;

/**
 * @author Epulapp
 */
public class Demineur {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Création de la grille du démineur
        Grille grille = new Grille(10, 10);
        
        //Création de la vue principale
        Vue vue = new Vue(grille);
        vue.setVisible(true);
        
        
    }
}
