package chatRoom_background;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

public class ReceiveRequest implements Runnable {

    private HashMap<String, HashMap<String, Stack<String>>> onlineList;
    private boolean lock;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private String nickName;
    private boolean isStoped;

    public ReceiveRequest(HashMap onlineList, boolean lock, ObjectInputStream ois,
                          ObjectOutputStream oos, String nickName, boolean isStoped) {
        this.onlineList = onlineList;
        this.lock = lock;
        this.oos = oos;
        this.ois = ois;
        this.nickName = nickName;
        this.isStoped = isStoped;
    }

    @Override
    public void run() {
        try {
            String target;
            String message;
            do {
                System.out.println("Waiting for client request");
                String clientRequest = (String) ois.readObject();
                System.out.println("New client message received:" + clientRequest);
                String response;


                if (clientRequest.startsWith("terminate")) {

                    // TODO: remove client from the list
                    oos.writeObject("Client terminating, closing socket");
                    onlineList.remove(nickName);
                    for (HashMap map : onlineList.values())
                        map.remove(nickName);
                    break;
                } else if (clientRequest.equals("UpdateList")) {
                    oos.writeObject(onlineList.get(nickName));
                    // when the message is send, immediately delete the cache
//                    onlineList.values().clear();
                } else if (clientRequest.startsWith("Msg")) {
                    StringTokenizer st = new StringTokenizer(clientRequest, " ");
                    st.nextElement();
                    target = (String) st.nextElement();
                    message = clientRequest.substring(5 + target.length());
                    //Todo: write Lock
//                    if(!onlineList.get(nickName).isEmpty()) {
                    onlineList.get(nickName).get(target).push(message);
                    oos.writeObject("Your message is received");
                }
//            for test
                else if (clientRequest.equals("show")) {
                    System.out.println("show");
                    for (String test : onlineList.keySet()) {
                        System.out.println("+" + test);
                        System.out.println("-");
                        for (String test2 : onlineList.get(test).keySet()) {
                            System.out.println("  -" + test2);
                            System.out.println("   -");
                        }
                    }
                    oos.writeObject("show message is received");
                } else {
//                    pw.println("Unexpected client message");
                    oos.writeObject("Unexpected client message");
                }

            } while (true);
            oos.flush();
            oos.close();
            ois.close();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Exception occurs: " + e);
        }
    }
}
