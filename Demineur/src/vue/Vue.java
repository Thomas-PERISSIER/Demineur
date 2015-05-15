package vue;

import modele.*;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

/**
 * @author Epulapp
 */
public class Vue extends JFrame implements Observer {
    
    public Vue(Grille grille) {
        super();
        
        //Créer la fenêtre principale
        build(grille);
        
        addWindowListener(new WindowAdapter() {
            @Override
            //Fonction de fermeture du démineur
            public void windowClosing(WindowEvent arg0) {
                super.windowClosing(arg0);
                System.exit(0);
            }
        });
    }
    
    private void build(Grille grille) {
        
        setTitle("Démineur");
        setSize(400, 400);
        
        //Création du menu du jeu démineur
        JMenuBar jmb = new JMenuBar();
        JMenu jm = new JMenu("Jeu");
        JMenuItem jmi = new JMenuItem("Nouvelle partie");
        
        //Ajout des items dans le menu
        jm.add(jmi);
        jmb.add(jm);
        
        //Ajout du menu dans le fenêtre du jeu démineur
        setJMenuBar(jmb);
        
        //Création du JPanel principal
        JPanel panelPrincipal = new JPanel(new GridLayout(grille.getWidthGrille(), grille.getHeightGrille()));
        Border blackBorder = BorderFactory.createLineBorder(Color.black, 1);
        
        //Création de l'ensemble des JPanel pour le démineur
        for (int i = 0; i < grille.getNbCases(); i++) {
            CaseVue caseGrille = new CaseVue(grille.getListCases().get(i));
            caseGrille.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent mouseEvent) {
                    if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                        
                    }
                    else if (SwingUtilities.isRightMouseButton(mouseEvent)) {
                        try {
                            //Appeler la méthode d'ajout d'un drapeau
                            caseGrille.getCase().ajouterImageDrapeau();
                        } catch (IOException ex) {
                            Logger.getLogger(Vue.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            caseGrille.setBorder(blackBorder);
            panelPrincipal.add(caseGrille);
        }
        panelPrincipal.setBorder(blackBorder);
        add(panelPrincipal);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }   
}