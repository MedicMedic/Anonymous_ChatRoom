package chatRoom_foreground;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ChatWindow{
    // instance field
    private String nickName;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    // java.swing
    private JFrame chatWindow;

    public ChatWindow(String nickName, ObjectOutputStream oos, ObjectInputStream ois){
        this.nickName = nickName;
        this.oos = oos;
        this.ois = ois;

        chatWindow = new JFrame("Welcome, " + nickName);
        chatWindow.setBounds(570,250, 300, 400);

        chatWindow.setVisible(true);
        chatWindow.setDefaultCloseOperation(new JFrame().EXIT_ON_CLOSE);
    }
}
