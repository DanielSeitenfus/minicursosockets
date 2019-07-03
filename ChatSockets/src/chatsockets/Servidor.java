package chatsockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
    static ArrayList<ClienteListener> listaClientes;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        listaClientes = new ArrayList<>();
        ServerSocket servidor = new ServerSocket(123);
        System.out.println("Servidor inicializado na porta "+ servidor.getLocalPort());
        while(true){
           Socket cliente = servidor.accept();
           System.out.println("Cliente conectado!");
           ClienteListener cl = new ClienteListener(cliente);
           cl.start();
           listaClientes.add(cl);
        }
    }
}

class ClienteListener extends Thread{
    static final int CONVERSAR = 1;
    static final int SAIR = 2;
    static final int ATUALIZAR_ONLINE = 3;
    Socket cliente;
    ObjectOutputStream out;
    ObjectInputStream in;
    String nome;
    public ClienteListener(Socket cliente) throws IOException{
        this.cliente=cliente;
        this.out = new ObjectOutputStream(cliente.getOutputStream());
        this.in = new ObjectInputStream(cliente.getInputStream());
    }
    
    @Override //sobrescrita de método
    public void run(){
        boolean executar=true;
        try {
            nome = (String) in.readObject();
            atualizaOnline();
            System.out.println("Nome: "+nome);
            while(executar){
                int opcao = in.readInt();
                String msg = (String) in.readObject();
                if(opcao==CONVERSAR){
                   sendToAll(msg, nome); 
                }else if(opcao==SAIR){
                    sendToAll(nome, "saiu.");
                    out.close();
                    in.close();
                    executar=false;
                }
            }
        } catch (IOException | ClassNotFoundException ex) {}
    }
    
    public void sendToAll(String msg, String nome) throws IOException{
        for(int i=0; i<Servidor.listaClientes.size(); i++){
            ClienteListener dest = Servidor.listaClientes.get(i);
            ObjectOutputStream outCliente = dest.out;
            if(outCliente!=out){
                outCliente.writeInt(CONVERSAR);
                outCliente.writeObject(nome);
                outCliente.writeObject(msg); 
            }
        }
    }
    
    public void atualizaOnline() throws IOException{
        String online="";
        for(int i=0; i<Servidor.listaClientes.size(); i++){
            ClienteListener dest = Servidor.listaClientes.get(i);
            online = online+dest.nome+"\n";
        }
        for(int i=0; i<Servidor.listaClientes.size(); i++){
            ClienteListener dest = Servidor.listaClientes.get(i);
            dest.out.writeInt(ATUALIZAR_ONLINE);
            dest.out.writeObject(online);
        } 
    }
}
