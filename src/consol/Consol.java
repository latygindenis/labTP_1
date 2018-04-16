package consol;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public abstract class Consol{
    ConsolView consolView;
    String str;

    public Consol(int wHeight, int wWidth) {
       consolView = new ConsolView(wHeight, wWidth);
       consolView.consolArea.addKeyListener(keyAdapter);
       consolView.consolArea.setText(">> ");
       consolView.consolArea.setCaretPosition(3);
    }
    public abstract boolean command(String str);

    private KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    e.consume();
                    str = consolView.consolArea.getText();
                    if (!command( str.substring(str.lastIndexOf('>') + 2, str.length()))){
                        consolView.consolArea.append("\nНет такой команды");
                    }
                    consolView.consolArea.append("\n");
                    consolView.consolArea.append(">> ");
                    break;
            }
        }
    };

    public static void main(String[] args) {
        Consol consol = new Consol(300, 300) {
            @Override
            public boolean command(String str) {
                return false;
            }
        };
    }
}
