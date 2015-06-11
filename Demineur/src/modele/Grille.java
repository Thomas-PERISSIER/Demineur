package modele;

import static demineur.Demineur.*;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * @author Epulapp
 */
public class Grille {
    
    private int heightGrille;
    private int widthGrille;
    private int nbCases;
    private int nbCasesNVides; //Nombre de cases visibles
    private int nbMines;
    private int nbDrapeau;
    private Case[][] tabCases;
    private HashMap<Case, Point> hmCases;
    private Boolean clickOnEOTGame;
    private Boolean winGame;

    public Grille(int difficulteDemineur) {
        if (difficulteDemineur == 1) {
            widthGrille = 10;
            heightGrille = 10;
        } else {
            widthGrille = 20;
            heightGrille = 20;
        }
        
        createGrille(widthGrille, heightGrille);
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
    
    public void setNbCasesNVides(int nbCasesNVides) {
        this.nbCasesNVides = nbCasesNVides;
    }
    
    public int getNbCasesNVides() {
        return nbCasesNVides;
    }
    
    public int getNbMines() {
        return nbMines;
    }
    
    public void setNbDrapeau(int nbDrapeau) {
        this.nbDrapeau = nbDrapeau;
    }
    
    public int getNbDrapeau() {
        return nbDrapeau;
    }
    
    public Case[][] getTabCases() {
        return tabCases;
    }
    
    public void setClickOnEOTGame(Boolean clickOnEOTGame) {
        this.clickOnEOTGame = clickOnEOTGame;
    }
    
    public Boolean getClickOnEOTGame() {
        return clickOnEOTGame;
    }
    
    public void setWinGame(Boolean winGame) {
        this.winGame = winGame;
    }
    
    public Boolean getWinGame() {
        return winGame;
    }
    
    //Fonction qui créée la grille
    public final void createGrille(int widthGrille, int heightGrille) {
        this.widthGrille = widthGrille;
        this.heightGrille = heightGrille;
        nbCases = this.heightGrille * this.widthGrille;
        nbMines = nbCases / 5;
        
        clickOnEOTGame = false;
        winGame = false;
        
        List<String> tabMines = new ArrayList();
        double positionMineTemp;
        String strgTemp;
        int positionMineX, positionMineY;
        int positionCarac;

        DecimalFormat df;
        if (difficulteDemineur == 1) {
            df = new DecimalFormat("0.0");
        } else {
            df = new DecimalFormat("0.00");
        }
        
        //Recherche aléatoire de l'emplacement des cases qui contiennent une mine
        //Mise en forme différente selon le niveau de difficulté...
        for (int i = 0; i < nbMines; i++) {
            if (difficulteDemineur == 1) {
                positionMineTemp = (Math.random() * (nbCases - 1)) / widthGrille;
                strgTemp = df.format(positionMineTemp);
            } else {
                positionMineTemp = (Math.random() * (nbCases - 1)) / widthGrille;
                
                strgTemp = df.format(positionMineTemp);
                positionCarac = strgTemp.indexOf(",");
                positionMineX = Integer.parseInt(strgTemp.substring(0, positionCarac));
                positionMineY = Integer.parseInt(strgTemp.substring(positionCarac + 1)) / 5;
                
                strgTemp = positionMineX + "," + positionMineY;
            }
            
            positionMineTemp = Double.parseDouble(strgTemp.replace(',', '.'));
            if (!tabMines.contains(String.valueOf(positionMineTemp))) {
                tabMines.add(String.valueOf(positionMineTemp));
            }
            else {
                i--;
            }
        }
        Collections.sort(tabMines);
        
        hmCases = new HashMap<>();
        tabCases = new Case[this.widthGrille][this.heightGrille];
        
        //Remplissage du tableau contenant les cases de la grille
        int i = 0;
        strgTemp = tabMines.get(i);
        positionCarac = strgTemp.indexOf(".");

        positionMineX = Integer.parseInt(strgTemp.substring(0, positionCarac));
        positionMineY = Integer.parseInt(strgTemp.substring(positionCarac + 1));
        while (i < tabMines.size() - 1) {
            for (int x = 0; x < this.heightGrille; x++) {
                for (int y = 0; y < this.widthGrille; y++) {
                    if (tabCases[x][y] == null || tabCases[x][y].getTypeCase() != 'M') {
                        //Si il y a mine
                        if (x == positionMineX && y == positionMineY) {
                            Case caseModel = new Case('M', this);
                            hmCases.put(caseModel, new Point(x, y));
                            tabCases[x][y] = caseModel; //Si une mine est présente

                            if (i < tabMines.size() - 1) {
                                i++;

                                strgTemp = tabMines.get(i);
                                positionCarac = strgTemp.indexOf(".");

                                positionMineX = Integer.parseInt(strgTemp.substring(0, positionCarac));
                                positionMineY = Integer.parseInt(strgTemp.substring(positionCarac + 1));
                            }
                        }
                        //Si il n'y a pas de mine
                        else {
                            Case caseModel = new Case('V', this);
                            hmCases.put(caseModel, new Point(x, y));
                            tabCases[x][y] = caseModel; //Si pas de mine
                        }
                    }
                }
            }
        }
        
        //Enregistre le nombre de mines adjacentes pour chaque case du tableau
        int compteurMines;
        for (int x = 0; x < this.heightGrille; x++) {
            for (int y = 0; y < this.widthGrille; y++) {
                compteurMines = cpteMinesCasesADJ(x, y);
                tabCases[x][y].setNbImageCase(compteurMines); 
            }
        }
    }
    
    //Fonction de calcul des mines sur les cases adjacentes...
    private int cpteMinesCasesADJ(int caseX, int caseY) {
        
        int compteurMines = 0;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (((caseX + x) >= 0) && ((caseX + x) < heightGrille) && ((caseY + y) >= 0) && ((caseY + y) < widthGrille) && (x != 0 || y != 0)) {
                    if (tabCases[caseX + x][caseY + y].getTypeCase() == 'M')
                        compteurMines++;
                }
            }
        }
        
