package chatRoom;

import java.net.*;
import java.io.*;

public class ChatServer {

    // Constant
    public static final int PORT = 8888;

    // instance field
    private static boolean stopRequested;

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
        try {
            while (!stopRequested) {

                // serverSocket listen to the client socket and accept it to make connection
                Socket socket = serverSocket.accept();
                System.out.println("Connection made with " + socket.getInetAddress());
                // TODO: add code to get name of the user
                Thread thread = new Thread();
                thread.start();
            }
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Can’t accept client connection: " + e);
        }
        System.out.println("Server finishing");
    }
    public static void main(String[] args){
        ChatServer chatServer = new ChatServer();
        ChatServer.startServer();
    }
}
