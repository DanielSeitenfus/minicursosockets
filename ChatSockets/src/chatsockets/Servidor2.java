/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author visitante
 */
public class Servidor2 extends Thread{
    ObjectOutputStream out;
    ObjectInputStream in;
    public Servidor2(Socket cliente) throws IOException{
        this.out = new ObjectOutputStream(cliente.getOutputStream());
        this.in = new ObjectInputStream(cliente.getInputStream());
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket socket = new ServerSocket(1234);
        System.out.println("Servidor rodando na porta " + socket.getLocalPort());
        while (true) {
            Socket cliente = socket.accept();
            System.out.println("Cliente conectado!");
            new Servidor2(cliente).start();
        }
    }
    
    @Override
     public void run(){
         System.out.println("Thread sendo executada..");
     }
}

