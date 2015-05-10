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
        Vue vue = new Vue();
        vue.setVisible(true);
        
        Grille grille = new Grille();
    }
    
}
