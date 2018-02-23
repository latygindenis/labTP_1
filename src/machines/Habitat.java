package machines;

import javax.swing.*; // графичейский интерфейс
import java.awt.*;
import java.awt.event.*; // для событий
import java.util.ArrayList; // для списка
import java.util.Timer;
import java.util.TimerTask;

public class Habitat extends JFrame implements  KeyListener{ // обработка событий клавиатуры

    private double timeP1 = 0.5;
    private double timeP2 = 0.5;
    private long time = 0;
    private int timeN1 = 1;
    private int timeN2 = 1;

    ArrayList<Machine> list;

    private void update(long t){
        if (t%timeN1==0){
            if(timeP1>(float)Math.random()){
                varG++;

                Machine rb = new MachineG();
                rb.paint(getGraphics());
                list.add(rb);
            }
        }

        if(t%timeN2==0){
            if(timeP2>(float)Math.random()){
                varL++;
                Machine rb = new MachineL();
                rb.paint(getGraphics());
                list.add(rb);
            }

        }
    }

    public int height;
    public int length;
    public int posX;
    public int posY;

    public Habitat(){
        this.length=500;
        this.height=500;
        this.posX=100;
        this.posY=100;
    }

    public Habitat(int length, int height, int posX, int posY){
        this.length=length;
        this.height=height;
        this.posX=posX;
        this.posY=posY;
    }

    private JLabel mes = null;
    private JPanel panel = null;
    private boolean mas = true;
    private Timer timer;
    private boolean begin = false;

    public  void pan(){
        list = new ArrayList<Machine>();
        panel = new JPanel();

        add(panel);

        mes = new JLabel(" ", JLabel.CENTER);
        panel.add(mes);

        setBounds(posX,posY,length,height);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this); // Обработка клавиатуры

        timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run(){
                if (begin){
                    time++;
                    update(time);
                }
            }
        } , 0, 1000);
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }

    private int varG = 0;
    private int varL = 0;
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_B:
                begin = true;
                varG=0;
                varL=0;
                time = 0;
                break;
            case KeyEvent.VK_E:
                if (begin!=false) {

                    mes.setText("Количество: " + (varL+varG) + " " + "Легковые: " + (varL) + "\n" + "Грузовые: " + varG + "\n" + "Время: " + time);
                    list.clear();
                    repaint();
                    varG=0;
                    varL=0;
                    time = 0;

                } begin = false;
                break;
            case KeyEvent.VK_T:
                if (!mas){
                    mes.setText("Количество: " + (varL+varG) + " " + "Легковые: " + (varL) + "\n" + "Грузовые: " + varG + "\n" + "Время: " + time);
                } else {
                    mes.setText(" ");
                }
                mas = !mas;

                break;
        }
    }
}
