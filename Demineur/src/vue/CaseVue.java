package vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import modele.*;

/**
 * @author Epulapp
 */
public class CaseVue extends JPanel implements Observer {
    
    private Image imageCase;
    private int nbImageCase;
    
    private Case modelCase;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public CaseVue(Case modelCase) {
        super();
        
        this.modelCase = modelCase;
        modelCase.addObserver(this);
        imageCase = null;
        nbImageCase = 0;
    }
    
    public Case getCase() {
        return modelCase;
    }
    
    public void setImageCase(Image imageCase) {
        this.imageCase = imageCase;
    }
    
    public Image getImageCase() {
        return imageCase;
    }
    
    public int getNbImageCase() {
        return nbImageCase;
    }
    
    //Fonction permettant de dessiner dans un JPanel
    //Il est courant en Java de redéfinir la méthode paint pour dessiner dans un JPanel (méthode la plus simple)
    public void paint(Graphics g) {
        super.paint(g);
        
        Border blackBorderLower = BorderFactory.createLoweredBevelBorder();
        Border blackBorderRaise = BorderFactory.createRaisedBevelBorder();
        
        //Si la case n'est pas déjà visible
        if (modelCase.getCaseVisible()) {
            //Chargement d'une image : mine ou drapeau
            if (imageCase != null) {
                g.drawImage(imageCase, 0, 0, this.getWidth(), this.getHeight(), null);
                this.setBorder(blackBorderLower);
            }
            //Ecriture du nombre de mines adjacentes
            else if (nbImageCase != 0 && !modelCase.getCaseExDrapeau()) {
                Font ft = new Font("Arial", Font.BOLD, 40);
                this.setFont(ft);
                
                switch (nbImageCase) {
                    case 1:
                        this.setForeground(Color.BLUE);
                        break;
                    case 2:
                        this.setForeground(Color.GREEN);
                        break;
                    case 3:
                        this.setForeground(Color.RED);
                        break;
                    case 4:
                        this.setForeground(Color.GRAY);
                        break;
                    case 5:
                        this.setForeground(Color.ORANGE);
                        break;
                    case 6:
                        this.setForeground(Color.BLACK);
                        break;
                }
                g.drawString(String.valueOf(nbImageCase), this.getWidth() / 4, (int) (this.getHeight() / 1.08));
                this.setBorder(blackBorderLower);
            }
            //Sans mine et mines adjacentes
            else {
                this.setBorder(blackBorderLower);
            }
        }
        //Enlever une mine
        else if (modelCase.getCaseExDrapeau()) {
            modelCase.setCaseExDrapeau(Boolean.FALSE);
            this.setBorder(blackBorderRaise);
        }
    }

    @Override
    public void update(Observable obs, Object obj) {
        
        if (obs instanceof Case) {
            if (modelCase.getNomImage() != "") {
                try {
                    imageCase = ImageIO.read(new File(modelCase.getNomImage()));
                } catch (IOException ex) {
                    Logger.getLogger(CaseVue.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            nbImageCase = modelCase.getNbImageCase();
            
            //Appel de la fonction permettant de dessiner dans un JPanel
            paint(this.getGraphics());
        }
        else {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}