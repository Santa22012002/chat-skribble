package serverskribble;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import static serverskribble.ServerSkribble.listaConnessioniClient;

public class Server {
    private int portaServizio;
    private ServerSocket listen;
    
    public Server(int portaServizio){
        this.portaServizio=portaServizio;
        listaConnessioniClient = new ArrayList<>();
        listaConnessioniClient.add(null);
    }
    
    public int startServer(){
        try{
            listen = new ServerSocket(portaServizio);
            System.out.println("il server è partito");
            return 0;
        }catch(Exception e){
            System.out.println("ci sono stati alcuni problemi nell'avvio del server");
            return -1;
        }
    }
    
    public void listen() throws IOException{
        while(true){
            Socket connection = listen.accept();
            
            int i=0;
            
            while(listaConnessioniClient.get(i) != null && i<listaConnessioniClient.size()){
                i++;
            }
            System.out.println(i);
         
            System.out.println();
                       
            listaConnessioniClient.add(i,new ConnessioneClient(connection,i));
            listaConnessioniClient.get(i).start();
        }
    }
}
