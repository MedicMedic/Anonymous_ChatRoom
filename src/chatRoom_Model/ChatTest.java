package chatRoom_Model;

import chatRoom_View.ChatWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ChatTest extends JFrame {
    JPanel messageList;
    ChatTest(){
        // Background settings
        Color[] nord = new Color[3];
        nord[0] = new Color(48,55,87);
        nord[1] = new Color(29, 35, 65);
        nord[2] = new Color(109, 122, 145);

        this.setLayout(new BorderLayout());

        JPanel fixed = new JPanel();fixed.setPreferredSize(new Dimension(330, 500));
        JPanel chat = new JPanel();chat.setPreferredSize(new Dimension(500, 500));
        this.add(fixed, BorderLayout.WEST);
        this.add(chat, BorderLayout.CENTER);

        JPanel quit=  new JPanel();quit.setPreferredSize(new Dimension(70,500));quit.setBackground(nord[0]);quit.setMinimumSize(new Dimension(70,500));
        messageList = new JPanel();
        messageList.setPreferredSize(new Dimension(260, 900));messageList.setBackground(nord[1]);
        FlowLayout flow = new FlowLayout(FlowLayout.LEADING, 0, 0);
        messageList.setLayout(flow);
//        messageList.setBorder(new EmptyBorder(-5, 0, -5, 0));

        fixed.setLayout(new BorderLayout());
        fixed.add(quit, BorderLayout.WEST);
        fixed.add(messageList, BorderLayout.EAST);

        JPanel quitA = new JPanel();quitA.setBackground(nord[0]);
        JButton terminate = new JButton();terminate.setBackground(nord[0]);terminate.setSize(new Dimension(25,25));
        terminate.setOpaque(true);
        terminate.setBorderPainted(false);
        ImageIcon exit = new ImageIcon("exit.png");
        Image temp = exit.getImage().getScaledInstance(terminate.getWidth(), terminate.getHeight(),
                exit.getImage().SCALE_DEFAULT);
        exit = new ImageIcon(temp);
        terminate.setIcon(exit);
        quit.setLayout(new BorderLayout());
        quit.add(quitA, BorderLayout.NORTH);
        quit.add(terminate, BorderLayout.SOUTH);

        JScrollPane scroll = new JScrollPane();
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setViewportView(messageList);
        fixed.add(scroll);
        scroll.setBorder(new EmptyBorder(-5, 0, -5, 0));

        for(int i = 0; i<15; i++) {
            messageList.add(new MessagePane("test"));
        }

        JPanel history = new JPanel();history.setPreferredSize(new Dimension(330, 900));history.setBackground(nord[0]);
        JPanel tempHis = new JPanel();tempHis.setPreferredSize(new Dimension(330, 900));tempHis.setBackground(nord[0]);
        tempHis.setLayout(new BorderLayout());
        tempHis.add(history, BorderLayout.CENTER);
        JPanel text = new JPanel();text.setPreferredSize(new Dimension(500, 120));text.setBackground(nord[2]);
        chat.setLayout(new BorderLayout());
        chat.add(tempHis, BorderLayout.CENTER);
        chat.add(text, BorderLayout.SOUTH);

        JPanel receive = new JPanel();receive.setBackground(Color.orange);
        JPanel send = new JPanel();send.setBackground(Color.CYAN);
        history.setLayout(new GridLayout(1,2));
        history.add(receive);
        history.add(send);

        JScrollPane historyScroll = new JScrollPane();
        historyScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        historyScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        historyScroll.setViewportView(history);
        tempHis.add(historyScroll);
        historyScroll.setBorder(new EmptyBorder(-5, 0, -5, 0));

        JPanel textPanel = new JPanel();textPanel.setPreferredSize(new Dimension(500, 90));textPanel.setBackground(nord[2]);
        JPanel sendPanel = new JPanel();textPanel.setPreferredSize(new Dimension(500, 30));sendPanel.setBackground(nord[2]);
        text.setLayout(new BorderLayout());
        text.add(textPanel, BorderLayout.CENTER);
        text.add(sendPanel, BorderLayout.SOUTH);

        JTextArea textArea = new JTextArea();textArea.setPreferredSize(new Dimension(500,90)); textArea.setBackground(nord[2]);
        textArea.setLineWrap(true);
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textArea, BorderLayout.CENTER);

        JButton sendButton = new JButton("send");
        sendButton.setBackground(new Color(193, 206, 218));
        sendButton.setOpaque(true);
        sendButton.setBorderPainted(false);
        sendPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        sendPanel.add(sendButton);

        // basic JFrame settings
        this.pack();
        this.setBounds(350,170, 830, 500);
        this.setMinimumSize(new Dimension(830, 500));
        this.setVisible(true);
        this.setDefaultCloseOperation(new JFrame().EXIT_ON_CLOSE);
    }

    public void showMessage(){
        messageList.removeAll();
        messageList.updateUI();
        messageList.repaint();
        for(int i = 0; i<2; i++)
            messageList.add(new MessagePane("ese"));

    }

    public static void main(String[] args) {
        ChatTest temp = new ChatTest();
//        temp.showMessage();
    }
}
