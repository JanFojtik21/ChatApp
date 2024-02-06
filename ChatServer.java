import java.io.*;
import java.lang.reflect.Executable;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {

    private int port;
    private ExecutorService executorService;    

    public ChatServer(int port){
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    public void startServer() throws ServerException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server nasloucha na portu " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                Runnable clientHandler = new ClientHandler(socket);
                executorService.execute(clientHandler);
            }
        } catch (IOException ex) {
            throw new ServerException("Error in the server: " + ex.getMessage(), ex);
        }
    }

    public class ClientHandler implements Runnable{
        private Socket socket;

        public ClientHandler(Socket socket){
            this.socket = socket;
        }
        @Override
        public void run(){
            try {
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                String message;
                do {
                    message = reader.readLine();
                    System.out.println("Received message: " + message);
                    writer.println("Server received the message: " + message);
                } while (!message.equals("exit"));
                socket.close();
            } catch (IOException ex) {
                System.out.println("Error in the server: " + ex.getMessage());
                ex.printStackTrace();
            }
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