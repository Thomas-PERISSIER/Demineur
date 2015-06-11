package modele;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javax.imageio.ImageIO;

/**
 * @author Epulapp
 */
public class Case extends Observable {
    
    private char typeCase;
    private Image imageCase;
    private int nbImageCase; //Nombre de mines adjacentes
    private Boolean caseVisible;
    private Boolean caseDrapeau;
    private Boolean caseExDrapeau;
    
    private Grille grille;
    
    public Case(char typeCase, Grille grille) {
        this.typeCase = typeCase;
        imageCase = null;
        caseVisible = false;
        caseDrapeau = false;
        caseExDrapeau = false;
        
        this.grille = grille;
    }
    
    public char getTypeCase() {
        return typeCase;
    }
    
    public void setCaseVisible(Boolean caseVisible) {
        this.caseVisible = caseVisible;
    }
    
    public Boolean getCaseVisible() {
        return caseVisible;
    }
    
    public void setCaseDrapeau(Boolean caseDrapeau) {
        this.caseDrapeau = caseDrapeau;
    }
    
    public Boolean getCaseDrapeau() {
        return caseDrapeau;
    }
    
    public void setCaseExDrapeau(Boolean caseExDrapeau) {
        this.caseExDrapeau = caseExDrapeau;
    }
    
    public Boolean getCaseExDrapeau() {
        return caseExDrapeau;
    }
    
    public void setImageCase(Image imageCase) {
        this.imageCase = imageCase;
    }
    
    public Image getImageCase() {
        return imageCase;
    }
    
    public void setNbImageCase(int nbImageCase) {
        this.nbImageCase = nbImageCase;
    }
    
    public int getNbImageCase() {
        return nbImageCase;
    }
    
    //Fonction de test : la case a une Mine... ou pas...
    public Boolean caseASMine() throws IOException {
        
        if (!caseVisible && !caseDrapeau) {
            switch (typeCase) {
                //La case ne possède pas de mine
                case 'V':
                    imageCase = null;
                    //Propagation lorsqu'il n'y a pas de cases adjacente possèdant une mine
                    if (nbImageCase == 0) {
                        propCases();
                    }
                    else {
                        //Comptabilise le nombre de cases visibles
                        grille.setNbCasesNVides(grille.getNbCasesNVides() + 1);
                    }
                    caseVisible = true;
                    //Notification aux observeurs...
                    notifObservers();
                    grille.testEOTGame();
                    break;
                case 'M':
                    //Fin de partie en affichant les mines sur la grille
                    grille.propCasesAMine();
                    break;
            }
        }
        
        return grille.getClickOnEOTGame();
    }
    
    //Fonction d'ajout du drapeau...
    public void ajouterImageDrapeau() throws IOException {
        
        if (!caseVisible || caseDrapeau) {
            //Ajout d'un drapeau si il n'y en a pas déjà
            if (!caseDrapeau) {
                //Importation de l'image du drapeau
                Image imageCaseTmp = ImageIO.read(new File("images/flag.png"));
                imageCase = imageCaseTmp;
                caseVisible = true;
                caseDrapeau = true;
                caseExDrapeau = true;
                //Comptabilise le nombre de cases ayant un drapeau
                grille.setNbCasesNVides(grille.getNbCasesNVides() + 1);
                grille.setNbDrapeau(grille.getNbDrapeau() + 1);
            }
            //Retirer le drapeau s'il y en a déjà un
            else {
                imageCase = null;
                caseVisible = false;
                caseDrapeau = false;
                //Comptabilise le nombre de cases ayant un drapeau
                grille.setNbCasesNVides(grille.getNbCasesNVides() - 1);
                grille.setNbDrapeau(grille.getNbDrapeau() - 1);
            }
            
            //Notification aux observeurs...
            notifObservers();
        }
    }
    
    //Fonction de propagation sur les cases adjacentes...
    private void propCases() throws IOException {
        
        //Retourne les cases adjacentes
        List<Case> caseADJ = new ArrayList<>();
        caseADJ = grille.casesADJ(this);
        
        //Pour chacune d'entres elle, on affiche ou propage..
        //On comptablise également le nombre de cases affichées
        for (Case caseADJ1 : caseADJ) {
            if (!caseADJ1.getCaseVisible()) {
                caseADJ1.setCaseVisible(true);
                grille.setNbCasesNVides(grille.getNbCasesNVides() + 1);
                if (caseADJ1.getNbImageCase() == 0) {
                    caseADJ1.propCases();
                }
                //Notification aux observeurs...
                caseADJ1.notifObservers();
            }
        }
    }
    
    //Fonction de notifications aux observeurs...
    public void notifObservers() {
        this.setChanged();
        this.notifyObservers();
    }
}