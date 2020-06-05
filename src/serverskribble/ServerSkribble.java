package serverskribble;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ServerSkribble {
    protected static ArrayList<ConnessioneClient> listaConnessioniClient;
    public static void main(String[] args) throws IOException{
        Server s = new Server(8080);
        
        if(s.startServer() == 0)
            s.listen();
   }
}