package chatRoom_Executive;

import chatRoom_Model.MessageMap;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ChatSeverSocket implements Runnable {

    // instance field
    private String nickName;
    private String groupNumber;
    private Socket socket;
    private static HashMap<String, MessageMap> onlineList;
//    private static HashMap<String, HashMap<String, MessageMap>> onlineList;
//    private
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private static Lock read = rwl.readLock();
    private static Lock write = rwl.writeLock();

//    private boolean isStoped = false;

    ChatSeverSocket(Socket socket, HashMap<String, MessageMap> onlineList) {
        this.socket = socket;
        ChatSeverSocket.onlineList = onlineList;
    }
    private void initilize(){
       try {
           do {
               this.nickName = (String) ois.readObject();
               this.groupNumber = (String)ois.readObject();
               if (onlineList.isEmpty()) {
                   oos.writeObject("successful");
                   break;
               } else if (onlineList.containsKey(nickName)) {
                   oos.writeObject("duplicated");
               } else {
                   oos.writeObject("successful");
                   break;
               }
           } while (true);
            try{
                write.lock();
                // for existing user, add new user in their onlineList HashMap
                for (String target : onlineList.keySet()) {
                    onlineList.get(target).put(this.nickName, new Stack<String>());

                }

                // add new nick Name, allocate the space to onlineList
                onlineList.put(this.nickName, new MessageMap());

                // add other user into new user's hashMap, except itself
                for (String target : onlineList.keySet())
                    if (!target.equals(this.nickName))
                        onlineList.get(this.nickName).put(target, new Stack<String>());
            }finally{
                write.unlock();
            }

           // first load onlineList
           oos.writeObject(onlineList.get(nickName));
//           oos.writeObject("Welcome " + nickName);
       }catch(IOException | ClassNotFoundException e){
           System.err.println("Error occurs  " + e);
       }
    }


    public void run() {
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());

            // DONE: send the list of users to the client
           this.initilize();

           // TODO: test need to be deleted
            for(String usr : onlineList.keySet()){
                System.out.println(usr);
                for(String sender :onlineList.get(usr).keySet()){
                    System.out.println("   +" + sender);
                }
            }



            String target;
            String message;

            // receive the commands from client
            do {
                System.out.println("Waiting for client request");

                String clientRequest = (String) ois.readObject();
                System.out.println(this.nickName +" received:" + clientRequest);

                // Recognize request command
                if (clientRequest.startsWith("terminate")) {

                    // TODO: remove client from the list
                    oos.writeObject("Client terminating, closing socket");
                    try {
                        write.lock();
                        onlineList.remove(nickName);
                        for (HashMap map : onlineList.values())
                            map.remove(nickName);
                        break;
                    }finally {
                        write.unlock();
                    }
                } else if (clientRequest.equals("UpdateList")) {
                    try{
                        read.lock();
                        MessageMap temp = new MessageMap();
                        for(String sender : onlineList.get(this.nickName).keySet()){
                            temp.put(sender, new Stack<String>());
                                while(!onlineList.get(this.nickName).get(sender).isEmpty())
                                    temp.get(sender).push(onlineList.get(this.nickName).get(sender).pop());
                        }

//                        System.out.println("In " +nickName+ " :");
//                        for(String me: temp.keySet()){
//                            System.out.println(" +" + me);
//                            for(String sender : temp.get(me))
//                                System.out.println("   -" + sender);
//                        }
                        oos.writeObject("Prepare for updating");
                        oos.writeObject(temp);
                    }finally{
                        read.unlock();
                    }
                    // when the message is send, immediately delete the cache
//                    onlineList.values().clear();
                } else if (clientRequest.startsWith("Msg")) {
                    StringTokenizer st;
                    st = new StringTokenizer(clientRequest, " ");
                    st.nextElement();
                    target = (String) st.nextElement();
                    message = clientRequest.substring(5 + target.length());
                    //Todo: write Lock
                    try {
                        write.lock();
                        onlineList.get(target).get(this.nickName).push(message);
                        oos.writeObject("Your message is received");
                    }finally{
                        write.unlock();
                    }
                }
                else {
//                    pw.println("Unexpected client message");
                    oos.writeObject("Unexpected client message");
                }

            } while (true);
            oos.flush();
            oos.close();
            ois.close();


//            Thread request = new Thread(new ReceiveRequest(onlineList, lock, ois, oos, nickName, isStoped));
//            request.setPriority(6);
//            System.out.println(request.getPriority());
//            request.start();
//            while(!isStoped){}


//            System.out.println("Closing connection with " + socket.getInetAddress());
//            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Server error with: " + e);
        }


    }
}
