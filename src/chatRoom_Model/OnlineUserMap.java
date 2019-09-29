package chatRoom_Model;

import java.io.Serializable;
import java.util.HashMap;

public class OnlineUserMap<String, MessageMap> extends HashMap implements Serializable {

    private static final long serialVersionUID = 7895454744061267073L;

   class MessageMap<String, Stack> extends HashMap implements Serializable{

       private static final long serialVersionUID = 715339733825100493L;
   }
}
