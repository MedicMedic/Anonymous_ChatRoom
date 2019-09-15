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

public class ChatRoom_Login implements ActionListener{

    // instance filed
    private JFrame frame;
    private JTextField heading;
    private JButton join;
    private JTextField nickName;
    private JTextField warning;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private boolean sign;

    private String newUser;

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == join){
            try {

                newUser = nickName.getText();
                oos.writeObject(newUser);
                if (((String) ois.readObject()).equals("duplicated")) {
                    warning.setText(newUser + " is existing, please change another name");
                    warning.setVisible(true);
                }else
                    this.sign = true;

            }catch(IOException | ClassNotFoundException ex) {
                System.out.println("Exception occurs " + ex);
            }
        }
    }
    public ChatRoom_Login(ObjectOutputStream oos, ObjectInputStream ois, boolean sign){
        this.oos = oos;
        this.ois = ois;
        this.sign = sign;


        frame = new JFrame("login");
        heading = new JTextField("Anonymous Chat Room");
        heading.setEnabled(false);

        heading.setHorizontalAlignment(JTextField.CENTER);

        nickName = new JTextField("nickName");
        nickName.setHorizontalAlignment(JTextField.CENTER);

        warning = new JTextField();
        warning.setVisible(false);

        join = new JButton("Join and Talk");
        join.addActionListener(this);

        //        frame.setLayout(new GridLayout(3,1));
        frame.setLayout(new FlowLayout());
        frame.add(heading);
        frame.add(nickName);
        frame.add(join);



        frame.setBounds(570,250, 300, 400);
        frame.setMaximumSize(frame.getSize());
        frame.setMinimumSize(frame.getSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(new JFrame().EXIT_ON_CLOSE);
    }

////public static void main(String[] args){
////        new ChatRoom_Login();
//}
}
