package vue;

import static demineur.Demineur.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import modele.*;

/**
 * @author Epulapp
 */
public class Vue extends JFrame {
    
    private JLabel nbCasesRestantes;
    private JLabel nbCasesMineRestantes;
    private JLabel timeGame;
    
    public Vue(Grille grille) throws IOException {
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
    
    public JLabel getTimeGame() {
        return timeGame; 
    }
    
    @SuppressWarnings("Convert2Lambda")
    public final void build(Grille grille) throws IOException {
        
        setTitle("Démineur");
        
        //Resize selon le niveau de difficulté (nombre de cases)
        switch (difficulteDemineur) {
            case 1:
                setSize(400, 450);
                break;
            case 2:
                setSize(800, 850);
                break;
        }
        setResizable(false);
        
        //Création du menu du jeu démineur
        JMenuBar jmb = new JMenuBar();
        
        //Pour la partie "Jeu"
        JMenu jmG = new JMenu("Jeu");
        JMenuItem jmiNG = new JMenuItem("Nouvelle partie");
        jmiNG.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                recommencerPartie = true;
            }
            
        });
        jmG.add(jmiNG);
        JMenuItem jmiQG = new JMenuItem("Quitter le jeu");
        jmiQG.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
            
        });
        jmG.add(jmiQG);
        
        //Ajout des items dans le menu
        jmb.add(jmG);
        
        //Pour la partie "Paramètres"
        JMenu jmP = new JMenu("Paramètres");
        JMenu jmD = new JMenu("Niveau de difficulté");
        JMenuItem jmiDifFacile = new JMenuItem("Facile");
        jmiDifFacile.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                difficulteDemineur = 1;
                recommencerPartie = true;
            }
            
        });
        jmD.add(jmiDifFacile);
        JMenuItem jmiDifNormal = new JMenuItem("Normal");
        jmiDifNormal.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                difficulteDemineur = 2;
                recommencerPartie = true;
            }
            
        });
        jmD.add(jmiDifNormal);
        
        jmP.add(jmD);
        jmiDifFacile.setSelected(true);
        
        //Ajout des items dans le menu
        jmb.add(jmP);
        
        //Ajout du menu dans le fenêtre du jeu démineur
        setJMenuBar(jmb);
        
        //Création du layout principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        
        Border blackBorderRaise = BorderFactory.createRaisedBevelBorder();
        
        //Création du JPanel des caractéristiques
        JPanel panelCaractGame = new JPanel();
        panelCaractGame.setLayout(new BoxLayout(panelCaractGame, BoxLayout.X_AXIS));
        
        //Compteur du nombre de cases non visibles
        Image imageCaseVide = ImageIO.read(new File("./images/vide.PNG"));
        ImageIcon iconCaseVide = new ImageIcon(imageCaseVide);
        JLabel libNbCasesRestantes = new JLabel(iconCaseVide);
        panelCaractGame.add(libNbCasesRestantes);
        panelCaractGame.add(Box.createRigidArea(new Dimension(10,0)));
        nbCasesRestantes = new JLabel(String.valueOf(grille.getNbCases() - grille.getNbCasesNVides()));
        panelCaractGame.add(nbCasesRestantes);
        
        if (difficulteDemineur == 1) {
            panelCaractGame.add(Box.createRigidArea(new Dimension(110,0)));
        } else {
            panelCaractGame.add(Box.createRigidArea(new Dimension(310,0)));
        }
        
        //Compteur du nombre de cases restantes pour les mines
        Image imageCaseMine = ImageIO.read(new File("./images/mine.png"));
        ImageIcon iconCaseMine = new ImageIcon(imageCaseMine);
        JLabel libNbCasesMineRestantes = new JLabel(iconCaseMine);
        panelCaractGame.add(libNbCasesMineRestantes);
        panelCaractGame.add(Box.createRigidArea(new Dimension(10,0)));
        nbCasesMineRestantes = new JLabel(String.valueOf(grille.getNbMines() - grille.getNbDrapeau()));
        panelCaractGame.add(nbCasesMineRestantes);
        
        if (difficulteDemineur == 1) {
            panelCaractGame.add(Box.createRigidArea(new Dimension(110,0)));
        } else {
            panelCaractGame.add(Box.createRigidArea(new Dimension(310,0)));
        }
        
        //Timer (en secondes)
        timeGame = new JLabel(String.valueOf(secondeTimer));
        panelCaractGame.add(timeGame);
        panelCaractGame.add(Box.createRigidArea(new Dimension(10,0)));
        
        panelCaractGame.setBorder(blackBorderRaise);
        panelPrincipal.add(panelCaractGame, CENTER_ALIGNMENT);
        
        //Création du JPanel de jeu
        JPanel panelPrincipalGame = new JPanel(new GridLayout(grille.getWidthGrille(), grille.getHeightGrille()));
        
        //Création de l'ensemble des JPanel pour les cases du démineur
        DecimalFormat df;
        if (difficulteDemineur == 1) {
            df = new DecimalFormat("0.0");
        } else {
            df = new DecimalFormat("0.00");
        }
        
        double positionCase;
        String positionCaseStr;
        int positionCaseX, positionCaseY;
        int positionCarac;
        
        //Création de l'ensemble des cases de la vue
        for (int i = 0; i < grille.getNbCases(); i++) {
            positionCase = (float) i / grille.getWidthGrille();
            positionCaseStr = df.format(positionCase);
            positionCarac = positionCaseStr.indexOf(",");
            
            if (difficulteDemineur == 1) {
                positionCaseX = Integer.parseInt(positionCaseStr.substring(0, positionCarac));
                positionCaseY = Integer.parseInt(positionCaseStr.substring(positionCarac + 1));
            } else {
                positionCaseX = Integer.parseInt(positionCaseStr.substring(0, positionCarac));
                positionCaseY = Integer.parseInt(positionCaseStr.substring(positionCarac + 1)) / 5;
            }
            
            CaseVue caseGrille = new CaseVue(grille.getTabCases()[positionCaseX][positionCaseY]);
            
            //Mise en lien avec la case du modèle
            caseGrille.addMouseListener(new MouseAdapter() {
                @SuppressWarnings("Convert2Lambda")
                //Evénements liés à la souris
                public void mousePressed(MouseEvent mouseEvent) {
                    if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                        grille.setClickOnEOTGame(caseGrille.getCase().caseASMine());
                        nbCasesRestantes.setText(String.valueOf(grille.getNbCases() - grille.getNbCasesNVides()));
                        nbCasesMineRestantes.setText(String.valueOf(grille.getNbMines()- grille.getNbDrapeau()));
                        if (grille.getClickOnEOTGame()) {
                            //Création de la fenêtre de dialogue pour la fin de partie
                            JDialog fenEOTGame = new JDialog();
                            fenEOTGame.setTitle("Fin de la partie");
                            fenEOTGame.setSize(400, 140);
                            fenEOTGame.addWindowListener(new WindowAdapter() {
                                @Override
                                //Fonction de fermeture du démineur
                                public void windowClosing(WindowEvent arg0) {
                                    super.windowClosing(arg0);
                                    System.exit(0);
                                }
                            });
                            
                            Box boxEOTGame = Box.createVerticalBox();
                            boxEOTGame.setAlignmentX(Component.CENTER_ALIGNMENT);
                            
                            JPanel panEOTGame = new JPanel();
                            JLabel messageEOTGame;
                            if (grille.getWinGame()) {
                                messageEOTGame = new JLabel("La partie est terminée et vous avez gagné !");
                            }
                            else {
                                messageEOTGame = new JLabel("La partie est terminée et vous avez perdu !");
                            }
                            panEOTGame.add(messageEOTGame);
                            panEOTGame.add(Box.createRigidArea(new Dimension(0, 10)));
                            JLabel message2EOTGame = new JLabel("Souhaitez-vous recommencer une partie ?");
                            panEOTGame.add(message2EOTGame);
                            boxEOTGame.add(panEOTGame);
                               
                            JPanel buttonEOTGame = new JPanel();
                            JButton yesBeginGame = new JButton("Oui");
                            
                            //Bouton pour recommencer une partie de démineur
                            yesBeginGame.addActionListener(new ActionListener() {
                                
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    recommencerPartie = true;
                                    fenEOTGame.dispose();
                                }
                            });
                            
                            buttonEOTGame.add(yesBeginGame);
                            buttonEOTGame.add(Box.createRigidArea(new Dimension(10, 0)));
                            
                            //Bouton qui permet alors de quitter le jeu
                            JButton noBeginGame = new JButton("Non");
                            
                            noBeginGame.addActionListener(new ActionListener() {
                                
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    System.exit(0);
                                }
                            });
                            buttonEOTGame.add(noBeginGame);
                            boxEOTGame.add(buttonEOTGame);
                            
                            fenEOTGame.add(boxEOTGame);
                            fenEOTGame.setModal(true);
                            fenEOTGame.setVisible(true);
                        }
                    }
                    else if (SwingUtilities.isRightMouseButton(mouseEvent)) {
                        try {
                            //Appeler la méthode d'ajout d'un drapeau
                            if (grille.getNbMines() - grille.getNbDrapeau() > 0) {
                                caseGrille.getCase().ajouterImageDrapeau();
                                
                                //Mise à jour du panel caractéristique
                                nbCasesRestantes.setText(String.valueOf(grille.getNbCases() - grille.getNbCasesNVides()));
                                nbCasesMineRestantes.setText(String.valueOf(grille.getNbMines()- grille.getNbDrapeau()));
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(Vue.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            caseGrille.setBorder(blackBorderRaise);
            panelPrincipalGame.add(caseGrille);
        }
        panelPrincipalGame.setBorder(blackBorderRaise);
        panelPrincipal.add(panelPrincipalGame);
        
        add(panelPrincipal);
    }
}