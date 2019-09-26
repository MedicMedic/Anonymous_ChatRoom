package chatRoom_foreground;

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
    private JTextField testInput;
    private JButton testButton;


    public ChatWindow(String nickName){
        this.nickName = nickName;
        this.oos = oos;
        this.ois = ois;

        testInput = new JTextField("Input here", 10);
        testButton = new JButton("testCommand");

        this.setLayout(new FlowLayout());
        this.add(testInput);
        this.add(testButton);
        this.setTitle("Welcome, " + nickName);
        this.setBounds(350,170, 800, 560);


        this.setVisible(true);
        this.setDefaultCloseOperation(new JFrame().EXIT_ON_CLOSE);
    }

    public JButton getTestButton(){
        return this.testButton;
    }
    public String getTestInput(){
        return this.testInput.getText();
    }
}
