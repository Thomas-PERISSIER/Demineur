package vue;

import java.awt.Color;
import java.awt.GridLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * @author Epulapp
 */
public class Vue extends JFrame implements Observer {
    
    public Vue() {
        super();
        
        build();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                super.windowClosing(arg0);
                System.exit(0);
            }
        });
    }
    
    public void build() {

        //JMenu jm = new JMenu();
        JMenuBar jm = new JMenuBar();
        JMenu m = new JMenu("Jeu");
        JMenuItem mi = new JMenuItem("Partie");

        //ItemListener i = new Item
        m.add(mi);
        jm.add(m);
        
        setJMenuBar(jm);
        
        setTitle("Ma première fenêtre");
        setSize(400, 400);
        JPanel pan = new JPanel(new GridLayout(10, 10));
        Border blackline = BorderFactory.createLineBorder(Color.black, 1);
        
        for (int i = 0; i < 100; i++) {
            JPanel caseVue = new CaseVue();
            caseVue.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                   //lien vers le modele
                }   
            });
            caseVue.setBorder(blackline);
            pan.add(caseVue);
        }
        pan.setBorder(blackline);
        add(pan);
        //setContentPane(pan);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }   
}