package chatRoom;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class ChatTransaction implements Runnable{
    private String nickName;
    private Socket socket;
    private static HashMap<String, Stack<String>> userList;

    public ChatTransaction(Socket socket, HashMap<String, Stack<String>> userList) {
        this.socket = socket;
        this.userList = userList;
    }

    public void run() {
        ObjectInputStream ois;
        ObjectOutputStream oos;
        PrintWriter pw; // output stream to client
        BufferedReader br; // input stream from client
        try {
            ois = new ObjectInputStream(socket.getInputStream() );
            oos = new ObjectOutputStream(socket.getOutputStream());
            pw = new PrintWriter(socket.getOutputStream(), true);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // DONE: send the list of users to the client
            String clientNameRequest = br.readLine();
            String ClientNameRequest = (String)ois.readObject();
            nickName = clientNameRequest;
            pw.println("Welcome "+ nickName);
            userList.put(nickName, new Stack<String>());

            do {

                System.out.println("Waiting for client request");
                String clientRequest = br.readLine();
                System.out.println("New client message received:" + clientRequest);
                String response;


                if (clientRequest.startsWith("terminate")) {

                    // TODO: remove client from the list
                    pw.println("Client terminating, closing socket");
                    break;
                }else if(clientRequest.startsWith(nickName)){
                    pw.println(clientRequest);
                    String message = cut;
                    cut your own name, and send the rest message back
                    //also do the refresh job, but how
                    //HashMap<String, Stack<String>> temp = userList;
                    //temp's key add the message
                    //send it back
                    //delete client's own key,
                    //client check new list, find that information
                }else if (clientRequest.equals("UpdateList")){
                    pw.
                }
                else {
                    pw.println("Unexpected client message");
                }

            } while (true);
            pw.close();
            br.close();
            System.out.println("Closing connection with " + socket.getInetAddress());
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Server error with: " + e);
        }


    }
}
