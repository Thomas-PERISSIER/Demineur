package demineur;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.*;
import vue.*;

/**
 * @author Epulapp
 */
public class Demineur {
    
    public static Boolean recommencerPartie = false;
    public static int secondeTimer = 0;
    public static int difficulteDemineur = 1;
    public static Grille grille;
    public static Vue vue;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        //Création du timer et du timerTask afin de comptabiliser le temps de jeu d'une partie
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            
            @Override
            public void run() 
            {
                //Permet de recommencer une partie de démineur
                if (recommencerPartie) {
                    //Réinitialise le timer et la grille
                    secondeTimer = 0;
                    grille = null;
                    
                    //Recrée une nouvelle grille
                    grille = new Grille(difficulteDemineur);
                    
                    try {
                        //Supprime la vue
                        vue.dispose();
                        
                        //Recrée une nouvelle vue
                        vue = new Vue(grille);
                        
                        //Rend visible la nouvelle vue
                        vue.setVisible(true);
                    } catch (IOException ex) {
                        Logger.getLogger(Demineur.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    recommencerPartie = false;
                }
                
                secondeTimer++;
                //Mise à jour du temps dans la vue principale
                vue.getTimeGame().setText(String.valueOf(secondeTimer));
            }
        };
        
        //Création de la grille du démineur
        grille = new Grille(difficulteDemineur);
        
        //Création de la vue principale
        vue = new Vue(grille);
        vue.setVisible(true);
        
        //Timer comptabilisant le temps (en secondes)
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }
    
}