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
import java.util.Stack;

public class ChatWindow extends JFrame{
//    private ObjectOutputStream oos;
//    private ObjectInputStream ois;

    private JPanel messageList;
    private JPanel fixed;
    // java.swing

    // send Msg
    private JTextField msg;
    private JButton msgButton;

    // terminate
    private JButton terminate;

    // updateList

    // test
    private JButton showMessage;
    private JButton updateButton;
    private Stack<JButton> messageButtonList;
    public static Color[] nord;


    public ChatWindow(String nickName){
        // instance field

        msg = new JTextField("Input here", 10);

        msgButton = new JButton("sendMsg");
        terminate = new JButton();
        showMessage = new JButton(("showMessage"));
        updateButton = new JButton("update");

        messageButtonList = new Stack<JButton>();

        // panels
        JPanel windowPanel = new JPanel();
        this.setContentPane(windowPanel);
        this.setLayout(new BorderLayout());


        // Background settings
         nord = new Color[3];
        nord[0] = new Color(48,55,87);
        nord[1] = new Color(29, 35, 65);
        nord[2] = new Color(109, 122, 145);

        fixed = new JPanel();
        fixed.setPreferredSize(new Dimension(330, 0));
        // set quit panel
        JPanel quit = new JPanel();quit.setPreferredSize(new Dimension(70, 0)); quit.setBackground(nord[0]);
        JPanel quitA = new JPanel();quitA.setBackground(nord[0]);
        // set quit button
        terminate.setBackground(nord[0]);
        terminate.setOpaque(true);
        terminate.setBorderPainted(false);
        ImageIcon exit = new ImageIcon("exit.png");
        terminate.setSize(25,25);
        Image temp = exit.getImage().getScaledInstance(terminate.getWidth(), terminate.getHeight(),
                exit.getImage().SCALE_DEFAULT);
        exit = new ImageIcon(temp);
        terminate.setIcon(exit);
        quit.setLayout(new BorderLayout());
        quit.add(quitA, BorderLayout.NORTH);
        quit.add(terminate, BorderLayout.SOUTH);
        // set messageList panel
        messageList = new JPanel();messageList.setPreferredSize(new Dimension(260, 500));
        messageList.setBackground(nord[1]);
        // show auto-updating userList
        FlowLayout flow = new FlowLayout(FlowLayout.LEADING, 0, -6);
        messageList.setLayout(flow);
        messageList.setBorder(new EmptyBorder(-5, 0, -5, 0));
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(messageList);
        for(int i = 0; i<20; i++)
            messageList.add(new MessagePane("ese"));

        // set fixed panel
        fixed.setLayout(new BorderLayout());
        fixed.add(quit, BorderLayout.WEST);
        fixed.add(messageList, BorderLayout.EAST);
        fixed.add(scroll);

        // set talk panel
        JPanel talk = new JPanel();
        // set history panel
        JPanel history = new JPanel();
        history.setBackground(nord[0]);


//        history.setMinimumSize(new Dimension(500, 340));
        // set text panel
        JPanel text = new JPanel();
        text.setBackground(nord[2]);
        text.setPreferredSize(new Dimension(0, 160));
//        text.setMinimumSize(new Dimension(500, 160));
        // set talk panel
//        talk.setMinimumSize(new Dimension(500, 500));
        talk.setLayout(new BorderLayout());
        talk.add(history, BorderLayout.CENTER);
        talk.add(text, BorderLayout.SOUTH);

        // set window panel
        windowPanel.setLayout(new BorderLayout());
        windowPanel.add(fixed, BorderLayout.WEST);
        windowPanel.add(talk, BorderLayout.CENTER);


        // cancel margin
        windowPanel.setBorder(new EmptyBorder(-5, 0, -5, 0));


        // basic JFrame settings
        setLocationRelativeTo(null);
        this.pack();
        this.setTitle("Welcome, " + nickName);
        this.setBounds(350,170, 830, 500);
        this.setMinimumSize(new Dimension(830, 500));
        this.setVisible(true);
        this.setDefaultCloseOperation(new JFrame().EXIT_ON_CLOSE);

    }

    public JButton getMsgButton(){
        return this.msgButton;
    }
    public JButton getTerminate(){
        return this.terminate;
    }
    public String getMsg(){
        return "Msg "+this.msg.getText();
    }

    // test
    public JButton getShowMessage(){
        return this.showMessage;
    }
    public JButton getUpdateButton(){
        return this.updateButton;
    }
    public Stack<JButton> getMessageButtonList(){
        return this.messageButtonList;
    }


    public void showMessageList(Stack<String> userStack){
//        JPanel temp = new JPanel();temp.setPreferredSize(new Dimension(260, 500));
//        temp.setBackground(Color.orange);
//        // show auto-updating userList
//        FlowLayout flow = new FlowLayout(FlowLayout.LEADING, 0, -6);
//        temp.setLayout(flow);
//        temp.setBorder(new EmptyBorder(-5, 0, -5, 0));
//        JScrollPane scroll = new JScrollPane();
//        scroll.setViewportView(temp);
//        System.out.println("@@@@@@@@@@@");
//        for(String user : userStack){
//            MessagePane mp = new MessagePane(user);
//            temp.add(new JButton("dalksfjl"));
//            fixed.add(temp, BorderLayout.EAST);
//        }

    }


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
//        new ChatWindow("233");
        JFrame jf = new JFrame();
        jf.setBounds(200, 200, 500, 500);
        jf.setVisible(true);
        jf.setLayout(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel1 = new JPanel();
        panel1.setBounds(0, 0, 500, 500);
        panel1.setLayout(null);
        panel1.setVisible(true);

        JScrollPane jsp = new JScrollPane();
        jsp.setBounds(0, 0, 350, 350);

        JPanel panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(600, 600));
        panel2.setVisible(true);

        jsp.getViewport().add(panel2);
        jsp.validate();
        panel1.add(jsp);
        jf.add(panel1);

        jf.setVisible(true);
    }
}
