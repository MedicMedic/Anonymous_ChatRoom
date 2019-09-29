package chatRoom_View;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ChatWindow extends JFrame{
    // instance field
    private String nickName;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private JPanel messageList;
    // java.swing

    // send Msg
    private JTextField msg;
    private JButton msgButton;

    // terminate
    private JButton terminate;

    // test
    private JButton showMessage;
    private JButton updateButton;


    public ChatWindow(String nickName){
        this.nickName = nickName;
        this.oos = oos;
        this.ois = ois;

        msg = new JTextField("Input here", 10);
        msgButton = new JButton("sendMsg");
        terminate = new JButton("quit");
        showMessage = new JButton(("showMessage"));
        updateButton = new JButton("update");

        this.setLayout(new FlowLayout());
        this.add(msg);
        this.add(msgButton);
        this.add(terminate);
        this.add(showMessage);
        this.add(updateButton);


        // basic JFrame settings
        this.setTitle("Welcome, " + nickName);
        this.setBounds(350,170, 800, 560);
        this.setVisible(true);
        this.setDefaultCloseOperation(new JFrame().EXIT_ON_CLOSE);
    }

    public JButton getMsgButton(){
        return this.msgButton;
    }
    public JButton getTerminate(){
        return this.terminate;
    }
    public String getMsg(){
        return "Msg "+this.msg.getText();
    }

    // test
    public JButton getShowMessage(){
        return this.showMessage;
    }
    public JButton getUpdateButton(){
        return this.updateButton;
    }

}
