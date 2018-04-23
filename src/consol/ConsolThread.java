package consol;

import presentation.HabitatView;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;

public class ConsolThread extends Thread {
    private DataInputStream readFromConsol;
    private ConsolView consolView;
    private Consol consol;

    public ConsolThread(ConsolView consolView, DataInputStream readFromConsol, Consol consol) {
        this.readFromConsol = readFromConsol;
        this.consolView = consolView;
        this.consol = consol;
    }

    public void run() {
        while (!isInterrupted()) {
            try {
                String cmd = this.readFromConsol.readUTF().trim();
                consol.command(cmd);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
