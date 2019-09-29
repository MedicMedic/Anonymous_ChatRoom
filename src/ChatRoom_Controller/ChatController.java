package ChatRoom_Controller;

import chatRoom_View.ChatLogin;
import chatRoom_View.ChatWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

public class ChatController implements ActionListener {
    private ChatLogin chatLogin;
    private String ifLogin;

    private ChatWindow chatWindow;
    private String nickName;
    private HashMap<String, Stack<String>> onlineList;

    private boolean isStopped;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;


    private Timer autoUpdate;


    // constructors

    // constructor for ChatLogin
    public ChatController(ChatLogin chatLogin, ObjectOutputStream oos, ObjectInputStream ois) {
        this.chatLogin = chatLogin;
        this.oos = oos;
        this.ois = ois;
        this.ifLogin=  "duplicated";
        this.chatLogin.getLoginButton().addActionListener(this);
    }

    // constructor for ChatWindow
    public ChatController(ChatWindow chatWindow, String nickName, HashMap<String, Stack<String>> onlineList,
                          boolean isStopped, ObjectOutputStream oos, ObjectInputStream ois)
    {
        this.chatWindow = chatWindow;
       this.nickName = nickName;
       this.onlineList = onlineList;
       this.isStopped = isStopped;
       this.oos = oos;
       this.ois = ois;


       this.autoUpdate = new Timer();


        this.chatWindow.getMsgButton().addActionListener(this);
        this.chatWindow.getTerminate().addActionListener(this);
        // test
        this.chatWindow.getShowMessage().addActionListener(this);
        this.chatWindow.getUpdateButton().addActionListener(this);
    }

    // for ChatLogin functions
    public String getNickName() {
        while(this.nickName == null){
            Thread.onSpinWait();
        }
        return this.nickName;
    }


    private void updateList(){
        try {
            this.oos.writeObject("UpdateList");
            System.out.println((String)ois.readObject());
            HashMap<String, HashMap<String, Stack<String>> > temp;
            temp = (HashMap<String, HashMap<String, Stack<String>>>) (ois.readObject());
            System.out.println(this.nickName);
            System.out.println("the size is " + temp.keySet());
            for(String sender : temp.get(this.nickName).keySet()){
                System.out.println("  +" + sender);
                for(String message : temp.get(this.nickName).get(sender))
                    System.out.println(message);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception occurs " + e);

        }
    }
    class Update extends TimerTask{
//        private HashMap<String, Stack<String>> onlineList;
//        private ObjectOutputStream oos;
//        private ObjectInputStream ois;
//        Update(HashMap<String, Stack<String>> onlineList, ObjectOutputStream oos, ObjectInputStream ois){
//           this.onlineList = onlineList;
//           this.oos = oos;
//           this.ois = ois;
//        }

        public void run() {
//            try {
                ChatController.this.updateList();
//                ChatController.this.oos.writeObject("UpdateList");
//
//
//                HashMap<String, Stack<String>> temp;
//                temp = (HashMap)ChatController.this.ois.readObject();
//
//                for(String sender : temp.keySet()){
//                    System.out.println(sender);
//                    for(String message : temp.get(sender))
//                        System.out.println("  +"+message);

//                }
                // test
//
//                for(String sender : temp.keySet()){
//                    // old sender
//                    if(onlineList.containsKey(sender)){
//                        for(String message : temp.get(sender))
//
//                        // while(!temp.get(sender).isEmpty())
//                        //     onlineList.get(sender).push(temp.get(sender).pop());
//                        // //:Todo: create a new receive panel
//                    }else{  // new sender is needed to add
//                        onlineList.put(sender, new Stack<String>());
//                        // while(!temp.get(sender).isEmpty())
//                        //     onlineList.get(sender).push(temp.get(sender).pop());
//                        for(String message : temp.get(sender))
//                            onlineList.get(sender).push(message);
//
//                        // //:Todo: create a new receive panel
//                    }
//                }
//                for(String sender : temp.keySet())
//                    if(!temp.get(sender).isEmpty()) {
//                        System.out.println(sender + " send me" + temp.get(sender).peek());
//                    }
//            }
//            catch (IOException | ClassNotFoundException e) {
//                System.err.println("There is exception :" + e);
//
//            }
        }
    }
   // for Chatwindow functions
   public void autoUpdateList(){
//        autoUpdate.schedule(new Update(this.onlineList, this.oos, this.ois), 1000, 5000);
       autoUpdate.schedule(new Update(), 1000, 5000);}
    // one for all controller actionListener
    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == this.chatLogin.getLoginButton()) {
            if (this.chatLogin != null) {
            try {
                String inputName = this.chatLogin.getInputNickName().getText();
                if (inputName.equals("")) {
                    this.chatLogin.getWarning().setText("Nickname should not be null");
                } else {
                    oos.writeObject(inputName);
                    this.ifLogin = (String) ois.readObject();
                    if (ifLogin.equals("duplicated")) {
                        this.chatLogin.getWarning().setText("Name already exists");
                    } else {
                        this.nickName = inputName;
                        this.chatLogin.dispose();
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("Exception occurs " + ex);
            }

        }
        else if(e.getSource() == this.chatWindow.getMsgButton()){
                try {
                    oos.writeObject(this.chatWindow.getMsg());
                    System.out.println((String)ois.readObject());
                } catch (IOException | ClassNotFoundException ex) {
                    System.err.println("Exception occurs" + ex);
                }
        }
        else if(e.getSource() == this.chatWindow.getTerminate()){
            try{
                oos.writeObject("terminate");
                System.out.println((String)ois.readObject());
                this.isStopped = true;
                this.autoUpdate.cancel();
            } catch (IOException | ClassNotFoundException ex) {
                System.err.println("Exception occurs: " + ex);
            }
            }
        else if(e.getSource() == this.chatWindow.getShowMessage()){

                try {
                    oos.writeObject("show");
                    System.out.println((String)ois.readObject());
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                System.out.println(this.nickName);
                for(String sender : onlineList.keySet()){
                    System.out.println("  +" + sender);
                    for(String message : onlineList.get(sender))
                        System.out.println("    -" + message);
                }
            }
        else if(e.getSource() == this.chatWindow.getUpdateButton()){
                this.updateList();
            }
    }


}