        return compteurMines;
    }
    
    //Fonction qui renvoie les cases adjacentes...
    public List<Case> casesADJ(Case maCase) {
        
        int caseX = (int) hmCases.get(maCase).getX();
        int caseY = (int) hmCases.get(maCase).getY();
        
        List<Case> caseADJ = new ArrayList<>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (((caseX + x) >= 0) && ((caseX + x) < heightGrille) && ((caseY + y) >= 0) && ((caseY + y) < widthGrille)) {
                    caseADJ.add(tabCases[caseX + x][caseY + y]);
                }
            }
        }
        
        return caseADJ;
    }
    
    //Fonction qui rend visible toutes les mines de la grille (fin de partie)
    public void propCasesAMine()  {
        
        for (int x = 0; x < this.heightGrille; x++) {
            for (int y = 0; y < this.widthGrille; y++) {
                if (!tabCases[x][y].getCaseVisible() && tabCases[x][y].getTypeCase() == 'M') {
                    tabCases[x][y].setCaseVisible(true);
                    tabCases[x][y].setNomImage("./images/mine.png");
                    nbCasesNVides++;
                    tabCases[x][y].notifObservers();
                }
            }
        }
        //Afin de mettre 0 au nombre de mines restant
        nbDrapeau = nbMines;
        
        winGame = false;
        clickOnEOTGame = true;
    }
    
    //Fonction qui test si le démineur est terminé
    public void testEOTGame() {
        
        Boolean testEOTGame = true;
        for (int x = 0; x < this.heightGrille; x++) {
            for (int y = 0; y < this.widthGrille; y++) {
                if (!tabCases[x][y].getCaseVisible() && tabCases[x][y].getTypeCase() == 'V') {
                    testEOTGame = false;
                }
            }
        }
        
        if (testEOTGame) {
            winGame = true;
            clickOnEOTGame = true;
        }
    }
    
}