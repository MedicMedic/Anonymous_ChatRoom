package chatRoom_Executive;

import chatRoom_Model.MessageMap;
import chatRoom_Model.MessagePane;

import java.util.Stack;

public class test {
    public static void main(String[] args) {
//        Stack<String> test = new Stack<>();
//        test.push("233");
//        test.push("asdfkjas");
//        for(String ad:test)
//            System.out.println(ad);
//        new ChatClient().start();

////        String test = "233";
//        HashMap<String, String> test = new HashMap<>();
//        System.out.println(test.toString());
//
//        String dudu = "Msg Medic Mao daidai";
//        StringTokenizer st = new StringTokenizer(dudu, " ");
//        System.out.println(st.nextElement());
//        String target = (String)st.nextElement();
//        System.out.println(dudu.substring(5+target.length()));
//        String a = "233";
//        System.out.println(a.equals("233"));
//
////        Timer timer = new Timer();
////        timer.schedule(new TimerTask() {
////            @Override
////            public void run() {
////                System.out.println("test");
////            }
////
////        System.out.println("fail"); System.out.println("fail"); System.out.println("fail"); System.out.println("fail");
//        HashMap<String, Stack<String>> list = new HashMap<>();
//        list.put("du", new Stack<String>());
//        list.get("du").push("sadafd");
//        System.out.println(list.get("du").peek());
//        new Thread(new ChatClient()).start();


        MessageMap test = new MessageMap();
        test.put("2", new Stack<>());
        test.get("2").push("adlskf");
        test.get("2").push("23432424");
        test.get("2").push("e23flkcvx");

        de(test.get("2"));
        System.out.println("The size is" + test.get("2").size());
    }
    public static void de(Stack<String> stack){
        while(!stack.isEmpty()){
            System.out.println(stack.pop());
        }
    }
}
