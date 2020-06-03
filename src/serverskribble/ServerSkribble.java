package serverskribble;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ServerSkribble {
    protected static ArrayList<Thread> listaClient = new ArrayList<Thread>();
    protected static ArrayList<PrintWriter> writeClient = new ArrayList<>();
    
    public static void main(String[] args) throws IOException{
        server();
   }
    
    
   private static void server() throws IOException{ 
        ServerSocket listener = new ServerSocket(8080);
        
        System.out.println("The server is running...");
        listaClient.add(null);
        writeClient.add(null);
        
        while(true){
            Socket connection = listener.accept();
            int i=0;
            
            while(listaClient.get(i) != null && i<listaClient.size()){
                i++;
            }
            System.out.println(i);
         
            System.out.println();
                       
            listaClient.add(i,new Thread(new GestisciClient(connection,i)));
            writeClient.add(i,new PrintWriter(connection.getOutputStream(),true));
            listaClient.get(i).start();
        }  
   }
}