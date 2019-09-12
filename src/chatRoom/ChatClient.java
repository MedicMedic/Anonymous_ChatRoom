package chatRoom;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatClient implements Runnable{
    public static final String HOST_NAME = "localhost";
    public static final int HOST_PORT = 8888; // host port number

    // non-parameter constructor
    public ChatClient() {
    }

    // thread start
    public void run() {
        Socket socket = null;
        Scanner keyboardInput = new Scanner(System.in);
        try {
            socket = new Socket(HOST_NAME, HOST_PORT);
            System.out.println("New socket has been created");
        } catch (IOException e) {
            System.err.println("Client could not make connection: " + e);
            System.exit(-1);
        }
        PrintWriter pw;
        BufferedReader br;
        try {
            pw = new PrintWriter(socket.getOutputStream(), true);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            do {
                System.out.println("waiting for user input");
                String newUserInput = keyboardInput.nextLine();
                System.out.println("keyboard message:" + newUserInput);
                pw.println(newUserInput);
                if (newUserInput.startsWith("terminate")) {
                    break;
                }
                System.out.println("waiting for server message");
                String serverResponse = br.readLine();
                System.out.println("Server sent me:" + serverResponse);
            } while (true);
            pw.close();
            br.close();
            socket.close();
            keyboardInput.close();
        } catch (IOException e) {
            System.err.println("Client error with game: " + e);
        }
    }

    public static void main(String[] args) {

        new Thread(new ChatClient()).start();
        new Thread(new ChatClient()).start();

    }
}
