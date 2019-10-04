package chatRoom_Executive;

import ChatRoom_Controller.ChatController;
import chatRoom_Model.MessageMap;
import chatRoom_View.ChatLogin;
import chatRoom_View.ChatWindow;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatClient extends Thread  {

    private static final String HOST_NAME = "localhost";
    private static final int HOST_PORT = 7777; // host port number


    public volatile boolean isStopped;
    // non-parameter constructor
    ChatClient() {
    }

    // thread start
    public void run() {
        while (!interrupted()) {
            Socket socket = null;
            Scanner keyboardInput = new Scanner(System.in);

            // create a socket for communication
            try {
                socket = new Socket(HOST_NAME, HOST_PORT);
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
                String nickName = new ChatController(new ChatLogin(), oos, ois).getNickName();


                // get the init inlineList
                MessageMap onlineList = (MessageMap) ois.readObject();
                MessageMap myMessage = new MessageMap();
                for (String sender : onlineList.keySet()) {
                    myMessage.put(sender, new Stack<>());
                }
                // begin to chat
                isStopped = false;
                ChatController windowControl = new ChatController(new ChatWindow(nickName), nickName, onlineList, myMessage, oos, ois, this);
                windowControl.autoUpdateList();
                String response;
                while(!isStopped) {
//               response = (String)ois.readObject();
//               System.out.println(response);
//               if(response.equals("terminate"))
//                   break;
                    Thread.onSpinWait();
                }
                oos.flush();
                oos.close();
                ois.close();
                socket.close();
                keyboardInput.close();
                interrupt();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Client error with game: " + e);
            }
        }
    }
    public static void main(String[] args) {

        new ChatClient().start();
        new ChatClient().start();
        new ChatClient().start();
        new ChatClient().start();
    }
}
