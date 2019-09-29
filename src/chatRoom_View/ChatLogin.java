package chatRoom_View;

import javax.swing.*;
import java.awt.*;

public class ChatLogin extends JFrame {

    private JButton login;
    private JTextField inputNickName;
    private JTextField warning;
    private boolean sign;


    public ChatLogin(){

        this.setTitle("Login");

        // instance filed
        JTextField heading = new JTextField("Anonymous Chat Room");
        heading.setEnabled(false);

        heading.setHorizontalAlignment(JTextField.CENTER);

        inputNickName = new JTextField("nickName");
        inputNickName.setHorizontalAlignment(JTextField.CENTER);

        warning = new JTextField(10);

        login = new JButton("login and Talk");

        this.setLayout(new FlowLayout());
        this.add(heading);
        this.add(inputNickName);
        this.add(login);
        this.add(warning);



        this.setBounds(570,250, 300, 400);
        this.setMaximumSize(this.getSize());
        this.setMinimumSize(this.getSize());
        this.setVisible(true);
        this.setDefaultCloseOperation(new JFrame().EXIT_ON_CLOSE);
    }

    public JButton getLoginButton(){
        return this.login;
    }
    public JTextField getWarning(){
        return this.warning;
    }

    public JTextField getInputNickName(){
        return this.inputNickName;
    }


}
