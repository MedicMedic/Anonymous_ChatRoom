package chatRoom_background;

import chatRoom_foreground.ChatJoin;
import chatRoom_foreground.ChatWindow;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ChatClient implements Runnable {
    private static final String HOST_NAME = "localhost";
    private static final int HOST_PORT = 8888; // host port number

    private static HashMap<String, HashMap<String, Stack<String>>> onlineList;

    private boolean sign = false;

    private String nickName;

    static ReentrantReadWriteLock rwlock;
    private static Lock read;
    private static Lock write;
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


            // deliver the lock
             this.rwlock = (ReentrantReadWriteLock) ois.readObject();
             read = rwlock.readLock();
             write = rwlock.writeLock();

            // TODO: show on gui


            // disposable instance
//            write.lock();
            this.nickName = new ChatJoin(nickName,oos, ois, sign).getNickName();
//            write.unlock();
            while(!sign){}
            new ChatWindow(nickName,oos,ois);
            // initialize your Nickname
//            System.out.println("Welcome to anonymous chatRoom, Please enter you Nickname: ");

            // check if the nick Name is already existing!


            Object serverResponse;


            // first load the online List
            serverResponse = ois.readObject();
            onlineList = (HashMap)serverResponse;

            serverResponse = ois.readObject(); // Welcome, nickname
            System.out.println("##########Server sent me:" + (String) serverResponse);
// TODO:update messageList per 5 second

            Timer updateRequest = new Timer();

            do {
                System.out.print(nickName + " said: ");
                System.out.println();

                String newUserInput = keyboardInput.nextLine();


                if (newUserInput.equals("terminate")) {
                    oos.writeObject(newUserInput);
                    break;
                } else if (newUserInput.startsWith("Msg")) {
                    oos.writeObject(newUserInput);
                    System.out.println(((String) ois.readObject()));
                } else if (newUserInput.equals("show")) {
                    oos.writeObject("show");
                    System.out.println("show");
                }

                System.out.println("waiting for server message");
                //:Todo: update view
//                updateRequest.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
////                    System.out.println("########List fresh time######");
//                        try {
//                            oos.writeObject("UpdateList");
//
//
//                            HashMap<String, Stack<String>> temp;
//                            temp = (HashMap)ois.readObject();
//                            for(String sender : temp.keySet()){
//                                if(onlineList.containsKey(sender)){
//                                    while(!temp.get(sender).isEmpty())
//                                        onlineList.get(sender).push(temp.get(sender).pop());
//                                    //:Todo: create a new receive panel
//                                }else{
//                                    onlineList.put(sender, new Stack<>());
//                                    while(!temp.get(sender).isEmpty())
//                                        onlineList.get(sender).push(temp.get(sender).pop());
//                                    //:Todo: create a new receive panel
//                                }
//                            }
//                            for(String s : onlineList.keySet())
//                                if(!onlineList.get(s).isEmpty()) {
//                                    System.out.println(s + " send me" + onlineList.get(s).peek());
//                                }
//                        } catch (IOException | ClassNotFoundException e) {
//                            System.err.println("There is exception :" + e);
//
//                        }
//                    }
//                }, 1000, 5000);
            } while (true);
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
