package chatRoom_View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ChatLogin extends JFrame {

    private JButton login;
    private JTextField inputNickName;
    private JLabel warning;
    private boolean sign;
    private JTextField group;

    public ChatLogin(){
        Color[] nord = new Color[4];
        nord[0] = new Color(48,55,87);
        nord[1] = new Color(29, 35, 65);
        nord[2] = new Color(109, 122, 145);
        nord[3] = new Color(193, 206, 218);

        this.setLayout(new BorderLayout());
        this.setBackground(nord[0]);
        this.setTitle("Login");

        // instance filed -- North
        JTextField heading = new JTextField("Anonymous Chat Room");
        heading.setPreferredSize(new Dimension(100, 50));
        heading.setEditable(false);
        heading.setBackground(nord[0]);
        heading.setForeground(nord[3]);
        heading.setOpaque(true);
        heading.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, nord[2]));
        heading.setHorizontalAlignment(JTextField.CENTER);


        // Center
        Box loginBox = Box.createVerticalBox();
        loginBox.setBackground(nord[0]);
        loginBox.setOpaque(true);
//        loginBox.add(Box.createHorizontalGlue());
        inputNickName = new JTextField();
//        inputNickName.setPreferredSize(new Dimension(100,20));
        inputNickName.setHorizontalAlignment(JTextField.CENTER);
        inputNickName.setBackground(nord[0]);
        inputNickName.setBorder(null);
        inputNickName.setBorder(BorderFactory.createTitledBorder("Please Input Nickname"));
        loginBox.add(inputNickName);
        group = new JTextField();
        group.setHorizontalAlignment(JTextField.CENTER);
        group.setBackground(nord[0]);
        group.setBorder(BorderFactory.createTitledBorder("Group Number"));
//        group.setColumns(4);
        group.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                if(group.getText().length() > 3) {
                    getToolkit().beep();
                    e.consume();
                }
                int keyChar=e.getKeyChar();
                if (keyChar < KeyEvent.VK_0 || keyChar > KeyEvent.VK_9) {
                        e.consume();
                }
            }

            public void keyPressed(KeyEvent e){
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        loginBox.add(group);
        loginBox.add(Box.createVerticalStrut(50));

        Box buttonBox = Box.createHorizontalBox();
        login = new JButton("login and Talk");
        login.setBackground(nord[2]);
        login.setOpaque(true);
        login.setBorderPainted(false);
        buttonBox.add(login);
        loginBox.add(buttonBox);

        // South
        warning = new JLabel();
        warning.setHorizontalAlignment(JTextField.CENTER);
        warning.setBackground(nord[0]);
        warning.setOpaque(true);
        warning.setForeground(nord[3]);

        this.add(heading, BorderLayout.NORTH);
        this.add(loginBox, BorderLayout.CENTER);
        this.add(warning, BorderLayout.SOUTH);


        this.setBounds(570,250, 300, 400);
        this.setMaximumSize(this.getSize());
        this.setMinimumSize(this.getSize());
        this.setVisible(true);
        this.setDefaultCloseOperation(new JFrame().EXIT_ON_CLOSE);
    }

    public JButton getLoginButton(){
        return this.login;
    }
    public JLabel getWarning(){
        return this.warning;
    }

    public JTextField getInputNickName(){
        return this.inputNickName;
    }

    public JTextField getGroup(){
        return this.group;
    }

    public static void main(String[] args) {
        new ChatLogin();
    }

}
