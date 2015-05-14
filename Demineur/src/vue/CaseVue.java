package vue;

import java.awt.image.BufferedImage;
import modele.*;

import javax.swing.JPanel;

/**
 * @author Epulapp
 */
public class CaseVue extends JPanel {
    
    private int caseX;
    private int caseY;
    
    private Case modelCase;
    private BufferedImage imageCase;
    
    public CaseVue(int i) {
       super();
       
       double positionCase = i / 10;
       String strTemp = String.valueOf(positionCase);
       caseX = Integer.parseInt(strTemp.substring(0, 1));
       caseY = Integer.parseInt(strTemp.substring(2));
       
       modelCase = new Case(caseX, caseY);
    }
    
    public int getCaseX() {
        return caseX;
    }
    
    public int getCaseY() {
        return caseY;
    }
    
    public Case getCase() {
        return modelCase;
    }
    
    public void setImageCase(BufferedImage imageCase) {
        this.imageCase = imageCase;
    }
    
    public BufferedImage getImageCase() {
        return imageCase;
    }
}