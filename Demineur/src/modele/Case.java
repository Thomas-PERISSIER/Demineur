package modele;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import javax.imageio.ImageIO;

/**
 * @author Epulapp
 */
public class Case extends Observable {
    
    private int caseX;
    private int caseY;
    
    private char typeCase;
    
    private BufferedImage imageCase;
    
    private Boolean caseVisible;
    private Boolean caseDrapeau;
    
    public Case(int caseX, int caseY, char typeCase) {
        this.caseX = caseX;
        this.caseY = caseY;
        
        this.typeCase = typeCase;
        caseVisible = false;
        caseDrapeau = false;
        imageCase = null;
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
        notifyObservers(this);
    }
}