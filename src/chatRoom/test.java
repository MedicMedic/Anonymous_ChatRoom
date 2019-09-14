package chatRoom;

import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

public class test {
    public static void main(String[] args){
//        String test = "233";
        HashMap<String, String> test = new HashMap<>();
        System.out.println(test.toString());

        String dudu = "Msg Medic Mao daidai";
        StringTokenizer st = new StringTokenizer(dudu, " ");
        System.out.println(st.nextElement());
        String target = (String)st.nextElement();
        System.out.println(dudu.substring(5+target.length()));
        String a = "233";
        System.out.println(a.equals("233"));

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("test");
            }
        }, 1000, 2000);

        System.out.println("fail"); System.out.println("fail"); System.out.println("fail"); System.out.println("fail");

    }
}
