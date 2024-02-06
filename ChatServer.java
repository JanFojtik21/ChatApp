import java.io.*;
import java.net.*;

public class ChatServer {

    private int port;

    public ChatServer(int port){
        this.port = port;
    }

    public void startServer() throws ServerException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server nasloucha na portu " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
            }
        } catch (IOException ex) {
            throw new ServerException("Error in the server: " + ex.getMessage(), ex);
        }
    }

    public static void main (String [] args){
        int port = 666;
        ChatServer server = new ChatServer(port);
        try {
            server.startServer();
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }
}

class ServerException extends Exception {
    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
}