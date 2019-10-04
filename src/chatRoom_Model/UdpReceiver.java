package chatRoom_Model;

import chatRoom_View.ChatWindow;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Timer;

public class UdpReceiver extends Thread{
    private ChatWindow chatWindow;
    private ChatClient chatClient;
    private Timer autoUpdate;
    private DatagramPacket datagramPacket;
    private int your_Port;
    private DatagramSocket udpSocket;
    private ObjectOutputStream oos;
    public UdpReceiver(ChatWindow chatWindow, ChatClient chatClient,
                       Timer autoUpdate, int your_Port, ObjectOutputStream oos){

        this.chatWindow = chatWindow;
        this.chatClient = chatClient;
        this.autoUpdate = autoUpdate;
        this.your_Port = your_Port;
        this.oos = oos;
    }
    public void run(){
        try {
            udpSocket = new DatagramSocket(your_Port);
            byte[] buf = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
            udpSocket.receive(datagramPacket);
            JFrame alter = new JFrame("WARNING");
            alter.setLayout(new GridLayout(1, 1));
            JLabel warning = new JLabel("Server will be terminated after 5 seconds, you will be forced to quit!");
            warning.setHorizontalAlignment(JTextField.CENTER);
            warning.setBackground(new Color(29, 35, 65));
            warning.setForeground(new Color(193, 206, 218));
            warning.setOpaque(true);
            alter.add(warning);
            alter.setBounds(this.chatWindow.getLocation().x + 100,this.chatWindow.getLocation().y + 100, 450, 50);
            alter.setVisible(true);
            alter.setDefaultCloseOperation(new JFrame().EXIT_ON_CLOSE);

            oos.writeObject("terminate");
            sleep(5000);
            this.chatWindow.dispose();
            this.autoUpdate.cancel();
            this.chatClient.isStopped = true;
            udpSocket.close();
        } catch (IOException | InterruptedException e) {
            System.out.println("Exception occurs :" + e);
        }
    }
    public DatagramSocket getUdpSocket(){
        return this.udpSocket;
    }
}
