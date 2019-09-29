package chatRoom_Executive;

import ChatRoom_Controller.ChatController;
import chatRoom_View.ChatLogin;
import chatRoom_View.ChatWindow;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatClient extends Thread  {

    private static final String HOST_NAME = "localhost";
    private static final int HOST_PORT = 8888; // host port number

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
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());



            // TODO: show on gui


            // disposable instance
            String nickName = new ChatController(new ChatLogin(), oos, ois).getNickName();


            // get the init inlineList
            HashMap<String, Stack<String>> onlineList = (HashMap<String, Stack<String>>) ois.readObject();

            // begin to chat
            boolean isStopped = false;
            ChatController windowControl = new  ChatController(new ChatWindow(nickName), nickName, onlineList, isStopped, oos, ois);
//            windowControl.autoUpdateList();
            String response;
           while(!isStopped){
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
            this.interrupt();
//            updateRequest.cancel();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Client error with game: " + e);
        }
    }

    public static void main(String[] args) {

//        new Thread(new ChatClient(), "Medic").start();
        new ChatClient().start();
        new ChatClient().start();
        new ChatClient().start();
//        new Thread(new ChatClient()).start();
//        new Thread(new ChatClient()).start();
    }
}
