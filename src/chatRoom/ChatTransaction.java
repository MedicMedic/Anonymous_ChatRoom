package chatRoom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;

public class ChatTransaction implements Runnable{
    private String name;
    private Socket socket;
    private static HashMap<String, LinkedList<String>> userList = new HashMap<>();

    public ChatTransaction(Socket socket, HashMap<String, LinkedList<String>> userList) {
        this.socket = socket;
        this.userList = userList;
    }

    public void run() {
        PrintWriter pw; // output stream to client
        BufferedReader br; // input stream from client
        try {
            pw = new PrintWriter(socket.getOutputStream(), true);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // TODO: send the list of users to the client

            do {

                System.out.println("Waiting for client request");
                String clientRequest = br.readLine();
                System.out.println("New client message received:" + clientRequest);
                String response;

                if (clientRequest.startsWith("Init")){
                    pw.println("Happy Chatting");
                    userList.put(clientRequest.substring(4), new LinkedList<String>());
                    System.out.println(userList.keySet().iterator().next());
                }
                else if (clientRequest.startsWith("terminate")) {

                    // TODO: remove client from the list
                    pw.println("Client terminating, closing socket");
                    break;
                } else {
                    pw.println("Unexpected client message");
                }

            } while (true);
            pw.close();
            br.close();
            System.out.println("Closing connection with " + socket.getInetAddress());
            socket.close();
        } catch (IOException e) {
            System.err.println("Server error with game: " + e);
        }


    }
}
