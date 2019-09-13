package chatRoom;

import java.util.HashMap;
import java.util.StringTokenizer;

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
    }
}
