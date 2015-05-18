package vue;

import java.awt.Color;
import java.awt.Graphics;
import modele.*;

import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * @author Epulapp
 */
public class CaseVue extends JPanel implements Observer {
    
    private int caseX;
    private int caseY;
    
    private BufferedImage imageCase;
    
    private Case modelCase;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public CaseVue(Case modelCase) {
       super();
       
       caseX = modelCase.getCaseX();
       caseY = modelCase.getCaseY();
       this.modelCase = modelCase;
       modelCase.addObserver(this);
       imageCase = null;
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
    
    //Fonction permettant de dessiner dans le composant JPanel
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int height = this.getSize().height;
        int width = this.getSize().width;
        
        g.drawImage(imageCase, 0, 0, width, height, this);
    }

    @Override
    public void update(Observable obs, Object obj) {
        
        Border blackBorder = BorderFactory.createLineBorder(Color.black, 1);
        
        if (obs instanceof Case) {
            imageCase = modelCase.getImageCase();
            paintComponent(this.getGraphics());
            this.setBorder(blackBorder);
        }
        else {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}