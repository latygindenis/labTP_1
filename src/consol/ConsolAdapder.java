package consol;

import java.awt.*;
import java.awt.event.WindowAdapter;

public class ConsolAdapder extends Consol{
    public ConsolAdapder(int wHeight, int wWidth) {
        super(wHeight, wWidth);
    }
    public void printToConsole(String str){
        super.consolView.consolArea.append('\n' + str );
    }

    @Override
    public boolean command(String str) {
        if (str.equals("exit")){
            super.consolView.setVisible(false);
            super.consolView.dispose();
            return true;
        }
       return false;
    }

}
