package chatRoom_Model;

import javax.swing.*;
import java.awt.*;

public class testScroll extends JFrame {

    testScroll(){
        JPanel contain = new JPanel();
        this.setContentPane(contain);
        contain.setLayout(new BorderLayout());

        JPanel left = new JPanel();
        left.setBackground(Color.black);
        left.setPreferredSize(new Dimension(330,900));
        FlowLayout flow = new FlowLayout(FlowLayout.LEADING, 0, -6);
        left.setLayout(flow);
//        for(int i = 0; i < 80 ; i++){
//            JButton button = new JButton();
//            button.setPreferredSize(new Dimension(330, 100));
//
//            left.add(button);
//        }

        JPanel right = new JPanel();
        JPanel out = new JPanel();

        out.setBackground(Color.black);
        out.setPreferredSize(new Dimension(330,900));
        out.setLayout(new FlowLayout());
        out.add(left);

        contain.add(out, BorderLayout.CENTER);
        contain.add(right, BorderLayout.EAST);

        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(left);
        contain.add(scroll);

        // basic settings
        this.setBounds(350,170, 830, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(new JFrame().EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new testScroll();
    }
}
