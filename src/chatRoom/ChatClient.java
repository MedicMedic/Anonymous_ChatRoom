package chatRoom;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatClient implements Runnable{
    public static final String HOST_NAME = "localhost";
    public static final int HOST_PORT = 8888; // host port number

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

//        PrintWriter pw;
//        BufferedReader br;
        ObjectOutputStream oos;
        ObjectInputStream ois;
        try {
            System.out.println("test");
//            pw = new PrintWriter(socket.getOutputStream(), true);
//            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());


            // TODO: show on gui
            // initialize your Nickname
            System.out.println("Welcome to anonymous charRoom, Please enter you Nickname");
            String nickName = keyboardInput.nextLine();
//            pw.println(nickName);
            oos.writeObject(nickName);

            String serverResponse;
//            serverResponse= br.readLine();
            serverResponse = (String)ois.readObject();
            System.out.println("##########Server sent me:" + serverResponse);



            do {
                System.out.println("waiting for" + nickName + "input");

                String newUserInput = keyboardInput.nextLine();
                System.out.println("keyboard message:" + newUserInput);
//                pw.println(newUserInput);
                oos.writeObject(newUserInput);
                if (newUserInput.startsWith("terminate")) {
                    break;
                }
                System.out.println("waiting for server message");
//                serverResponse = br.readLine();
                serverResponse =  (String)ois.readObject();
//                if(serverResponse.startsWith(nickName)){
//                    cut the head, and save the message
//                }
//                else if(serverResponse.startsWith("Online")){
//                    // add k to userList
//                }
//                else if(serverResponse.startsWith("offline")){
//                    find the k to delete
//                }
                System.out.println("#########Server sent me:" + serverResponse);
            } while (true);
            oos.flush();
            oos.close();
            ois.close();
//            pw.close();
//            br.close();
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
