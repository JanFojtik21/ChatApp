import java.io.*;
import java.net.*;
import java.util.*;
import java.net.Socket;




public class ChatServer {

    private int port;

    public ChatServer(int port){
        this.port = port;

    }


    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server nasloucha na portu " + port);

            while (true) {
                Socket socket = new Socket();

                socket = serverSocket.accept();
                System.out.println("New client connected");




            }
        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main (String [] args){
        int port = 666;
        ChatServer server = new ChatServer(port);
        server.startServer();
    }






}
