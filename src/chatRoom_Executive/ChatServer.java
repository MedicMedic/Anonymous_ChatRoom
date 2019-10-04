package chatRoom_Executive;

import chatRoom_Model.MessageMap;

import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer extends Thread{
    // instance field
    // Constant
    private static final int PORT = 7777;
    private static final int MAX_BYTES = 2048;

    private static boolean stopRequested;
    // Avoid problems like deadlock, conflict or starvation

    private static HashMap<String, MessageMap> onlineList = new HashMap<>();
    /*
      use the Singleton to ensure the server could be at most one
      and just be used in package
    */

    // constructor
    private ChatServer() {
    }

    private static class SingletonBuilder {
        private static ChatServer chatServer = new ChatServer();
    }

    protected static ChatServer getServer() {
        return SingletonBuilder.chatServer;
    }

    public void run() {
        startServer();
    }

    // start the chatServer
    private static void startServer() {
        stopRequested = false;
        ServerSocket serverSocket = null;


        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started at " + InetAddress.getLocalHost() + " on port " + PORT);

            // udp socket create
            DatagramSocket udpSocket = new DatagramSocket(PORT, InetAddress.getLocalHost());
            byte[] receiveBytes = new byte[MAX_BYTES];
            // create MessageReceive packet object
            DatagramPacket receivePacket = new DatagramPacket(receiveBytes, receiveBytes.length);
        } catch (IOException e) {
            System.err.println("server can't listen on port: " + e);
            System.exit(-1);
        }
        // server create sockets to communicate with clients
        try {
            while (!stopRequested) {

                // srverSocket listen to the client socket and accept it to make connection
                Socket socket = serverSocket.accept();
                System.out.println("Connection made with " + socket.getInetAddress());
                // TODO: add code to get name of the user
                Thread thread = new Thread(new ChatSeverSocket(socket, onlineList));
                System.out.println(thread.getPriority());
                thread.start();

            }
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Can’t accept client connection: " + e);
        }
        System.out.println("Server finishing");
    }


    public static void main(String[] args) {
        new ChatServer().start();
    }
}
