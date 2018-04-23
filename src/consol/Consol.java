package consol;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;

public class Consol {
    public ConsolView consolView;
    ConsolThread consolThread;
    DataOutputStream sendToConsol;
    PipedOutputStream _sendToConsol = new PipedOutputStream();//Создали поток


    public Consol(int wHeight, int wWidth) {
        consolView = new ConsolView(wHeight, wWidth);
        this.sendToConsol = new DataOutputStream(_sendToConsol);
        try {
            this.consolThread = new ConsolThread(consolView, new DataInputStream(new PipedInputStream(_sendToConsol)), this);
            this.consolThread.start();

        }catch (IOException ex){
            ex.printStackTrace();
        }
        consolView.consolArea.addKeyListener(keyAdapter);
        consolView.consolArea.setText(">> ");
        consolView.consolArea.setCaretPosition(3);
    }



    private KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    try {
                        int offset = consolView.consolArea.getLineOfOffset(consolView.consolArea.getCaretPosition());
                        int start = consolView.consolArea.getLineStartOffset(offset) + 3;
                        int end = consolView.consolArea.getLineEndOffset(offset);
                        String currStr = consolView.consolArea.getText(start, (end - start));
                        sendToConsol.writeUTF(currStr); // отправка текста в трубу
                    } catch (BadLocationException | IOException e1) {
                        e1.printStackTrace();
                    }
            }
        }
    };

    public static void main(String[] args) {

    }
    public String result = null;
    public void command(String cmd){
        {
            SwingUtilities.invokeLater(new Runnable() { // поток работающий с gui напрямую для вывода результата
                @Override
                public void run() {
                    consolView.consolArea.append("$ " + (result == null ? "Неизвестная команда" : result) + "\n>> ");
                }
            });
        }
    }
}
