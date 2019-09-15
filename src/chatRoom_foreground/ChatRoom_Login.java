package chatRoom_foreground;

import javax.swing.*;
import java.awt.*;

public class ChatRoom_Login {

    // instance filed
    private JFrame frame;
    private JTextField heading;
    private JButton join;
    private JTextField nickName;

    public ChatRoom_Login(){
        frame = new JFrame("login");

        heading = new JTextField("Anonymous Chat Room");
        heading.setEnabled(false);

        heading.setHorizontalAlignment(JTextField.CENTER);

        nickName = new JTextField("nickName");
        nickName.setHorizontalAlignment(JTextField.CENTER);

        join = new JButton("Join and Talk");
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

public static void main(String[] args){
        new ChatRoom_Login();
}
}
