package serverskribble;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static serverskribble.ServerSkribble.listaConnessioniClient;

public class ConnessioneClient extends Thread{
    private Socket connection;
    private BufferedReader streamIn;
    private PrintWriter streamOut;
    private int indice;
    private boolean continua=true;
    
    public ConnessioneClient(Socket connection,int indice) throws IOException{
        this.connection=connection;
        this.streamIn=new BufferedReader(new InputStreamReader(connection.getInputStream()));
        this.streamOut=new PrintWriter(connection.getOutputStream(),true);
        this.indice=indice;
    }
    
    public void ferma(){this.continua=false;}
    public void sendMessage(String messaggio){streamOut.println(messaggio);}
    
    private void close() throws IOException{
         streamOut.close();
        listaConnessioniClient.get(indice).ferma();
        connection.close();
        listaConnessioniClient.set(indice,null);
    }
    @Override
    public void run(){
        
            streamOut.println("ACCEPTED");
            System.out.println("connessione stabilita con "+connection.getInetAddress());

            while(continua){
                try {
                    String messageIn = streamIn.readLine();

                    if(messageIn.matches("stop")){
                       close();
                        System.out.println("connesione chiusa");
                        break;
                    }else{
                        for(ConnessioneClient i: listaConnessioniClient){
                            if(i != null && i!=this)
                                i.sendMessage("Client "+indice+":"+messageIn);
                        }
                    }
                } catch (IOException ex) {
                    System.out.println(ex);
                    try {
                        close();
                    } catch (IOException ex1) {
                        Logger.getLogger(ConnessioneClient.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    System.out.println("connesione chiusa");

                    break;
                }
            }
        
        
    }
}
