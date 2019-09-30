package chatRoom_View;

import chatRoom_Model.FastGridBag;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ChatWindow extends JFrame{
//    private ObjectOutputStream oos;
//    private ObjectInputStream ois;

    private JPanel messageList;
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


    public ChatWindow(String nickName){
        // instance field

        msg = new JTextField("Input here", 10);

        msgButton = new JButton("sendMsg");
        terminate = new JButton();
        showMessage = new JButton(("showMessage"));
        updateButton = new JButton("update");

        // panels
        JPanel windowPanel = (JPanel) this.getContentPane();
        this.setLayout(new BorderLayout());
        JPanel additionalPanel = new JPanel();
        JPanel onlinePanel = new JPanel();
        JPanel chatPanel = new JPanel();
        JPanel sendPaenl = new JPanel();

        // Background settings
        additionalPanel.setBackground(new Color(48,55,87));
        onlinePanel.setBackground(new Color(29,35,65));
        chatPanel.setBackground(new Color(48,55,87));
        sendPaenl.setBackground(new Color(109,122,145));

        // Layout settings
        GridBagConstraints gbc = new GridBagConstraints();
        windowPanel.setLayout(new GridBagLayout());
        additionalPanel.setLayout(new GridBagLayout());
        JPanel additionalPanelTempA = new JPanel();
        JPanel additionalPanelTempB = new JPanel();
        additionalPanelTempA.setBackground(new Color(48, 55, 87));
        additionalPanelTempB.setBackground(new Color(48, 55, 87));

        terminate.setBackground(new Color(48, 55, 87));
//        terminate.setOpaque(true);
//        terminate.setBorderPainted(false);
//        ImageIcon exit = new ImageIcon("exit.png");
//        terminate.setSize(25,25);
//        Image temp = exit.getImage().getScaledInstance(terminate.getWidth(), terminate.getHeight(),
//                exit.getImage().SCALE_DEFAULT);
//
//        exit = new ImageIcon(temp);
//        terminate.setIcon(exit);
//        terminate.setToolTipText("exit");
        additionalPanel.add(additionalPanelTempA, fastGridBag(gbc, 0, 0, 1, 1, 1, 0.95, 0));
        additionalPanel.add(additionalPanelTempB, fastGridBag(gbc, 0, 1, 1, 1, 1, 0.05,15));
        additionalPanelTempB.add(terminate);

//        additionalPanel.add(terminate, BorderLayout.SOUTH);
//        terminate.setSize(5,5);
        windowPanel.add(additionalPanel, fastGridBag(gbc, 0, 0, 1, 2, 0.1, 1, 1));
        additionalPanel.setMaximumSize(new Dimension(70,900));
        additionalPanel.setMaximumSize(new Dimension(70,495));
        windowPanel.add(onlinePanel, fastGridBag(gbc, 1, 0, 2, 2, 0.3, 1, 1));
        windowPanel.add(chatPanel, fastGridBag(gbc, 3, 0, 5, 1, 0.6, 0.9, 1));
        windowPanel.add(sendPaenl, fastGridBag(gbc, 3, 1, 5, 1, 0.6, 0.1, 1));

        windowPanel.setBorder(new EmptyBorder(-5, 0, -5, 0));
        // panel
//        windowPanel.add(msg);
//        windowPanel.add(msgButton);
//        windowPanel.add(terminate);
//        windowPanel.add(showMessage);
//        windowPanel.add(updateButton);


        // basic JFrame settings
        this.setTitle("Welcome, " + nickName);
        this.setBounds(350,170, 800, 560);
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

    private GridBagConstraints fastGridBag(GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight,
                        double weightx, double weighty, int fill){
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.fill = fill;

        return gbc;
    }

    public static void main(String[] args) {
        new ChatWindow("233");
    }
}
