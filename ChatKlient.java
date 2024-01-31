import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;


public class ChatKlient {

    private JFrame frame;
    private JTextArea textArea;
    private JTextField textField;
    private JButton sendButton;
    private Socket socket;
    private PrintWriter writer;

    public ChatKlient(String serverAddress, int serverPort) {
        // Vytvoření socketu a připojení k serveru
        try {
            this.socket = new Socket(serverAddress, serverPort);
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error connecting to the server: " + ex.getMessage());
            ex.printStackTrace();
        }

        // GUI setup
        frame = new JFrame("Chat Client");
        textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        textField = new JTextField(50);
        sendButton = new JButton("Send");
        frame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.getContentPane().add(textField, BorderLayout.SOUTH);
        frame.getContentPane().add(sendButton, BorderLayout.EAST);
        frame.pack();

        // Action Listener for send button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writer.println(textField.getText());
                textField.setText("");
                textField.requestFocus();
            }
        });
    }

    public static void main(String[] args) {
        ChatKlient client = new ChatKlient("127.0.0.1", 6789); // Server IP and Port
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
    }
}
