/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsockets;

import com.sun.corba.se.spi.activation.Server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author visitante
 */
public class Servidor {
    static ArrayList<ClienteListener> listaClientes;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket socket = new ServerSocket(1234);
        listaClientes = new ArrayList<>();
        System.out.println("Servidor rodando na porta " + socket.getLocalPort());
        while (true) {
            Socket cliente = socket.accept();
            System.out.println("Cliente conectado!");
            ClienteListener cl = new ClienteListener(cliente);
            listaClientes.add(cl);
            cl.start();
        }

    }
}

class ClienteListener extends Thread {
    ObjectOutputStream out;
    ObjectInputStream in;

    public ClienteListener(Socket cliente) throws IOException {
        this.out = new ObjectOutputStream(cliente.getOutputStream());
        this.in = new ObjectInputStream(cliente.getInputStream());
    }

    @Override
    public void run() {
   
        try {
            String nome = (String) in.readObject();
            //leitura das mensagens
            while (true) {
                String msg = (String) in.readObject();
                System.out.println(msg);
                sendToAll(nome, msg);
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClienteListener.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    public void sendToAll(String nome, String msg) throws IOException{
        for(int i=0; i<Servidor.listaClientes.size(); i++){
            ClienteListener cl = Servidor.listaClientes.get(i);
            ObjectOutputStream outCliente = cl.out;
            if(this.out!=outCliente){
                outCliente.writeObject(nome);
                outCliente.writeObject(msg);
            }
        }
    }
}
