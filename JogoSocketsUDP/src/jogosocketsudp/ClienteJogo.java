/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogosocketsudp;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author danie
 */
public class ClienteJogo extends JFrame{
    int x=300,y=300;
    byte[] sendData = new byte[1024];
    byte[] receiveData = new byte[1024];
    DatagramSocket clientSocket;
    InetAddress IPAddress;
    ImageIcon setas = new ImageIcon(getClass().getResource("setas.png"));
    JLabel lSetas = new JLabel(setas);
    
    public ClienteJogo() throws SocketException, UnknownHostException{
        clientSocket = new DatagramSocket();
        IPAddress = InetAddress.getByName("localhost");
        editarTela();
        addMovimento();
    }
    public static void main(String args[]) throws SocketException, UnknownHostException {
        new ClienteJogo();
    }
    
    public void editarTela(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(270,270);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(null);
        add(lSetas);
        lSetas.setBounds(0,0,256,256);
        setTitle("Cliente");
    }

    public void addMovimento() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("captei");
                switch (e.getKeyCode()) {
                    case 38:
                        y -= 10;
                        break;
                    case 40:
                        y += 10;
                        break;
                    case 37:
                        x -= 10;
                        break;
                    case 39:
                        x += 10;
                        break;
                }
                String msg = x+";"+y;
                sendData = msg.getBytes();
                //conteúdo, tamanho, ip, porta
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 123);
                try {
                    clientSocket.send(sendPacket);
                    System.out.println("enviei");
                } catch (IOException ex) {
                    Logger.getLogger(ClienteJogo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
}
