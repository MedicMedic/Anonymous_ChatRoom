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

public class ChatJoin extends JFrame implements ActionListener{

    // instance filed
    private JTextField heading;
    private JButton join;
    private JTextField inputNickName;
    private JTextField warning;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private boolean sign;
    private String nickName;


    public ChatJoin(String nickName, ObjectOutputStream oos, ObjectInputStream ois, boolean sign){
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

        warning = new JTextField("Name Exisiting");
        warning.setVisible(false);

        join = new JButton("Join and Talk");
        join.addActionListener(this);

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
    public String getNickName(){
        return this.nickName;
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == join){
            try {

                this.nickName = inputNickName.getText();
                oos.writeObject(nickName);
                if (((String) ois.readObject()).equals("duplicated")) {
                    warning.setVisible(true);
                }else {
                    this.sign = true;
                    this.dispose();
                }
            }catch(IOException | ClassNotFoundException ex) {
                System.out.println("Exception occurs " + ex);
            }
        }
    }

}
