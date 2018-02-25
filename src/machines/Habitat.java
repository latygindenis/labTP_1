package machines;

import javax.swing.*; // графичейский интерфейс
import java.awt.*;
import java.awt.event.*; // для событий
import java.util.ArrayList; // для списка
import java.util.Timer;
import java.util.TimerTask;

public class Habitat extends JFrame implements KeyListener{ // обработка событий клавиатуры

    private double pHeavy; //Вероятность появления CarHeavy
    private double pLight; //Вероятность появления CarLight
    private long time = 0;
    private int timeHeavy; //Период появления CarHeavy
    private int timeLight; //Период появления CarLight


    private int wHeight;

    private int wLength;
    private int wPosX;
    private int wPosY;

    private JLabel mes = null;
    private JPanel panel = null;
    private boolean mas = true;
    private Timer timer;
    private boolean begin = false;

    private int amountOfG = 0;
    private int amountOfL = 0;

    ArrayList<Car> list;


    public Habitat(){
        this.wLength = 500;
        this.wHeight = 500;
        this.wPosX = 100;
        this.wPosY = 100;
    }

    Habitat(int wLength, int wHeight, int wPosX, int wPosY){
        this.wLength = wLength;
        this.wHeight = wHeight;
        this.wPosX = wPosX;
        this.wPosY = wPosY;
        pHeavy = 0.5;
        pLight = 0.5;
        timeHeavy = 1;
        timeLight = 1;
    }


    private void update(long t){
        if (t%timeHeavy==0){ //Каждые timeHeavy секунд
            if(pHeavy >(float)Math.random()){ // Если прошло по вероятности
                amountOfG++;
                Car rb = new CarHeavy();
                rb.paint(getGraphics());
                list.add(rb);
            }
        }

        if(t%timeLight==0){ //Каждые timeLight секунд
            if(pLight>(float)Math.random()){ //Если прошло по вероятности
                amountOfL++;
                Car rb = new CarLight();
                rb.paint(getGraphics());
                list.add(rb);
            }
        }
    }


    public void drawUI(){
        list = new ArrayList<Car>();
        panel = new JPanel();
        add(panel);
        mes = new JLabel("", JLabel.RIGHT);
        panel.add(mes);

        setBounds(wPosX, wPosY, wLength, wHeight);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this); // Обработка клавиатуры
        timer = new Timer(); //Создание таймера
        timer.schedule(new TimerTask(){ //Добавление задания в таймер
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

    public void keyPressed(KeyEvent e) { //Обработкчик нажатия клавиши
        switch (e.getKeyCode()) {
            case KeyEvent.VK_B:
                begin = true;
                amountOfG=0;
                amountOfL =0;
                time = 0;
                mes.setText("");
                break;
            case KeyEvent.VK_E:
                if (begin) {
                    mes.setText("<html> <br>" +
                                "Количество: " +(amountOfL + amountOfG) + "<br>" +
                                "Легковые: " + amountOfL + "<br>" +
                                "Грузовые: " + amountOfG +"<br>"+
                                "Время: " + time + "<br></html>");
                    list.clear();
                    repaint(); //"Очистка" интерфейса
                    amountOfG=0;
                    amountOfL =0;
                    time = 0;
                }

                begin = false;
                break;
            case KeyEvent.VK_T:
                if (!mas){
                    mes.setText("<html> <br>" +
                                "Количество: " +(amountOfL + amountOfG) + "<br>" +
                                "Легковые: " + amountOfL + "<br>" +
                                "Грузовые: " + amountOfG +"<br>"+
                                "Время: " + time + "<br></html>");
                } else {
                    mes.setText("");
                }
                mas = !mas;
                break;
        }
    }
}
