package consol;

import javax.swing.*;
import java.awt.*;

public class ConsolView extends JFrame {
    int wHeight;
    int wWindth;
    JTextArea consolArea;

    public ConsolView(int wHeight, int wWidth) {
        this.wHeight = wHeight;
        this.wWindth = wWidth;
        drawConsol();
    }

    public void drawConsol(){
        setBounds(100, 100, wWindth, wHeight);
        consolArea = new JTextArea();
        consolArea.setBounds(0, 0, wWindth, wHeight);
        add(consolArea);
        setVisible(true);
    }
}
