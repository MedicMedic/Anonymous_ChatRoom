package chatRoom_background;

import ChatRoom_controller.ChatController;
import chatRoom_foreground.ChatLogin;
import chatRoom_foreground.ChatWindow;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatClient implements Runnable {
    private static final String HOST_NAME = "localhost";
    private static final int HOST_PORT = 8888; // host port number

    private static HashMap<String, Stack<String>> onlineList;

    private volatile boolean sign = false;

    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    // non-parameter constructor
    ChatClient() {
    }

    // thread start
    public void run() {
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
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());



            // TODO: show on gui


            // disposable instance
            String nickName = new ChatController(new ChatLogin(), oos, ois).getNickName();


            // get the init inlineList
            onlineList = (HashMap<String, Stack<String>>)ois.readObject();

            // begin to chat
            ChatController windowControl = new  ChatController(new ChatWindow(nickName), nickName, onlineList, oos, ois);
            windowControl.autoUpdateList();
            String response;
           while(!sign){
//                   response = (String)ois.readObject();
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
//            updateRequest.cancel();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Client error with game: " + e);
        }
    }

    public static void main(String[] args) {

        new Thread(new ChatClient(), "Medic").start();


        new Thread(new ChatClient()).start();
        new Thread(new ChatClient()).start();
    }
}
