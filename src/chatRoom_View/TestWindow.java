package chatRoom_View;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.concurrent.Flow;

public class TestWindow extends JFrame {
    TestWindow(){
        this.setLayout(new BorderLayout());
        JPanel a = new JPanel();a.setPreferredSize(new Dimension(330,0));a.setBackground(Color.black);
        JPanel a1 = new JPanel();a1.setPreferredSize(new Dimension(70,0));a.setBackground(Color.black);
        a1.setLayout(new BorderLayout());
        JButton test = new JButton();
        test.setOpaque(true);
        test.setBorderPainted(false);
        ImageIcon exit = new ImageIcon("exit.png");
        test.setSize(25,25);
        Image temp = exit.getImage().getScaledInstance(test.getWidth(), test.getHeight(),
                exit.getImage().SCALE_DEFAULT);
        exit = new ImageIcon(temp);
        test.setIcon(exit);

        a1.add(new JPanel(), BorderLayout.NORTH);
        a1.add(test, BorderLayout.SOUTH);


        JPanel a2 = new JPanel();a2.setPreferredSize(new Dimension(260, 500));a2.setBackground(Color.LIGHT_GRAY);
//        Box box = Box.createVerticalBox();
//        box.setPreferredSize(new Dimension(260, 500));
        JButton [] bb = new JButton[100];


        for(JButton button : bb) {
            button = new JButton();
//            button.setBorderPainted(false);
            JPanel tP = new JPanel();
//            button.setPreferredSize(new Dimension(260, 63));
            tP.setPreferredSize(new Dimension(260, 63));
            tP.setBackground(Color.LIGHT_GRAY);
            button.setBorder(BorderFactory.createLineBorder(Color.red));
            tP.setLayout(new GridLayout(1,1));
//            tP.setBorder(new EmptyBorder(-5, 0, -5, 0));
            tP.add(button);
            a2.add(tP);
        }

        JScrollPane sp =new JScrollPane(a2);
        sp.setBounds(320, 0, 10, 100);
//        sp.setHorizontalScrollBarPolicy (JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        sp.setVerticalScrollBarPolicy (JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        a.add(sp);

        FlowLayout flow = new FlowLayout(FlowLayout.LEADING, 0, -6);
        a2.setLayout(flow);
//        a2.add(box, Box.createVerticalGlue());
//        box.setBorder(new EmptyBorder(-5, 0, -5, 0));

        a2.setBorder(new EmptyBorder(-5, 0, -5, 0));

        a.setLayout(new BorderLayout());
        a.add(a1, BorderLayout.WEST);
        a.add(a2, BorderLayout.EAST);
        JPanel b = new JPanel();b.setPreferredSize(new Dimension(500,0));

        this.add(a, BorderLayout.WEST);
        b.setLayout(new BorderLayout());
        JPanel b1 = new JPanel();b1.setBackground(Color.orange);
        JPanel b2 = new JPanel();b2.setBackground(Color.CYAN);
        b.add(b1, BorderLayout.CENTER);
        b2.setPreferredSize(new Dimension(0, 160));
        b.add(b2, BorderLayout.SOUTH);
        this.add(b, BorderLayout.CENTER);

        this.setTitle("Welcome, " );
        this.pack();
        this.setBounds(350,170, 830, 500);
        this.setMinimumSize(new Dimension(830, 500));
        this.setVisible(true);
        this.setDefaultCloseOperation(new JFrame().EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new TestWindow();
        JFrame test = new JFrame();

//        Container c = test.getContentPane();
//        JPanel jp = new JPanel();
////        JTextArea ta = new JTextArea(20,50);
//        JPanel tp = new JPanel();
//        tp.setSize(20,50);
//        ScrollPane sp = new ScrollPane();
//        c.add(jp);
//        jp.add(sp);
//        sp.add(tp);
//        sp.setSize(300,180);
//        test.setBounds(100,100,300,200);
//
//        test.setVisible(true);
//        test.setDefaultCloseOperation(new JFrame().EXIT_ON_CLOSE);
//        test.setLayout(null);
//        JPanel jp=new JPanel();
//        jp.setPreferredSize(new Dimension(200,100));
//        JScrollPane sp=new JScrollPane(jp);
//        test.setBounds(100,100,300,200);
//        sp.setBounds(10,10,175,70);
//        test.getContentPane().add(sp);

//        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

//        test.getContentPane().add(sp);
//        test.setVisible(true);
//        test.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
