package ChatRoom_controller;

import chatRoom_foreground.ChatJoin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class ChatJoinController implements ActionListener {
    private ChatJoin chatJoin;
    private JFrame chatWindow;
    private HashMap OnlineList;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private String nickName;


    public ChatJoinController(ChatJoin chatJoin, ObjectOutputStream oos, ObjectInputStream ois) {
        this.chatJoin = chatJoin;
        this.oos = oos;
        this.ois = ois;
        this.chatJoin.getJoinButton().addActionListener(this);
    }

    public void setNickName(ChatJoin chatJoin) {
        this.chatJoin.getJoinButton().addActionListener(this);
    }

    public String getNickName() {
        return this.nickName;
    }


    // single controller actionListener
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.chatJoin.getJoinButton()) {
            try {
                this.nickName = this.chatJoin.getInputNickName().getText();
                if (nickName.equals("")) {
                    this.chatJoin.getWarning().setText("Nickname should not be null");
                    System.out.println("233");


                } else {
                    oos.writeObject(this.nickName);
                    if (((String) ois.readObject()).equals("duplicated")) {
                        this.chatJoin.getWarning().setText("Name already exists");
                        this.chatJoin.getWarning().setVisible(true);
                    } else {

                        this.chatJoin.getWarning().setText("The nick names should not be blank");
                        this.chatJoin.getWarning().setVisible(true);
                        this.chatJoin.dispose();
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("Exception occurs " + ex);
            }

        }
    }
}
