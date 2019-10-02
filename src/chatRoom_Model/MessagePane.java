package chatRoom_Model;

import chatRoom_View.ChatWindow;

import javax.swing.*;
import java.awt.*;


public class MessagePane extends JPanel {
    private JButton messageButton;

    public MessagePane(String userName) {
        this.setPreferredSize(new Dimension(260, 60));
        this.setBackground(new Color(109, 122, 145));

        this.setLayout(new GridLayout(1, 1));

        messageButton = new JButton(userName);
        messageButton.setBorder(BorderFactory.createLineBorder(new Color(29, 35, 65)));
//        messageButton.setOpaque(true);
//        messageButton.setBorderPainted(false);
//        messageButton.setBorder(BorderFactory.createLineBorder(new Color(48,55,87)));
        this.add(messageButton);
    }

    public JButton getMessageButton() {
        return messageButton;
    }
}
