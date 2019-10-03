package chatRoom_View;

import chatRoom_Model.FastGridBag;
import chatRoom_Model.MessageMap;
import chatRoom_Model.MessagePane;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Stack;

public class ChatWindow extends JFrame{
//    private ObjectOutputStream oos;
//    private ObjectInputStream ois;

    // JComponent
    Color[] nord;
    JButton terminate;
    JButton sendButton;

    JPanel messageList;
    JPanel send;
    JPanel receive;

    // chat instance
    String senderName;

    public ChatWindow(String nickName){
        nord = new Color[4];
        nord[0] = new Color(48,55,87);
        nord[1] = new Color(29, 35, 65);
        nord[2] = new Color(109, 122, 145);
        nord[3] = new Color(193, 206, 218);

        this.setLayout(new BorderLayout());

        JPanel fixed = new JPanel();fixed.setPreferredSize(new Dimension(330, 500));
        JPanel chat = new JPanel();chat.setPreferredSize(new Dimension(500, 500));
        this.add(fixed, BorderLayout.WEST);
        this.add(chat, BorderLayout.CENTER);

        JPanel quit=  new JPanel();quit.setPreferredSize(new Dimension(70,500));quit.setBackground(nord[0]);quit.setMinimumSize(new Dimension(70,500));
        messageList = new JPanel();
        messageList.setPreferredSize(new Dimension(260, 900));messageList.setBackground(nord[1]);
        FlowLayout flowLead = new FlowLayout(FlowLayout.LEADING, 0, 0);
        messageList.setLayout(flowLead);
//        messageList.setBorder(new EmptyBorder(-5, 0, -5, 0));

        fixed.setLayout(new BorderLayout());
        fixed.add(quit, BorderLayout.WEST);
        fixed.add(messageList, BorderLayout.EAST);

        JPanel quitA = new JPanel();quitA.setBackground(nord[0]);
        terminate = new JButton();terminate.setBackground(nord[0]);terminate.setSize(new Dimension(25,25));
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

        JPanel history = new JPanel();history.setPreferredSize(new Dimension(500, 740));history.setBackground(nord[0]);
        JPanel tempHis = new JPanel();tempHis.setPreferredSize(new Dimension(500, 740));tempHis.setBackground(nord[0]);
        tempHis.setLayout(new BorderLayout());
        tempHis.add(history, BorderLayout.CENTER);
        JPanel text = new JPanel();text.setPreferredSize(new Dimension(500, 120));text.setBackground(nord[2]);
        chat.setLayout(new BorderLayout());
        chat.add(tempHis, BorderLayout.CENTER);
        chat.add(text, BorderLayout.SOUTH);

        receive = new JPanel();receive.setBackground(Color.orange);receive.setLayout(flowLead);
        FlowLayout flowTrail = new FlowLayout(FlowLayout.TRAILING, 0, 0);
        send = new JPanel();send.setBackground(Color.CYAN);send.setLayout(flowTrail);
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

        sendButton = new JButton("send");
        sendButton.setBackground(nord[3]);
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

    public JButton getsendButton(){
        return this.sendButton;
    }
    public JButton getTerminate(){
        return this.terminate;
    }
    public String setSenderName(){
        return senderName;
    }
    public void showHistory(Stack<String>sendMessageList, Stack<String>receiveMessageList){
        this.send.removeAll();
        this.receive.removeAll();

        for(String sendMessage : sendMessageList){
            if(sendMessage == null){
                JPanel blank = new JPanel();
                blank.setPreferredSize(new Dimension(250, 24));
                blank.setBackground(nord[0]);
                send.add(blank);
            }
            else{
                send.add(new MessagePane(sendMessage, 0));
            }
        }
        for(String receiveMessage : receiveMessageList){
            if(receiveMessage == null){
                JPanel blank = new JPanel();
                blank.setPreferredSize(new Dimension(250, 24));
                blank.setBackground(nord[0]);
                send.add(blank);
            }
            else{
                receive.add(new MessagePane(receiveMessage, 0));
            }
        }

    }



    public ArrayList<JButton> showMessageList(Stack<String> userStack, MessageMap onlinList){
        messageList.removeAll();
        ArrayList<JButton> buttonList = new ArrayList<>();
//        messageList.updateUI();
//        messageList.repaint();
        for(String user : userStack){
            MessagePane messagePane;
            if(onlinList.get(user).isEmpty()) {
                messagePane = new MessagePane(user);
                messageList.add(messagePane);
            }
            else {
                messagePane = new MessagePane(user + ": " + onlinList.get(user).peek());
                messageList.add(messagePane);
            }
            buttonList.add(messagePane.getMessageButton());
        }
        messageList.revalidate();
        return buttonList;

    }

//    public String getMsg(){
//        return "Msg "+this.msg.getText();
//    }

    // test
//    public JButton getShowMessage(){
//        return this.showMessage;
//    }
//    public JButton getUpdateButton(){
//        return this.updateButton;
//    }
//    public Stack<JButton> getMessageButtonList(){
//        return this.messageButtonList;
//    }
//    private GridBagConstraints fastGridBag(GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight,
//                        double weightx, double weighty, int fill){
//        gbc.gridx = gridx;
//        gbc.gridy = gridy;
//        gbc.gridwidth = gridwidth;
//        gbc.gridheight = gridheight;
//        gbc.weightx = weightx;
//        gbc.weighty = weighty;
//        gbc.fill = fill;
//
//        return gbc;
//    }

    public static void main(String[] args) {
        new ChatWindow("23e");
    }
}
