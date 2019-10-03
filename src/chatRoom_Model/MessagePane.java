package chatRoom_Model;

import chatRoom_View.ChatWindow;

import javax.swing.*;
import java.awt.*;


public class MessagePane extends JPanel {
    private JButton messageButton;
    Color[] nord;
    {
        nord = new Color[4];
        nord[0] = new Color(48, 55, 87);
        nord[1] = new Color(29, 35, 65);
        nord[2] = new Color(109, 122, 145);
        nord[3] = new Color(193, 206, 218);
    }
    public MessagePane(String SRMessage, int LR){
        this.setPreferredSize(new Dimension(250,24));
        this.setBackground(nord[0]);

        this.setLayout(new GridLayout(1, 1));
        JLabel message = new JLabel(SRMessage);

        if(LR == 1){
            message.setHorizontalAlignment(JTextField.LEFT);
            message.setBackground(nord[3]);
            this.add(message);
        }else{
            message.setHorizontalAlignment(JTextField.RIGHT);
            message.setBackground(nord[2]);
            this.add(message);
        }
    }
    public MessagePane(String userName) {
        this.setPreferredSize(new Dimension(260, 60));
        this.setBackground(nord[2]);

        this.setLayout(new GridLayout(1, 1));

        messageButton = new JButton(userName);
        messageButton.setHorizontalAlignment(JTextField.LEFT);
        messageButton.setBorder(BorderFactory.createLineBorder(nord[1]));
//        messageButton.setOpaque(true);
//        messageButton.setBorderPainted(false);
//        messageButton.setBorder(BorderFactory.createLineBorder(new Color(48,55,87)));
        this.add(messageButton);
    }

    public JButton getMessageButton() {
        return messageButton;
    }
}
