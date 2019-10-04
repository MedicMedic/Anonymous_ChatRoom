package chatRoom_View;

import chatRoom_Model.MessageMap;
import chatRoom_Model.MessagePane;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class ChatWindow extends JFrame{
//    private ObjectOutputStream oos;
//    private ObjectInputStream ois;

    // JComponent
    private Color[] nord;
    private JButton terminate;
    private JButton sendButton;

    private JPanel messageList;
    private JPanel send;
    private JPanel receive;

    private JLabel senderLabel;
    private JTextArea textArea;

    public ChatWindow(String nickName){

        // chat instance
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
        JPanel text = new JPanel();text.setPreferredSize(new Dimension(500, 100));text.setBackground(nord[2]);
        chat.setLayout(new BorderLayout());
        JPanel senderPanel = new JPanel();senderPanel.setPreferredSize(new Dimension(0, 60));senderPanel.setBackground(nord[0]);
        senderPanel.setBorder(BorderFactory.createLineBorder(nord[1]));
        senderPanel.setLayout(new GridLayout(1,1));
        senderLabel = new JLabel();senderPanel.setBackground(nord[0]);
        senderPanel.add(senderLabel);

        chat.add(senderPanel, BorderLayout.NORTH);
        chat.add(tempHis, BorderLayout.CENTER);
        chat.add(text, BorderLayout.SOUTH);

        receive = new JPanel();receive.setBackground(nord[0]);receive.setLayout(flowLead);receive.setPreferredSize(new Dimension(250, 0));
        FlowLayout flowTrail = new FlowLayout(FlowLayout.TRAILING, 0, 0);
        send = new JPanel();send.setBackground(nord[0]);send.setLayout(flowTrail);send.setPreferredSize(new Dimension(250, 0));

//        history.setLayout(new GridLayout(1,2));
        history.setLayout(new BorderLayout());
        history.add(receive, BorderLayout.WEST);
        history.add(send, BorderLayout.EAST);
//        history.setBorder(new EmptyBorder(-5, 0, -5, 0));
//        for(int i = 0; i<10; i++){
//            send.add(new MessagePane("233", 1));
//            receive.add(new MessagePane("baba", 1));
//        }

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

        textArea = new JTextArea();textArea.setPreferredSize(new Dimension(500,90)); textArea.setBackground(nord[2]);
        textArea.setLineWrap(true);
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textArea, BorderLayout.CENTER);

        sendButton = new JButton("send");
        sendButton.setBackground(nord[3]);
        sendButton.setOpaque(true);
        sendButton.setBorderPainted(false);
        sendButton.setEnabled(false);
        sendPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        sendPanel.add(sendButton);

        // basic JFrame settings
        this.pack();
        this.setBounds(350,170, 830, 500);
        this.setMinimumSize(new Dimension(830, 500));
        this.setTitle("Welcome " + nickName);
        this.setVisible(true); this.setDefaultCloseOperation(new JFrame().DO_NOTHING_ON_CLOSE); } public JButton getsendButton(){ return this.sendButton;
    }
    public JButton getTerminate(){
        return this.terminate;
    }

    public String getMsg(){
        return this.textArea.getText();
    }
    public void setTextArea(){
        this.textArea.setText("");
    }
    public void setSenderLabel(String senderName){
        senderLabel.setText(senderName);
        senderLabel.setHorizontalAlignment(JTextField.CENTER);
        senderLabel.setForeground(nord[3]);
    }

    public void showHistory(Stack<String>sendMessageList, Stack<String>receiveMessageList){
        this.send.removeAll();
        this.receive.removeAll();
//        send.revalidate();
//        receive.revalidate();
        if(sendMessageList.isEmpty() && receiveMessageList.isEmpty()){
            this.send.revalidate();
            this.receive.revalidate();
            this.send.repaint();
            this.receive.repaint();
        }
        else {
            for (String sendMessage : sendMessageList) {
                if (sendMessage == null) {
                    JPanel blank = new JPanel();
                    blank.setPreferredSize(new Dimension(250, 24));
                    blank.setBackground(nord[0]);
                    send.add(blank);
                } else {
                    send.add(new MessagePane(sendMessage, 0));
                }
            }
            for (String receiveMessage : receiveMessageList) {
                if (receiveMessage == null) {
                    JPanel blank = new JPanel();
                    blank.setPreferredSize(new Dimension(250, 24));
                    blank.setBackground(nord[0]);
                    receive.add(blank);
                } else {
                    receive.add(new MessagePane(receiveMessage, 1));
                }
            }
            this.send.revalidate();
            this.receive.revalidate();
            this.send.repaint();
            this.receive.repaint();
        }

    }



    public ArrayList<MessagePane> showMessageList(Stack<String> userStack, MessageMap onlinList, MessageMap myMessage){
        messageList.removeAll();
        ArrayList<MessagePane> messagePaneList = new ArrayList<>();
//        messageList.updateUI();
//        messageList.repaint();

        for(String user : userStack){
            MessagePane messagePane;
            if(onlinList.get(user).isEmpty()) {
                messagePane = new MessagePane(user);
                messageList.add(messagePane);
            }
            else {
                String latestMessage;

                if(onlinList.get(user).peek() != null){
                    latestMessage = user + " says: " + onlinList.get(user).peek();
                }
                else{
                    latestMessage = user + " " + myMessage.get(user).peek();
                }
                messagePane = new MessagePane(latestMessage);
                messageList.add(messagePane);
            }
            messagePane.setUserName(user);
            messagePaneList.add(messagePane);

        }
        messageList.revalidate();
        messageList.repaint();
        return messagePaneList;

    }


}
