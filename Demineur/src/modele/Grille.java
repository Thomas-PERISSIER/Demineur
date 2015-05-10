package modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Epulapp
 */
public class Grille {
    
    private int heightGrille = 10;
    private int widthGrille = 10;
    private int nbCases = heightGrille * widthGrille;
    private int nbMines = 15;
    private int[][] tabCases = new int[heightGrille][widthGrille];

    public Grille(){
        List<Integer> tabMines = new ArrayList();
        
        //Recherche aléatoire de l'emplacement des cases qui contiennent une mine
        for(int i=0; i< nbMines; i++){
            int positionMineTemp = (int) (Math.random() * (nbCases - 1));
            if(!tabMines.contains(positionMineTemp)){
                tabMines.add(positionMineTemp);
            }
        }
        Collections.sort(tabMines);
        
        //Remplissage du tableau
        
    }
}