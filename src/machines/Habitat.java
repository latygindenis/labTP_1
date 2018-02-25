package machines;

import javax.swing.*; // графичейский интерфейс
import java.awt.*;
import java.awt.event.*; // для событий
import java.util.ArrayList; // для списка
import java.util.Timer;
import java.util.TimerTask;

public class Habitat extends JFrame{ // обработка событий клавиатуры

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
    private JPanel mainPanel = null;
    private JPanel panelGen = null;
    private JButton startButton = null;
    private JButton endButton = null;
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
                Car rb = new CarHeavy(10 + (int)(Math.random() * 400),10 + (int)(Math.random() * 400));
                rb.paint(getGraphics());
                list.add(rb);
            }
        }

        if(t%timeLight==0){ //Каждые timeLight секунд
            if(pLight>(float)Math.random()){ //Если прошло по вероятности
                amountOfL++;
                Car rb = new CarLight((int)(Math.random() * 400), (int)(Math.random() * 400));
                rb.paint(getGraphics());
                list.add(rb);
            }
        }
    }

    public void startSimulation(){
        System.out.print(1);
        begin = true;
        amountOfG=0;
        amountOfL =0;
        time = 0;
        mes.setText("");
    }
    public void endSimulation(){
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
    }

    public void drawUI(){
        setLayout(null);
        list = new ArrayList<Car>();
        mainPanel = new JPanel();
        startButton = new JButton("start");
        endButton = new JButton("end");

        startButton.setFocusable(false);
        endButton.setFocusable(false);
        startButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                startSimulation();
            }
        });

        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endSimulation();
            }
        });


        mainPanel.add(startButton);
        mainPanel.add(endButton);
        panelGen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) { //Необходимо при перерисовки интерфейса
                super.paintComponent(g);
                g.drawRect(10, 10, 500, 500);
                for(Car car:list){
                    car.paint(g);
                }
            }
        };
        panelGen.setFocusable(true); //Разрешить обработку клавиш
        panelGen.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_B:
                        startSimulation();
                        break;
                    case KeyEvent.VK_E:
                        endSimulation();
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

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        panelGen.setBounds(10, 10, 520, 520);
        mainPanel.setBounds(520, 10, 100, 500);
        mes = new JLabel("", JLabel.RIGHT);
        panelGen.add(mes);
        add(panelGen);
        add(mainPanel);


//              startButton = new JButton("Start");
//        mainPanel.add(startButton);
//
//        add(mainPanel);
        setBounds(wPosX, wPosY, wLength, wHeight);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
}
