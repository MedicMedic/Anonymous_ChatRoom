package chatRoom_Model;

import ChatRoom_Controller.ChatController;
import chatRoom_View.ChatLogin;
import chatRoom_View.ChatWindow;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatClient extends Thread  {

    private static final String HOST_NAME = "localhost";
    private static final int HOST_PORT = 7777; // host port number
    private static int udp_Port = 8888;

    private int your_Port;

    public volatile boolean isStopped;
    // non-parameter constructor
    private ChatClient() {
        this.your_Port = ++ udp_Port;
    }

    // thread start
    public void run() {
        while (!interrupted()) {
            Socket socket = null;
            Scanner keyboardInput = new Scanner(System.in);

            // create a socket for communication
            try {
                socket = new Socket(HOST_NAME, HOST_PORT);
                System.out.println("the port is " + socket.getInetAddress());
                System.out.println("New socket has been created");
            } catch (IOException e) {
                System.err.println("Client could not make connection: " + e);
                System.exit(-1);
            }

            try {
                // use ObjectStream to send/receive to exchange information with Server
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());


                // TODO: show on gui


                // disposable instance
                ChatController loginController = new ChatController(new ChatLogin(), oos, ois);
                String nickName = loginController.getNickName();
                String groupName = loginController.getGroupName();


                // get the init inlineList
                MessageMap onlineList = (MessageMap) ois.readObject();
                MessageMap myMessage = new MessageMap();
                for (String sender : onlineList.keySet()) {
                    myMessage.put(sender, new Stack<>());
                }
                // begin to chat
                isStopped = false;
                ChatWindow chatWindow = new ChatWindow(nickName);
                ChatController windowControl = new ChatController(chatWindow, nickName,groupName, onlineList, myMessage, oos, ois, this, socket, your_Port);
                windowControl.autoUpdateList();
                String response;
                while(!isStopped) {
                    Thread.onSpinWait();
                }
                oos.flush();
                oos.close();
                ois.close();
                socket.close();
                keyboardInput.close();
                interrupt();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Client error with " + e);
            }
        }
    }
    public boolean getisStopped(){
        return this.isStopped;
    }
    public static void main(String[] args) {

        new ChatClient().start();
        new ChatClient().start();
        new ChatClient().start();
        new ChatClient().start();
    }

}
