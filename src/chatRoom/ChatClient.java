package chatRoom;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatClient implements Runnable{
    public static final String HOST_NAME = "localhost";
    public static final int HOST_PORT = 8888; // host port number

    private static HashMap<String, Stack<String>> onlineList;
    // non-parameter constructor
    public ChatClient() {
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

        ObjectOutputStream oos;
        ObjectInputStream ois;
        try {

            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());


            // TODO: show on gui
            // initialize your Nickname
            System.out.println("Welcome to anonymous chatRoom, Please enter you Nickname");
            String nickName = keyboardInput.nextLine();
            oos.writeObject(nickName);

            Object serverResponse;
            serverResponse = ois.readObject();
            System.out.println("##########Server sent me:" + (String)serverResponse);

            // first load the online List
            serverResponse = ois.readObject();
            onlineList = (HashMap)serverResponse;

            HashMap<String, Stack<String>> temp;
// TODO:update messageList per 5 second

            do {
                System.out.println("waiting for =" + nickName + "= input");

                String newUserInput = keyboardInput.nextLine();
                System.out.println("keyboard message:" + newUserInput);
//                oos.writeObject(newUserInput);


                if (newUserInput.startsWith("terminate")) {
                    break;
                }else if(newUserInput.equals("UpdateList")){
                    oos.writeObject("UpdateList");

                    temp = (HashMap)ois.readObject();
                    for(String sender : temp.keySet()){
                        if(onlineList.containsKey(sender)){
                            while(!temp.get(sender).isEmpty())
                                onlineList.get(sender).push(temp.get(sender).pop());
                            //:Todo: create a new receive panel
                        }else{
                            onlineList.put(sender, new Stack<>());
                            while(!temp.get(sender).isEmpty())
                                onlineList.get(sender).push(temp.get(sender).pop());
                            //:Todo: create a new receive panel
                        }
                    }
                    for(String s : onlineList.keySet())
                        System.out.println(s);

                }else if(newUserInput.startsWith("Msg")){
                    oos.writeObject(newUserInput);
                }

                System.out.println("waiting for server message");
//                serverResponse = (String) ois.readObject();
//                if (((String)serverResponse).equals("new command")) {
                    // update the onlineList



//                System.out.println("#########Server sent me:" + serverResponse);
            } while (true);
            oos.flush();
            oos.close();
            ois.close();
            socket.close();
            keyboardInput.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Client error with game: " + e);
        }
    }

    public static void main(String[] args) {

        new Thread(new ChatClient()).start();
        new Thread(new ChatClient()).start();

    }
}
