package modele;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import javax.imageio.ImageIO;

/**
 * @author Epulapp
 */
public class Case extends Observable {
    
    private int numCase;
    private int caseX;
    private int caseY;
    private char typeCase;
    private BufferedImage imageCase;
    private Boolean caseVisible;
    private Boolean caseDrapeau;
    
    private int nbCases;
    private Grille grille;
    private List<Case> caseADJ;
    
    public Case(int numCase, int caseX, int caseY, int nbCases, char typeCase, Grille grille) {
        this.numCase = numCase;
        this.caseX = caseX;
        this.caseY = caseY;
        this.typeCase = typeCase;
        imageCase = null;
        caseVisible = false;
        caseDrapeau = false;
        
        this.nbCases = nbCases;
        this.grille = grille;
        
        listCaseADJ();
    }
    
    public int getNumCase() {
        return numCase;
    }
    
    public int getCaseX() {
        return caseX;
    }
    
    public int getCaseY() {
        return caseY;
    }
    
    public char getTypeCase() {
        return typeCase;
    }
    
    public Boolean getCaseVisible() {
        return caseVisible;
    }
    
    public Boolean getCaseDrapeau() {
        return caseDrapeau;
    }
    
    public void setImageCase(BufferedImage imageCase) {
        this.imageCase = imageCase;
    }
    
    public BufferedImage getImageCase() {
        return imageCase;
    }
    
    //Fonction de création du tableau des cases adjacentes...
    private void listCaseADJ() {
        
        if (numCase == 0) {
            
        }
        else if (numCase == nbCases) {
            
        }
        else {
            caseADJ.add(grille.getListCases().get(caseX));
        }
        
    }
    
    //Fonction de test : la case a une Mine... ou pas...
    public boolean caseAMine() {
        
        boolean isEnd = false;
        
        if (!caseVisible) {
            switch (typeCase) {
            case 'V':
                
                break;
            case 'M':
                isEnd = true;
                break;
            }
            caseVisible = true;
        }
        
        return isEnd;
    }
    
    //Fonction d'ajout du drapeau...
    public void ajouterImageDrapeau() throws IOException {
        if (!caseDrapeau) {
            BufferedImage imageCaseTmp = ImageIO.read(new File("images/flag.png"));
            imageCase = imageCaseTmp;
            caseDrapeau = true;
        }
        else {
            imageCase = null;
            caseDrapeau = false;
        }
        //Notification aux observeurs...
        this.setChanged();
        this.notifyObservers();
    }
}