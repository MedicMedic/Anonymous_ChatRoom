package ChatRoom_Controller;

import chatRoom_Model.MessageMap;
import chatRoom_Model.MessagePane;
import chatRoom_View.ChatLogin;
import chatRoom_View.ChatWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

public class ChatController implements ActionListener {
    private ChatLogin chatLogin;
    private String ifLogin;

    private ChatWindow chatWindow;
    private String nickName;
    private MessageMap onlineList;
    private MessageMap myMessage;

    private boolean isStopped;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private ArrayList<MessagePane> messagePaneList;
    private String senderName;
    private Timer autoUpdate;

    Stack<String> sendMessageList;
    Stack<String> receiveMessageList;
    // constructors

    // constructor for ChatLogin
    public ChatController(ChatLogin chatLogin, ObjectOutputStream oos, ObjectInputStream ois) {
        this.chatLogin = chatLogin;
        this.oos = oos;
        this.ois = ois;
        this.ifLogin = "duplicated";
        this.chatLogin.getLoginButton().addActionListener(this);
    }

    // constructor for ChatWindow
    public ChatController(ChatWindow chatWindow, String nickName, MessageMap onlineList,
                          MessageMap myMessage, boolean isStopped, ObjectOutputStream oos, ObjectInputStream ois) {
        this.chatWindow = chatWindow;
        this.nickName = nickName;
        this.onlineList = onlineList;
        this.myMessage = myMessage;
        this.isStopped = isStopped;
        this.oos = oos;
        this.ois = ois;

        this.autoUpdate = new Timer();


        this.chatWindow.getsendButton().addActionListener(this);
        this.chatWindow.getTerminate().addActionListener(this);
        // test
//        this.chatWindow.getShowMessage().addActionListener(this);
//        this.chatWindow.getUpdateButton().addActionListener(this);
    }

    // for ChatLogin functions
    public String getNickName() {
        while (this.nickName == null) {
            Thread.onSpinWait();
        }
        return this.nickName;
    }


    private void updateList() {
        try {
            this.oos.writeObject("UpdateList");
            System.out.println((String) ois.readObject());
            MessageMap temp;
            temp = (MessageMap) (ois.readObject());

            Stack<String> userStack = new Stack<>();
            for(String user : onlineList.keySet()){
                if(!temp.containsKey(user)){
                    userStack.push(user);
                }
            }
            for(String user : temp.keySet()){
                userStack.push(user);
            }
            // Todo: update myMessageMap
            // update local onlineList
            for (String sender : temp.keySet()) {
                if (!onlineList.containsKey(sender)) {
                    onlineList.put(sender, new Stack<String>());
                    myMessage.put(sender, new Stack<String>());
                }
                while(!temp.get(sender).isEmpty()) {
                    onlineList.get(sender).push(temp.get(sender).pop());
                    myMessage.get(sender).push(null);
                }
            }
            // avoid errors from first client
            if(!userStack.isEmpty()) {
                messagePaneList = chatWindow.showMessageList(userStack, onlineList, myMessage);
                for(MessagePane messagePane: messagePaneList){
                    messagePane.getMessageButton().addActionListener(this);
                }
            }
            // test
            System.out.println(this.nickName);
            for (String sender : onlineList.keySet()) {
                System.out.println("  +" + sender);
                for (String message : onlineList.get(sender))
                    System.out.println(message);
            }
            updateHistory();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception occurs " + e);

        }
    }




    // for Chatwindow functions
    public void autoUpdateList() {
        autoUpdate.schedule(new TimerTask() {
            @Override
            public void run() {
                updateList();
            }
        }, 1000, 5000);
    }

    private void updateHistory(){
        System.out.println("##########");
        System.out.println(senderName);
        sendMessageList = new Stack<>();
        receiveMessageList = new Stack<>();
        System.out.println(onlineList.values().isEmpty());
        if(senderName != null) {
            if (!onlineList.get(senderName).isEmpty()) {
                for (String receiveMessage : onlineList.get(senderName))
                    receiveMessageList.push(receiveMessage);
            }
            if (!myMessage.get(senderName).isEmpty()) {
                for (String sendMessage : myMessage.get(senderName))
                    sendMessageList.push(sendMessage);
            }
            this.chatWindow.showHistory(sendMessageList, receiveMessageList);
        }
    }
    // one for all controller actionListener
    public void actionPerformed(ActionEvent e) {
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

        } else if (e.getSource() == this.chatWindow.getsendButton()) {
            try {
                oos.writeObject("Msg " + this.senderName + " " +this.chatWindow.getMsg());
                myMessage.get(senderName).push(this.chatWindow.getMsg());
                onlineList.get(senderName).push(null);
                this.chatWindow.setTextArea();
                System.out.println((String) ois.readObject());

                updateHistory();

            } catch (IOException | ClassNotFoundException ex) {
                System.err.println("Exception occurs" + ex);
            }
        } else if (e.getSource() == this.chatWindow.getTerminate()) {
            try {
                oos.writeObject("terminate");
                System.out.println((String) ois.readObject());
                this.isStopped = true;
                this.autoUpdate.cancel();
            } catch (IOException | ClassNotFoundException ex) {
                System.err.println("Exception occurs: " + ex);
            }
        } else{ // buttonList
            JButton userButton = (JButton)e.getSource();
            for(MessagePane messagePane : messagePaneList){
                if(messagePane.getMessageButton().getText().equals(userButton.getText())){
                    this.senderName = messagePane.getUserName();
                }
            }

            updateHistory();
        }

//        else if (e.getSource() == this.chatWindow.getShowMessage()) {
//
//            try {
//                oos.writeObject("show");
//                System.out.println((String) ois.readObject());
//            } catch (IOException | ClassNotFoundException ex) {

//            }
//            System.out.println(this.nickName);
//            for (String sender : onlineList.keySet()) {
//                System.out.println("  +" + sender);
//                for (String message : onlineList.get(sender))
//                    System.out.println("    -" + message);
//            }
//        } else if (e.getSource() == this.chatWindow.getUpdateButton()) {
//            this.updateList();
//        }
    }


}

