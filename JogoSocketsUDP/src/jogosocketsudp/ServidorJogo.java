/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogosocketsudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;

/**
 *
 * @author danie
 */
public class ServidorJogo extends JFrame{
    ImageIcon superhomem = new ImageIcon(getClass().getResource("superman.gif"));
    ImageIcon ceu = new ImageIcon(getClass().getResource("ceu.jpg"));
    JLabel lSuperHomem = new JLabel(superhomem);
    JLabel lCeu = new JLabel(ceu);
    int x=300,y=300;
    DatagramSocket serverSocket;
    byte[] receiveData = new byte[1024];
    
    public ServidorJogo() throws SocketException, IOException{
        serverSocket = new DatagramSocket(123);
        editarTela();
        listener();
    }
    
    
    public void editarTela(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,700);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(null);
        add(lSuperHomem);
        add(lCeu);
        lCeu.setBounds(0,0,800,700);
        lSuperHomem.setBounds(x,y, 320,400);
        setTitle("Servidor");
    }
    
    public void listener() throws IOException{
        while(true){
            DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
            serverSocket.receive(receivePacket);
            String sentence = new String(receivePacket.getData());
            String vetor[] = sentence.split(";");
            System.out.println(""+vetor[0]+"   "+vetor[1]);
            int x = Integer.parseInt(vetor[0]);
            //int y = Integer.parseInt(vetor[1]);
            lSuperHomem.setBounds(x,280,320, 400);
        }
    }
    
    public static void main(String args[]) throws IOException{
        new ServidorJogo();
    } 
}
