package consol;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ConsolController {
    ConsolView consolView;
    ConsolModel consolModel;

    public ConsolController(ConsolView consolView, ConsolModel consolModel){
        this.consolView = consolView;
        this.consolModel = consolModel;
    }


    private KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            consolModel.command();
        }
    };

}