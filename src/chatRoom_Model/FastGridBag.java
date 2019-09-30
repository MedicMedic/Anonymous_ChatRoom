package chatRoom_Model;

import javax.swing.*;
import java.awt.*;

public class FastGridBag extends GridBagConstraints {

   public FastGridBag(GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight,
                      double weightx, double weighty){
           gbc.gridx = gridx;
           gbc.gridy = gridy;
           gbc.gridwidth = gridwidth;
           gbc.gridheight = gridheight;
           gbc.weightx = weightx;
           gbc.weighty = weighty;
   }


}
