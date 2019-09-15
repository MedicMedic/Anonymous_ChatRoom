package chatRoom;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;

public class ChatTransaction implements Runnable{
    private String nickName;
    private Socket socket;
    private static HashMap<String, HashMap<String,Stack<String>>> onlineList;

    private static boolean lock = true;

    public ChatTransaction(Socket socket, HashMap<String, HashMap<String, Stack<String>>> onlineList) {
        this.socket = socket;
        this.onlineList = onlineList;
    }

    public void run() {
        ObjectInputStream ois;
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream() );


            // DONE: send the list of users to the client
            String nickName;
            do{
//                while(!lock){}
//                lock = false;
                nickName = (String)ois.readObject();
                if(onlineList.isEmpty()){
                    oos.writeObject("successful");
                    break;
                }
                else if(onlineList.keySet().contains(nickName))
                    oos.writeObject("duplicated");

                else{
                    oos.writeObject("successful");
                    break;
                }

            }while(true);

            if(!onlineList.isEmpty()) {
                for (HashMap<String, Stack<String>> target : onlineList.values())
                    target.put(nickName, new Stack<String>());
            }
            oos.writeObject("Welcome "+ nickName);

            while(!lock){}
            lock = false;
            // put the new k:user - v:
            onlineList.put(nickName, new HashMap<>());

            for(String target : onlineList.keySet())
                if(!target.equals(nickName))
                    onlineList.get(nickName).put(target, new Stack<>());
            // first load onlineList
            oos.writeObject(onlineList.get(nickName));
            lock = true;
//            lock = true;


            String target;
            String message;
            do {
                System.out.println("Waiting for client request");
                String clientRequest = (String)ois.readObject();
                System.out.println("New client message received:" + clientRequest);
                String response;



                if (clientRequest.startsWith("terminate")) {

                    // TODO: remove client from the list
                    oos.writeObject("Client terminating, closing socket");
                    onlineList.remove(nickName);
                    for(HashMap map : onlineList.values())
                        map.remove(nickName);
                    break;
                }
                else if (clientRequest.equals("UpdateList")){
                    oos.writeObject(onlineList.get(nickName));
                    // when the message is send, immediately delete the cache
//                    onlineList.values().clear();
                }
                else if( clientRequest.startsWith("Msg")){
                    StringTokenizer st = new StringTokenizer(clientRequest, " ");
                    st.nextElement();
                    target = (String)st.nextElement();
                    message = clientRequest.substring(5+target.length());
                    //Todo: write Lock
//                    if(!onlineList.get(nickName).isEmpty()) {
                        onlineList.get(nickName).get(target).push(message);
                }

                else {
//                    pw.println("Unexpected client message");
                    oos.writeObject("Unexpected client message");
                }

            } while (true);
            oos.flush();
            oos.close();
            ois.close();
            System.out.println("Closing connection with " + socket.getInetAddress());
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Server error with: " + e);
        }


    }
}
