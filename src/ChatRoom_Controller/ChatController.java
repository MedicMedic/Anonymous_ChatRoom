package ChatRoom_Controller;

import chatRoom_Model.MessageMap;
import chatRoom_View.ChatLogin;
import chatRoom_View.ChatWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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


    private Timer autoUpdate;


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


        this.chatWindow.getMsgButton().addActionListener(this);
        this.chatWindow.getTerminate().addActionListener(this);
        // test
        this.chatWindow.getShowMessage().addActionListener(this);
        this.chatWindow.getUpdateButton().addActionListener(this);
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

            // test
            System.out.println(this.nickName);
            for (String sender : onlineList.keySet()) {
                System.out.println("  +" + sender);
                for (String message : onlineList.get(sender))
                    System.out.println(message);
            }
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

        } else if (e.getSource() == this.chatWindow.getMsgButton()) {
            try {
                oos.writeObject(this.chatWindow.getMsg());
                System.out.println((String) ois.readObject());


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
        }
//        else if (e.getSource() == this.chatWindow.getShowMessage()) {
//
//            try {
//                oos.writeObject("show");
//                System.out.println((String) ois.readObject());
//            } catch (IOException | ClassNotFoundException ex) {
//                ex.printStackTrace();
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

