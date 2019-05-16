/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogosocketsudp;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author danie
 */
public class MainTela extends JFrame{
    ImageIcon superhomem = new ImageIcon(getClass().getResource("superman.gif"));
    ImageIcon ceu = new ImageIcon(getClass().getResource("ceu.jpg"));
    JLabel lSuperHomem = new JLabel(superhomem);
    JLabel lCeu = new JLabel(ceu);
    int x=300,y=300;
    
    public MainTela(){
        editarTela();
        addMovimento();
    }
    
    public void editarTela(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,700);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(null);
        lCeu.setBounds(0,0,800,700);
        lSuperHomem.setBounds(x,y, 320,400);
        add(lSuperHomem);
        add(lCeu);
    }
    
    public void addMovimento(){
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                 switch(e.getKeyCode()){
                    case 38:
                        y-=10;
                        break;
                    case 40:
                        y+=10;
                        break;
                    case 37:
                        x-=10;
                        break;
                    case 39:
                        x+=10;
                        break;
                }
                lSuperHomem.setBounds(x, y, 320, 400);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
            }
        });
    }
    
    public static void main(String args[]){
        new MainTela();
    }
    
}
