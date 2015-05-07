package demineur;

import modele.Grille;
import vue.Vue;

/**
 * @author Epulapp
 */
public class Demineur {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Vue vue = new Vue();
        vue.setVisible(true);
        
        Grille grille = new Grille();
    }
    
}
