/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author visitante
 */
public class Cliente {
    public static void main(String args[]) throws IOException, ClassNotFoundException{
        Socket socket = new Socket("localhost",1234);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        Scanner entrada = new Scanner(System.in);
        System.out.println("Informe seu nome: ");
        String nome = entrada.nextLine();
        out.writeObject(nome);
        Thread listener = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        String nome = (String) in.readObject();
                        String msg = (String) in.readObject();
                        System.out.println(nome+" disse: "+msg);
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        listener.start();
        while(true){
            System.out.println("Diga algo: ");
            String msg = entrada.nextLine();
            out.writeObject(msg);
        }
    }
    
}
