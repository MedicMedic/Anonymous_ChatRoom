package chatRoom_Model;

import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer extends Thread{
    // instance field
    // Constant
    private static final int PORT = 7777;
    private static final int MAX_BYTES = 2048;
    private static int user_count = 0;

    private static DatagramSocket service;
    // Avoid problems like deadlock, conflict or starvation

    private static HashMap<String, HashMap<String, MessageMap>> onlineList = new HashMap<>();
    /*
      use the Singleton to ensure the server could be at most one
      and just be used in package
    */

    // constructor
    private ChatServer() {
    }

    // design for singleton pattern
    private static class SingletonBuilder {
        private static ChatServer chatServer = new ChatServer();
    }

    protected static ChatServer getServer() {
        return SingletonBuilder.chatServer;
    }

    public void run() {
        while(!interrupted()) {
            StartServer ss = new StartServer();
            ss.start();
            Scanner in = new Scanner(System.in);
            while (true) {
                String command = in.nextLine();
                if (command.equals("quit")) {
                    in.close();
                    StartServer.stopRequested = true;
                    ss.interrupt();
                    break;
                }
            }
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startUdpService();
            this.interrupt();
        }
    }

    static class StartServer extends Thread{
        private static boolean stopRequested;
        public void run(){
            while(!interrupted()) {
                startServer();
            }
        }
        private static void startServer() {
            stopRequested = false;
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(PORT);
                System.out.println("Server started at " + InetAddress.getLocalHost() + " on port " + PORT);


            } catch (IOException e) {
                System.err.println("server can't listen on port: " + e);
                System.exit(-1);
            }
            // server create sockets to communicate with clients
            try {
                while(!stopRequested) {
                    user_count ++;
                    // srverSocket listen to the client socket and accept it to make connection
                    Socket socket = serverSocket.accept();
                    System.out.println("Connection made with " + socket.getInetAddress());
                    // TODO: add code to get name of the user
                    ChatServerSocket chatServerSocket = new ChatServerSocket(socket, onlineList);
                    chatServerSocket.start();
                }
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Can’t accept client connection: " + e);
            }
            System.out.println("Server finishing");
        }
    }
    // start the chatServer



    private void startUdpService(){
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket();
            String data = "Server terminated";
            for(int i = 1; i < user_count; i++) {
                DatagramPacket packet = new DatagramPacket(data.getBytes(), data.getBytes().length, InetAddress.getLocalHost(), (i+8888));
                datagramSocket.send(packet);
            }
            datagramSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void main(String[] args) {
        new ChatServer().start();
    }
}
