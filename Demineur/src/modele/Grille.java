package modele;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Epulapp
 */
public class Grille {
    
    private int heightGrille;
    private int widthGrille;
    private int nbCases;
    private int nbMines;
    private int[][] tabCases;

    public Grille(int widthGrille, int heightGrille) {
        this.widthGrille = widthGrille;
        this.heightGrille = heightGrille;
        nbCases = this.heightGrille * this.widthGrille;
        nbMines = nbCases / 10;
        
        List<String> tabMines = new ArrayList();
        double positionMineTemp;
        String strTemp;
        DecimalFormat df = new DecimalFormat("#.0");
        
        //Recherche aléatoire de l'emplacement des cases qui contiennent une mine
        for (int i = 0; i < nbMines; i++) {
            positionMineTemp = (Math.random() * (nbCases - 1)) / 10;
            strTemp = df.format(positionMineTemp);
            positionMineTemp = Double.parseDouble(strTemp.replace(',', '.'));
            if (!tabMines.contains(String.valueOf(positionMineTemp))) {
                tabMines.add(String.valueOf(positionMineTemp));
            }
            else {
                i--;
            }
        }
        Collections.sort(tabMines);
        
        int positionMineX, positionMineY;
        tabCases = new int[this.heightGrille][this.widthGrille];
        
        //Remplissage du tableau
        int i = 0;
        positionMineX = Integer.parseInt(tabMines.get(i).substring(0, 1));
        positionMineY = Integer.parseInt(tabMines.get(i).substring(2));        
        for (int x = 0; x < this.heightGrille; x++) {
            for (int y = 0; y < this.widthGrille; y++) {
                if (x == positionMineX && y == positionMineY) {
                    
                    tabCases[x][y] = 1; //Si une mine est présente
                    
                    if (i < tabMines.size() - 1) {
                        i++;
                        
                        positionMineX = Integer.parseInt(tabMines.get(i).substring(0, 1));
                        positionMineY = Integer.parseInt(tabMines.get(i).substring(2));  
                    }
                }
                else {
                    tabCases[x][y] = 0; //Si pas de mine
                }
            }
        }
    }
    
    public int getHeightGrille() {
        return heightGrille;
    }
    
    public void setHeightGrille(int heightGrille) {
        this.heightGrille = heightGrille;
    }
    
    public int getWidthGrille() {
        return widthGrille;
    }
    
    public void setWidthGrille(int widthGrille) {
        this.widthGrille = widthGrille;
    }
    
    public int getNbCases() {
        return nbCases;
    }
    
    public int[][] getTabCases() {
        return tabCases;
    }
}