package ChatRoom_controller;

import chatRoom_foreground.ChatLogin;
import chatRoom_foreground.ChatWindow;

import javax.swing.*;
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
    private ChatWindow chatWindow;
    private HashMap<String, Stack<String>> onlineList;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private String nickName;
    private String ifLogin;
    private Timer autoUpdate;



    // constructors

    // constructor for ChatLogin
    public ChatController(ChatLogin chatLogin, ObjectOutputStream oos, ObjectInputStream ois) {
        this.chatLogin = chatLogin;
        this.oos = oos;
        this.ois = ois;
        this.ifLogin=  "duplicated";
        this.chatLogin.getJoinButton().addActionListener(this);
    }

    // constructor for ChatWindow
    public ChatController(ChatWindow chatWindow, String nickName, HashMap<String, Stack<String>> onlineList,
                          ObjectOutputStream oos, ObjectInputStream ois){
        this.chatWindow = chatWindow;
       this.nickName = nickName;
       this.onlineList = onlineList;
       this.oos = oos;
       this.ois = ois;
       this.autoUpdate = new Timer();


       // test
        this.chatWindow.getTestButton().addActionListener(this);
    }

    // for ChatLogin functions
    public String getNickName() {
        while(this.nickName == null){
            Thread.onSpinWait();
        }
        return this.nickName;
    }

   // for Chatwindow functions
   public void autoUpdateList(){
        autoUpdate.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    oos.writeObject("UpdateList");


                    HashMap<String, Stack<String>> temp;
                    temp = (HashMap)ois.readObject();
                    for(String sender : temp.keySet()){
                        // old sender
                        if(onlineList.containsKey(sender)){
                            while(!temp.get(sender).isEmpty())
                                onlineList.get(sender).push(temp.get(sender).pop());
                            //:Todo: create a new receive panel
                        }else{  // new sender is needed to add
                            onlineList.put(sender, new Stack<>());
                            while(!temp.get(sender).isEmpty())
                                onlineList.get(sender).push(temp.get(sender).pop());
                            //:Todo: create a new receive panel
                        }
                    }
                    for(String s : temp.keySet())
                        if(!temp.get(s).isEmpty()) {
                            System.out.println(s + " send me" + temp.get(s).peek());
                        }
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("There is exception :" + e);

                }
            }
        }, 1000, 5000);
   }
    // one for all controller actionListener
    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == this.chatLogin.getJoinButton()) {
            if (this.chatLogin != null) {
            try {
                String inputName = this.chatLogin.getInputNickName().getText();
                if (inputName.equals("")) {
                    this.chatLogin.getWarning().setText("Nickname should not be null");
                    System.out.println("233");


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
        else if(e.getSource() == this.chatWindow.getTestButton()){
                try {
                    oos.writeObject(this.chatWindow.getTestInput());
                    System.out.println((String)ois.readObject());
                } catch (IOException | ClassNotFoundException ex) {
                    System.err.println("Exception occurs" + ex);
                }
        }
    }


}
