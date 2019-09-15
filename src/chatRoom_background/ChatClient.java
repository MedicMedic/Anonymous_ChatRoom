package chatRoom_background;

import chatRoom_foreground.ChatRoom_Login;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatClient implements Runnable {
    public static final String HOST_NAME = "localhost";
    public static final int HOST_PORT = 8888; // host port number

    private static HashMap<String, Stack<String>> onlineList;

    // non-parameter constructor
    public ChatClient() {
    }

    // thread start
    public void run() {
        String nickName;
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

        ObjectOutputStream oos;
        ObjectInputStream ois;
        try {

            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());


            // TODO: show on gui
            new ChatRoom_Login();
            // initialize your Nickname
            System.out.println("Welcome to anonymous chatRoom, Please enter you Nickname: ");

            // check if the nick Name is already existing!
            do {
                nickName = keyboardInput.nextLine();
                oos.writeObject(nickName);
                if (((String) ois.readObject()).equals("duplicated")) {
                    System.out.println(nickName + " is already existing, change another one!");
                } else
                    break;

            } while (true);

            Object serverResponse;


            // first load the online List
            serverResponse = ois.readObject();
            onlineList = (HashMap) serverResponse;

            serverResponse = ois.readObject(); // Welcome, nickname
            System.out.println("##########Server sent me:" + (String) serverResponse);
// TODO:update messageList per 5 second

            Timer updateRequest = new Timer();

            do {
                System.out.print(nickName + " said: ");
                System.out.println("");

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
