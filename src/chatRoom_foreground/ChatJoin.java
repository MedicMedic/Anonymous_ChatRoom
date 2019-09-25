package chatRoom_foreground;

import javax.imageio.IIOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ChatJoin extends JFrame {

    // instance filed
    private JTextField heading;
    private JButton join;
    private JTextField inputNickName;
    private JTextField warning;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private boolean sign;
    private String nickName;


    public ChatJoin(){
        this.nickName = nickName;
        this.oos = oos;
        this.ois = ois;
        this.sign = sign;
        this.setTitle("Login");

        heading = new JTextField("Anonymous Chat Room");
        heading.setEnabled(false);

        heading.setHorizontalAlignment(JTextField.CENTER);

        inputNickName = new JTextField("nickName");
        inputNickName.setHorizontalAlignment(JTextField.CENTER);

        warning = new JTextField(10);

        join = new JButton("Join and Talk");

        //        frame.setLayout(new GridLayout(3,1));
        this.setLayout(new FlowLayout());
        this.add(heading);
        this.add(inputNickName);
        this.add(join);
        this.add(warning);



        this.setBounds(570,250, 300, 400);
        this.setMaximumSize(this.getSize());
        this.setMinimumSize(this.getSize());
        this.setVisible(true);
        this.setDefaultCloseOperation(new JFrame().EXIT_ON_CLOSE);
    }

    public JButton getJoinButton(){
        return this.join;
    }
    public JTextField getWarning(){
        return this.warning;
    }
    public String getNickName(){
        return this.nickName;
    }
    public JTextField getInputNickName(){
        return this.inputNickName;
    }


}
