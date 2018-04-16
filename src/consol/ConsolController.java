package consol;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ConsolController {
    ConsolView consolView;
    ConsolModel consolModel;
    String str;

    public ConsolController(ConsolView consolView, ConsolModel consolModel){
        this.consolView = consolView;
        this.consolModel = consolModel;
        consolView.consolArea.addKeyListener(keyAdapter);
        consolView.consolArea.setText(">> ");
        consolView.consolArea.setCaretPosition(3);
    }


    private KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                        e.consume();
                        str = consolView.consolArea.getText().toString();
                        if (!consolModel.command( str.substring(str.lastIndexOf('>') + 2, str.length()))){
                            System.out.println("Нет такой команды");
                        }
                        consolView.consolArea.append("\n");
                        consolView.consolArea.append(">> ");
                        break;
            }
        }
    };



}