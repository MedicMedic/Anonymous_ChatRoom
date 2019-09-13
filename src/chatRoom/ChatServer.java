package chatRoom;

import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer {
    // instance field
    // Constant
    public static final int PORT = 8888;


    private static boolean stopRequested;

    // Avoid problems like deadlock, conflict or starvation
    private static int numReaders = 0;

    private static HashMap<String, Stack<String>> userList = new HashMap<>();
    /*
      use the Singleton to ensure the server could be at most one
      and just be used in package
    */

    // constructor
    private ChatServer(){
        stopRequested = false;
    }
    private static class SingletonBuilder{
        private static ChatServer chatServer = new ChatServer();
    }
    protected static ChatServer getServer(){
        return SingletonBuilder.chatServer;
    }

    // start the chatServer
    public static void startServer(){
        stopRequested = false;
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started at " + InetAddress.getLocalHost() + " on port " + PORT);
        }catch (IOException e) {
            System.err.println("server can't listen on port: " + e);
            System.exit(-1);
        }
        // server create sockets to communicate with clients
        try {
            while (!stopRequested) {

                // serverSocket listen to the client socket and accept it to make connection
                Socket socket = serverSocket.accept();
                System.out.println("Connection made with " + socket.getInetAddress());
                // TODO: add code to get name of the user
                Thread thread = new Thread(new ChatTransaction(socket, userList));
                thread.start();
            }
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Can’t accept client connection: " + e);
        }
        System.out.println("Server finishing");
    }


    public static void main(String[] args){
        ChatServer.startServer();
    }
}
