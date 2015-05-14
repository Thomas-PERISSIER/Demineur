package modele;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author Epulapp
 */
public class Case {
    
    private int caseX;
    private int caseY;
    
    private BufferedImage imageCase;
    
    public Case(int caseX, int caseY) {
        this.caseX = caseX;
        this.caseY = caseY;
    }
    
    public int getCaseX() {
        return caseX;
    }
    
    public int getCaseY() {
        return caseY;
    }
    
    public void setImageCase(BufferedImage imageCase) {
        this.imageCase = imageCase;
    }
    
    public BufferedImage getImageCase() {
        return imageCase;
    }
    
    public void ajouterImageDrapeau() throws IOException {
        BufferedImage imageCaseTmp = ImageIO.read(new File("../../images/flag.png"));
        setImageCase(imageCaseTmp);
    }
}