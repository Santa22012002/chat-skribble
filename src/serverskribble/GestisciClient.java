package serverskribble;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static serverskribble.ServerSkribble.listaClient;
import static serverskribble.ServerSkribble.writeClient;

public class GestisciClient implements Runnable {
    private Socket connection;
    private BufferedReader streamIn;
    private PrintWriter streamOut;
    private int indice;
    
    public GestisciClient(Socket connection,int indice) throws IOException{
        this.connection=connection;
        this.streamIn=new BufferedReader(new InputStreamReader(connection.getInputStream()));
        this.indice=indice;
    }
    
    
        
    @Override
    public void run(){
        PrintWriter streamOut = writeClient.get(indice);
        
        streamOut.println("ACCEPTED");
        System.out.println("connection accepted");
        
        while(true){
            try {
                String messageIn = streamIn.readLine();
                
                if(messageIn.matches("stop")){
                    streamOut.close();
                    
                    connection.close();
                    System.out.println("connecttion closed");
                    break;
                }else{
                    for(PrintWriter i: writeClient){
                        if(i != streamOut && i!=null)
                            i.println("\nClient "+indice+">>>"+messageIn);
                        else if(i == null){
                            continue;
                        }
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex);
                break;
            }
        }
        
        streamOut.close();
        
        writeClient.set(indice,null);
        listaClient.get(indice).interrupt();
        listaClient.set(indice,null);
    }
}
